package com.example.desafio_tcnico_android.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.Button
import android.widget.CheckBox
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.desafio_tcnico_android.R


//Configuração para acessar a ela através do Btn Filtros na tela principal
class FilterActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.filter_flights)

        // Ajustar o cabeçalho para evitar sobreposição com a barra de status
        val headerLayout = findViewById<View>(R.id.header_layout)
        headerLayout.setOnApplyWindowInsetsListener { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsets.Type.systemBars())
            val params = view.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = systemBarsInsets.top
            view.layoutParams = params
            insets
        }



        //Configuração do botão de voltar
        val backButton = findViewById<View>(R.id.btn_back)
        backButton.setOnClickListener {
            finish()
        }

        //Botão LIMPAR
        val clearButton = findViewById<View>(R.id.btn_clear)

        //Checkboxes
        val morningCheckbox = findViewById<CheckBox>(R.id.check_morning)
        val afternoonCheckbox = findViewById<CheckBox>(R.id.check_afternoon)
        val nightCheckbox = findViewById<CheckBox>(R.id.check_night)
        val dawnCheckbox = findViewById<CheckBox>(R.id.check_dawn)
        val directCheckbox = findViewById<CheckBox>(R.id.check_direct)
        val oneStopCheckbox = findViewById<CheckBox>(R.id.check_one_stop)

        // Receber os estados dos filtros enviados pela MainActivity
        val isMorningSelected = intent.getBooleanExtra("FILTER_MORNING", false)
        val isAfternoonSelected = intent.getBooleanExtra("FILTER_AFTERNOON", false)
        val isNightSelected = intent.getBooleanExtra("FILTER_NIGHT", false)
        val isDawnSelected = intent.getBooleanExtra("FILTER_DAWN", false)
        val isDirectSelected = intent.getBooleanExtra("FILTER_DIRECT", false)
        val isOneStopSelected = intent.getBooleanExtra("FILTER_ONE_STOP", false)

        // Atualizar os checkboxes com os estados recebidos
        morningCheckbox.isChecked = isMorningSelected
        afternoonCheckbox.isChecked = isAfternoonSelected
        nightCheckbox.isChecked = isNightSelected
        dawnCheckbox.isChecked = isDawnSelected
        directCheckbox.isChecked = isDirectSelected
        oneStopCheckbox.isChecked = isOneStopSelected


        //Configurar ação do botão LIMPAR
        clearButton.setOnClickListener {
            morningCheckbox.isChecked = false
            afternoonCheckbox.isChecked = false
            nightCheckbox.isChecked = false
            dawnCheckbox.isChecked = false
            directCheckbox.isChecked = false
            oneStopCheckbox.isChecked = false
        }
        // Botão Aplicar Filtro
        val applyFilterButton = findViewById<Button>(R.id.btn_apply_filter)
        applyFilterButton.setOnClickListener {
            // Intent para passar os filtros selecionados
            val intent = Intent()
            intent.putExtra("FILTER_MORNING", morningCheckbox.isChecked)
            intent.putExtra("FILTER_AFTERNOON", afternoonCheckbox.isChecked)
            intent.putExtra("FILTER_NIGHT", nightCheckbox.isChecked)
            intent.putExtra("FILTER_DAWN", dawnCheckbox.isChecked)
            intent.putExtra("FILTER_DIRECT", directCheckbox.isChecked)
            intent.putExtra("FILTER_ONE_STOP", oneStopCheckbox.isChecked)

            // Retornar para a tela principal com os filtros
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}