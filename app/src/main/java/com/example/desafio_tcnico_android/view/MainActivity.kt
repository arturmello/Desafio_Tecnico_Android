package com.example.desafio_tcnico_android.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio_tcnico_android.adapter.FlightAdapter
import com.example.desafio_tcnico_android.R
import com.example.desafio_tcnico_android.util.TabNavigator
import com.example.desafio_tcnico_android.model.Flight


class MainActivity : AppCompatActivity() {

    private lateinit var tabNavigator: TabNavigator

    //Vriáveis para armazenar estados dos filtros
    private var isMorningSelected = false
    private var isAfternoonSelected = false
    private var isNightSelected = false
    private var isDawnSelected = false
    private var isDirectSelected = false
    private var isOneStopSelected = false

    //Declaração da variável flightAdapter de forma global
    private lateinit var flightAdapter: FlightAdapter
    private var flightList: List<Flight> = emptyList()

    // Variável para armazenar o critério selecionado
    private var selectedSortCriteria: String? = null

    companion object {
        const val FILTER_REQUEST_CODE = 100 // Constante para identificar o request code da tela de filtros
        const val SORT_REQUEST_CODE = 101 // Constante para identificar o request code da tela de ordenação
    }




    @OptIn(UnstableApi::class)
    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_flights)

        //Adaptando o app as bordas do sartphone
        val headerLayout = findViewById<View>(R.id.header_layout)
        headerLayout.setOnApplyWindowInsetsListener { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsets.Type.systemBars())
            val params = view.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = systemBarsInsets.top
            view.layoutParams = params
            insets
        }


        val bottomLayout = findViewById<View>(R.id.btn_bottom)
        bottomLayout.setOnApplyWindowInsetsListener { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsets.Type.systemBars())
            val params = view.layoutParams as ViewGroup.MarginLayoutParams
            params.bottomMargin = systemBarsInsets.bottom
            view.layoutParams = params
            insets
        }
        //Ação que navega nos VOOS DE IDA e VOOS DE VOLTA
        val tabOneButton = findViewById<TextView>(R.id.btn_voo_ida)
        val tabTwoButton = findViewById<TextView>(R.id.btn_voo_volta)
        val tabOneIndicator = findViewById<View>(R.id.indicator_voo_ida)
        val tabTwoIndicator = findViewById<View>(R.id.indicator_voo_volta)

        tabNavigator = TabNavigator(
            tabOneButton,
            tabTwoButton,
            tabOneIndicator,
            tabTwoIndicator,
            this
        )
        tabNavigator.setupTabs()

        //COnfiguração do botão "FILTRAR"
        val filterButton = findViewById<Button>(R.id.btn_filter)
        filterButton.setOnClickListener {
            val intent = Intent(this, FilterActivity::class.java)
            intent.putExtra("FILTER_MORNING", isMorningSelected)
            intent.putExtra("FILTER_AFTERNOON", isAfternoonSelected)
            intent.putExtra("FILTER_NIGHT", isNightSelected)
            intent.putExtra("FILTER_DAWN", isDawnSelected)
            intent.putExtra("FILTER_DIRECT", isDirectSelected)
            intent.putExtra("FILTER_ONE_STOP", isOneStopSelected)
            startActivityForResult(intent, FILTER_REQUEST_CODE) // Espera um resultado
        }


        // Configuração do RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_flights)
        //Inicializando a variavel global flightAdapter
        flightAdapter = FlightAdapter(emptyList())
        recyclerView.adapter = flightAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        Log.d("MainActivity", "RecyclerView e flightAdapter configurados.")


        //Configurando o botão "ORDENAR"
        val sortButton = findViewById<Button>(R.id.btn_sort)
        sortButton.setOnClickListener {
            val intent = Intent(this, SortActivity::class.java)
            intent.putExtra("SORT_CRITERIA", selectedSortCriteria) //Envia o critério selecionado atual
            startActivityForResult(intent, SORT_REQUEST_CODE)
        }
    }

@Suppress("DEPRECATION")
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    //Recebendo critérios de filtros e aplicando
    if (requestCode == FILTER_REQUEST_CODE && resultCode == RESULT_OK) {
        // Atualizar as variáveis com os estados dos filtros
        isMorningSelected = data?.getBooleanExtra("FILTER_MORNING", false) ?: false
        isAfternoonSelected = data?.getBooleanExtra("FILTER_AFTERNOON", false) ?: false
        isNightSelected = data?.getBooleanExtra("FILTER_NIGHT", false) ?: false
        isDawnSelected = data?.getBooleanExtra("FILTER_DAWN", false) ?: false
        isDirectSelected = data?.getBooleanExtra("FILTER_DIRECT", false) ?: false
        isOneStopSelected = data?.getBooleanExtra("FILTER_ONE_STOP", false) ?: false

        // Aplicar os filtros (exemplo básico)
        applyFilters(isMorningSelected, isAfternoonSelected, isNightSelected, isDawnSelected, isDirectSelected, isOneStopSelected)
    }

    if(requestCode == SORT_REQUEST_CODE && resultCode == RESULT_OK) {
        val sortCriteria = data?.getStringExtra("SORT_CRITERIA")
        selectedSortCriteria = sortCriteria // Atualiza o critério selecionado

        //Ordenando os voos com base nos critérios recebidos
        sortFlights(sortCriteria)
    }
}

    private fun sortFlights(criteria: String?) {
        val sortedFlights = when (criteria) {
            "highest_price" -> flightList.sortedByDescending { it.price.replace(",", ".").toDouble() }
            "lowest_price" -> flightList.sortedBy { it.price.replace(",", ".").toDouble() }
            "lowest_price_time" -> flightList.sortedWith(
                compareBy(
                    { it.price.replace(",", ".").toDouble() }, // Ordenar por preço (convertido)
                    { it.duration } // Ordenar por duração
                )
            )
            else -> flightList
        }

        // Atualizar o RecyclerView com a lista ordenada
        flightAdapter.updateFlights(sortedFlights)
        Log.d("MainActivity", "Lista de voos ordenada pelo critério: $criteria")
    }



    @OptIn(UnstableApi::class)
    private fun applyFilters(
        morning: Boolean,
        afternoon: Boolean,
        night: Boolean,
        dawn: Boolean,
        direct: Boolean,
        oneStop: Boolean
    ) {
        // Dados simulados (use o dataset real no seu projeto)
        val allFlights = listOf(
            Flight("morning", 0, "CNF", "GRU", "13:30", "15:55", "1h30, 1 parada", "160,90"),
            Flight("afternoon", 1, "CNF", "GRU", "12:00", "13:30", "1h30, 1 parada", "144,50"),
            Flight("night", 0, "GRU", "CNF", "18:30", "20:00", "1h30", "180,50")
        )

        // Atualizar a lista global de voos
        flightList = allFlights

        // Aplicar os filtros
        val filteredFlights = flightList.filter { flight ->
            val matchesTime = when {
                morning -> flight.time == "morning"
                afternoon -> flight.time == "afternoon"
                night -> flight.time == "night"
                dawn -> flight.time == "dawn"
                else -> true
            }
            val matchesStops = when {
                direct -> flight.stops == 0
                oneStop -> flight.stops == 1
                else -> true
            }
            matchesTime && matchesStops
        }

        // Atualizar RecyclerView com os dados filtrados
        if (::flightAdapter.isInitialized) { // Verifica se o flightAdapter foi inicializado
            flightAdapter.updateFlights(filteredFlights)
        } else {
            Log.e("ApplyFilters", "flightAdapter não foi inicializado!")
        }
        Log.d("ApplyFilters", "Adapter inicializado e filtros aplicados: $filteredFlights")

        val filterInfoTextView = findViewById<TextView>(R.id.txt_filter_info)

        // Atualize o número de voos filtrados
        val flightCount = filteredFlights.size
        val filterText = if (flightCount == 1) {
            "Com filtro: $flightCount voo"
        } else {
            "Com filtro: $flightCount voos"
        }
        filterInfoTextView.text = filterText
    }

}
