package com.example.drinkr

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.drinkr.ui.agenda.DrinkModel
import java.io.*

// create a general function to write to a file
fun writeToFile(context: Context, fileName: String, data: String) {
    try {
        val outputStreamWriter =
            OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_APPEND))
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
    try {
        while (run {
                text = bufferedReader.readLine()
                text
            } != null) {
            stringBuilder.append(text)
        }
    } catch (e: FileNotFoundException) {
        Log.e("login activity", "File not found: $e")
    } catch (e: IOException) {
        Log.e("login activity", "Can not read file: $e")
    }
    return stringBuilder
}
//
//// create a general function to delete a single line from a file
//fun deleteLineFromFile(context: Context, fileName: String, line: String) {
//    //open the file
//    val file = File(context.filesDir, fileName)
//    //read the file
//    val lines = file.readLines()
//    //delete the line
//    val newLines = lines.filter { it != line }
//    //write the file
//    writeToFile(context, fileName, newLines.joinToString())
//}