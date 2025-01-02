package com.example.desafio_tcnico_android.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.desafio_tcnico_android.R
import com.example.desafio_tcnico_android.model.Flight
import com.example.desafio_tcnico_android.viewmodel.SharedViewModel

class FilterActivity : AppCompatActivity() {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var morningCheckBox: CheckBox
    private lateinit var afternoonCheckBox: CheckBox
    private lateinit var nightCheckBox: CheckBox
    private lateinit var dawnCheckBox: CheckBox
    private lateinit var directCheckBox: CheckBox
    private lateinit var oneStopCheckBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.filter_flights)

        // Inicializar o SharedViewModel
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        val flightList = sharedViewModel.flights.value ?: emptyList()

        // Inicializar os componentes CheckBox
        initializeUI()

        // Atualizar os contadores de filtros
        updateFilterCounts(flightList)

        // Configurar botões
        configureButtons()
    }

    private fun initializeUI() {
        morningCheckBox = findViewById(R.id.check_morning)
        afternoonCheckBox = findViewById(R.id.check_afternoon)
        nightCheckBox = findViewById(R.id.check_night)
        dawnCheckBox = findViewById(R.id.check_dawn)
        directCheckBox = findViewById(R.id.check_direct)
        oneStopCheckBox = findViewById(R.id.check_one_stop)
    }

    private fun configureButtons() {
        val backButton = findViewById<ImageView>(R.id.btn_back)
        val clearButton = findViewById<TextView>(R.id.btn_clear)
        val applyFilterButton = findViewById<Button>(R.id.btn_apply_filter)

        backButton.setOnClickListener { finish() }
        clearButton.setOnClickListener { clearFilters() }
        applyFilterButton.setOnClickListener { applyFilters() }
    }

    private fun clearFilters() {
        morningCheckBox.isChecked = false
        afternoonCheckBox.isChecked = false
        nightCheckBox.isChecked = false
        dawnCheckBox.isChecked = false
        directCheckBox.isChecked = false
        oneStopCheckBox.isChecked = false

        Toast.makeText(this, "Filtros Limpos", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("SetTextI18n")
    private fun updateFilterCounts(flightList: List<Flight>) {
        val morningCount = flightList.count { it.departureDate.substring(11, 13).toInt() in 6..11 }
        val afternoonCount = flightList.count { it.departureDate.substring(11, 13).toInt() in 12..17 }
        val nightCount = flightList.count { it.departureDate.substring(11, 13).toInt() in 18..23 }
        val dawnCount = flightList.count { it.departureDate.substring(11, 13).toInt() in 0..5 }
        val directCount = flightList.count { it.stops == 0 }
        val oneStopCount = flightList.count { it.stops == 1 }

        morningCheckBox.text = "Manhã (06:00 às 11:59) ($morningCount ${if (morningCount == 1) "Voo" else "Voos"})"
        afternoonCheckBox.text = "Tarde (12:00 às 17:59) ($afternoonCount ${if (afternoonCount == 1) "Voo" else "Voos"})"
        nightCheckBox.text = "Noite (18:00 às 23:59) ($nightCount ${if (nightCount == 1) "Voo" else "Voos"})"
        dawnCheckBox.text = "Madrugada (00:00 às 05:59) ($dawnCount ${if (dawnCount == 1) "Voo" else "Voos"})"
        directCheckBox.text = "Voo Direto ($directCount ${if (directCount == 1) "Voo" else "Voos"})"
        oneStopCheckBox.text = "1 Parada ($oneStopCount ${if (oneStopCount == 1) "Voo" else "Voos"})"
    }

    private fun applyFilters() {
        val intent = Intent().apply {
            putExtra("FILTER_MORNING", morningCheckBox.isChecked)
            putExtra("FILTER_AFTERNOON", afternoonCheckBox.isChecked)
            putExtra("FILTER_NIGHT", nightCheckBox.isChecked)
            putExtra("FILTER_DAWN", dawnCheckBox.isChecked)
            putExtra("FILTER_DIRECT", directCheckBox.isChecked)
            putExtra("FILTER_ONE_STOP", oneStopCheckBox.isChecked)
        }

        setResult(RESULT_OK, intent)
        finish()
    }
}
