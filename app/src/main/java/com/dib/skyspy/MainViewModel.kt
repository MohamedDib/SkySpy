package com.dib.skyspy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel : ViewModel() {

    private val airportListLiveData = MutableLiveData<List<String>>()

    private val fromCalendarLiveData = MutableLiveData<Calendar>()
    private val toCalendarLiveData = MutableLiveData<Calendar>()

    fun getAirportListNamesLiveData(): LiveData<List<String>> {
        return airportListLiveData
    }

    fun getFromCalendarLiveData(): LiveData<Calendar> {
        return fromCalendarLiveData
    }

    fun getToCalendarLiveData(): LiveData<Calendar> {
        return toCalendarLiveData
    }

    init {
        val airportList = Utils.generateAirportList()
        val airportListNames = ArrayList<String>()
        for (airport in airportList) {
            airportListNames.add(airport.getFormattedName())
        }
        airportListLiveData.value = airportListNames

        fromCalendarLiveData.value = Calendar.getInstance()
        toCalendarLiveData.value = Calendar.getInstance()
    }

    fun updateCalendar(year: Int, month: Int, day: Int, isFromCalendar: Boolean) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        if (isFromCalendar)
            fromCalendarLiveData.value = calendar
        else
            toCalendarLiveData.value = calendar
    }
}