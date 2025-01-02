package com.example.desafio_tcnico_android.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio_tcnico_android.R
import com.example.desafio_tcnico_android.model.Flight
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class FlightAdapter(private var flights: List<Flight>) :
    RecyclerView.Adapter<FlightAdapter.FlightViewHolder>() {

        @SuppressLint("NotifyDataSetChanged")
        fun updateFlights(newFlights: List<Flight>) {
            flights = newFlights
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_flight, parent, false)
            return FlightViewHolder(view)
        }

        override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
            val flight = flights[position]
            holder.bind(flight)
        }

        override fun getItemCount(): Int = flights.size

        class FlightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val airline: TextView = itemView.findViewById(R.id.airline)
            private val origin: TextView = itemView.findViewById(R.id.departure_location)
            private val destination: TextView = itemView.findViewById(R.id.arrival_location)
            private val departureTime: TextView = itemView.findViewById(R.id.departure_time)
            private val arrivalTime: TextView = itemView.findViewById(R.id.arrival_time)
            private val price: TextView = itemView.findViewById(R.id.price)
            private val durationStops: TextView = itemView.findViewById(R.id.duration_stops)



            @SuppressLint("SetTextI18n")
            fun bind(flight: Flight) {
                airline.text = flight.airline
                origin.text = flight.from
                destination.text = flight.to

                // Formatando as datas para exibir apenas horário
                val formattedDeparture = flight.departureDate.substring(11, 16) // "HH:mm"
                val formattedArrival = flight.arrivalDate.substring(11, 16) // "HH:mm"

                departureTime.text = formattedDeparture
                arrivalTime.text = formattedArrival

                // Formatando o preço com 2 casas decimais
                price.text = String.format("R$ %.2f", flight.pricing.ota.saleTotal)

                // Configurando duração e paradas
                val stopsText = if (flight.stops == 0) "Direto" else "${flight.stops} parada(s)"
                durationStops.text = "${flight.duration} min, $stopsText"
            }
        }
    }