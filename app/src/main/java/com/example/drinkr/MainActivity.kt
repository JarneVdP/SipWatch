package com.example.drinkr

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drinkr.databinding.ActivityMainBinding
import com.example.drinkr.ui.agenda.DrinkAdapter
import com.example.drinkr.ui.agenda.DrinkModel
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_drinks, R.id.navigation_notifications
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        // initiate the button to delete a drink from the recycler view
//        deleteButton = findViewById(R.id.delete_drink)
//        //set the onclick listener
//        deleteButton.setOnClickListener {
//            //get the text from the recyclerview
//            val text = findViewById<TextView>(R.id.text_drink_name).text.toString()
//            //get the text from the file
//            val fileText = readFromFile(this, "drinkStorage.txt")
//            //loop trough the file text and remove the line that matches the text from the recyclerview
//            val linesplit = fileText.split("//")
//
//            for (line in linesplit){
//                val drink = line.split(";")
//                if (drink.size == 5){
//                    //if the text from the recyclerview matches the text from the file, remove the line
//                    if (drink[2] == text){
//                        //remove the line
//                        val newFileText = line.replace(line, "")
//                        writeToFile(this, "drinkStorage.txt",newFileText)
//                    }
//                }
//            }
//        }
    }
}

