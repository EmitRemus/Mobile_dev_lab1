package com.example.mobiledevlab1

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FuelMazutCalcScreen() {
    var C by remember { mutableStateOf("") }
    var H by remember { mutableStateOf("") }
    var O by remember { mutableStateOf("") }
    var S by remember { mutableStateOf("") }
    var Q by remember { mutableStateOf("") }
    var W by remember { mutableStateOf("") }
    var Ash by remember { mutableStateOf("") }
    var V by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }

    fun performCalculation() {
        val Cg = C.toDoubleOrNull() ?: 0.0
        val Hg = H.toDoubleOrNull() ?: 0.0
        val Og = O.toDoubleOrNull() ?: 0.0
        val Sg = S.toDoubleOrNull() ?: 0.0
        val Qg = Q.toDoubleOrNull() ?: 0.0
        val Wp = W.toDoubleOrNull() ?: 0.0
        val Ad = Ash.toDoubleOrNull() ?: 0.0
        val Vg = V.toDoubleOrNull() ?: 0.0

        val factor = (100 - Wp - Ad) / 100

        val Cr = Cg * factor
        val Hr = Hg * factor
        val Or = Og * factor
        val Sr = Sg * factor
        val Ar = Ad * (100 - Wp) / 100
        val Vr = Vg * (100 - Wp) / 100
        val Qr = Qg * factor - 0.025*Wp
        output = """
            Fuel Mazut - Working Mass Composition:
            Carbon: %.2f%%
            Hydrogen: %.2f%%
            Oxygen: %.2f%%
            Sulfur: %.2f%%
            Ash: %.2f%%
            Vanadium: %.2f mg/kg
            Net Calorific Value: %.2f MJ/kg
        """.trimIndent().format(Cr, Hr, Or, Sr, Ar, Vr, Qr)
    }

    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        FuelInputField("Hydrogen (Hg, %)", H) { H = it }
        FuelInputField("Carbon (Cg, %)", C) { C = it }
        FuelInputField("Sulfur (Sg, %)", S) { S = it }
        FuelInputField("Oxygen (Og, %)", O) { O = it }
        FuelInputField("Vanadium (Vg, mg/kg)", V) { V = it }
        FuelInputField("Moisture (Wp, %)", W) { W = it }
        FuelInputField("Ash (Ad, %)", Ash) { Ash = it }
        FuelInputField("Low Heat Value (Qdaf, MJ/kg)", Q) { Q = it }

        Button(onClick = { performCalculation() }, modifier = Modifier.fillMaxWidth()) {
            Text("Calculate")
        }

        Text(output, modifier = Modifier.padding(top = 16.dp))
    }
}