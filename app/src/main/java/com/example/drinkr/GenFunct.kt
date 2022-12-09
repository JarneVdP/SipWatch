package com.example.drinkr

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.drinkr.ui.agenda.DrinkModel
import java.io.*

// create a general function to write to a file
fun writeToFile(context: Context, fileName: String, data: String, mode: Int) {
    try {
        val outputStreamWriter =
            OutputStreamWriter(context.openFileOutput(fileName, mode))
        outputStreamWriter.write(data)
        outputStreamWriter.close()
    } catch (e: IOException) {
        Log.e("Exception", "File write failed: $e")
    }
}

// create a general function to read from a file
fun readFromFile(context: Context, fileName: String): StringBuilder {
    val fileInputStream: FileInputStream? = context.openFileInput(fileName)
    val inputStreamReader = InputStreamReader(fileInputStream)
    val bufferedReader = BufferedReader(inputStreamReader)
    val stringBuilder = StringBuilder()
    var text: String?
    while (bufferedReader.readLine().also { text = it } != null) {
        stringBuilder.append(text)
    }
    try {
        while (bufferedReader.readLine().also { text = it } != null) {
            stringBuilder.append(text)
        }
    } catch (e: FileNotFoundException) {
        Log.e("login activity", "File not found: $e")
    } catch (e: IOException) {
        Log.e("login activity", "Can not read file: $e")
    } //catch empty file
    catch (e: NullPointerException) {
        Log.e("login activity", "File is empty: $e")
    }
    return stringBuilder
}

//check is drinkStorage.txt exist else create it
fun checkFile(context: Context, fileName: String) {
    val file = File(context.filesDir, fileName)
    if (!file.exists()) {
        writeToFile(context, fileName, "", Context.MODE_APPEND)
    }
}

//Delete a drink from the file and the recyclerview
fun deleteDrink(context: Context, fileName: String, drink: DrinkModel) {
    //get the text from the file
    val fileText = readFromFile(context, fileName)
    var updatedLines = ""
    //loop trough the file text and remove the line that matches the text from the recyclerview
    val linesplit = fileText.split("//")

    for (line in linesplit) {
        val drinkFile = line.split(";")
        if (drinkFile.size == 5) {
            Toast.makeText(context, drinkFile[4] + drink.getDrink_amount(), Toast.LENGTH_SHORT).show()
            if (drinkFile[0] == drink.getDrink_date() && drinkFile[1] == drink.getDrink_time() && drinkFile[2] == drink.getDrink_name() && drinkFile[3] == drink.getDrink_type() && drinkFile[4] == drink.getDrink_amount()) {
                updatedLines += ""
            }
            else{
                updatedLines += line + "//"
            }
        }
    }
    Toast.makeText(context, updatedLines, Toast.LENGTH_SHORT).show()
    //write the new file to the file
    writeToFile(context, fileName, updatedLines, Context.MODE_PRIVATE)
}