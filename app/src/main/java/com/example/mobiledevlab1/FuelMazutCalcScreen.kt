package com.example.mobiledevlab1

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FuelMazutCalcScreen() {
    var carbon by remember { mutableStateOf("") }
    var hydrogen by remember { mutableStateOf("") }
    var oxygen by remember { mutableStateOf("") }
    var sulfur by remember { mutableStateOf("") }
    var qCombust by remember { mutableStateOf("") }
    var moisture by remember { mutableStateOf("") }
    var ashDry by remember { mutableStateOf("") }
    var vanadium by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    fun calculate() {
        val Cg = carbon.toDoubleOrNull() ?: 0.0
        val Hg = hydrogen.toDoubleOrNull() ?: 0.0
        val Og = oxygen.toDoubleOrNull() ?: 0.0
        val Sg = sulfur.toDoubleOrNull() ?: 0.0
        val Qg = qCombust.toDoubleOrNull() ?: 0.0
        val Wp = moisture.toDoubleOrNull() ?: 0.0
        val Ad = ashDry.toDoubleOrNull() ?: 0.0
        val Vg = vanadium.toDoubleOrNull() ?: 0.0

        val factor = (100 - Wp - Ad) / 100

        val Cr = Cg * factor
        val Hr = Hg * factor
        val Or = Og * factor
        val Sr = Sg * factor
        val Qr = Qg * factor
        val Vr = Vg * (100 - Wp) / 100

        result = """
            ➤ Fuel Mazut - Working Mass Composition:
            Carbon: %.2f%%
            Hydrogen: %.2f%%
            Oxygen: %.2f%%
            Sulfur: %.2f%%
            Vanadium: %.2f mg/kg
            Net Calorific Value: %.2f MJ/kg
        """.trimIndent().format(Cr, Hr, Or, Sr, Vr, Qr)
    }

    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        FuelInputField("Hydrogen (Hg, %)", hydrogen) { hydrogen = it }
        FuelInputField("Carbon (Cg, %)", carbon) { carbon = it }
        FuelInputField("Sulfur (Sg, %)", sulfur) { sulfur = it }
        FuelInputField("Oxygen (Og, %)", oxygen) { oxygen = it }
        FuelInputField("Vanadium (Vg, mg/kg)", vanadium) { vanadium = it }
        FuelInputField("Moisture (Wp, %)", moisture) { moisture = it }
        FuelInputField("Ash (Ad, %)", ashDry) { ashDry = it }
        FuelInputField("Low Heat Value (Qdaf, MJ/kg)", qCombust) { qCombust = it }

        Button(onClick = { calculate() }, modifier = Modifier.fillMaxWidth()) {
            Text("Calculate")
        }

        Text(result, modifier = Modifier.padding(top = 16.dp))
    }
}