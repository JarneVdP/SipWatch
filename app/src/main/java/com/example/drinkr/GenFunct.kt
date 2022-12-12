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
        outputStreamWriter.append("\n")
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
        while (bufferedReader.readLine().also { text = it } != null) {
            stringBuilder.append(text)
            stringBuilder.append("\n")
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

fun removeLineFromFile(context:Context, fileName: String, drink: DrinkModel,
                       drinkModelArrayList: ArrayList<DrinkModel>, lineToRemove: String) {
    val file = File(context.filesDir, fileName)
    val tempFile = File(file.getAbsolutePath() + ".tmp")

    val reader = BufferedReader(FileReader(file))
    val writer = PrintWriter(FileWriter(tempFile))
    drinkModelArrayList.clear()
    var line: String? = null
    while (reader.readLine().also { line = it } != null) {
        if (!line?.trim().equals(lineToRemove)) {
            drinkModelArrayList.add(
                DrinkModel(
                    drink.getDrink_date(),   //date
                    drink.getDrink_time(),   //hour-minute
                    drink.getDrink_name(),   //name
                    drink.getDrink_amount(),   //amount
                    drink.getDrink_type()   //type
                )
            )
            writer.println(line)
            writer.flush()
        }
    }
    writer.close();
    reader.close();

    file.delete()
    tempFile.renameTo(file)
    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
}