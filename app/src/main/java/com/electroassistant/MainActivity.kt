package com.electroassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFF5F7FA)
        ) {

            when (page) {

                "home" -> HomeScreen(
                    onChute = { page = "chute" },
                    onSection = { page = "section" },
                    onDisjoncteur = { page = "disjoncteur" },
                    onPdf = { page = "pdf" }
                )

                "chute" -> ChuteTensionScreen(
                    onBack = { page = "home" }
                )

                "section" -> ComingSoonScreen(
                    title = "🔌 Section de câble",
                    description = "Calcul automatique de la section du câble.",
                    onBack = { page = "home" }
                )

                "disjoncteur" -> ComingSoonScreen(
                    title = "🛡️ Calibre disjoncteur",
                    description = "Détermination du calibre adapté du disjoncteur.",
                    onBack = { page = "home" }
                )

                "pdf" -> ComingSoonScreen(
                    title = "📄 Analyse de plan PDF",
                    description = "Analyse intelligente des plans électriques.",
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
    onDisjoncteur: () -> Unit,
    onPdf: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        /* HEADER */

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF0D47A1),
                            Color(0xFF1976D2)
                        )
                    )
                )
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 35.dp,
                    bottom = 30.dp
                )
        ) {

            Column {

                Text(
                    text = "⚡",
                    style = MaterialTheme.typography.displaySmall
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "ElectroAssistant",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Assistant technique • Électricité bâtiment",
                    color = Color.White.copy(alpha = 0.85f)
                )
            }
        }

        /* CONTENU */

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = "Outils électriques",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Sélectionnez l'outil dont vous avez besoin.",
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(18.dp))

            ToolCard(
                emoji = "📐",
                title = "Chute de tension",
                description = "Calculer la chute de tension d'un circuit.",
                onClick = onChute
            )

            Spacer(modifier = Modifier.height(12.dp))

            ToolCard(
                emoji = "🔌",
                title = "Section de câble",
                description = "Déterminer la section adaptée du conducteur.",
                onClick = onSection
            )

            Spacer(modifier = Modifier.height(12.dp))

            ToolCard(
                emoji = "🛡️",
                title = "Calibre disjoncteur",
                description = "Choisir le calibre du disjoncteur.",
                onClick = onDisjoncteur
            )

            Spacer(modifier = Modifier.height(12.dp))

            ToolCard(
                emoji = "📄",
                title = "Analyser un plan PDF",
                description = "Analyser un plan électrique automatiquement.",
                onClick = onPdf
            )

            Spacer(modifier = Modifier.height(25.dp))

            /* INFO CARD */

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 3.dp
                )
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Text(
                        text = "💡 ElectroAssistant",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Un assistant destiné aux électriciens, techniciens et étudiants en électricité bâtiment.",
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Version 1.0",
                modifier = Modifier.fillMaxWidth(),
                color = Color.Gray,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
fun ToolCard(
    emoji: String,
    title: String,
    description: String,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(55.dp)
                    .background(
                        color = Color(0xFFE3F2FD),
                        shape = RoundedCornerShape(15.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = emoji,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.width(15.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = onClick,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Ouvrir")
            }
        }
    }
}

@Composable
fun ComingSoonScreen(
    title: String,
    description: String,
    onBack: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        TextButton(
            onClick = onBack
        ) {
            Text("← Retour")
        }

        Spacer(modifier = Modifier.height(25.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {

            Column(
                modifier = Modifier.padding(25.dp)
            ) {

                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = description,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(25.dp))

                Text(
                    text = "🚧 Module en préparation",
                    fontWeight = FontWeight.Bold
                )
            }
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

        TextButton(
            onClick = onBack
        ) {
            Text("← Retour")
        }

        Text(
            text = "📐 Chute de tension",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        ) {

            Column(
                modifier = Modifier.padding(18.dp)
            ) {

                Text(
                    "Type de circuit",
                    fontWeight = FontWeight.Bold
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    RadioButton(
                        selected = !triphase,
                        onClick = {
                            triphase = false
                            tension = "230"
                        }
                    )

                    Text("Monophasé")

                    Spacer(modifier = Modifier.width(12.dp))

                    RadioButton(
                        selected = triphase,
                        onClick = {
                            triphase = true
                            tension = "400"
                        }
                    )

                    Text("Triphasé")
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    "Matériau",
                    fontWeight = FontWeight.Bold
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    RadioButton(
                        selected = cuivre,
                        onClick = { cuivre = true }
                    )

                    Text("Cuivre")

                    Spacer(modifier = Modifier.width(12.dp))

                    RadioButton(
                        selected = !cuivre,
                        onClick = { cuivre = false }
                    )

                    Text("Aluminium")
                }
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        InputField(
            value = tension,
            onValueChange = { tension = it },
            label = "Tension (V)"
        )

        InputField(
            value = courant,
            onValueChange = { courant = it },
            label = "Courant I (A)"
        )

        InputField(
            value = longueur,
            onValueChange = { longueur = it },
            label = "Longueur L (m)"
        )

        InputField(
            value = section,
            onValueChange = { section = it },
            label = "Section (mm²)"
        )

        InputField(
            value = cosPhi,
            onValueChange = { cosPhi = it },
            label = "cos φ"
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = {

                val u = tension.toDoubleOrNull()
                val i = courant.toDoubleOrNull()
                val l = longueur.toDoubleOrNull()
                val s = section.toDoubleOrNull()
                val cos = cosPhi.toDoubleOrNull()

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
                        if (cuivre) 0.0175 else 0.0282

                    val sinPhi =
                        sqrt(1.0 - cos * cos)

                    val resistance =
                        rho * l / s

                    val deltaU =
                        if (triphase) {
                            sqrt(3.0) *
                                    i *
                                    resistance *
                                    cos
                        } else {
                            2.0 *
                                    i *
                                    resistance *
                                    cos
                        }

                    val percent =
                        deltaU / u * 100.0

                    resultat =
                        "ΔU = %.2f V\nChute = %.2f %%".format(
                            deltaU,
                            percent
                        )

                } else {

                    resultat = "⚠️ Vérifiez les valeurs saisies."
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {

            Text(
                text = "⚡ Calculer"
            )
        }

        if (resultat.isNotEmpty()) {

            Spacer(modifier = Modifier.height(15.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE8F5E9)
                ),
                shape = RoundedCornerShape(18.dp)
            ) {

                Text(
                    text = resultat,
                    modifier = Modifier.padding(20.dp),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(label)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        singleLine = true,
        shape = RoundedCornerShape(14.dp)
    )
}
