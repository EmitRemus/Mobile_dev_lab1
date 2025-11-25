package com.example.mobiledevlab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mobiledevlab1.ui.theme.GreenFuelTheme

class GrossEjectionCalcActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreenFuelTheme {
                GrossEjectionCalcScreen()
            }
        }
    }
}