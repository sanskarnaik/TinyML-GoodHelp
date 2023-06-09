package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SecondPage : AppCompatActivity() {

    private val bedNumber = BedNumber.getInstanceBedNumber()
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference
    private var TAG = "SecondPage"
    private var itemGlobal: String? = bedNumber.getBedNumber()
    private var displayStatusHandler: Handler? = null
    private var displayStatusRunnable: Runnable? = null
    private val displayStatusDelay: Long = 1000 // Adjust the delay as needed


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.bedn.text = itemGlobal
        setContentView(binding.root)
        itemGlobal?.let { getValue(it) }
        buttonClick()
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
            itemGlobal?.let { getValue(it) }
            displayStatusRunnable?.let { displayStatusHandler?.postDelayed(it, displayStatusDelay) }
        }
        displayStatusHandler?.postDelayed(displayStatusRunnable!!, 0)
    }

    private fun stopDisplayStatusLoop() {
        displayStatusRunnable?.let { displayStatusHandler?.removeCallbacks(it) }
        displayStatusHandler = null
        displayStatusRunnable = null
    }

    private fun buttonClick() {
        binding.b10min.setOnClickListener{addTime("10min")}

        binding.b20min.setOnClickListener{addTime("20min")}

        binding.bLater.setOnClickListener{addTime("Later")}

        binding.bNurse.setOnClickListener{addTime("Nurse")}
    }

    private fun addTime(time:String) {
        val waitTime = mapOf(
            "Wait Time" to time
        )
        database = FirebaseDatabase.getInstance().getReference("BedNo")
        itemGlobal?.let {
            database.child(it).updateChildren(waitTime).addOnSuccessListener {
                Log.i(TAG, "Database Updated")
            }.addOnCanceledListener {
                Log.i(TAG, "Database Update Failed")
            }
        }
    }

    private fun getValue(myItem:String) {
        database = FirebaseDatabase.getInstance().getReference("BedNo")
        database.child(myItem).get().addOnSuccessListener {

            if(it.exists()) {

                binding.textMsg.text = it.child("Message").value.toString()
                binding.textName.text = it.child("Name").value.toString()
                binding.textGender.text = it.child("Gender").value.toString()
                binding.textAge.text = it.child("Age").value.toString()
                binding.textCond.text = it.child("Condition").value.toString()
                binding.bLater.isEnabled = true
                binding.b10min.isEnabled = true
                binding.bNurse.isEnabled = true
                binding.b20min.isEnabled = true

            } else {
                Toast.makeText(applicationContext, "No such BedNo", Toast.LENGTH_SHORT).show()
                binding.textMsg.text = ""
                binding.textName.text = ""
                binding.textGender.text = ""
                binding.textAge.text = ""
                binding.textCond.text = ""
                binding.bLater.isEnabled = false
                binding.b10min.isEnabled = false
                binding.bNurse.isEnabled = false
                binding.b20min.isEnabled = false
            }

        }.addOnFailureListener{
            Log.i(TAG, "Failed to retrieve data")
        }
    }
}