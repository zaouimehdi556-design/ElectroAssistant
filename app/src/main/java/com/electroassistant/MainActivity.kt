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
import androidx.compose.ui.text.style.TextAlign
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
                textAlign = TextAlign.Center
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
                        Color(0xFFE3F2FD),
                        RoundedCornerShape(15.dp)
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
            text = "🔌 Section de câble",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "Pré-dimensionnement professionnel du câble",
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(20.dp))

        SectionCard(title = "⚡ Circuit") {

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

            Spacer(modifier = Modifier.height(8.dp))

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

        SectionCard(title = "📊 Charge") {

            InputField(
                value = courant,
                onValueChange = {
                    courant = it
                },
                label = "Courant Ib (A)"
            )

            InputField(
                value = longueur,
                onValueChange = {
                    longueur = it
                },
                label = "Longueur du câble (m)"
            )

            InputField(
                value = cosPhi,
                onValueChange = {
                    cosPhi = it
                },
                label = "cos φ"
            )

            InputField(
                value = chuteMax,
                onValueChange = {
                    chuteMax = it
                },
                label = "Chute maximale (%)"
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        SectionCard(title = "🏗️ Installation") {

            Text(
                "Type de charge",
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            ChargeButton(
                selected = loadType == LoadType.LIGHTING,
                text = "💡 Éclairage",
                onClick = {
                    loadType = LoadType.LIGHTING
                    chuteMax = "3"
                }
            )

            ChargeButton(
                selected = loadType == LoadType.POWER,
                text = "🔌 Prises / puissance",
                onClick = {
                    loadType = LoadType.POWER
                    chuteMax = "5"
                }
            )

            ChargeButton(
                selected = loadType == LoadType.MOTOR,
                text = "⚙️ Moteur",
                onClick = {
                    loadType = LoadType.MOTOR
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                "Mode de pose",
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            InstallationSelector(
                selected = installation,
                onSelected = {
                    installation = it
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                "Isolation",
                fontWeight = FontWeight.Bold
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                RadioButton(
                    selected = isolation == Insulation.PVC,
                    onClick = {
                        isolation = Insulation.PVC
                    }
                )

                Text("PVC")

                Spacer(modifier = Modifier.width(15.dp))

                RadioButton(
                    selected = isolation == Insulation.XLPE,
                    onClick = {
                        isolation = Insulation.XLPE
                    }
                )

                Text("XLPE")
            }

            InputField(
                value = conducteurs,
                onValueChange = {
                    conducteurs = it
                },
                label = "Conducteurs chargés"
            )

            InputField(
                value = temperature,
                onValueChange = {
                    temperature = it
                },
                label = "Température ambiante (°C)"
            )

            InputField(
                value = grouped,
                onValueChange = {
                    grouped = it
                },
                label = "Circuits regroupés"
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = {

                val i = courant.toDoubleOrNull()
                val l = longueur.toDoubleOrNull()
                val cos = cosPhi.toDoubleOrNull()
                val maxDrop = chuteMax.toDoubleOrNull()
                val loaded = conducteurs.toIntOrNull()
                val temp = temperature.toDoubleOrNull()
                val groups = grouped.toIntOrNull()

                if (
                    i == null ||
                    l == null ||
                    cos == null ||
                    maxDrop == null ||
                    loaded == null ||
                    temp == null ||
                    groups == null
                ) {

                    erreur = "⚠️ Vérifiez toutes les valeurs."
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

                        loadType = loadType,
                        current = i,
                        length = l,
                        maxVoltageDropPercent = maxDrop,
                        cosPhi = cos,
                        installationMethod = installation,
                        insulation = isolation,
                        loadedConductors = loaded,
                        ambientTemperature = temp,
                        groupedCircuits = groups
                    )

                    val calculated =
                        calculateCableSizing(input)

                    if (calculated == null) {

                        erreur =
                            "⚠️ Aucune section compatible trouvée avec les paramètres saisis."

                        resultat = null

                    } else {

                        erreur = ""
                        resultat = calculated
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {

            Text(
                text = "⚡ Calculer la section",
                fontWeight = FontWeight.Bold
            )
        }

        if (erreur.isNotEmpty()) {

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFEBEE)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {

                Text(
                    text = erreur,
                    modifier = Modifier.padding(18.dp),
                    color = Color(0xFFC62828),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        resultat?.let { result ->

            Spacer(modifier = Modifier.height(15.dp))

            ResultCard(result)
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun ResultCard(
    result: CableSizingResult
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE8F5E9)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
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

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Section recommandée",
                color = Color.Gray
            )

            Text(
                text = "${result.section} mm²",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Chute de tension : %.2f V".format(
                    result.voltageDropVolts
                )
            )

            Text(
                text = "Pourcentage : %.2f %%".format(
                    result.voltageDropPercent
                )
            )

            Text(
                text = "Facteur de correction : %.2f".format(
                    result.correctionFactor
                )
            )

            result.warning?.let {

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = it,
                    color = Color(0xFFE65100),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "⚠️ Résultat de pré-dimensionnement. La validation finale doit tenir compte des conditions réelles d'installation et des prescriptions locales.",
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray
            )
        }
    }
}

@Composable
fun InstallationSelector(
    selected: InstallationMethod,
    onSelected: (InstallationMethod) -> Unit
) {

    Column {

        InstallationRow(
            "A1",
            InstallationMethod.A1,
            selected,
            onSelected
        )

        InstallationRow(
            "A2",
            InstallationMethod.A2,
            selected,
            onSelected
        )

        InstallationRow(
            "B1",
            InstallationMethod.B1,
            selected,
            onSelected
        )

        InstallationRow(
            "B2",
            InstallationMethod.B2,
            selected,
            onSelected
        )

        InstallationRow(
            "C",
            InstallationMethod.C,
            selected,
            onSelected
        )

        InstallationRow(
            "D1",
            InstallationMethod.D1,
            selected,
            onSelected
        )

        InstallationRow(
            "E",
            InstallationMethod.E,
            selected,
            onSelected
        )

        InstallationRow(
            "F",
            InstallationMethod.F,
            selected,
            onSelected
        )
    }
}

@Composable
fun InstallationRow(
    text: String,
    value: InstallationMethod,
    selected: InstallationMethod,
    onSelected: (InstallationMethod) -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        RadioButton(
            selected = selected == value,
            onClick = {
                onSelected(value)
            }
        )

        Text(text)
    }
}

@Composable
fun ChargeButton(
    selected: Boolean,
    text: String,
    onClick: () -> Unit
) {

    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        shape = RoundedCornerShape(12.dp)
    ) {

        Text(
            text = text,
            fontWeight = if (selected) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            }
        )
    }
}

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
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp),
            content = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                content()
            }
        )
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
            "📐 Chute de tension",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        SectionCard(title = "⚡ Circuit") {

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

        InputField(
            value = tension,
            onValueChange = {
                tension = it
            },
            label = "Tension (V)"
        )

        InputField(
            value = courant,
            onValueChange = {
                courant = it
            },
            label = "Courant I (A)"
        )

        InputField(
            value = longueur,
            onValueChange = {
                longueur = it
            },
            label = "Longueur L (m)"
        )

        InputField(
            value = section,
            onValueChange = {
                section = it
            },
            label = "Section (mm²)"
        )

        InputField(
            value = cosPhi,
            onValueChange = {
                cosPhi = it
            },
            label = "cos φ"
        )

        Spacer(modifier = Modifier.height(10.dp))

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

                    resultat =
                        "⚠️ Vérifiez les valeurs saisies."
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {

            Text("⚡ Calculer")
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
