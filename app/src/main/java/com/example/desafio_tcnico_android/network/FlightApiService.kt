package com.example.desafio_tcnico_android.network

import com.example.desafio_tcnico_android.model.Flight
import retrofit2.http.GET

interface FlightApiService {
    @GET("flights") // Rota da API mockada
    suspend fun getFlights(): List<Flight>
}
