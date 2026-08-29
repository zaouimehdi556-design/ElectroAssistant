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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ElectroAssistantApp()
                }
            }
        }
    }
}

@Composable
fun ElectroAssistantApp() {

    var screen by remember { mutableStateOf("home") }

    when (screen) {

        "home" -> HomeScreen(
            onBreaker = { screen = "breaker" },
            onPower = { screen = "power" },
            onDrop = { screen = "drop" },
            onPlan = { screen = "plan" }
        )

        "breaker" -> BreakerScreen(
            onBack = { screen = "home" }
        )

        "power" -> PowerScreen(
            onBack = { screen = "home" }
        )

        "drop" -> VoltageDropScreen(
            onBack = { screen = "home" }
        )

        "plan" -> PlanScreen(
            onBack = { screen = "home" }
        )
    }
}

/* =========================================================
   HOME
   ========================================================= */

@Composable
fun HomeScreen(
    onBreaker: () -> Unit,
    onPower: () -> Unit,
    onDrop: () -> Unit,
    onPlan: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "ElectroAssistant",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Application de calcul électrique",
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "NF C 15-100",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(15.dp))

        MenuButton(
            title = "⚡ Calcul du disjoncteur",
            description = "Calculer le courant et choisir une protection",
            onClick = onBreaker
        )

        MenuButton(
            title = "🔌 Calcul de puissance",
            description = "Calculer P, U, I et cos φ",
            onClick = onPower
        )

        MenuButton(
            title = "📏 Chute de tension",
            description = "Calculer la chute de tension dans un câble",
            onClick = onDrop
        )

        MenuButton(
            title = "🏠 Plan électrique",
            description = "Estimation des circuits d'une installation",
            onClick = onPlan
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Assistant électrique",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Outil de calcul destiné à l'estimation et à la vérification des installations électriques."
                )
            }
        }
    }
}

@Composable
fun MenuButton(
    title: String,
    description: String,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ouvrir")
            }
        }
    }
}

/* =========================================================
   BREAKER
   ========================================================= */

@Composable
fun BreakerScreen(
    onBack: () -> Unit
) {

    var powerText by remember { mutableStateOf("") }
    var voltageText by remember { mutableStateOf("230") }
    var cosText by remember { mutableStateOf("1") }

    var result by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        BackButton(onBack)

        Text(
            text = "Calcul du disjoncteur",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(15.dp))

        SectionTitle("Données")

        NumberField(
            value = powerText,
            label = "Puissance (W)",
            onValueChange = { powerText = it }
        )

        Spacer(modifier = Modifier.height(10.dp))

        NumberField(
            value = voltageText,
            label = "Tension (V)",
            onValueChange = { voltageText = it }
        )

        Spacer(modifier = Modifier.height(10.dp))

        NumberField(
            value = cosText,
            label = "cos φ",
            onValueChange = { cosText = it }
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = {

                val power = powerText.toDoubleOrNull()
                val voltage = voltageText.toDoubleOrNull()
                val cos = cosText.toDoubleOrNull()

                if (power == null || voltage == null || cos == null) {

                    error = "Veuillez remplir tous les champs correctement."
                    result = ""

                } else if (power <= 0 || voltage <= 0 || cos <= 0) {

                    error = "Les valeurs doivent être positives."
                    result = ""

                } else {

                    val current = power / (voltage * cos)
                    val breaker = chooseBreaker(current)

                    error = ""

                    result =
                        "Courant calculé : ${formatNumber(current)} A\n\n" +
                        "Disjoncteur conseillé : ${breaker} A\n\n" +
                        "Vérifiez également la section du conducteur et les conditions réelles de l'installation."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculer")
        }

        Spacer(modifier = Modifier.height(15.dp))

        if (error.isNotEmpty()) {
            ErrorBox(error)
        }

        if (result.isNotEmpty()) {
            ResultBox(result)
        }
    }
}

/* =========================================================
   POWER
   ========================================================= */

@Composable
fun PowerScreen(
    onBack: () -> Unit
) {

    var voltageText by remember { mutableStateOf("230") }
    var currentText by remember { mutableStateOf("") }
    var cosText by remember { mutableStateOf("1") }

    var result by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        BackButton(onBack)

        Text(
            text = "Calcul de puissance",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(15.dp))

        NumberField(
            value = voltageText,
            label = "Tension U (V)",
            onValueChange = { voltageText = it }
        )

        Spacer(modifier = Modifier.height(10.dp))

        NumberField(
            value = currentText,
            label = "Courant I (A)",
            onValueChange = { currentText = it }
        )

        Spacer(modifier = Modifier.height(10.dp))

        NumberField(
            value = cosText,
            label = "cos φ",
            onValueChange = { cosText = it }
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = {

                val voltage = voltageText.toDoubleOrNull()
                val current = currentText.toDoubleOrNull()
                val cos = cosText.toDoubleOrNull()

                if (voltage == null || current == null || cos == null) {

                    error = "Veuillez remplir tous les champs."
                    result = ""

                } else if (voltage <= 0 || current <= 0 || cos <= 0) {

                    error = "Les valeurs doivent être positives."
                    result = ""

                } else {

                    val power = voltage * current * cos
                    val apparent = voltage * current

                    error = ""

                    result =
                        "Puissance
