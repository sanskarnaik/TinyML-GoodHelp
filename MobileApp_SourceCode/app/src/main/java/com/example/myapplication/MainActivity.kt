package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.myapplication.databinding.FirstpageBinding
import com.google.firebase.database.*
import com.google.firebase.database.FirebaseDatabase.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: FirstpageBinding
    private lateinit var database: DatabaseReference
    private var TAG = "MainActivity"
    private var displayStatusHandler: Handler? = null
    private var displayStatusRunnable: Runnable? = null
    private val displayStatusDelay: Long = 1000 // Adjust the delay as needed


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FirstpageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        displayStatus()
        bedCheck()
    }

    override fun onResume() {
        super.onResume()
        startDisplayStatusLoop()
    }

    override fun onPause() {
        super.onPause()
        stopDisplayStatusLoop()
    }

    private fun startDisplayStatusLoop() {
        displayStatusHandler = Handler(Looper.getMainLooper())
        displayStatusRunnable = Runnable {
            displayStatus()
            displayStatusRunnable?.let { displayStatusHandler?.postDelayed(it, displayStatusDelay) }
        }
        displayStatusHandler?.postDelayed(displayStatusRunnable!!, 0)
    }

    private fun stopDisplayStatusLoop() {
        displayStatusRunnable?.let { displayStatusHandler?.removeCallbacks(it) }
        displayStatusHandler = null
        displayStatusRunnable = null
    }

    private fun displayStatus() {
        val bedNumbers = listOf("01", "02", "03", "04", "05", "06")

        database = getInstance().getReference("BedNo")
        bedNumbers.forEach { bedNumber ->
            database.child(bedNumber).get().addOnSuccessListener { snapshot ->
                val message = if (snapshot.exists()) snapshot.child("Message").value.toString() else "N/A"
                when (bedNumber) {
                    "01" -> binding.ps1.text = message
                    "02" -> binding.ps2.text = message
                    "03" -> binding.ps3.text = message
                    "04" -> binding.ps4.text = message
                    "05" -> binding.ps5.text = message
                    "06" -> binding.ps6.text = message
                }
            }
        }
//        database = getInstance().getReference("BedNo")
//        database.child("01").get().addOnSuccessListener {
//            if(it.exists()) {
//                binding.ps1.text = it.child("Message").value.toString()
//            } else {
//                binding.ps1.text = "N/A"
//            }
//        }
//        database.child("02").get().addOnSuccessListener {
//            if(it.exists()) {
//                binding.ps2.text = it.child("Message").value.toString()
//            } else {
//                binding.ps2.text = "N/A"
//            }
//        }
//        database.child("03").get().addOnSuccessListener {
//            if(it.exists()) {
//                binding.ps3.text = it.child("Message").value.toString()
//            } else {
//                binding.ps3.text = "N/A"
//            }
//        }
//        database.child("04").get().addOnSuccessListener {
//            if(it.exists()) {
//                binding.ps4.text = it.child("Message").value.toString()
//            } else {
//                binding.ps4.text = "N/A"
//            }
//        }
//        database.child("05").get().addOnSuccessListener {
//            if(it.exists()) {
//                binding.ps5.text = it.child("Message").value.toString()
//            } else {
//                binding.ps5.text = "N/A"
//            }
//        }
//        database.child("06").get().addOnSuccessListener {
//            if(it.exists()) {
//                binding.ps6.text = it.child("Message").value.toString()
//            } else {
//                binding.ps6.text = "N/A"
//            }
//        }
    }

    private fun bedCheck() {
        binding.bed1.setOnClickListener{setBedNumber("01")}
        binding.bed2.setOnClickListener{setBedNumber("02")}
        binding.bed3.setOnClickListener{setBedNumber("03")}
        binding.bed4.setOnClickListener{setBedNumber("04")}
        binding.bed5.setOnClickListener{setBedNumber("05")}
        binding.bed6.setOnClickListener{setBedNumber("06")}
    }

    private fun setBedNumber(bedNumber:String) {
        val bedNumObject = BedNumber.getInstanceBedNumber()
        bedNumObject.setBedNumber(bedNumber)
        val intent = Intent(this, SecondPage::class.java)
        startActivity(intent)
    }
}