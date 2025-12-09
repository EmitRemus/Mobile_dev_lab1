package com.example.mobiledevlab1.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.sqrt


data class GroupEP(
    val n: Double,
    val Pn: Double,
    val Kv: Double,
    val tg: Double
)

@Composable
fun SystemLoadScreen() {

    var grinderPn by remember { mutableStateOf("20.0") }
    var polisherKv by remember { mutableStateOf("0.20") }
    var circularTg by remember { mutableStateOf("1.52") }
    var U by remember { mutableStateOf("0.38") }

    var resultText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text("Параметри варіанту", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = grinderPn,
            onValueChange = { grinderPn = it },
            label = { Text("Pₙ шліфувального, кВт") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = polisherKv,
            onValueChange = { polisherKv = it },
            label = { Text("Kᵥ полірувального") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = circularTg,
            onValueChange = { circularTg = it },
            label = { Text("tgφ циркулярної пили") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            U,
            { U = it },
            label = { Text("Напруга U, кВ") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {

                val gPn = grinderPn.toDouble()
                val pKv = polisherKv.toDouble()
                val cTg = circularTg.toDouble()

                val U = 0.38

                val groups = listOf(
                    GroupEP(4.0, gPn, 0.15, 1.33),
                    GroupEP(2.0, 14.0, 0.12, 1.0),
                    GroupEP(4.0, 42.0, 0.15, 1.33),
                    GroupEP(1.0, 36.0, 0.30, cTg),
                    GroupEP(1.0, 20.0, 0.50, 0.75),
                    GroupEP(1.0, 40.0, pKv, 1.0),
                    GroupEP(2.0, 32.0, 0.20, 1.0),
                    GroupEP(1.0, 20.0, 0.65, 0.75)
                )

                val sumNPn = groups.sumOf { it.n * it.Pn }
                val sumP = groups.sumOf { it.n * it.Pn * it.Kv }
                val sumQ = groups.sumOf { it.n * it.Pn * it.Kv * it.tg }

                val KvGroup = sumP / sumNPn
                val ne = 15.0
                val Kp = 1.25
                val sumPnShop = 2330.0
                val sumKvPnShop = 752.0
                val sumNPn2Shop = 96388.0

                val Pp = Kp * sumP
                val Qp = sumQ
                val Sp = sqrt(Pp * Pp + Qp * Qp)
                val Ip = Pp /  U

                val KvShop = sumKvPnShop / sumPnShop

                val neShop = (sumPnShop * sumPnShop) / sumNPn2Shop

                val KpShop = 0.7

                val Pshop = KpShop * sumKvPnShop

                val Qshop = KpShop * 657.0

                val Sshop = sqrt(Pshop * Pshop + Qshop * Qshop)

                val Ishop = Pshop / U

                resultText = """
        Результати для ШР:
        
        Σ(n·Pₙ) = ${"%.1f".format(sumNPn)} кВт
        ΣP = ${"%.2f".format(sumP)} кВт
        ΣQ = ${"%.2f".format(sumQ)} квар
        
        Kᵥ = ${"%.4f".format(KvGroup)}
        nₑ = ${ne.toInt()}
        Kₚ = $Kp
        
        Pₚ = ${"%.2f".format(Pp)} кВт
        Qₚ = ${"%.2f".format(Qp)} квар
        Sₚ = ${"%.2f".format(Sp)} кВА
        Iₚ = ${"%.2f".format(Ip)} А
        
        Результати для цеху:
        
        Kᵥц = ${"%.2f".format(KvShop)} кВт
        nₑц = ${"%.0f".format(neShop)}
        
        Pₚ = ${"%.1f".format(Pshop)} кВт
        Qₚ = ${"%.1f".format(Qshop)} квар
        Sₚ = ${"%.0f".format(Sshop)} кВА
        Iₚ = ${"%.2f".format(Ishop)} А
    """.trimIndent()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Обчислити")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (resultText.isNotEmpty()) {
            Text(resultText)
        }
    }

}
