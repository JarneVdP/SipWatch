package com.example.drinkr.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.drinkr.databinding.FragmentHomeBinding
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

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val GreetingText: TextView = binding.greeting
        // get the date
        //get the today's date
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val currentDate = sdf.format(Date())
        // get the hour of the day
        val hour = currentDate.substring(11,13).toInt()
        // set the greeting based on the hour
        if (hour in 0..11) {
            GreetingText.text = "Good Morning!"
        } else if (hour in 12..17) {
            GreetingText.text = "Good Afternoon!"
        } else if (hour in 18..22) {
            GreetingText.text = "Good Evening!"
        } else {
            GreetingText.text = "So long partner!"
        }


//        // Read the file using readFromFile
//        val lines = DrinkClass.readFromFile()
//        //print fileContents to toast
//        for (line in lines) {
//            Toast.makeText(context, line, Toast.LENGTH_SHORT).show()
//        }

//        val file = "drinkStorage.txt"
//
//        val fileInputStream: FileInputStream? = activity?.openFileInput(file)
//        val inputStreamReader = InputStreamReader(fileInputStream)
//        val bufferedReader = BufferedReader(inputStreamReader)
//        val stringBuilder = StringBuilder()
//        var text: String? = null
//        while (run {
//                text = bufferedReader.readLine()
//                text
//            } != null) {
//            stringBuilder.append(text)
//        }
//
//        //split the text on the newline and place in new array
//        val lines = stringBuilder.toString().split("//").toTypedArray()
//        var displayline = ""
//        // display all the lines
//        for (line in lines) {
//            Toast.makeText(context, line, Toast.LENGTH_SHORT).show()
//        }


//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    data class Drink(val date: String, val time: String, val drink: String, val type: String, val amount: String){}

    //function to read from file
//    fun readFromFile(file: String): Array<String> {
//
//        val fileInputStream: FileInputStream? = activity?.openFileInput(file)
//        val inputStreamReader = InputStreamReader(fileInputStream)
//        val bufferedReader = BufferedReader(inputStreamReader)
//        val stringBuilder = StringBuilder()
//        var text: String? = null
//        try {
//            while (run {
//                    text = bufferedReader.readLine()
//                    text
//                } != null) {
//                stringBuilder.append(text)
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        //split the text on the newline and place in new array
//        val lines = stringBuilder.toString().split("//").toTypedArray()
//        return lines
//    }
}