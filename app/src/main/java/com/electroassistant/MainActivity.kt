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
import kotlin.math.sqrt

// ============================================================
// MAIN
// ============================================================

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ElectroAssistantApp()
        }
    }
}

// ============================================================
// APP
// ============================================================

@Composable
fun ElectroAssistantApp() {

    var page by remember {
        mutableStateOf("home")
    }

    MaterialTheme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFF5F7FA)
        ) {

            when (page) {

                "home" -> HomeScreen(
                    onChute = {
                        page = "chute"
                    },
                    onSection = {
                        page = "section"
                    },
                    onDisjoncteur = {
                        page = "disjoncteur"
                    },
                    onPdf = {
                        page = "pdf"
                    }
                )

                "chute" -> ChuteTensionScreen(
                    onBack = {
                        page = "home"
                    }
                )

                "section" -> SectionCableScreen(
                    onBack = {
                        page = "home"
                    }
                )

                "disjoncteur" -> ComingSoonScreen(
                    title = "🛡️ Calibre disjoncteur",
                    description = "Cette fonction sera ajoutée dans la prochaine étape.",
                    onBack = {
                        page = "home"
                    }
                )

                "pdf" -> ComingSoonScreen(
                    title = "📄 Analyse de plan PDF",
                    description = "L'analyse intelligente des plans électriques sera ajoutée prochainement.",
                    onBack = {
                        page = "home"
                    }
                )
            }
        }
    }
}

// ============================================================
// HOME
// ============================================================

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

        // HEADER

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

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "ElectroAssistant",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

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

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Sélectionnez l'outil dont vous avez besoin.",
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(18.dp))

            ToolCard(
                emoji = "📐",
                title = "Chute de tension",
                description = "Calculer la chute de tension d'une ligne.",
                onClick = onChute
            )

            Spacer(modifier = Modifier.height(12.dp))

            ToolCard(
                emoji = "🔌",
                title = "Section de câble",
                description = "Déterminer une section de câble adaptée.",
                onClick = onSection
            )

            Spacer(modifier = Modifier.height(12.dp))

            ToolCard(
                emoji = "🛡️",
                title = "Calibre disjoncteur",
                description = "Déterminer le calibre du disjoncteur.",
                onClick = onDisjoncteur
            )

            Spacer(modifier = Modifier.height(12.dp))

            ToolCard(
                emoji = "📄",
                title = "Analyser un plan PDF",
                description = "Analyser un plan électrique.",
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
                        text = "💡 À propos",
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "ElectroAssistant est un outil d'aide au pré-dimensionnement électrique pour les installations bâtiment.",
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "ElectroAssistant • Version 1.0",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
        }
    }
}

// ============================================================
// TOOL CARD
// ============================================================

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
                    text = emoji,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    fontWeight = FontWeight.Bold
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

// ============================================================
// SECTION CABLE
// ============================================================

@Composable
fun SectionCableScreen(
    onBack: () -> Unit
) {

    var triphase by remember {
        mutableStateOf(false)
    }

    var cuivre by remember {
        mutableStateOf(true)
    }

    var courant by remember {
        mutableStateOf("")
    }

    var longueur by remember {
        mutableStateOf("")
    }

    var cosPhi by remember {
        mutableStateOf("0.90")
    }

    var chuteMax by remember {
        mutableStateOf("5")
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
            text = "🔌 Section de câble",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Pré-dimensionnement du conducteur",
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(18.dp))

        // ====================================================
        // CIRCUIT
        // ====================================================

        SectionCard(
            title = "⚡ Circuit"
        ) {

            Text(
                text = "Type de circuit",
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

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Matériau",
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

                RadioButton(
                    selected = !cuivre,
                    onClick = {
                        cuivre = false
                    }
                )

                Text("Aluminium")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ====================================================
        // DONNEES
        // ====================================================

        SectionCard(
            title = "📏 Données électriques"
        ) {

            OutlinedTextField(
                value = courant,
                onValueChange = {
                    courant = it
                    erreur = ""
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
                    erreur = ""
                },
                label = {
                    Text("Longueur L (m)")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = cosPhi,
                onValueChange = {
                    cosPhi = it
                    erreur = ""
                },
                label = {
                    Text("cos φ")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = chuteMax,
                onValueChange = {
                    chuteMax = it
                    erreur = ""
                },
                label = {
                    Text("Chute maximale (%)")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ====================================================
        // MODE DE POSE
        // ====================================================

        SectionCard(
            title = "🏗️ Installation"
        ) {

            DropdownField(
                label = "Mode de pose",
                selected = installationLabel(installation),
                options = listOf(
                    "A1",
                    "A2",
                    "B1",
                    "B2",
                    "C",
                    "D1",
                    "E",
                    "F"
                ),
                onSelected = {
                    installation = installationFromLabel(it)
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            DropdownField(
                label = "Isolation",
                selected = insulationLabel(isolation),
                options = listOf(
                    "PVC",
                    "XLPE"
                ),
                onSelected = {
                    isolation = if (it == "PVC") {
                        Insulation.PVC
                    } else {
                        Insulation.XLPE
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = conducteurs,
                onValueChange = {
                    conducteurs = it
                    erreur = ""
                },
                label = {
                    Text("Conducteurs chargés")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = temperature,
                onValueChange = {
                    temperature = it
                    erreur = ""
                },
                label = {
                    Text("Température ambiante (°C)")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = grouped,
                onValueChange = {
                    grouped = it
                    erreur = ""
                },
                label = {
                    Text("Circuits groupés")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // ====================================================
        // ERREUR
        // ====================================================

        if (erreur.isNotEmpty()) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFEBEE)
                ),
                shape = RoundedCornerShape(14.dp)
            ) {

                Text(
                    text = erreur,
                    modifier = Modifier.padding(16.dp),
                    color = Color(0xFFC62828)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        // ====================================================
        // BUTTON
        // ====================================================

        Button(
            onClick = {

                val currentValue =
                    courant.replace(',', '.').toDoubleOrNull()

                val lengthValue =
                    longueur.replace(',', '.').toDoubleOrNull()

                val cosValue =
                    cosPhi.replace(',', '.').toDoubleOrNull()

                val maxDropValue =
                    chuteMax.replace(',', '.').toDoubleOrNull()

                val tempValue =
                    temperature.replace(',', '.').toDoubleOrNull()

                val groupedValue =
                    grouped.toIntOrNull()

                val conductorsValue =
                    conducteurs.toIntOrNull()

                if (
                    currentValue == null ||
                    lengthValue == null ||
                    cosValue == null ||
                    maxDropValue == null ||
                    tempValue == null ||
                    groupedValue == null ||
                    conductorsValue == null
                ) {

                    erreur = "⚠️ Vérifiez les valeurs saisies."
                    resultat = null

                } else {

                    val input = CableSizingInput(
                        material = if (cuivre) {
                            Material.COPPER
                        } else {
                            Material.ALUMINIUM
                        },
                        phase = if (triphase) {
                            Phase.THREE_PHASE
                        } else {
                            Phase.SINGLE_PHASE
                        },
                        loadType = LoadType.POWER,
                        current = currentValue,
                        length = lengthValue,
                        maxVoltageDropPercent = maxDropValue,
                        cosPhi = cosValue,
                        installationMethod = installation,
                        insulation = isolation,
                        loadedConductors = conductorsValue,
                        ambientTemperature = tempValue,
                        groupedCircuits = groupedValue
                    )

                    val result =
                        calculateCableSizing(input)

                    if (result == null) {

                        erreur =
                            "⚠️ Aucune section adaptée trouvée. Vérifiez les paramètres."

                        resultat = null

                    } else {

                        erreur = ""
                        resultat = result
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(14.dp)
        ) {

            Text(
                text = "Calculer la section",
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // ====================================================
        // RESULT
        // ====================================================

        resultat?.let { result ->

            ResultCard(
                result = result
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

// ============================================================
// SECTION CARD
// ============================================================

@Composable
fun SectionCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            content = content
        ) {

            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            content()
        }
    }
}

// ============================================================
// DROPDOWN
// ============================================================

@Composable
fun DropdownField(
    label: String,
    selected: String,
    options: List<String>,
    onSelected: (String) -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    Column {

        Text(
            text = label,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedButton(
                onClick = {
                    expanded = true
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {

                Text(
                    text = selected,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )

                Text("▼")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {

                options.forEach { option ->

                    DropdownMenuItem(
                        text = {
                            Text(option)
                        },
                        onClick = {

                            onSelected(option)

                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

// ============================================================
// RESULT CARD
// ============================================================

@Composable
fun ResultCard(
    result: CableSizingResult
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE8F5E9)
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = "✅ Résultat",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Section recommandée",
                color = Color.Gray
            )

            Text(
                text = "${formatNumber(result.section)} mm²",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(14.dp))

            ResultLine(
                label = "Chute de tension",
                value = "${formatNumber(result.voltageDropVolts)} V"
            )

            ResultLine(
                label = "Chute en %",
                value = "${formatNumber(result.voltageDropPercent)} %"
            )

            ResultLine(
                label = "Résistance",
                value = "${formatNumber(result.resistance)} Ω"
            )

            ResultLine(
                label = "Facteur correction",
                value = formatNumber(result.correctionFactor)
            )

            result.warning?.let {

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = it,
                    color = Color(0xFFE65100),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// ============================================================
// RESULT LINE
// ============================================================

@Composable
fun ResultLine(
    label: String,
    value: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = label,
            color = Color.Gray
        )

        Text(
            text = value,
            fontWeight = FontWeight.Bold
        )
    }
}

// ============================================================
// CHUTE TENSION
// ============================================================

@Composable
fun ChuteTensionScreen(
    onBack: () -> Unit
) {

    var triphase by remember {
        mutableStateOf(false)
    }

    var cuivre by remember {
        mutableStateOf(true)
    }

    var tension by remember {
        mutableStateOf("230")
    }

    var courant by remember {
        mutableStateOf("")
    }

    var longueur by remember {
        mutableStateOf("")
    }

    var section by remember {
        mutableStateOf("2.5")
    }

    var cosPhi by remember {
        mutableStateOf("0.90")
    }

    var resultat by remember {
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
            text = "📐 Chute de tension",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(18.dp))

        SectionCard(
            title = "⚡ Paramètres"
        ) {

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

                Spacer(modifier = Modifier.width(10.dp))

                RadioButton(
                    selected = triphase,
                    onClick = {
                        triphase = true
                        tension = "400"
                    }
                )

                Text("Triphasé")
            }

            Spacer(modifier = Modifier.height(8.dp))

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

                RadioButton(
                    selected = !cuivre,
                    onClick = {
                        cuivre = false
                    }
                )

                Text("Aluminium")
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
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = {

                val u =
                    tension.replace(',', '.').toDoubleOrNull()

                val i =
                    courant.replace(',', '.').toDoubleOrNull()

                val l =
                    longueur.replace(',', '.').toDoubleOrNull()

                val s =
                    section.replace(',', '.').toDoubleOrNull()

                val cos =
                    cosPhi.replace(',', '.').toDoubleOrNull()

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
                        "ΔU = ${formatNumber(deltaU)} V\n" +
                                "Chute = ${formatNumber(percent)} %"

                } else {

                    resultat =
                        "⚠️ Vérifiez les valeurs saisies."
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(14.dp)
        ) {

            Text(
                text = "Calculer",
                fontWeight = FontWeight.Bold
            )
        }

        if (resultat.isNotEmpty()) {

            Spacer(modifier = Modifier.height(18.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE3F2FD)
                )
            ) {

                Text(
                    text = resultat,
                    modifier = Modifier.padding(20.dp),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// ============================================================
// COMING SOON
// ============================================================

@Composable
fun ComingSoonScreen(
    title: String,
    description: String,
    onBack: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "← Retour",
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(80.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = description,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "🚧",
            style = MaterialTheme.typography.displaySmall
        )
    }
}

// ============================================================
// DATA
// ============================================================

enum class Material {
    COPPER,
    ALUMINIUM
}

enum class Phase {
    SINGLE_PHASE,
    THREE_PHASE
}

enum class LoadType {
    LIGHTING,
    POWER,
    MOTOR
}

enum class InstallationMethod {
    A1,
    A2,
    B1,
    B2,
    C,
    D1,
    E,
    F
}

enum class Insulation {
    PVC,
    XLPE
}

data class CableSizingInput(
    val material: Material,
    val phase: Phase,
    val loadType: LoadType,
    val current: Double,
    val length: Double,
    val maxVoltageDropPercent: Double,
    val cosPhi: Double,
    val installationMethod: InstallationMethod,
    val insulation: Insulation,
    val loadedConductors: Int,
    val ambientTemperature: Double,
    val groupedCircuits: Int
)

data class CableSizingResult(
    val section: Double,
    val voltageDropVolts: Double,
    val voltageDropPercent: Double,
    val resistance: Double,
    val correctionFactor: Double,
    val warning: String?
)

// ============================================================
// CALCULATION
// ============================================================

fun calculateCableSizing(
    input: CableSizingInput
): CableSizingResult? {

    if (
        input.current <= 0 ||
        input.length <= 0 ||
        input.cosPhi <= 0 ||
        input.cosPhi > 1 ||
        input.maxVoltageDropPercent <= 0 ||
        input.groupedCircuits < 1 ||
        input.loadedConductors < 1
    ) {
        return null
    }

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

    val rho =
        if (input.material == Material.COPPER) {
            0.0175
        } else {
            0.0282
        }

    val temperatureFactor =
        when (input.insulation) {

            Insulation.PVC -> when {
                input.ambientTemperature <= 30 -> 1.00
                input.ambientTemperature <= 35 -> 0.94
                input.ambientTemperature <= 40 -> 0.87
                input.ambientTemperature <= 45 -> 0.79
                input.ambientTemperature <= 50 -> 0.71
                input.ambientTemperature <= 55 -> 0.61
                input.ambientTemperature <= 60 -> 0.50
                else -> 0.0
            }

            Insulation.XLPE -> when {
                input.ambientTemperature <= 30 -> 1.00
                input.ambientTemperature <= 35 -> 0.96
                input.ambientTemperature <= 40 -> 0.91
                input.ambientTemperature <= 45 -> 0.87
                input.ambientTemperature <= 50 -> 0.82
                input.ambientTemperature <= 55 -> 0.76
                input.ambientTemperature <= 60 -> 0.71
                input.ambientTemperature <= 65 -> 0.65
                input.ambientTemperature <= 70 -> 0.58
                input.ambientTemperature <= 75 -> 0.50
                input.ambientTemperature <= 80 -> 0.41
                else -> 0.0
            }
        }

    if (temperatureFactor <= 0) {
        return null
    }

    val groupingFactor =
        when {

            input.groupedCircuits <= 1 -> 1.00
            input.groupedCircuits == 2 -> 0.80
            input.groupedCircuits == 3 -> 0.70
            input.groupedCircuits == 4 -> 0.65
            input.groupedCircuits <= 6 -> 0.57
            input.groupedCircuits <= 9 -> 0.50
            input.groupedCircuits <= 12 -> 0.45
            else -> 0.40
        }

    val correctionFactor =
        temperatureFactor * groupingFactor

    val baseCurrent =
        when (input.material) {

            Material.COPPER -> when (input.installationMethod) {

                InstallationMethod.A1 -> listOf(
                    1.5 to 14.0,
                    2.5 to 18.5,
                    4.0 to 25.0,
                    6.0 to 32.0,
                    10.0 to 44.0,
                    16.0 to 59.0,
                    25.0 to 77.0,
                    35.0 to 96.0,
                    50.0 to 117.0,
                    70.0 to 149.0,
                    95.0 to 179.0,
                    120.0 to 206.0,
                    150.0 to 236.0,
                    185.0 to 268.0,
                    240.0 to 315.0
                )

                InstallationMethod.B1 -> listOf(
                    1.5 to 17.5,
                    2.5 to 24.0,
                    4.0 to 32.0,
                    6.0 to 41.0,
                    10.0 to 57.0,
                    16.0 to 76.0,
                    25.0 to 101.0,
                    35.0 to 125.0,
                    50.0 to 150.0,
                    70.0 to 192.0,
                    95.0 to 232.0,
                    120.0 to 269.0,
                    150.0 to 309.0,
                    185.0 to 353.0,
                    240.0 to 415.0
                )

                else -> listOf(
                    1.5 to 18.5,
                    2.5 to 25.0,
                    4.0 to 34.0,
                    6.0 to 43.0,
                    10.0 to 60.0,
                    16.0 to 80.0,
                    25.0 to 106.0,
                    35.0 to 131.0,
                    50.0 to 158.0,
                    70.0 to 200.0,
                    95.0 to 242.0,
                    120.0 to 280.0,
                    150.0 to 320.0,
                    185.0 to 366.0,
                    240.0 to 430.0
                )
            }

            Material.ALUMINIUM -> when (input.installationMethod) {

                InstallationMethod.A1 -> listOf(
                    2.5 to 14.0,
                    4.0 to 19.0,
                    6.0 to 24.0,
                    10.0 to 33.0,
                    16.0 to 43.0,
                    25.0 to 56.0,
                    35.0 to 69.0,
                    50.0 to 84.0,
                    70.0 to 107.0,
                    95.0 to 128.0,
                    120.0 to 147.0,
                    150.0 to 169.0,
                    185.0 to 192.0,
                    240.0 to 227.0
                )

                else -> listOf(
                    2.5 to 18.0,
                    4.0 to 24.0,
                    6.0 to 30.0,
                    10.0 to 41.0,
                    16.0 to 54.0,
                    25.0 to 70.0,
                    35.0 to 86.0,
                    50.0 to 103.0,
                    70.0 to 132.0,
                    95.0 to 158.0,
                    120.0 to 182.0,
                    150.0 to 209.0,
                    185.0 to 237.0,
                    240.0 to 280.0
                )
            }
        }

    for (section in sections) {

        val referenceCurrent =
            baseCurrent.firstOrNull {
                it.first == section
            }?.second ?: continue

        val admissibleCurrent =
            referenceCurrent * correctionFactor

        if (input.current > admissibleCurrent) {
            continue
        }

        val resistance =
            rho * input.length / section

        val voltageDrop =
            if (input.phase == Phase.THREE_PHASE) {

                sqrt(3.0) *
                        input.current *
                        resistance *
                        input.cosPhi

            } else {

                2.0 *
                        input.current *
                        resistance *
                        input.cosPhi
            }

        val nominalVoltage =
            if (input.phase == Phase.THREE_PHASE) {
                400.0
            } else {
                230.0
            }

        val voltageDropPercent =
            voltageDrop /
                    nominalVoltage *
                    100.0

        if (voltageDropPercent <= input.maxVoltageDropPercent) {

            val warning =
                if (
                    input.groupedCircuits > 1 ||
                    input.ambientTemperature != 30.0
                ) {

                    "⚠️ Facteurs de correction appliqués. Vérification normative finale recommandée."

                } else {

                    null
                }

            return CableSizingResult(
                section = section,
                voltageDropVolts = voltageDrop,
                voltageDropPercent = voltageDropPercent,
                resistance = resistance,
                correctionFactor = correctionFactor,
                warning = warning
            )
        }
    }

    return null
}

// ============================================================
// HELPERS
// ============================================================

fun formatNumber(value: Double): String {
    return String.format("%.2f", value)
}

fun installationLabel(
    method: InstallationMethod
): String {
    return method.name
}

fun installationFromLabel(
    label: String
): InstallationMethod {

    return when (label) {

        "A1" -> InstallationMethod.A1
        "A2" -> InstallationMethod.A2
        "B1" -> InstallationMethod.B1
        "B2" -> InstallationMethod.B2
        "C" -> InstallationMethod.C
        "D1" -> InstallationMethod.D1
        "E" -> InstallationMethod.E
        "F" -> InstallationMethod.F

        else -> InstallationMethod.C
    }
}

fun insulationLabel(
    insulation: Insulation
): String {

    return when (insulation) {

        Insulation.PVC -> "PVC"
        Insulation.XLPE -> "XLPE"
    }
}
