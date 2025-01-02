package com.example.desafio_tcnico_android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.desafio_tcnico_android.model.Flight
import com.example.desafio_tcnico_android.model.OTA
import com.example.desafio_tcnico_android.model.Pricing
import com.example.desafio_tcnico_android.viewmodel.SharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)


class SharedViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SharedViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined) // Configurar o dispatcher de teste
        viewModel = SharedViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Resetar o dispatcher após os testes
    }

    @Test
    fun `test fetchFlights updates flights`() {
        // Configurar voos mockados
        val mockFlights = listOf(
            Flight(
                stops = 0,
                airline = "Mock Airline 1",
                duration = 120,
                flightNumber = "MK123",
                from = "GRU",
                to = "RIO",
                departureDate = "2025-01-02T08:00:00",
                arrivalDate = "2025-01-02T10:00:00",
                pricing = Pricing(OTA(fareTotal = 150.0, saleTotal = 200.0)),
                direction = "IDA"
            )
        )

        // Atualizar os voos diretamente
        viewModel.setFlights(mockFlights)

        // Verificar se os voos foram atualizados
        assertEquals(mockFlights, viewModel.flights.value)
    }

    @Test
    fun `test resetFlights restores original flights`() {
        // Mock de voos originais
        val originalFlights = listOf(
            Flight(
                stops = 0,
                airline = "Mock Airline 1",
                duration = 120,
                flightNumber = "MK123",
                from = "GRU",
                to = "RIO",
                departureDate = "2025-01-02T08:00:00",
                arrivalDate = "2025-01-02T10:00:00",
                pricing = Pricing(OTA(fareTotal = 150.0, saleTotal = 200.0)),
                direction = "IDA"
            )
        )


        // Definir os voos originais
        viewModel.setFlights(originalFlights)

        // Mock de voos atualizados
        val updatedFlights = listOf(
            Flight(
                stops = 1,
                airline = "Mock Airline 2",
                duration = 180,
                flightNumber = "MK456",
                from = "RIO",
                to = "GRU",
                departureDate = "2025-01-02T14:00:00",
                arrivalDate = "2025-01-02T17:00:00",
                pricing = Pricing(OTA(fareTotal = 120.0, saleTotal = 150.0)),
                direction = "VOLTA"
            )
        )

        viewModel.setFlights(originalFlights)
        viewModel.flights.value = updatedFlights

        // Chamar o reset
        viewModel.resetFlights()

        // Verificar se os voos originais foram restaurados
        assertEquals(originalFlights, viewModel.flights.value)

    }
}
