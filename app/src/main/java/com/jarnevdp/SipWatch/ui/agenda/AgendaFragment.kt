package com.jarnevdp.SipWatch.ui.agenda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jarnevdp.SipWatch.checkFile
import com.jarnevdp.SipWatch.databinding.FragmentAgendaBinding
import com.jarnevdp.SipWatch.readFromFile

class AgendaFragment : Fragment() {
    private var _binding: FragmentAgendaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAgendaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val dates = binding.textCalendar
        val calendarView = binding.calendarView
        dates.text = "Geen datum geselecteerd"

        val file = "drinkStorage.txt"

        //Initiate the recycler view
        val recyclerView = binding.rcv
        val drinkModelArrayList: ArrayList<DrinkModel> = ArrayList()

        val courseAdapter = context?.let { DrinkAdapter(it, drinkModelArrayList) }
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        //if the date gets changed from the calenderView, fill in the recycler view with the drinks of that date
        calendarView.setOnDateChangeListener{ view, year, month, dayOfMonth ->
            //make dayofmonth leading zero if it is less than 10
            val day = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
            val d8 = (day + "-" + (month + 1) + "-" + year) //get
            dates.text = d8
            //check if file exists
            context?.let { checkFile(it, d8+file) }

            //clear drinkModelArrayList
            drinkModelArrayList.clear()
            val stringOfInput = context?.let { it1 -> readFromFile(it1, d8+file) }
            val linesplit = stringOfInput?.split("\n")
            if (linesplit != null) {
                for (line in linesplit){
                    val drink = line.split(";")
                    if (drink.size == 5){
                        //compare the date of the drink with the date selected in the calendar
                        if (drink[0].substring(0, 2) == day && drink[0].substring(3, 5) == (month + 1).toString() && drink[0].substring(6, 10) == year.toString()){
                            //add the drink to the recycler view
                            drinkModelArrayList.add(
                                DrinkModel(
                                    drink[0],   //date
                                    drink[1],   //hour-minute
                                    drink[2],   //name
                                    drink[3],   //type
                                    drink[4]    //amount
                                )
                            )
                        }
                    }
                }
            }
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.adapter = courseAdapter
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
