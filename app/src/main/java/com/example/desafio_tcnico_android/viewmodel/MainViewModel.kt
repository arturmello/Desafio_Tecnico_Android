package com.example.desafio_tcnico_android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafio_tcnico_android.model.Flight
import com.example.desafio_tcnico_android.network.ApiClient
import com.example.desafio_tcnico_android.network.FlightApiService
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _flights = MutableLiveData<List<Flight>>()
    val flights: LiveData<List<Flight>> get() = _flights
}
