package com.electroassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ElectroAssistantApp()
        }
    }
}

@Composable
fun ElectroAssistantApp() {

    var page by remember { mutableStateOf("home") }

    MaterialTheme {

        Surface(
            modifier = Modifier.fillMaxSize()
        ) {

            if (page == "home") {

                HomeScreen(
                    onChute = {
                        page = "chute"
                    }
                )

            } else {

                ChuteTensionScreen(
                    onBack = {
                        page = "home"
                    }
                )
            }
        }
    }
}

@Composable
fun HomeScreen(
    onChute: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "⚡ ElectroAssistant",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Assistant technique – Électricité bâtiment"
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = onChute,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("📐 Chute de tension")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("🔌 Section de câble")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("🛡️ Calibre disjoncteur")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("📄 Analyser un plan PDF")
        }
    }
}

@Composable
fun ChuteTensionScreen(
    onBack: () -> Unit
) {

    var triphase by remember { mutableStateOf(false) }

    var cuivre by remember { mutableStateOf(true) }

    var tension by remember { mutableStateOf("230") }

    var courant by remember { mutableStateOf("") }

    var longueur by remember { mutableStateOf("") }

    var section by remember { mutableStateOf("2.5") }

    var cosPhi by remember { mutableStateOf("0.90") }

    var resultat by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        Text(
            text = "📐 Chute de tension",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = onBack) {
            Text("← Retour")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Type de circuit")

        Row {

            RadioButton(
                selected = !triphase,
                onClick = {
                    triphase = false
                    tension = "230"
                }
            )

            Text(
                text = "Monophasé",
                modifier = Modifier.padding(top = 12.dp)
            )

            Spacer(modifier = Modifier.width(15.dp))

            RadioButton(
                selected = triphase,
                onClick = {
                    triphase = true
                    tension = "400"
                }
            )

            Text(
                text = "Triphasé",
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text("Matériau du conducteur")

        Row {

            RadioButton(
                selected = cuivre,
                onClick = {
                    cuivre = true
                }
            )

            Text(
                text = "Cuivre",
                modifier = Modifier.padding(top = 12.dp)
            )

            Spacer(modifier = Modifier.width(15.dp))

            RadioButton(
                selected = !cuivre,
                onClick = {
                    cuivre = false
                }
            )

            Text(
                text = "Aluminium",
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = tension,
            onValueChange = {
                tension = it
            },
            label = {
                Text("Tension (V)")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = courant,
            onValueChange = {
                courant = it
            },
            label = {
                Text("Courant I (A)")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = longueur,
            onValueChange = {
                longueur = it
            },
            label = {
                Text("Longueur L (m)")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = section,
            onValueChange = {
                section = it
            },
            label = {
                Text("Section (mm²)")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = cosPhi,
            onValueChange = {
                cosPhi = it
            },
            label = {
                Text("cos φ")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                val u = tension.replace(",", ".").toDoubleOrNull()
                val i = courant.replace(",", ".").toDoubleOrNull()
                val l = longueur.replace(",", ".").toDoubleOrNull()
                val s = section.replace(",", ".").toDoubleOrNull()
                val cos = cosPhi.replace(",", ".").toDoubleOrNull()

                if (
                    u != null &&
                    i != null &&
                    l != null &&
                    s != null &&
                    cos != null &&
                    u > 0 &&
                    i >= 0 &&
                    l >= 0 &&
                    s > 0 &&
                    cos > 0 &&
                    cos <= 1
                ) {

                    val rho =
                        if (cuivre) {
                            0.0175
                        } else {
                            0.0282
                        }

                    val resistance = rho * l / s

                    val deltaU =
                        if (triphase) {
                            sqrt(3.0) * i * resistance
                        } else {
                            2.0 * i * resistance
                        }

                    val pourcentage =
                        deltaU / u * 100.0

                    resultat =
                        """
                        ✅ Résultat
                        
                        Matériau : ${if (cuivre) "Cuivre" else "Aluminium"}
                        
                        Circuit : ${if (triphase) "Triphasé" else "Monophasé"}
                        
                        Résistance : ${"%.4f".format(resistance)} Ω
                        
                        Chute de tension : ${"%.2f".format(deltaU)} V
                        
                        Chute : ${"%.2f".format(pourcentage)} %
                        """.trimIndent()

                } else {

                    resultat = "⚠️ Vérifie les valeurs saisies."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("🧮 Calculer")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (resultat.isNotEmpty()) {

            Surface(
                modifier = Modifier.fillMaxWidth(),
                tonalElevation = 4.dp
            ) {

                Text(
                    text = resultat,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Cuivre : 0.0175 Ω·mm²/m   |   Aluminium : 0.0282 Ω·mm²/m",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
