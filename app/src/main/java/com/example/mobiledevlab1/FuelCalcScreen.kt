// User interface + calculations
package com.example.mobiledevlab1

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FuelCalcScreen() {
    // UI vars for inputs
    var inputC by remember { mutableStateOf("") }
    var inputH by remember { mutableStateOf("") }
    var inputS by remember { mutableStateOf("") }
    var inputN by remember { mutableStateOf("") }
    var inputO by remember { mutableStateOf("") }
    var inputW by remember { mutableStateOf("") }
    var inputAsh by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }

    // Calculate button logic
    fun performCalculation() {
        val C = inputC.toDoubleOrNull() ?: 0.0
        val H = inputH.toDoubleOrNull() ?: 0.0
        val S = inputS.toDoubleOrNull() ?: 0.0
        val N = inputN.toDoubleOrNull() ?: 0.0
        val O = inputO.toDoubleOrNull() ?: 0.0
        val W = inputW.toDoubleOrNull() ?: 0.0
        val A = inputAsh.toDoubleOrNull() ?: 0.0

        val kDry = 100 / (100 - W)
        val kComb = 100 / (100 - W - A)

        // Dry mass composition
        val hDry = H * kDry
        val cDry = C * kDry
        val sDry = S * kDry
        val nDry = N * kDry
        val oDry = O * kDry

        // Combustible mass composition
        val hComb = H * kComb
        val cComb = C * kComb
        val sComb = S * kComb
        val nComb = N * kComb
        val oComb = O * kComb

        // Low heat of combustion (MJ/kg)
        val Qr = (339 * C + 1030 * H - 108.8 * (O - S) - 25 * W)/1000
        val Qd = (Qr + 0.025*W)*100/(100-W)
        val Qc = (Qr + 0.025*W)*100/(100-W-A)

        output = """
            Dry Mass Composition:
            H: %.2f%%, C: %.2f%%, S: %.2f%%, N: %.2f%%, O: %.2f%%
            
            Combustible Mass Composition:
            H: %.2f%%, C: %.2f%%, S: %.2f%%, N: %.2f%%, O: %.2f%%
            
            Net Calorific Value (Qr): %.4f MJ/kg
            
            Dry Calorific Value (Qd): %.4f MJ/kg
            
            Combustion Calorific Value (Qc): %.4f MJ/kg
        """.trimIndent().format(hDry, cDry, sDry, nDry, oDry, hComb, cComb, sComb, nComb, oComb, Qr, Qd, Qc)
    }

    // Layout with inputs and button
    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        FuelInputField("Hydrogen (H%)", inputH) { inputH = it }
        FuelInputField("Carbon (C%)", inputC) { inputC = it }
        FuelInputField("Sulfur (S%)", inputS) { inputS = it }
        FuelInputField("Nitrogen (N%)", inputN) { inputN = it }
        FuelInputField("Oxygen (O%)", inputO) { inputO = it }
        FuelInputField("Moisture (W%)", inputW) { inputW = it }
        FuelInputField("Ash (A%)", inputAsh) { inputAsh = it }

        Button(onClick = { performCalculation() }, modifier = Modifier.fillMaxWidth()) {
            Text("Calculate")
        }

        Text(output, modifier = Modifier.padding(top = 12.dp))
    }
}

@Composable
fun FuelInputField(label: String, value: String, onChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth()
    )
}
