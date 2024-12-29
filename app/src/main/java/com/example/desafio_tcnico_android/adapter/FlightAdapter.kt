package com.example.desafio_tcnico_android.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio_tcnico_android.R
import com.example.desafio_tcnico_android.model.Flight


class FlightAdapter(private var flightList: List<Flight>) : RecyclerView.Adapter<FlightAdapter.FlightViewHolder>() {

    fun updateFlights(newFlights: List<Flight>) {
        flightList = newFlights
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_flight, parent, false)
        return FlightViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        val flight = flightList[position]
        // Vincular os dados do voo ao item da lista
        holder.departureLocation.text = flight.departure
        holder.arrivalLocation.text = flight.arrival
        holder.departureTime.text = flight.departureTime
        holder.arrivalTime.text = flight.arrivalTime
        holder.durationStops.text = flight.duration
        holder.price.text = flight.price
    }

    override fun getItemCount(): Int = flightList.size

    class FlightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val departureLocation: TextView = itemView.findViewById(R.id.departure_location)
        val arrivalLocation: TextView = itemView.findViewById(R.id.arrival_location)
        val departureTime: TextView = itemView.findViewById(R.id.departure_time)
        val arrivalTime: TextView = itemView.findViewById(R.id.arrival_time)
        val durationStops: TextView = itemView.findViewById(R.id.duration_stops)
        val price: TextView = itemView.findViewById(R.id.price)
    }
}

