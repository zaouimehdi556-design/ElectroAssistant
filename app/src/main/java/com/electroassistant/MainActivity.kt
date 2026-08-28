package com.electroassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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

            var page by remember { mutableStateOf("home") }

            when (page) {

                "home" -> HomeScreen(
                    onCable = { page = "cable" },
                    onDrop = { page = "drop" },
                    onBreaker = { page = "breaker" },
                    onPlan = { page = "plan" }
                )

                "cable" -> CableScreen(
                    onBack = { page = "home" }
                )

                "drop" -> VoltageDropScreen(
                    onBack = { page = "home" }
                )

                "breaker" -> BreakerScreen(
                    onBack = { page = "home" }
                )

                "plan" -> PlanScreen(
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
    onCable: () -> Unit,
    onDrop: () -> Unit,
    onBreaker: () -> Unit,
    onPlan: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1565C0))
                .padding(24.dp)
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
                    color = Color.White.copy(alpha = 0.9f)
                )

                Spacer(modifier = Modifier.height(14.dp))

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
                text = "Choisissez l'outil dont vous avez besoin.",
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(18.dp))

            HomeCard(
                emoji = "🔌",
                title = "Section de câble",
                description = "Calculer une section adaptée.",
                onClick = onCable
            )

            Spacer(modifier = Modifier.height(12.dp))

            HomeCard(
                emoji = "📐",
                title = "Chute de tension",
                description = "Calculer la chute de tension.",
                onClick = onDrop
            )

            Spacer(modifier = Modifier.height(12.dp))

            HomeCard(
                emoji = "🛡️",
                title = "Calibre disjoncteur",
                description = "Estimer le calibre du disjoncteur.",
                onClick = onBreaker
            )

            Spacer(modifier = Modifier.height(12.dp))

            HomeCard(
                emoji = "📄",
                title = "Plan électrique",
                description = "Préparer l'analyse d'un plan.",
                onClick = onPlan
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
                        text = "⚠️ Les résultats sont indicatifs et doivent "
                                + "être vérifiés selon les normes applicables "
                                + "et les conditions réelles de l'installation.",
                        color = Color(0xFF795548),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "ElectroAssistant • Version 2.0",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

/* =========================================================
   HOME CARD
   ========================================================= */

@Composable
fun HomeCard(
    emoji: String,
    title: String,
    description: String,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(58.dp)
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

            Text(
                text = "›",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF1565C0)
            )
        }
    }
}

/* =========================================================
   SECTION CABLE
   ========================================================= */

@Composable
fun CableScreen(
    onBack: () -> Unit
) {

    var currentText by remember { mutableStateOf("") }
    var lengthText by remember { mutableStateOf("") }
    var voltageText by remember { mutableStateOf("230") }
    var maxDropText by remember { mutableStateOf("5") }

    var threePhase by remember { mutableStateOf(false) }
    var copper by remember { mutableStateOf(true) }

    var result by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

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

        TextButton(onClick = onBack) {
            Text("← Retour")
        }

        Text(
            text = "🔌 Section de câble",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Pré-dimensionnement selon la chute de tension",
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(18.dp))

        SectionBox("⚡ Circuit") {

            Text(
                text = "Type de réseau",
                fontWeight = FontWeight.Bold
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                RadioButton(
                    selected = !threePhase,
                    onClick = { threePhase = false }
                )

                Text("Monophasé")

                Spacer(modifier = Modifier.width(8.dp))

                RadioButton(
                    selected = threePhase,
                    onClick = { threePhase = true }
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
                    selected = copper,
                    onClick = { copper = true }
                )

                Text("Cuivre")

                Spacer(modifier = Modifier.width(8.dp))

                RadioButton(
                    selected = !copper,
                    onClick = { copper = false }
                )

                Text("Aluminium")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        SectionBox("📊 Paramètres") {

            NumberField(
                value = currentText,
                label = "Courant (A)",
                onValueChange = {
                    currentText = it
                    error = ""
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            NumberField(
                value = lengthText,
                label = "Longueur de la ligne (m)",
                onValueChange = {
                    lengthText = it
                    error = ""
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            NumberField(
                value = voltageText,
                label = "Tension (V)",
                onValueChange = {
                    voltageText = it
                    error = ""
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            NumberField(
                value = maxDropText,
                label = "Chute maximale (%)",
                onValueChange = {
                    maxDropText = it
                    error = ""
                }
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = {

                try {

                    val current = currentText.toDouble()
                    val length = lengthText.toDouble()
                    val voltage = voltageText.toDouble()
                    val maxDrop = maxDropText.toDouble()

                    if (current <= 0 ||
                        length <= 0 ||
                        voltage <= 0 ||
                        maxDrop <= 0
                    ) {

                        error = "Les valeurs doivent être supérieures à zéro."
                        result = ""

                    } else {

                        val rho = if (copper) {
                            0.0175
                        } else {
                            0.0282
                        }

                        val factor = if (threePhase) {
                            sqrt(3.0)
                        } else {
                            2.0
                        }

                        var selectedSection: Double? = null
                        var selectedDrop = 0.0
                        var selectedPercent = 0.0

                        for (section in sections) {

                            val resistance =
                                rho * length / section

                            val drop =
                                factor * current * resistance

                            val percent =
                                drop / voltage * 100.0

                            if (percent <= maxDrop) {

                                selectedSection = section
                                selectedDrop = drop
                                selectedPercent = percent

                                break
                            }
                        }

                        if (selectedSection == null) {

                            result = ""
                            error =
                                "Aucune section disponible ne respecte la chute maximale."

                        } else {

                            error = ""

                            result =
                                "Section recommandée : " +
                                        "${formatNumber(selectedSection)} mm²\n\n" +
                                        "Chute de tension : " +
                                        "${formatNumber(selectedDrop)} V\n" +
                                        "Pourcentage : " +
                                        "${formatNumber(selectedPercent)} %\n\n" +
                                        "Matériau : " +
                                        if (copper) "Cuivre" else "Aluminium"
                        }
                    }

                } catch (e: Exception) {

                    result = ""
                    error = "Veuillez vérifier les valeurs saisies."
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(15.dp)
        ) {

            Text(
                text = "CALCULER LA SECTION",
                fontWeight = FontWeight.Bold
            )
        }

        if (error.isNotEmpty()) {

            Spacer(modifier = Modifier.height(12.dp))

            ErrorBox(error)
        }

        if (result.isNotEmpty()) {

            Spacer(modifier = Modifier.height(18.dp))

            ResultBox(result)
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

/* =========================================================
   CHUTE DE TENSION
   ========================================================= */

@Composable
fun VoltageDropScreen(
    onBack: () -> Unit
) {

    var currentText by remember { mutableStateOf("") }
    var lengthText by remember { mutableStateOf("") }
    var sectionText by remember { mutableStateOf("2.5") }
    var voltageText by remember { mutableStateOf("230") }

    var threePhase by remember { mutableStateOf(false) }
    var copper by remember { mutableStateOf(true) }

    var result by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        TextButton(onClick = onBack) {
            Text("← Retour")
        }

        Text(
            text = "📐 Chute de tension",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Calcul de la chute de tension de la ligne",
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(18.dp))

        SectionBox("⚡ Circuit") {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                RadioButton(
                    selected = !threePhase,
                    onClick = { threePhase = false }
                )

                Text("Monophasé")

                Spacer(modifier = Modifier.width(8.dp))

                RadioButton(
                    selected = threePhase,
                    onClick = { threePhase = true }
                )

                Text("Triphasé")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                RadioButton(
                    selected = copper,
                    onClick = { copper = true }
                )

                Text("Cuivre")

                Spacer(modifier = Modifier.width(8.dp))

                RadioButton(
                    selected = !copper,
                    onClick = { copper = false }
                )

                Text("Aluminium")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        SectionBox("📊 Paramètres") {

            NumberField(
                value = currentText,
                label = "Courant (A)",
                onValueChange = {
                    currentText = it
                    error = ""
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            NumberField(
                value = lengthText,
                label = "Longueur (m)",
                onValueChange = {
                    lengthText = it
                    error = ""
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            NumberField(
                value = sectionText,
                label = "Section (mm²)",
                onValueChange = {
                    sectionText = it
                    error = ""
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            NumberField(
                value = voltageText,
                label = "Tension (V)",
                onValueChange = {
                    voltageText = it
                    error = ""
                }
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = {

                try {

                    val current = currentText.toDouble()
                    val length = lengthText.toDouble()
                    val section = sectionText.toDouble()
                    val voltage = voltageText.toDouble()

                    if (current <= 0 ||
                        length <= 0 ||
                        section <= 0 ||
                        voltage <= 0
                    ) {

                        error = "Toutes les valeurs doivent être positives."
                        result = ""

                    } else {

                        val rho = if (copper) {
                            0.0175
                        } else {
                            0.0282
                        }

                        val resistance =
                            rho * length / section

                        val factor =
                            if (threePhase) sqrt(3.0) else 2.0

                        val drop =
                            factor * current * resistance

                        val percent =
                            drop / voltage * 100.0

                        error = ""

                        result =
                            "ΔU = ${formatNumber(drop)} V\n\n" +
                                    "Chute = ${formatNumber(percent)} %\n\n" +
                                    "Résistance de ligne = " +
                                    "${formatNumber(resistance)} Ω"
                    }

                } catch (e: Exception) {

                    result = ""
                    error = "Veuillez vérifier les valeurs saisies."
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(15.dp)
        ) {

            Text(
                text = "CALCULER",
                fontWeight = FontWeight.Bold
            )
        }

        if (error.isNotEmpty()) {

            Spacer(modifier = Modifier.height(12.dp))

            ErrorBox(error)
        }

        if (result.isNotEmpty()) {

            Spacer(modifier = Modifier.height(18.dp))

            ResultBox(result)
        }
    }
}

/* =========================================================
   DISJONCTEUR
   ========================================================= */

@Composable
fun BreakerScreen(
    onBack: () -> Unit
) {

    var currentText by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    val breakers = listOf(
        2, 4, 6, 10, 16, 20, 25, 32,
        40, 50, 63, 80, 100, 125, 160
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        TextButton(onClick = onBack) {
            Text("← Retour")
        }

        Text(
            text = "🛡️ Calibre disjoncteur",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Estimation simple du calibre supérieur au courant.",
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(20.dp))

        SectionBox("⚡ Courant") {

            NumberField(
                value = currentText,
                label = "Courant calculé (A)",
                onValueChange = {
                    currentText = it
                    error = ""
                }
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = {

                try {

                    val current =
                        currentText.toDouble()

                    if (current <= 0) {

                        error =
                            "Le courant doit être supérieur à zéro."

                        result = ""

                    } else {

                        val selected =
                            breakers.firstOrNull {
                                it >= current
                            }

                        if (selected == null) {

                            error =
                                "Courant trop élevé pour la liste disponible."

                            result = ""

                        } else {

                            error = ""

                            result =
                                "Calibre indicatif : ${selected} A\n\n" +
                                        "Courant saisi : " +
                                        "${formatNumber(current)} A"
                        }
                    }

                } catch (e: Exception) {

                    error =
                        "Veuillez entrer un courant valide."

                    result = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(15.dp)
        ) {

            Text(
                text = "CALCULER LE CALIBRE",
                fontWeight = FontWeight.Bold
            )
        }

        if (error.isNotEmpty()) {

            Spacer(modifier = Modifier.height(12.dp))

            ErrorBox(error)
        }

        if (result.isNotEmpty()) {

            Spacer(modifier = Modifier.height(18.dp))

            ResultBox(result)
        }

        Spacer(modifier = Modifier.height(25.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        ) {

            Text(
                text =
                    "⚠️ Attention : le choix réel du disjoncteur "
                            + "dépend notamment de la section du câble, "
                            + "du mode de pose, du pouvoir de coupure "
                            + "et des règles de protection applicables.",
                modifier = Modifier.padding(18.dp),
                color = Color(0xFF795548),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

/* =========================================================
   PLAN
   ========================================================= */

@Composable
fun PlanScreen(
    onBack: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        TextButton(onClick = onBack) {
            Text("← Retour")
        }

        Text(
            text = "📄 Plan électrique",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Modes de pose de référence",
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(18.dp))

        PlanImage(
            image = R.drawable.mode_a1,
            title = "Mode A1"
        )

        PlanImage(
            image = R.drawable.mode_a2,
            title = "Mode A2"
        )

        PlanImage(
            image = R.drawable.mode_b1,
            title = "Mode B1"
        )

        PlanImage(
            image = R.drawable.mode_b2,
            title = "Mode B2"
        )

        PlanImage(
            image = R.drawable.mode_c,
            title = "Mode C"
        )

        PlanImage(
            image = R.drawable.mode_d1,
            title = "Mode D1"
        )

        PlanImage(
            image = R.drawable.mode_e,
            title = "Mode E"
        )

        PlanImage(
            image = R.drawable.mode_f,
            title = "Mode F"
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        ) {

            Text(
                text =
                    "💡 هذه الصور موجودة داخل التطبيق كمرجع "
                            + "لاختيار طريقة التركيب المناسبة.",
                modifier = Modifier.padding(18.dp),
                color = Color.Gray
            )
        }
    }
}

/* =========================================================
   IMAGE
   ========================================================= */

@Composable
fun PlanImage(
    image: Int,
    title: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 14.dp),
        shape = RoundedCornerShape(18.dp)
    ) {

        Column {

            Text(
                text = title,
                modifier = Modifier.padding(15.dp),
                fontWeight = FontWeight.Bold
            )

            Image(
                painter = painterResource(id = image),
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

/* =========================================================
   NUMBER FIELD
   ========================================================= */

@Composable
fun NumberField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(label)
        },
        singleLine = true
    )
}

/* =========================================================
   SECTION BOX
   ========================================================= */

@Composable
fun SectionBox(
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
   RESULT BOX
   ========================================================= */

@Composable
fun ResultBox(
    text: String
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
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = text,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/* =========================================================
   ERROR BOX
   ========================================================= */

@Composable
fun ErrorBox(
    text: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFEBEE)
        )
    ) {

        Text(
            text = "⚠️ $text",
            modifier = Modifier.padding(16.dp),
            color = Color(0xFFC62828)
        )
    }
}

/* =========================================================
   FORMAT
   ========================================================= */

fun formatNumber(
    value: Double
): String {

    return String.format(
        java.util.Locale.US,
        "%.2f",
        value
    )
}
