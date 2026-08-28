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

    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF1565C0),
            secondary = Color(0xFF00A6A6),
            background = Color(0xFFF5F7FA)
        )
    ) {

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

/* =========================================================
   ACCUEIL
   ========================================================= */

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
                .background(Color(0xFF1565C0))
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
                    style = MaterialTheme.typography.displayMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

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

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "Dimensionnez vos installations plus simplement.",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
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
                description = "Calculer la chute de tension d'une ligne.",
                onClick = onChute
            )

            Spacer(modifier = Modifier.height(12.dp))

            ToolCard(
                emoji = "🔌",
                title = "Section de câble",
                description = "Déterminer la section du conducteur.",
                onClick = onSection
            )

            Spacer(modifier = Modifier.height(12.dp))

            ToolCard(
                emoji = "🛡️",
                title = "Calibre disjoncteur",
                description = "Choisir le calibre adapté.",
                onClick = onDisjoncteur
            )

            Spacer(modifier = Modifier.height(12.dp))

            ToolCard(
                emoji = "📄",
                title = "Analyser un plan PDF",
                description = "Analyse intelligente d'un plan électrique.",
                onClick = onPdf
            )

            Spacer(modifier = Modifier.height(22.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "💡 À propos",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "ElectroAssistant est un outil d'aide au "
                                + "pré-dimensionnement des installations "
                                + "électriques du bâtiment.",
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "⚠️ Les résultats doivent être vérifiés "
                                + "selon les normes et les conditions réelles "
                                + "de l'installation.",
                        color = Color(0xFF795548),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "ElectroAssistant • Version 1.0",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

/* =========================================================
   CARTE OUTIL
   ========================================================= */

@Composable
fun ToolCard(
    emoji: String,
    title: String,
    description: String,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
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
                    .size(55.dp)
                    .background(
                        Color(0xFFE3F2FD),
                        RoundedCornerShape(16.dp)
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

/* =========================================================
   SECTION CÂBLE
   ========================================================= */

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

        Text(
            text = "Pré-dimensionnement du conducteur",
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(18.dp))

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
                    onClick = { triphase = false }
                )

                Text("Monophasé")

                Spacer(modifier = Modifier.width(8.dp))

                RadioButton(
                    selected = triphase,
                    onClick = { triphase = true }
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
                    onClick = { cuivre = true }
                )

                Text("Cuivre")

                Spacer(modifier = Modifier.width(8.dp))

                RadioButton(
                    selected = !cuivre,
                    onClick = { cuivre = false }
                )

                Text("Aluminium")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        SectionCard("📊 Charge") {

            Text(
                "Type de charge",
                fontWeight = FontWeight.Bold
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                RadioButton(
                    selected = loadType == LoadType.LIGHTING,
                    onClick = {
                        loadType = LoadType.LIGHTING
                    }
                )

                Text("Éclairage")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                RadioButton(
                    selected = loadType == LoadType.POWER,
                    onClick = {
                        loadType = LoadType.POWER
                    }
                )

                Text("Prises / puissance")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                RadioButton(
                    selected = loadType == LoadType.MOTOR,
                    onClick = {
                        loadType = LoadType.MOTOR
                    }
                )

                Text("Moteur")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = courant,
                onValueChange = {
                    courant = it
                    erreur = ""
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Courant (A)") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = longueur,
                onValueChange = {
                    longueur = it
                    erreur = ""
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Longueur (m)") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = cosPhi,
                onValueChange = {
                    cosPhi = it
                    erreur = ""
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("cos φ") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = chuteMax,
                onValueChange = {
                    chuteMax = it
                    erreur = ""
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Chute max (%)") },
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        SectionCard("🔧 Installation") {

            Text(
                "Mode de pose",
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            InstallationSelector(
                selected = installation,
                onSelected = {
                    installation = it
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

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

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = conducteurs,
                onValueChange = {
                    conducteurs = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Conducteurs chargés") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = temperature,
                onValueChange = {
                    temperature = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Température ambiante (°C)") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = grouped,
                onValueChange = {
                    grouped = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Circuits groupés") },
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = {

                try {

                    val currentValue =
                        courant.toDouble()

                    val lengthValue =
                        longueur.toDouble()

                    val cosValue =
                        cosPhi.toDouble()

                    val maxDrop =
                        chuteMax.toDouble()

                    val conductorValue =
                        conducteurs.toInt()

                    val tempValue =
                        temperature.toDouble()

                    val groupedValue =
                        grouped.toInt()

                    val input = CableSizingInput(
                        material =
                            if (cuivre)
                                Material.COPPER
                            else
                                Material.ALUMINIUM,

                        phase =
                            if (triphase)
                                Phase.THREE_PHASE
                            else
                                Phase.SINGLE_PHASE,

                        loadType = loadType,

                        current = currentValue,

                        length = lengthValue,

                        maxVoltageDropPercent = maxDrop,

                        cosPhi = cosValue,

                        installationMethod = installation,

                        insulation = isolation,

                        loadedConductors = conductorValue,

                        ambientTemperature = tempValue,

                        groupedCircuits = groupedValue
                    )

                    resultat =
                        calculateCableSizing(input)

                    if (resultat == null) {
                        erreur =
                            "Aucune section adaptée trouvée avec ces paramètres."
                    } else {
                        erreur = ""
                    }

                } catch (e: Exception) {

                    resultat = null

                    erreur =
                        "Veuillez vérifier les valeurs saisies."
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(15.dp)
        ) {

            Text(
                "CALCULER LA SECTION",
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
                shape = RoundedCornerShape(15.dp)
            ) {

                Text(
                    text = "⚠️ $erreur",
                    modifier = Modifier.padding(16.dp),
                    color = Color(0xFFC62828)
                )
            }
        }

        resultat?.let { result ->

            Spacer(modifier = Modifier.height(18.dp))

            ResultCard(result)
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

/* =========================================================
   SELECTEUR MODE DE POSE
   ========================================================= */

@Composable
fun InstallationSelector(
    selected: InstallationMethod,
    onSelected: (InstallationMethod) -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    Box {

        OutlinedButton(
            onClick = {
                expanded = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                "Mode ${selected.name}"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {

            InstallationMethod.values()
                .forEach { method ->

                    DropdownMenuItem(
                        text = {
                            Text(
                                "Mode ${method.name}"
                            )
                        },
                        onClick = {

                            onSelected(method)

                            expanded = false
                        }
                    )
                }
        }
    }
}

/* =========================================================
   RESULTAT
   ========================================================= */

@Composable
fun ResultCard(
    result: CableSizingResult
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
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
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1565C0)
            )

            Spacer(modifier = Modifier.height(15.dp))

            ResultLine(
                "Chute de tension",
                "${"%.2f".format(result.voltageDropVolts)} V"
            )

            ResultLine(
                "Pourcentage",
                "${"%.2f".format(result.voltageDropPercent)} %"
            )

            ResultLine(
                "Résistance",
                "${"%.5f".format(result.resistance)} Ω"
            )

            ResultLine(
                "Facteur correction",
                "${"%.2f".format(result.correctionFactor)}"
            )

            result.warning?.let {

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = it,
                    color = Color(0xFF795548),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun ResultLine(
    title: String,
    value: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = title,
            color = Color.Gray
        )

        Text(
            text = value,
            fontWeight = FontWeight.Bold
        )
    }
}

/* =========================================================
   CARD GENERIQUE
   ========================================================= */

@Composable
fun SectionCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            content()
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

    var current by remember {
        mutableStateOf("")
    }

    var length by remember {
        mutableStateOf("")
    }

    var section by remember {
        mutableStateOf("2.5")
    }

    var result by remember {
        mutableStateOf<String?>(null)
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
            "📐 Chute de tension",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            "Calcul rapide de la chute de tension",
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(20.dp))

        SectionCard("Paramètres") {

            OutlinedTextField(
                value = current,
                onValueChange = {
                    current = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Courant (A)")
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = length,
                onValueChange = {
                    length = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Longueur (m)")
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = section,
                onValueChange = {
                    section = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Section (mm²)")
                },
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = {

                try {

                    val i = current.toDouble()
                    val l = length.toDouble()
                    val s = section.toDouble()

                    val rho = 0.0175

                    val resistance =
                        rho * l / s

                    val voltageDrop =
                        2.0 * i * resistance

                    val percent =
                        voltageDrop / 230.0 * 100.0

                    result =
                        "ΔU = %.2f V\nΔU = %.2f %%"
                            .format(
                                voltageDrop,
                                percent
                            )

                } catch (e: Exception) {

                    result =
                        "⚠️ Vérifiez les valeurs saisies."
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(15.dp)
        ) {

            Text(
                "CALCULER",
                fontWeight = FontWeight.Bold
            )
        }

        result?.let {

            Spacer(modifier = Modifier.height(18.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp)
            ) {

                Text(
                    text = it,
                    modifier = Modifier.padding(20.dp),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/* =========================================================
   COMING SOON
   ========================================================= */

@Composable
fun ComingSoonScreen(
    title: String,
    description: String,
    onBack: () -> Unit
) {

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

        Spacer(modifier = Modifier.height(40.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp)
        ) {

            Column(
                modifier = Modifier.padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = description,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "🚧",
                    style = MaterialTheme.typography.displaySmall
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Cette fonctionnalité sera disponible prochainement.",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
