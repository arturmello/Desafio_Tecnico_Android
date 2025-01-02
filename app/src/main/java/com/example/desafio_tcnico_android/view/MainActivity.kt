package com.example.desafio_tcnico_android.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio_tcnico_android.R
import com.example.desafio_tcnico_android.adapter.FlightAdapter
import com.example.desafio_tcnico_android.model.Flight
import com.example.desafio_tcnico_android.viewmodel.SharedViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var flightAdapter: FlightAdapter
    private lateinit var tabVooIda: TextView
    private lateinit var tabVooVolta: TextView
    private lateinit var indicatorVooIda: View
    private lateinit var indicatorVooVolta: View
    private lateinit var recyclerViewFlights: RecyclerView
    private var isVooIdaSelected = true // Controle da aba selecionada

    companion object {
        const val FILTER_REQUEST_CODE = 100
        const val SORT_REQUEST_CODE = 101
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flights)

        // Inicializar ViewModel
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        //Inicializar RecyclerView
        flightAdapter = FlightAdapter(emptyList())
        recyclerViewFlights = findViewById<RecyclerView>(R.id.recyclerViewFlights).apply {
            adapter = flightAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        // Observar as mudanças nos voos
        sharedViewModel.flights.observe(this) { flights ->
            updateFlightList(flights)
        }

        // Buscar os dados da API
        sharedViewModel.fetchFlights()

        //Configurar abas
        tabVooIda = findViewById(R.id.btn_voo_ida)
        tabVooVolta = findViewById(R.id.btn_voo_volta)
        indicatorVooIda = findViewById(R.id.indicator_voo_ida)
        indicatorVooVolta = findViewById(R.id.indicator_voo_volta)


        // Configura os cliques nas abas
        tabVooIda.setOnClickListener { selectTab(true) }
        tabVooVolta.setOnClickListener { selectTab(false) }
        // Seleciona a aba inicial
        selectTab(isVooIdaSelected)

        // Configurar botões
        configureFilterButton()
        configureSortButton()
    }

    private fun selectTab(isVooIda: Boolean) {
        isVooIdaSelected = isVooIda

        //Atualiza a visibilidade dos indicadores
        indicatorVooIda.visibility = if (isVooIda) View.VISIBLE else View.GONE
        indicatorVooVolta.visibility = if (isVooIda) View.GONE else View.VISIBLE

        val flights = if (isVooIda) getVooIdaFlights() else getVooVoltaFlights()
        updateFlightList(flights)

    }

    private fun getVooIdaFlights(): List<Flight> {
        return sharedViewModel.flights.value?.filter { it.direction == "IDA" } ?: emptyList()
    }

    private fun getVooVoltaFlights(): List<Flight> {
        return sharedViewModel.flights.value?.filter { it.direction == "VOLTA" } ?: emptyList()
    }




    private fun updateFlightList(flights: List<Flight>) {
        flightAdapter.updateFlights(flights)
        updateFlightCount(flights)
    }

    //Função para atualizar o texto "Com filtro: x voos"
    @SuppressLint("SetTextI18n")
    private fun updateFlightCount(flights: List<Flight>) {
        val filterInfoTextView = findViewById<TextView>(R.id.txt_filter_info)
        val flightCount = flights.size
        filterInfoTextView.text = "Com filtro: $flightCount voo${if (flightCount != 1) "s" else ""}"
    }

    //Função que configura o botão "FILTRAR"
    private fun configureFilterButton() {
        val filterButton = findViewById<Button>(R.id.btnFilter)
        filterButton.setOnClickListener {
            //Intent para abrir a tela de filtros
            val intent = Intent(this, FilterActivity::class.java)
            startActivityForResult(intent, FILTER_REQUEST_CODE)
            }
        }

    private fun configureSortButton() {
        val sortButton = findViewById<Button>(R.id.btn_sort)
        sortButton.setOnClickListener {
            val intent = Intent(this, SortActivity::class.java)
            startActivityForResult(intent, SORT_REQUEST_CODE)
        }
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            FILTER_REQUEST_CODE -> {
                if (resultCode == RESULT_OK) {
                    val filteredFlights = applyFilter(data)
                    updateFlightList(filteredFlights)
                }
            }

            SORT_REQUEST_CODE -> {
                if (resultCode == RESULT_OK) {
                    val sortedFlights = applySort(data)
                    updateFlightList(sortedFlights)
                }
            }
        }
    }

    private fun applyFilter(data: Intent?): List<Flight> {
        val isMorningSelected = data?.getBooleanExtra("FILTER_MORNING", false) ?: false
        val isAfternoonSelected = data?.getBooleanExtra("FILTER_AFTERNOON", false) ?: false
        val isNightSelected = data?.getBooleanExtra("FILTER_NIGHT", false) ?: false
        val isDawnSelected = data?.getBooleanExtra("FILTER_DAWN", false) ?: false
        val isDirectSelected = data?.getBooleanExtra("FILTER_DIRECT", false) ?: false
        val isOneStopSelected = data?.getBooleanExtra("FILTER_ONE_STOP", false) ?: false

        return sharedViewModel.flights.value?.filter { flight ->
            val matchesTime = when {
                isMorningSelected -> flight.departureDate.substring(11, 13).toInt() in 6..11
                isAfternoonSelected -> flight.departureDate.substring(11, 13).toInt() in 12..17
                isNightSelected -> flight.departureDate.substring(11, 13).toInt() in 18..23
                isDawnSelected -> flight.departureDate.substring(11, 13).toInt() in 0..5
                else -> true
            }
            val matchesStops = when {
                isDirectSelected -> flight.stops == 0
                isOneStopSelected -> flight.stops == 1
                else -> true
            }
            matchesTime && matchesStops
        } ?: emptyList()
    }

    private fun applySort(data: Intent?): List<Flight> {
        val sortCriteria = data?.getStringExtra("SORT_CRITERIA") ?: "default"

        val flights = sharedViewModel.flights.value ?: emptyList() // Garante que flights nunca seja nulo

        return when (sortCriteria) {
            "highest_price" -> flights.sortedByDescending { it.pricing.ota.saleTotal }
            "lowest_price" -> flights.sortedBy { it.pricing.ota.saleTotal }
            "lowest_price_time" -> flights.sortedWith(
                compareBy(
                    { it.pricing.ota.saleTotal },
                    { it.duration }
                )
            )
            else -> flights
        }
    }
}