package com.electroassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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


/* =========================================================
   HOME
   ========================================================= */

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


/* =========================================================
   CHUTE DE TENSION
   ========================================================= */

@Composable
fun ChuteTensionScreen(
    onBack: () -> Unit
) {

    var triphase by remember { mutableStateOf(false) }
    var cuivre by remember { mutableStateOf(true) }

    var tension by remember { mutableStateOf("230") }
    var courant by remember { mutableStateOf("") }
    var longueur by remember { mutableStateOf("") }
    var section by remember { mutableStateOf("4") }
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

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = onBack
        ) {
            Text("← Retour")
        }

        Spacer(modifier = Modifier.height(15.dp))

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
            onValueChange = { tension = it },
            label = { Text("Tension (V)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = courant,
            onValueChange = { courant = it },
            label = { Text("Courant I (A)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = longueur,
            onValueChange = { longueur = it },
            label = { Text("Longueur L (m)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = section,
            onValueChange = { section = it },
            label = { Text("Section (mm²)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = cosPhi,
            onValueChange = { cosPhi = it },
            label = { Text("cos φ") },
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

                    val rho = if (cuivre) {
                        0.0175
                    } else {
                        0.0282
                    }

                    val resistance = rho * l / s

                    val deltaU = if (triphase) {
                        sqrt(3.0) * i * resistance * cos
                    } else {
                        2.0 * i * resistance * cos
                    }

                    val pourcentage = deltaU / u * 100.0

                    resultat =
                        "Chute de tension : %.2f V\nPourcentage : %.2f %%".format(
                            deltaU,
                            pourcentage
                        )

                } else {

                    resultat = "⚠️ Vérifiez les valeurs saisies."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculer")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (resultat.isNotEmpty()) {

            Text(
                text = resultat,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}


/* =========================================================
   SECTION DE CÂBLE
   ========================================================= */

@Composable
fun SectionCableScreen(
    onBack: () -> Unit
) {

    var cuivre by remember { mutableStateOf(true) }
    var courant by remember { mutableStateOf("") }
    var longueur by remember { mutableStateOf("") }
    var resultat by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        Text(
            text = "🔌 Section de câble",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = onBack
        ) {
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

            Spacer(modifier = Modifier.width(15.dp))

            RadioButton(
                selected = !cuivre,
                onClick = { cuivre = false }
            )

            Text(
                "Aluminium",
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

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

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                val i = courant.replace(",", ".").toDoubleOrNull()
                val l = longueur.replace(",", ".").toDoubleOrNull()

                if (i != null && l != null && i > 0 && l >= 0) {

                    /*
                     * Estimation simple.
                     * Les sections proposées sont des sections
                     * normalisées courantes.
                     */

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
                        70.0
                    )

                    val sectionChoisie = when {
                        i <= 10 -> 1.5
                        i <= 16 -> 2.5
                        i <= 25 -> 4.0
                        i <= 32 -> 6.0
                        i <= 40 -> 10.0
                        i <= 63 -> 16.0
                        i <= 80 -> 25.0
                        i <= 100 -> 35.0
                        i <= 125 -> 50.0
                        else -> 70.0
                    }

                    val materiau = if (cuivre) {
                        "Cuivre"
                    } else {
                        "Aluminium"
                    }

                    resultat =
                        "Section conseillée : %.1f mm²\nMatériau : %s\n\n⚠️ Vérification finale selon mode de pose, température et norme nécessaire."
                            .format(sectionChoisie, materiau)

                } else {

                    resultat = "⚠️ Vérifiez les valeurs."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculer la section")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (resultat.isNotEmpty()) {

            Text(
                text = resultat,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}


/* =========================================================
   DISJONCTEUR
   ========================================================= */

@Composable
fun DisjoncteurScreen(
    onBack: () -> Unit
) {

    var courant by remember { mutableStateOf("") }
    var resultat by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "🛡️ Calibre disjoncteur",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = onBack
        ) {
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

                    val calibre = when {
                        i <= 10 -> 10
                        i <= 16 -> 16
                        i <= 20 -> 20
                        i <= 25 -> 25
                        i <= 32 -> 32
                        i <= 40 -> 40
                        i <= 50 -> 50
                        i <= 63 -> 63
                        else -> 80
                    }

                    resultat =
                        "Calibre proposé : $calibre A"

                } else {

                    resultat = "⚠️ Entrez un courant valide."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculer")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (resultat.isNotEmpty()) {

            Text(
                text = resultat,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
