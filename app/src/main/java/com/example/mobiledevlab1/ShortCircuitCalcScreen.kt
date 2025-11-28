package com.example.mobiledevlab1


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.sqrt

@Composable
fun ShortCircuitCalcScreen() {
    // Input states
    var smInput by remember { mutableStateOf("1300") }
    var tfInput by remember { mutableStateOf("2.5") }
    var ikInput by remember { mutableStateOf("2.5") }
    var ctInput by remember { mutableStateOf("92") }
    var xTotalInput by remember { mutableStateOf("2.31") }
    var uNomInput by remember { mutableStateOf("10000") }
    var z1Input by remember { mutableStateOf("2.31") }
    var z2Input by remember { mutableStateOf("2.31") }
    var z0Input by remember { mutableStateOf("2.31") }

    val scrollState = rememberScrollState()

    val im = calculateIM(smInput)
    val imAfter = im * 2
    val sek = calculateSek(im, 1.4)
    val sMin = calculateSMin(ikInput, tfInput, ctInput)
    val isc3 = calculateIsc3(uNomInput, xTotalInput)
    val isc1 = calculateIsc1(uNomInput, z1Input, z2Input, z0Input)
    val ith = calculateIth(ikInput, tfInput)
    val idyn = calculateIdyn(ikInput)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text("Short Circuit Calculator", fontSize = 20.sp, fontWeight = FontWeight.Bold)


        Spacer(modifier = Modifier.height(16.dp))


        FuelInputField("Sм (кВА)", smInput) { smInput = it }
        FuelInputField("Iк (кА)", ikInput) { ikInput = it }
        FuelInputField("tф (с)", tfInput) { tfInput = it }
        FuelInputField("CT", ctInput) { ctInput = it }
        FuelInputField("X∑ (Ом)", xTotalInput) { xTotalInput = it }
        FuelInputField("Uном (кВ)", uNomInput) { uNomInput = it }
        FuelInputField("Z1 (Ом)", z1Input) { z1Input = it }
        FuelInputField("Z2 (Ом)", z2Input) { z2Input = it }
        FuelInputField("Z0 (Ом)", z0Input) { z0Input = it }


        Spacer(modifier = Modifier.height(24.dp))


        Text("Результати розрахунку:", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Iм = %.2f A".format(im))
        Text("Iм.пa = %.2f A".format(imAfter))
        Text("sек = %.2f мм²".format(sek))
        Text("sмін = %.2f мм²".format(sMin))
        Spacer(modifier = Modifier.height(8.dp))
        Text("Струм трифазного КЗ = %.2f A".format(isc3))
        Text("Струм однофазного КЗ = %.2f A".format(isc1))
        Text("Теплова стійкість = %.2f А·√с".format(ith))
        Text("Динамічна стійкість = %.2f А·√с".format(idyn))
    }
}

fun calculateIM(sm: String): Double {
    val smVal = sm.toDoubleOrNull() ?: return 0.0
    return (smVal / 2) / (sqrt(3.0) * 10)
}


fun calculateSek(im: Double, jek: Double): Double {
    return im / jek
}


fun calculateSMin(ik: String, tf: String, ct: String): Double {
    val ikVal = ik.toDoubleOrNull()?.times(1000)?: return 0.0
    val tfVal = tf.toDoubleOrNull() ?: return 0.0
    val ctVal = ct.toDoubleOrNull() ?: return 1.0
    return (ikVal * sqrt(tfVal)) / ctVal
}


fun calculateIsc3(unom: String, xTotal: String): Double {
    val u = unom.toDoubleOrNull()?.times(1000) ?: return 0.0
    val x = xTotal.toDoubleOrNull() ?: return 1.0
    return (u) / (sqrt(3.0) * x)
}


fun calculateIsc1(unom: String, z1: String, z2: String, z0: String): Double {
    val u = unom.toDoubleOrNull()?.times(1000) ?: return 0.0
    val z1Val = z1.toDoubleOrNull() ?: return 0.0
    val z2Val = z2.toDoubleOrNull() ?: return 0.0
    val z0Val = z0.toDoubleOrNull() ?: return 0.0
    return u / (z1Val + z2Val + z0Val)
}


fun calculateIth(ik: String, tf: String): Double {
    val ikVal = ik.toDoubleOrNull()?.times(1000) ?: return 0.0
    val tfVal = tf.toDoubleOrNull() ?: return 0.0
    return ikVal * sqrt(tfVal)
}


fun calculateIdyn(ik: String): Double {
    val ikVal = ik.toDoubleOrNull()?.times(1000) ?: return 0.0
    return ikVal * 2.5
}
