package com.jarnevdp.SipWatch.ui.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jarnevdp.SipWatch.R
import com.jarnevdp.SipWatch.checkFile
import com.jarnevdp.SipWatch.databinding.FragmentHomeBinding
import com.jarnevdp.SipWatch.readFromFile
import com.jarnevdp.SipWatch.ui.agenda.PrgrsAdapter
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null


    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val file = "drinkStorage.txt"
        //check if file exists
        //get the today's date
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm")
        val currentDate = sdf.format(Date())
        val date = currentDate.substring(0,10)
        context?.let { checkFile(it, date+file) }

        val GreetingText: TextView = binding.greeting

        // get the hour of the day
        val hour = currentDate.substring(11, 13).toInt()
        // set the greeting based on the hour
        if (hour in 0..11) {
            GreetingText.text = "Goeiemorgen!"
        } else if (hour in 12..17) {
            GreetingText.text = "Middag!"
        } else if (hour in 18..22) {
            GreetingText.text = "Goeienavond!"
        } else {
            GreetingText.text = "See ya!"
        }

        val recyclerview_prgrs = binding.rcvProgressbar
        val totalDrankPerType =  calculateTotalDrankPerTypePerDay()

        val rcvAdapter = context?.let { PrgrsAdapter(it, totalDrankPerType) }
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerview_prgrs.layoutManager = linearLayoutManager
        recyclerview_prgrs.adapter = rcvAdapter


        //https://developer.android.com/develop/ui/views/notifications/build-notification#kts
        val CHANNEL_ID = "channelId"
        val notificationId = 1

        val builder = NotificationCompat.Builder(this.requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle("Drink water!")
            .setContentText("Vergeet niet om water te drinken!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        //Send a notification every 3 hours starting from 12:00
        if (hour in 12..24 && hour % 17 == 0 && totalDrankPerType["Water"] != null && totalDrankPerType["Water"]!! < 150) {
            with(NotificationManagerCompat.from(this.requireContext())) {
                // notificationId is a unique int for each notification that you must define
                notify(notificationId, builder.build())
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //create a function to calculate the total amount drank per type per day
    fun calculateTotalDrankPerTypePerDay(): MutableMap<String, Int> {
        //get the date of today
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())

        //create a dictionary to store the total amount drank per type
        val totalDrankPerType = mutableMapOf<String, Int>()
        //read the text file
        val file = "drinkStorage.txt"
        val stringOfInput = context?.let { it1 -> readFromFile(it1, currentDate+file) }
        val linesplit = stringOfInput?.split("\n")
        if (linesplit != null) {
            for (line in linesplit) {
                if (line.isNotBlank()) {
                    val splitted = line.split(";")
                    val splittedamount = splitted[4].split("cl")
                    if (splitted[0] == currentDate) {
                        //if the type is already in the dictionary, add the amount to the total amount
                        if (totalDrankPerType.containsKey(splitted[3])) {
                            totalDrankPerType[splitted[3]] =
                                totalDrankPerType[splitted[3]]!! + splittedamount[0].toInt()
                        }
                        //if the type is not in the dictionary, add the type and the amount to the dictionary
                        else {
                            totalDrankPerType[splitted[3]] = splittedamount[0].toInt()
                        }
                    }
                }
            }
        }
        return totalDrankPerType
    }
}
