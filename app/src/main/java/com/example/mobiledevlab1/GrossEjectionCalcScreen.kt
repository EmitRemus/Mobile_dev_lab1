package com.example.mobiledevlab1

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GrossEjectionCalcScreen() {
    // Input states
    var coalMass by remember { mutableStateOf("") }
    var mazutMass by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }

    fun performCalculation() {
        val coalMass = coalMass.toDoubleOrNull() ?: 0.0
        val mazutMass = mazutMass.toDoubleOrNull() ?: 0.0
        val coalQri = 20.47
        val mazutQri = 39.48
        val coalAvyn = 0.8
        val mazutAvyn = 1
        val nuZu = 0.985
        val coalAshr = 25.20
        val mazutAshr = 0.15
        val coalGvyn = 1.5

        val coalKtv = (1000000/coalQri)*coalAvyn*(coalAshr/(100 - coalGvyn))*(1 - nuZu)
        val coalEjection = (coalKtv*coalQri*coalMass)/1000000

        val mazutKtv = (1000000/mazutQri)*mazutAvyn*(mazutAshr/100)*(1 - nuZu)
        val mazutEjection = (mazutKtv*mazutQri*mazutMass)/1000000

        output = """
            Coal Emission: %.2f g/GJ
            Gross Ejection of Coal: %.3f tons
            ---------------------------------
            Mazut Emission: %.2f g/GJ
            Gross Ejection of Mazut: %.3f tons
        """.trimIndent().format(coalKtv, coalEjection, mazutKtv, mazutEjection)
    }

    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        FuelInputField("Coal mass (t)", coalMass) { coalMass = it }
        FuelInputField("Mazut mass (t)", mazutMass) { mazutMass = it }

        Button(onClick = { performCalculation() }, modifier = Modifier.fillMaxWidth()) {
            Text("Calculate Ejection")
        }

        Text(output, modifier = Modifier.padding(top = 12.dp))
    }
}