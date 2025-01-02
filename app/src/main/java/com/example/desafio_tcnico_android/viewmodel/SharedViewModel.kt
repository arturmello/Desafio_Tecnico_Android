package com.example.desafio_tcnico_android.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafio_tcnico_android.model.Flight
import com.example.desafio_tcnico_android.network.ApiClient
import com.example.desafio_tcnico_android.network.FlightApiService
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {
    val flights = MutableLiveData<List<Flight>>()
    private val originalFlights = mutableListOf<Flight>()

    val isMorningSelected = MutableLiveData(false)
    val isAfternoonSelected = MutableLiveData(false)
    val isNightSelected = MutableLiveData(false)
    val isDawnSelected = MutableLiveData(false)
    val isDirectSelected = MutableLiveData(false)
    val isOneStopSelected = MutableLiveData(false)

    val selectedSortOption = MutableLiveData("default")

    fun fetchFlights() {
        viewModelScope.launch {
            try {
                val apiService = ApiClient.instance.create(FlightApiService::class.java)
                val response = apiService.getFlights()

                if (response.isNotEmpty()) {
                    // Adicione todos os voos à lista original e à lista observável
                    originalFlights.clear()
                    originalFlights.addAll(response)
                    flights.postValue(response)
                    println("Voos recebidos: ${response.size}")
                } else {
                    println("Nenhum voo foi retornado pela API")
                }
            } catch (e: Exception) {
                println("Erro ao buscar voos: ${e.message}")
                e.printStackTrace()
            }
        }
    }


    fun setFlights(flightList: List<Flight>) {
        originalFlights.clear()
        originalFlights.addAll(flightList)
        flights.value = flightList
    }

    fun resetFlights() {
        flights.value = originalFlights.map { it.copy() }
    }
}