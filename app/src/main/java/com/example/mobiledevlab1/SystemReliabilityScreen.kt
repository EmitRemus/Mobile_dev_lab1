package com.example.mobiledevlab1.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.Locale

@Composable
fun SystemReliabilityScreen() {

    var lambdaSingle by remember { mutableStateOf("0.295") }
    var tRepair by remember { mutableStateOf("10.7") }

    var damageEmergency by remember { mutableStateOf("23.6") }
    var damagePlanned by remember { mutableStateOf("17.6") }

    var resultText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Порівняння надійності", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = lambdaSingle,
            onValueChange = { lambdaSingle = it },
            label = { Text("λ_ос, 1/рік") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = tRepair,
            onValueChange = { tRepair = it },
            label = { Text("t_в,ос, год") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Розрахунок збитків", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = damageEmergency,
            onValueChange = { damageEmergency = it },
            label = { Text("z_0еп,а, грн/кВт·год") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = damagePlanned,
            onValueChange = { damagePlanned = it },
            label = { Text("z_0еп,п, грн/кВт·год") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val omega = lambdaSingle.toDouble()
                val t = tRepair.toDouble()

                val kA = (omega * t) / 8760.0

                val kP = (1.2 * 43.0) / 8760.0

                val omegaDK = 2 * omega * (kA + kP)

                val omegaSV = 0.02

                val omegaDS = omegaDK + omegaSV

                val K = 20577.433835866505
                val zA = damageEmergency.toDouble()
                val zP = damagePlanned.toDouble()

                val totalLoss = (zA + zP) * omega * t * K

                resultText = """
        Порівняння надійності
        Частота відмов одноколової системи: ${"%.3f".format(omega)} рік⁻¹
        Частота відмов двоколової системи з секційним перемикачем: ${"%.5f".format(omegaDS)} рік⁻¹

        Розрахунок збитків від перерв електропостачання
        Математичне сподівання збитків:
        ${"%.2f".format(totalLoss)} грн
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
