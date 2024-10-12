package com.example.internalstrorage

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var editTextInputData: EditText
    private lateinit var buttonSave: Button
    private lateinit var buttonRead: Button
    private lateinit var textViewOutput: TextView

    private val fileName = "myfile.txt"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //initialize the value
        editTextInputData = findViewById(R.id.editTextInputData)
        buttonSave = findViewById(R.id.buttonSave)
        buttonRead = findViewById(R.id.buttonRead)
        textViewOutput = findViewById(R.id.textViewOutput)


        //save button click listner
        buttonSave.setOnClickListener {
            val data = editTextInputData.text.toString()
            if (data.isNotEmpty()) {
                saveToInternalStorage(data)
            }else{
                Toast.makeText(this, "plz enter some data", Toast.LENGTH_SHORT).show()
            }
        }
        //retrieve button click listener
        buttonRead.setOnClickListener {
            val data = readFromInternalStorage()
            if (data != null){
                textViewOutput.text = data
            }else{
                textViewOutput.text= "No data found!"
            }
        }


    }

    private fun saveToInternalStorage(data: String) {
        var fos: FileOutputStream? = null
        try {
            fos = openFileOutput(fileName, Context.MODE_PRIVATE)
            fos.write(data.toByteArray())
            Toast.makeText(this,"Data saved", Toast.LENGTH_SHORT).show()
        }catch (e: IOException){
            e.printStackTrace()
        }finally {
            fos?.close()
        }

    }
    private fun readFromInternalStorage(): String? {
        var fis: FileInputStream? = null
        return try{
            fis = openFileInput(fileName)
            val fileContent = fis.readBytes().toString(Charsets.UTF_8)
            fileContent
        }catch (e: IOException){
            e.printStackTrace()
            null
        }finally {
            fis?.close()
        }

    }

}