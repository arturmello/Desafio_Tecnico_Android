package com.example.desafio_tcnico_android.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.desafio_tcnico_android.R

class SortActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sort_flights)

        //Ajustando cabeçalho para evitar sobreposição com a barra de status
        val headerLayout = findViewById<View>(R.id.header_layout)
        headerLayout.setOnApplyWindowInsetsListener { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsets.Type.systemBars())
            val params = view.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = systemBarsInsets.top
            view.layoutParams = params
            insets
        }

        //Receber o critério atual enviao pela MainActivity
        val currentSortCriteria = intent.getStringExtra("SORT_CRITERIA")

        //Referências aos RadioButtons
        val radioHighestPrice = findViewById<RadioButton>(R.id.radio_highest_price)
        val radioLowestPrice = findViewById<RadioButton>(R.id.radio_lowest_price)
        val radioLowestPriceTime = findViewById<RadioButton>(R.id.radio_lowest_price_time)

        // Marcar o RadioButton correspondente ao critério atual
        when (currentSortCriteria) {
            "highet_price" -> radioHighestPrice.isChecked = true
            "lowest_price" -> radioLowestPrice.isChecked = true
            "lowest_price_time" -> radioLowestPriceTime.isChecked = true
        }

        //Configurando o botão de voltar
        val backButton = findViewById<View>(R.id.btn_back)
        backButton.setOnClickListener {
            finish()
        }

        //Configurando botão limpar
        val clearButton = findViewById<TextView>(R.id.btn_clear_sort)
        clearButton.setOnClickListener {
            // Desmarca todos os RadioButtons no RadioGroup
            val radioGroupSort = findViewById<RadioGroup>(R.id.radio_group_sort)
            radioGroupSort.clearCheck()
        }

        //Capturando e enviando critério selecionado para a MainActivity
        val applySortButton = findViewById<Button>(R.id.btn_apply_sort)
        applySortButton.setOnClickListener {
            val radioGroupSort = findViewById<RadioGroup>(R.id.radio_group_sort)
            val selectedOption = when (radioGroupSort.checkedRadioButtonId) {
                R.id.radio_highest_price -> "highest_price"
                R.id.radio_lowest_price -> "lowest_price"
                R.id.radio_lowest_price_time -> "lowest_price_time"
                else -> null
            }

            if (selectedOption != null) {
                //Retornando o critério selecionado para a MainActivity
                val resultIntent = Intent()
                resultIntent.putExtra("SORT_CRITERIA", selectedOption)
                setResult(RESULT_OK, resultIntent)
                finish() //Voltando para a tela principal do app
            } else {
                //Mostrando uma mensagem se nenhum critério for selecionado
                Toast.makeText(
                    this,
                    "Selecione uma opção para aplicar a ordenação.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
}