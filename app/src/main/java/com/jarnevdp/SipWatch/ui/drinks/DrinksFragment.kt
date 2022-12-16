package com.jarnevdp.SipWatch.ui.drinks

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.barcode.common.Barcode.FORMAT_CODE_128
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.jarnevdp.SipWatch.R
import com.jarnevdp.SipWatch.databinding.FragmentDrinksBinding
import com.jarnevdp.SipWatch.writeToFile
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
    private var recognizedText = MutableList(0) { "" }

    //barcode scanner

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
            resources.getStringArray(R.array.drinks)
        recognizedText += autocmpstring
        val adapter = ArrayAdapter(requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, recognizedText)
        autocompletetextview.setAdapter(adapter)

        val buttonsubmit = binding.btnsubmit

        binding.apply {
            captureImageBtn.setOnClickListener {
                takeImage()
            }
        }

        //create a barcode scanner
//        val btnbarcode = binding.captureBarcodeBtn
//        btnbarcode.setOnClickListener {
//            //convert imageBitmap to InputImage
//            val image:InputImage = InputImage.fromBitmap(imageBitmap!!, 0)
//            scanBarcodes(image)
//            takeImage()
//        }


        //file
        val file = "drinkStorage.txt"

        //get the today's date
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm")
        val currentDateHourMin = sdf.format(Date())
        //get the date
        val currentDate = currentDateHourMin.substring(0, 10)
        //Get the hour and minute
        val hourminute = currentDateHourMin.substring(11, 16)
        //set mode as append
        val mode = Context.MODE_APPEND
        buttonsubmit.setOnClickListener {
            if (autocompletetextview.text.toString() == "" || spinner_cl.selectedItem.toString() == "" || spinner_type.selectedItem.toString() == "") {
                Toast.makeText(context, "Gelieve alle velden in te vullen", Toast.LENGTH_SHORT).show()
            } else {
                //write to file using wToFIle function
                context?.let { it1 -> writeToFile(it1,
                    currentDate+file,
                    currentDate + ";" + hourminute + ";" + autocompletetextview.text.toString()
                            + ";" + spinner_type.selectedItem.toString() + ";" +
                            spinner_cl.selectedItem.toString(), mode) }
                Toast.makeText(context, "Saved ${autocompletetextview.text}", Toast.LENGTH_SHORT).show()
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
                //if button captureImageBtn is clicked, run processimage. If button captureBarcodeBtn is clicked, run scanBarcodes
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
                        val adapter = ArrayAdapter(requireContext(),
                            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, recognizedText)
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

    // Does not work as expected
//    private fun scanBarcodes(image: InputImage): Task<MutableList<Barcode>> {
//        //https://developers.google.com/ml-kit/vision/barcode-scanning/android
//        // [START set_detector_options]
//        val options = BarcodeScannerOptions.Builder()
//            .setBarcodeFormats(
//                Barcode.FORMAT_QR_CODE,
//                Barcode.FORMAT_AZTEC)
//            .build()
//        // [END set_detector_options]
//        // [START get_detector]
//        val scanner = BarcodeScanning.getClient()
//        // Or, to specify the formats to recognize:
//        // val scanner = BarcodeScanning.getClient(options)
//        // [END get_detector]
//        // [START run_detector]
//        val result = scanner.process(image)
//            .addOnSuccessListener { barcodes ->
//                // Task completed successfully
//                // [START_EXCLUDE]
//                // [START get_barcodes]
//                for (barcode in barcodes) {
//                    val bounds = barcode.boundingBox
//                    val corners = barcode.cornerPoints
//
//                    val rawValue = barcode.rawValue
//
//                    val valueType = barcode.valueType
//
//                    // See API reference for complete list of supported types
//                    Toast.makeText(context, "Barcode format: ${barcode.format}", Toast.LENGTH_SHORT).show()
//                    when (valueType) {
//
//                        Barcode.TYPE_ISBN -> {
//                            Toast.makeText(context, "ISBN: $rawValue", Toast.LENGTH_SHORT).show()
//                        }
//                        Barcode.FORMAT_ITF -> {
//                            val format = barcode.format
//                        }
//                        Barcode.FORMAT_EAN_13 -> {
//                            val format = barcode.format
//                        }
//                        Barcode.TYPE_WIFI -> {
//                            val ssid = barcode.wifi!!.ssid
//                            val password = barcode.wifi!!.password
//                            val type = barcode.wifi!!.encryptionType
//                        }
//                        Barcode.TYPE_URL -> {
//                            val title = barcode.url!!.title
//                            val url = barcode.url!!.url
//                        }
//                    }
//                    binding.tempo.text = buildString {
//                    append(rawValue.toString())
//                    append(" ")
//                    append(barcode.valueType)
//                    append(" ")
//                    append(barcode.format)
//                    append(" ")
//                    append(barcode)
//                    append(" ")
//                    }
//                }
//                // [END get_barcodes]
//                // [END_EXCLUDE]
//            }
//            .addOnFailureListener {
//                // Task failed with an exception
//                // ...
//            }
//        // [END run_detector]
//        return result
//    }
}