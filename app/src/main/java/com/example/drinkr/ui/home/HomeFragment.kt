package com.example.drinkr.ui.home

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drinkr.checkFile
import com.example.drinkr.databinding.FragmentHomeBinding
import com.example.drinkr.readFromFile
import com.example.drinkr.ui.agenda.PrgrsAdapter
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val file = "drinkStorage.txt"
        //chcek if file exists
        context?.let { checkFile(it, file) }

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val GreetingText: TextView = binding.greeting
        // get the date
        //get the today's date
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val currentDate = sdf.format(Date())
        // get the hour of the day
        val hour = currentDate.substring(11, 13).toInt()
        // set the greeting based on the hour
        if (hour in 0..11) {
            GreetingText.text = "Goedemorgen!"
        } else if (hour in 12..17) {
            GreetingText.text = "Middag!"
        } else if (hour in 18..22) {
            GreetingText.text = "Goeienavond !"
        } else {
            GreetingText.text = "See ya!"
        }

        //TODO: optellen hoeveel gedronken per soort. bij water zetten hoeveel nog te drinken en achohol ...
        //val progressBar:ProgressBar = binding.progressBar
        val recyclerview_prgrs = binding.rcvProgressbar
        val totalDrankPerType =  calculateTotalDrankPerTypePerDay()

        val rcvAdapter = context?.let { PrgrsAdapter(it, totalDrankPerType) }
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerview_prgrs.layoutManager = linearLayoutManager
        recyclerview_prgrs.adapter = rcvAdapter


        //TODO: linearlayout updaten bij verwijderen van line
        //TODO: logo toevoegen en evt example uit naam halen

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //create a function to calculate the total amount drank per type per day
    fun calculateTotalDrankPerTypePerDay(): MutableMap<String, Int> {
        //get the date of today
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val currentDate = sdf.format(Date())

        //create a dictionary to store the total amount drank per type
        val totalDrankPerType = mutableMapOf<String, Int>()
        //reac the text file
        val file = "drinkStorage.txt"
        val stringOfInput = context?.let { it1 -> readFromFile(it1, file) }
        val linesplit = stringOfInput?.split("\n")
        if (linesplit != null) {
            for (line in linesplit) {
                if (line.isNotBlank()) {
                    val splitted = line.split(";")
                    val splittedamount = splitted[3].split("cl")
                    if (splitted[0] == currentDate) {
                        //if the type is already in the dictionary, add the amount to the total amount
                        if (totalDrankPerType.containsKey(splitted[4])) {
                            totalDrankPerType[splitted[4]] =
                                totalDrankPerType[splitted[4]]!! + splittedamount[0].toInt()
                        }
                        //if the type is not in the dictionary, add the type and the amount to the dictionary
                        else {
                            totalDrankPerType[splitted[4]] = splittedamount[0].toInt()
                        }
                    }
                }
            }
        }
        return totalDrankPerType
    }
}
