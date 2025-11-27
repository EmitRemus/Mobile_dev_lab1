package com.example.mobiledevlab1

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun SolarProfitCalcScreen() {
    var inputPower by remember { mutableStateOf("") }
    var firstErrorMargin by remember { mutableStateOf("") }
    var secondErrorMargin by remember { mutableStateOf("") }
    var electricityRate by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        FuelInputField("Power (MW)" , inputPower) { inputPower = it }
        FuelInputField("Initial σ₁ (MW)" , firstErrorMargin) { firstErrorMargin = it }
        FuelInputField("Improved σ₂ (MW)" , secondErrorMargin) { secondErrorMargin = it }
        FuelInputField("Electricity Rate (UAH/kWh)" , electricityRate) { electricityRate = it }

        Button(onClick = {
            result = performCalculation(
                inputPower.toDoubleOrNull() ?: 0.0,
                firstErrorMargin.toDoubleOrNull() ?: 0.0,
                secondErrorMargin.toDoubleOrNull() ?: 0.0,
                electricityRate.toDoubleOrNull() ?: 0.0
            )
        }) {
            Text("Calculate Profit")
        }

        Text(result)
    }
}

fun performCalculation(Pc: Double, sigma1: Double, sigma2: Double, rate: Double): String {
    val t = 24.0
    val delta1 = approximateIntegral(Pc - 0.25, Pc + 0.25, 1000, Pc, sigma1)
    val delta2 = approximateIntegral(Pc - 0.25, Pc + 0.25, 1000, Pc, sigma2)

    val W1 = Pc * t * delta1
    val profit1 = W1 * rate * 1000
    val W2 = Pc * t * (1 - delta1)
    val fine1 = W2 * rate * 1000
    val net1 = profit1 - fine1

    val W3 = Pc * t * delta2
    val profit2 = W3 * rate * 1000
    val W4 = Pc * t * (1 - delta2)
    val fine2 = W4 * rate * 1000
    val net2 = profit2 - fine2

    return """Before Improvement:
        Profit: %.1f UAH
        Penalty: %.1f UAH
        Net: %.1f UAH
        ---------------------------------
        After Improvement:
        Profit: %.1f UAH
        Penalty: %.1f UAH
        Net: %.1f UAH""".trimIndent().format(profit1, fine1, net1, profit2, fine2, net2)
}

fun approximateIntegral(a: Double, b: Double, n: Int, mean: Double, sigma: Double): Double {
    val dx = (b - a) / n
    var sum = 0.0
    for (i in 0 until n) {
        val x = a + i * dx
        val pdf = (1 / (sigma * sqrt(2 * Math.PI))) * exp(-((x - mean).pow(2)) / (2 * sigma * sigma))
        sum += pdf * dx
    }
    return sum
}
