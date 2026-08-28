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

            when (page) {

                "home" -> HomeScreen(
                    onChute = { page = "chute" },
                    onSection = { page = "section" },
                    onDisjoncteur = { page = "disjoncteur" }
                )

                "chute" -> ChuteTensionScreen(
                    onBack = { page = "home" }
                )

                "section" -> SectionCableScreen(
                    onBack = { page = "home" }
                )

                "disjoncteur" -> DisjoncteurScreen(
                    onBack = { page = "home" }
                )
            }
        }
    }
}

@Composable
fun HomeScreen(
    onChute: () -> Unit,
    onSection: () -> Unit,
    onDisjoncteur: () -> Unit
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

        Text("Assistant technique – Électricité bâtiment")

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = onChute,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("📐 Chute de tension")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onSection,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("🔌 Section de câble")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onDisjoncteur,
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
    var resultat by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        Text(
            "📐 Chute de tension",
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
                "Monophasé",
                modifier = Modifier.padding(top = 12.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            RadioButton(
                selected = triphase,
                onClick = {
                    triphase = true
                    tension = "400"
                }
            )

            Text(
                "Triphasé",
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text("Matériau")

        Row {

            RadioButton(
                selected = cuivre,
                onClick = { cuivre = true }
            )

            Text(
                "Cuivre",
                modifier = Modifier.padding(top = 12.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            RadioButton(
                selected = !cuivre,
                onClick = { cuivre = false }
            )

            Text(
                "Aluminium",
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = tension,
            onValueChange = { tension = it },
            label = { Text("Tension (V)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = courant,
            onValueChange = { courant = it },
            label = { Text("Courant (A)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = longueur,
            onValueChange = { longueur = it },
            label = { Text("Longueur (m)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = section,
            onValueChange = { section = it },
            label = { Text("Section (mm²)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                val u = tension.replace(",", ".").toDoubleOrNull()
                val i = courant.replace(",", ".").toDoubleOrNull()
                val l = longueur.replace(",", ".").toDoubleOrNull()
                val s = section.replace(",", ".").toDoubleOrNull()

                if (u != null && i != null && l != null && s != null &&
                    u > 0 && i >= 0 && l >= 0 && s > 0
                ) {

                    val rho = if (cuivre) 0.0175 else 0.0282

                    val r = rho * l / s

                    val deltaU =
                        if (triphase) {
                            sqrt(3.0) * i * r
                        } else {
                            2.0 * i * r
                        }

                    val percent = deltaU / u * 100.0

                    resultat =
                        "Matériau : ${if (cuivre) "Cuivre" else "Aluminium"}\n\n" +
                        "ΔU = %.2f V\n".format(deltaU) +
                        "ΔU = %.2f %%".format(percent)

                } else {
                    resultat = "⚠️ Vérifie les valeurs."
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
                    resultat,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun SectionCableScreen(
    onBack: () -> Unit
) {

    var cuivre by remember { mutableStateOf(true) }
    var courant by remember { mutableStateOf("") }
    var longueur by remember { mutableStateOf("") }
    var tension by remember { mutableStateOf("230") }
    var triphase by remember { mutableStateOf(false) }
    var resultat by remember { mutableStateOf("") }

    val sections = listOf(
        1.5,
        2.5,
        4.0,
        6.0,
        10.0,
        16.0,
        25.0,
        35.0,
        50.0,
        70.0,
        95.0,
        120.0,
        150.0,
        185.0,
        240.0
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        Text(
            "🔌 Section de câble",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = onBack) {
            Text("← Retour")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Matériau")

        Row {

            RadioButton(
                selected = cuivre,
                onClick = { cuivre = true }
            )

            Text(
                "Cuivre",
                modifier = Modifier.padding(top = 12.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            RadioButton(
                selected = !cuivre,
                onClick = { cuivre = false }
            )

            Text(
                "Aluminium",
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        Text("Réseau")

        Row {

            RadioButton(
                selected = !triphase,
                onClick = {
                    triphase = false
                    tension = "230"
                }
            )

            Text(
                "Mono",
                modifier = Modifier.padding(top = 12.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            RadioButton(
                selected = triphase,
                onClick = {
                    triphase = true
                    tension = "400"
                }
            )

            Text(
                "Tri",
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = tension,
            onValueChange = { tension = it },
            label = { Text("Tension (V)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = courant,
            onValueChange = { courant = it },
            label = { Text("Courant prévu (A)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = longueur,
            onValueChange = { longueur = it },
            label = { Text("Longueur (m)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                val i = courant.replace(",", ".").toDoubleOrNull()

                if (i != null && i > 0) {

                    val section = when {
                        i <= 10 -> sections[0]
                        i <= 16 -> sections[1]
                        i <= 25 -> sections[2]
                        i <= 32 -> sections[3]
                        i <= 50 -> sections[4]
                        i <= 63 -> sections[5]
                        i <= 80 -> sections[6]
                        i <= 100 -> sections[7]
                        i <= 125 -> sections[8]
                        i <= 160 -> sections[9]
                        i <= 200 -> sections[10]
                        i <= 250 -> sections[11]
                        i <= 315 -> sections[12]
                        i <= 355 -> sections[13]
                        else -> sections[14]
                    }

                    resultat =
                        "Section indicative : %.1f mm²\n\n".format(section) +
                        "Matériau : ${if (cuivre) "Cuivre" else "Aluminium"}\n" +
                        "Courant : %.1f A\n".format(i) +
                        "Longueur : $longueur m\n\n" +
                        "⚠️ Vérification du mode de pose et de la chute de tension nécessaire."

                } else {

                    resultat = "⚠️ Entre un courant valide."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("🔍 Déterminer la section")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (resultat.isNotEmpty()) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                tonalElevation = 4.dp
            ) {
                Text(
                    resultat,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun DisjoncteurScreen(
    onBack: () -> Unit
) {

    var courant by remember { mutableStateOf("") }
    var resultat by remember { mutableStateOf("") }

    val calibres = listOf(
        2,
        4,
        6,
        10,
        16,
        20,
        25,
        32,
        40,
        50,
        63,
        80,
        100,
        125,
        160,
        200,
        250,
        315,
        400
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        Text(
            "🛡️ Calibre disjoncteur",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = onBack) {
            Text("← Retour")
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = courant,
            onValueChange = { courant = it },
            label = { Text("Courant de charge (A)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                val i = courant.replace(",", ".").toDoubleOrNull()

                if (i != null && i > 0) {

                    val calibre = calibres.firstOrNull {
                        it >= i
                    }

                    if (calibre != null) {

                        resultat =
                            "Courant : %.1f A\n\n".format(i) +
                            "Calibre supérieur disponible : ${calibre} A\n\n" +
                            "⚠️ Le choix final doit vérifier la capacité du câble, " +
                            "le mode de pose et les règles de protection."

                    } else {

                        resultat =
                            "⚠️ Courant supérieur à la liste."
                    }

                } else {

                    resultat =
                        "⚠️ Entre un courant valide."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("🔍 Choisir le calibre")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (resultat.isNotEmpty()) {

            Surface(
                modifier = Modifier.fillMaxWidth(),
                tonalElevation = 4.dp
            ) {

                Text(
                    resultat,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
