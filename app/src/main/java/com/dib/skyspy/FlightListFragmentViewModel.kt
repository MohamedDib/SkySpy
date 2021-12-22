package com.dib.skyspy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FlightListFragmentViewModel : ViewModel() {
    private val flightListLiveData = MutableLiveData<List<FlightModel>>()

    init {
        flightListLiveData.value = DataHolder.flightsList
    }

    fun getFlightListLiveData(): LiveData<List<FlightModel>> {
        return flightListLiveData
    }


}