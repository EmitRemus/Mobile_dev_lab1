// Main screen for navigation
package com.example.mobiledevlab1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobiledevlab1.ui.theme.GreenFuelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreenFuelTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Button(onClick = {
                            startActivity(Intent(this@MainActivity, FuelCalcActivity::class.java))
                        }, modifier = Modifier.fillMaxWidth()) {
                            Text("Fuel Composition Calculator")
                        }
                        Button(
                            onClick = {
                                val intent = Intent(this@MainActivity, FuelMazutCalcActivity::class.java)
                                this@MainActivity.startActivity(intent)
                            },
                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            Text("Fuel Mazut Calculator")
                        }
                        Button(
                            onClick = {
                                val intent = Intent(this@MainActivity, GrossEjectionCalcActivity::class.java)
                                this@MainActivity.startActivity(intent)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text("Gross Ejection Calculator")
                        }
                    }
                }
            }
        }
    }
}
