package com.example.desafio_tcnico_android

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.desafio_tcnico_android.view.MainActivity
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testFilterButtonOpensFilterActivity() {
        // Clique no botão "Filtrar"
        onView(withId(R.id.btnFilter)).perform(click())

        // Verifique se o título da tela de filtro está visível
        onView(withText("FILTROS")).check(matches(isDisplayed()))
    }

    @Test
    fun testSortButtonOpensSortActivity() {
        // Clique no botão "Ordenar"
        onView(withId(R.id.btn_sort)).perform(click())

        // Verifique se o título da tela de ordenação está visível
        onView(withText("ORDENAÇÃO")).check(matches(isDisplayed()))
    }

    @Test
    fun testTabsSwitch() {
        // Clique na aba "Voo de Volta"
        onView(withId(R.id.btn_voo_volta)).perform(click())

        // Verifique se a aba de "Voo de Volta" está selecionada
        onView(withId(R.id.indicator_voo_volta)).check(matches(isDisplayed()))

        // Clique na aba "Voo de Ida"
        onView(withId(R.id.btn_voo_ida)).perform(click())

        // Verifique se a aba de "Voo de Ida" está selecionada
        onView(withId(R.id.indicator_voo_ida)).check(matches(isDisplayed()))
    }
}
