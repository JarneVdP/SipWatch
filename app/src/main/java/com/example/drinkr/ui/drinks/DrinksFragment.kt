package com.example.drinkr.ui.drinks

import android.R
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.drinkr.databinding.FragmentDrinksBinding
import com.example.drinkr.writeToFile
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.FileOutputStream
import java.io.File
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*


@Suppress("DEPRECATION")
class DrinksFragment : Fragment() {

    private var _binding: FragmentDrinksBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //Declare variables for text recognition
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    private val REQUEST_IMAGE_CAPTURE = 1
    private var imageBitmap: Bitmap? = null

    //create an empty global list to store the recognized text
    private var recognizedText = MutableList<String>(0) { "" }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /* first */
        val drinksViewModel =
            ViewModelProvider(this).get(DrinksViewModel::class.java)

        _binding = FragmentDrinksBinding.inflate(inflater, container, false)

        val root: View = binding.root

//        val textView: TextView = binding.textDrinks
//        drinksViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    //pass the text from the textbox with the calendar fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /* second */
        //declare the spinner
        val spinner_cl: Spinner = binding.drinkamountInput
        val spinner_type: Spinner = binding.drinktypeInput
        //declare the autocomplete
        val autocompletetextview: AutoCompleteTextView = binding.autocompleteDrink
        val autocmpstring: Array<String> =
            resources.getStringArray(com.example.drinkr.R.array.drinks)
        recognizedText += autocmpstring
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, recognizedText)
        autocompletetextview.setAdapter(adapter)

        val buttonsubmit = binding.btnsubmit

        binding.apply {
            captureImageBtn.setOnClickListener {
                takeImage()
            }
        }

        //file
        val file = "drinkStorage.txt"

        //get the today's date
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val currentDateHourMin = sdf.format(Date())
        //get the date
        val currentDate = currentDateHourMin.substring(0, 10)
        //Get the hour and minute
        val hourminute = currentDateHourMin.substring(11, 16)
        //set mode as append
        val mode = Context.MODE_APPEND
        buttonsubmit.setOnClickListener {
            if (autocompletetextview.text.toString() == "" || spinner_cl.selectedItem.toString() == "" || spinner_type.selectedItem.toString() == "") {
                Toast.makeText(context, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            } else {
                //write to file using wToFIle function
                context?.let { it1 -> writeToFile(it1,
                    file,
                    currentDate + ";" + hourminute + ";" + autocompletetextview.text.toString()
                            + ";" + spinner_cl.selectedItem.toString() + ";" +
                            spinner_type.selectedItem.toString(), mode) }
                Toast.makeText(context, "Saved to $file", Toast.LENGTH_SHORT).show()
                autocompletetextview.text.clear()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun takeImage() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        try {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == AppCompatActivity.RESULT_OK) {
            //val extras: Bundle? = data?.extras
            imageBitmap = data?.extras?.get("data") as Bitmap
            if (imageBitmap != null) {
                binding.imageView.setImageBitmap(imageBitmap)
                processImage()
            }
        }
    }

    private fun processImage() {
        if (imageBitmap != null) {
            val image = imageBitmap?.let {
                InputImage.fromBitmap(it, 0)
            }
            val autocompletetextview: AutoCompleteTextView = binding.autocompleteDrink
            image?.let {
                recognizer.process(it)
                    //if its successful, show text in textview
                    .addOnSuccessListener { visionText ->
                        // split the text into lines and added them tp recognizedText Mutablelist
                        val tmp = visionText.text.split("\n", " ") as MutableList<String>
                        recognizedText.addAll(tmp)
                        //add the recognized text to the adapter
                        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, recognizedText)
                        autocompletetextview.setAdapter(adapter)
                        autocompletetextview.showDropDown()

                        //recognizedText.forEach { Toast.makeText(context, "reco  "+ recognizedText, Toast.LENGTH_SHORT).show() }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }
            }
        } else {
            Toast.makeText(context, "No image found", Toast.LENGTH_SHORT).show()
        }
    }
}