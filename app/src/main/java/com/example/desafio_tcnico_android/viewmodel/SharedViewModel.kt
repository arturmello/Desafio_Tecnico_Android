package com.example.desafio_tcnico_android.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafio_tcnico_android.model.Flight
import com.example.desafio_tcnico_android.model.OTA
import com.example.desafio_tcnico_android.model.Pricing
import com.example.desafio_tcnico_android.network.ApiClient
import com.example.desafio_tcnico_android.network.FlightApiService
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {
    val flights = MutableLiveData<List<Flight>>()
    private val originalFlights = mutableListOf<Flight>()

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
        flights.value = originalFlights.toList()
    }

    private fun getMockedFlights(): List<Flight> {
        return listOf(
            Flight(
                stops = 0,
                airline = "Mock Airline 1",
                duration = 120,
                flightNumber = "MK123",
                from = "GRU",
                to = "RIO",
                departureDate = "2025-01-02T08:00:00",
                arrivalDate = "2025-01-02T10:00:00",
                pricing = Pricing(OTA(fareTotal = 150.0, saleTotal = 200.0)),
                direction = "IDA"
            ),
            Flight(
                stops = 1,
                airline = "Mock Airline 2",
                duration = 180,
                flightNumber = "MK456",
                from = "RIO",
                to = "GRU",
                departureDate = "2025-01-02T14:00:00",
                arrivalDate = "2025-01-02T17:00:00",
                pricing = Pricing(OTA(fareTotal = 120.0, saleTotal = 150.0)),
                direction = "VOLTA"
            )
        )
    }



}