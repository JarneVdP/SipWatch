package com.jarnevdp.SipWatch

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.jarnevdp.SipWatch.ui.agenda.DrinkModel
import java.io.*
import java.text.DateFormat
import java.text.DateFormat.getDateInstance
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

val sdf = SimpleDateFormat("dd-MM-yyyy")
val currentDate = sdf.format(Date())

// create a general function to write to a file
fun writeToFile(context: Context, fileName: String, data: String, mode: Int) {
    val outputStreamWriter =
        OutputStreamWriter(context.openFileOutput(fileName, mode))
    outputStreamWriter.write(data)
    if (data !=""){ outputStreamWriter.append("\n") }
    outputStreamWriter.close()

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
        stringBuilder.append("\n")
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
                       drinkModelArrayList: ArrayList<DrinkModel>, lineToRemove: String):
        ArrayList<DrinkModel>{
    val file = File(context.filesDir, fileName)
    val tempFile = File(file.absolutePath + ".tmp")
    
    val reader = BufferedReader(FileReader(file))
    val writer = PrintWriter(FileWriter(tempFile))
    var line: String?
    var i = 0
    while (reader.readLine().also { line = it } != null) {
        if (!line?.trim().equals(lineToRemove)) {
            writer.println(line)
            writer.flush()
        }
        else{ drinkModelArrayList.removeAt(i) }
        i +=1
    }

    writer.close()
    reader.close()

    file.delete()
    tempFile.renameTo(file)
    Toast.makeText(context, "Deleted ${drink.getDrink_name()}", Toast.LENGTH_SHORT).show()
    return drinkModelArrayList
}