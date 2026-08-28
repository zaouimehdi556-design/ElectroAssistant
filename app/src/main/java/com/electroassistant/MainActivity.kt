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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

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

                "section" -> SectionCableScreen(
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

/* =========================
   ACCUEIL
   ========================= */

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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1565C0))
                .padding(24.dp)
        ) {

            Text(
                text = "⚡",
                style = MaterialTheme.typography.displaySmall
            )

            Text(
                text = "ElectroAssistant",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Assistant technique • Électricité bâtiment",
                color = Color.White.copy(alpha = 0.85f)
            )
        }

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = "Outils électriques",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Choisissez l'outil dont vous avez besoin.",
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(18.dp))

            ToolCard(
                emoji = "📐",
                title = "Chute de tension",
                description = "Calcul de la chute de tension.",
                onClick = onChute
            )

            Spacer(modifier = Modifier.height(12.dp))

            ToolCard(
                emoji = "🔌",
                title = "Section de câble",
                description = "Dimensionnement du câble.",
                onClick = onSection
            )

            Spacer(modifier = Modifier.height(12.dp))

            ToolCard(
                emoji = "🛡️",
                title = "Calibre disjoncteur",
                description = "Choix du calibre adapté.",
                onClick = onDisjoncteur
            )

            Spacer(modifier = Modifier.height(12.dp))

            ToolCard(
                emoji = "📄",
                title = "Analyser un plan PDF",
                description = "Analyse d'un plan électrique.",
                onClick = onPdf
            )

            Spacer(modifier = Modifier.height(22.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp)
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Text(
                        "💡 ElectroAssistant",
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    Text(
                        "Outil d'aide au pré-dimensionnement électrique pour les installations bâtiment.",
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "Version 1.0",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.Gray
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
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(54.dp)
                    .background(
                        Color(0xFFE3F2FD),
                        RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    emoji,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    title,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    description,
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

/* =========================
   SECTION CÂBLE
   ========================= */

@Composable
fun SectionCableScreen(
    onBack: () -> Unit
) {

    var triphase by remember { mutableStateOf(false) }
    var cuivre by remember { mutableStateOf(true) }

    var courant by remember { mutableStateOf("") }
    var longueur by remember { mutableStateOf("") }
    var cosPhi by remember { mutableStateOf("0.90") }
    var chuteMax by remember { mutableStateOf("5") }

    var loadType by remember {
        mutableStateOf(LoadType.POWER)
    }

    var installation by remember {
        mutableStateOf(InstallationMethod.C)
    }

    var isolation by remember {
        mutableStateOf(Insulation.PVC)
    }

    var conducteurs by remember {
        mutableStateOf("2")
    }

    var temperature by remember {
        mutableStateOf("30")
    }

    var grouped by remember {
        mutableStateOf("1")
    }

    var resultat by remember {
        mutableStateOf<CableSizingResult?>(null)
    }

    var erreur by remember {
        mutableStateOf("")
    }

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
            "🔌 Section de câble",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            "Pré-dimensionnement du conducteur",
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(18.dp))

        /* Circuit */

        SectionCard("⚡ Circuit") {

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
                    }
                )

                Text("Monophasé")

                Spacer(modifier = Modifier.width(10.dp))

                RadioButton(
                    selected = triphase,
                    onClick = {
                        triphase = true
                    }
                )

                Text("Triphasé")
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                "Matériau",
                fontWeight = FontWeight.Bold
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                RadioButton(
                    selected = cuivre,
                    onClick = {
                        cuivre = true
                    }
                )

                Text("Cuivre")

                Spacer(modifier = Modifier.width(10.dp))

               
