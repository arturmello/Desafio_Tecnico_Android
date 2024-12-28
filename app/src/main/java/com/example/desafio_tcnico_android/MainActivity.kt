package com.example.desafio_tcnico_android

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
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.desafio_tcnico_android.TabNavigator



class MainActivity : AppCompatActivity() {

    private lateinit var tabNavigator: TabNavigator

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

        //Ação de clique no botão de Filtros
        val filterButton = findViewById<Button>(R.id.btn_filter)
        filterButton.setOnClickListener {
            val intent = Intent(this, FilterActivity::class.java)
            startActivity(intent)
        }

    }
}
