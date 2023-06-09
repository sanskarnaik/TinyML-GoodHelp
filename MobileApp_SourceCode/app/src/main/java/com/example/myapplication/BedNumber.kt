package com.example.myapplication

class BedNumber private constructor() {
    private lateinit var bedNum: String

    companion object {
        private val instance: BedNumber by lazy { BedNumber() }

        fun getInstanceBedNumber(): BedNumber {
            return instance
        }
    }

    fun setBedNumber(bedNumber: String) {
        bedNum = bedNumber
    }

    fun getBedNumber(): String {
        return bedNum
    }
}


