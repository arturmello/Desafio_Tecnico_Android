package com.example.desafio_tcnico_android.view

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.desafio_tcnico_android.R
import com.example.desafio_tcnico_android.viewmodel.SharedViewModel

class SortActivity : AppCompatActivity() {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var radioGroup: RadioGroup
    private lateinit var highestPrice: RadioButton
    private lateinit var lowestPrice: RadioButton
    private lateinit var lowestPriceTime: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sort_flights)

        // Inicializar SharedViewModel
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        // Inicializar componentes
        initializeUI()

        // Configurar botões
        configureButtons()
    }

    private fun initializeUI() {
        radioGroup = findViewById(R.id.radio_group_sort)
        highestPrice = findViewById(R.id.radio_highest_price)
        lowestPrice = findViewById(R.id.radio_lowest_price)
        lowestPriceTime = findViewById(R.id.radio_lowest_price_time)
    }

    private fun configureButtons() {
        val backButton = findViewById<ImageView>(R.id.btn_back)
        val clearButton = findViewById<TextView>(R.id.btn_clear_sort)
        val applyButton = findViewById<Button>(R.id.btn_apply_sort)

        backButton.setOnClickListener { finish() }
        clearButton.setOnClickListener { clearSort() }
        applyButton.setOnClickListener { applySort() }
    }

    private fun clearSort() {
        radioGroup.clearCheck()
        Toast.makeText(this, "Ordenação padrão aplicada", Toast.LENGTH_SHORT).show()
    }

    private fun applySort() {
        val selectedSort = when {
            highestPrice.isChecked -> "highest_price"
            lowestPrice.isChecked -> "lowest_price"
            lowestPriceTime.isChecked -> "lowest_price_time"
            else -> "default"
        }

        val resultIntent = Intent().apply {
            putExtra("SORT_CRITERIA", selectedSort)
        }

        setResult(RESULT_OK, resultIntent)
        finish()
    }
}
