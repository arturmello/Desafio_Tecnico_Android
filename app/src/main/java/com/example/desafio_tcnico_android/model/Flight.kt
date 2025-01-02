package com.example.desafio_tcnico_android.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Flight(
    val stops: Int,
    val airline: String,
    val duration: Int,
    val flightNumber: String,
    val from: String,
    val to: String,
    val departureDate: String,
    val arrivalDate: String,
    val pricing: Pricing,
    val direction: String,
    val otaAvailableIn: String? = null,
    val airlineTarget: String? = null
) : Parcelable


@Parcelize
data class Pricing(
    val ota: OTA
) : Parcelable

@Parcelize
data class OTA(
    val fareTotal: Double,
    val saleTotal: Double
) : Parcelable