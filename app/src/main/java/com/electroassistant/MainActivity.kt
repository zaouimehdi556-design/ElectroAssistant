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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                ElectroAssistantApp()
            }
        }
    }
}

/* ---------------------------------------------------------
   التطبيق الرئيسي
--------------------------------------------------------- */

@Composable
fun ElectroAssistantApp() {

    var screen by remember { mutableStateOf("home") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("ElectroAssistant")
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            when (screen) {

                "home" -> HomeScreen(
                    onBreaker = { screen = "breaker" },
                    onPlan = { screen = "plan" },
                    onPower = { screen = "power" },
                    onVoltageDrop = { screen = "drop" }
                )

                "breaker" -> BreakerScreen(
                    onBack = { screen = "home" }
                )

                "plan" -> PlanScreen(
                    onBack = { screen = "home" }
                )

                "power" -> PowerScreen(
                    onBack = { screen = "home" }
                )

                "drop" -> VoltageDropScreen(
                    onBack = { screen = "home" }
                )
            }
        }
    }
}

/* ---------------------------------------------------------
   الصفحة الرئيسية
--------------------------------------------------------- */

@Composable
fun HomeScreen(
    onBreaker: () -> Unit,
    onPlan: () -> Unit,
    onPower: () -> Unit,
    onVoltageDrop: () -> Unit
) {

    Text(
        text = "المساعد الكهربائي",
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = "حسابات كهربائية وفق مبادئ NF C 15-100"
    )

    Spacer(modifier = Modifier.height(20.dp))

    SectionBox(title = "الحسابات") {

        Button(
            onClick = onBreaker,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("حساب القاطع")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onPlan,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("اختيار مقطع الكابل")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onPower,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("حساب القدرة والتيار")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onVoltageDrop,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("حساب هبوط الجهد")
        }
    }
}

/* ---------------------------------------------------------
   شاشة القاطع
--------------------------------------------------------- */

@Composable
fun BreakerScreen(
    onBack: () -> Unit
) {

    var powerText by remember { mutableStateOf("") }
    var voltageText by remember { mutableStateOf("230") }
    var result by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column {

        OutlinedButton(onClick = onBack) {
            Text("رجوع")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "حساب القاطع",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        SectionBox(title = "المعطيات") {

            NumberField(
                label = "القدرة W",
                value = powerText,
                onValueChange = {
                    powerText = it
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            NumberField(
                label = "الجهد V",
                value = voltageText,
                onValueChange = {
                    voltageText = it
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {

                    val power = powerText.toDoubleOrNull()
                    val voltage = voltageText.toDoubleOrNull()

                    if (power == null || voltage == null || power <= 0 || voltage <= 0) {
                        error = "أدخل أرقامًا صحيحة."
                        result = ""
                    } else {

                        val current = power / voltage

                        val breaker = when {
                            current <= 2 -> 2
                            current <= 6 -> 6
                            current <= 10 -> 10
                            current <= 16 -> 16
                            current <= 20 -> 20
                            current <= 25 -> 25
                            current <= 32 -> 32
                            current <= 40 -> 40
                            current <= 50 -> 50
                            else -> 63
                        }

                        error = ""

                        result =
                            "التيار المحسوب: ${formatNumber(current)} A\n" +
                            "القاطع المقترح: ${breaker} A"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("احسب")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (error.isNotEmpty()) {
            ErrorBox(error)
        }

        if (result.isNotEmpty()) {
            ResultBox(
                title = "النتيجة",
                value = result
            )
        }
    }
}

/* ---------------------------------------------------------
   شاشة اختيار الكابل
--------------------------------------------------------- */

@Composable
fun PlanScreen(
    onBack: () -> Unit
) {

    var currentText by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column {

        OutlinedButton(onClick = onBack) {
            Text("رجوع")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "اختيار مقطع الكابل",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        SectionBox(title = "التيار") {

            NumberField(
                label = "التيار A",
                value = currentText,
                onValueChange = {
                    currentText = it
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {

                    val current = currentText.toDoubleOrNull()

                    if (current == null || current <= 0) {

                        error = "أدخل قيمة تيار صحيحة."
                        result = ""

                    } else {

                        val section = when {
                            current <= 10 -> 1.5
                            current <= 16 -> 2.5
                            current <= 25 -> 4.0
                            current <= 32 -> 6.0
                            current <= 40 -> 10.0
                            current <= 50 -> 10.0
                            current <= 63 -> 16.0
                            current <= 80 -> 25.0
                            current <= 100 -> 35.0
                            else -> 50.0
                        }

                        error = ""

                        result =
                            "التيار: ${formatNumber(current)} A\n" +
                            "المقطع المقترح: ${formatNumber(section)} mm²"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("اختيار الكابل")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (error.isNotEmpty()) {
            ErrorBox(error)
        }

        if (result.isNotEmpty()) {
            ResultBox(
                title = "النتيجة",
                value = result
            )
        }
    }
}

/* ---------------------------------------------------------
   شاشة القدرة والتيار
--------------------------------------------------------- */

@Composable
fun PowerScreen(
    onBack: () -> Unit
) {

    var voltageText by remember { mutableStateOf("230") }
    var currentText by remember { mutableStateOf("") }
    var powerFactorText by remember { mutableStateOf("1") }

    var result by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column {

        OutlinedButton(onClick = onBack) {
            Text("رجوع")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "حساب القدرة والتيار",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        SectionBox(title = "المعطيات") {

            NumberField(
                label = "الجهد V",
                value = voltageText,
                onValueChange = {
                    voltageText = it
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            NumberField(
                label = "التيار A",
                value = currentText,
                onValueChange = {
                    currentText = it
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            NumberField(
                label = "معامل القدرة cos φ",
                value = powerFactorText,
                onValueChange = {
                    powerFactorText = it
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {

                    val voltage = voltageText.toDoubleOrNull()
                    val current = currentText.toDoubleOrNull()
                    val pf = powerFactorText.toDoubleOrNull()

                    if (
                        voltage == null ||
                        current == null ||
                        pf == null ||
                        voltage <= 0 ||
                        current <= 0 ||
                        pf <= 0 ||
                        pf > 1
                    ) {

                        error = "تحقق من القيم المدخلة."
                        result = ""

                    } else {

                        val power = voltage * current * pf

                        error = ""

                        result =
                            "القدرة الفعالة: ${formatNumber(power)} W\n" +
                            "القدرة بالكيلوواط: ${formatNumber(power / 1000)} kW"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("احسب القدرة")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (error.isNotEmpty()) {
            ErrorBox(error)
        }

        if (result.isNotEmpty()) {
            ResultBox(
                title = "النتيجة",
                value = result
            )
        }
    }
}

/* ---------------------------------------------------------
   شاشة هبوط الجهد
--------------------------------------------------------- */

@Composable
fun VoltageDropScreen(
    onBack: () -> Unit
) {

    var currentText by remember { mutableStateOf("") }
    var lengthText by remember { mutableStateOf("") }
    var sectionText by remember { mutableStateOf("2.5") }
    var voltageText by remember { mutableStateOf("230") }

    var result by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column {

        OutlinedButton(onClick = onBack) {
            Text("رجوع")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "حساب هبوط الجهد",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        SectionBox(title = "المعطيات") {

            NumberField(
                label = "التيار A",
                value = currentText,
                onValueChange = {
                    currentText = it
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            NumberField(
                label = "طول الخط m",
                value = lengthText,
                onValueChange = {
                    lengthText = it
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            NumberField(
                label = "مقطع الكابل mm²",
                value = sectionText,
                onValueChange = {
                    sectionText = it
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            NumberField(
                label = "الجهد V",
                value = voltageText,
                onValueChange = {
                    voltageText = it
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {

                    val current = currentText.toDoubleOrNull()
                    val length = lengthText.toDoubleOrNull()
                    val section = sectionText.toDoubleOrNull()
                    val voltage = voltageText.toDoubleOrNull()

                    if (
                        current == null ||
                        length == null ||
                        section == null ||
                        voltage == null ||
                        current <= 0 ||
                        length <= 0 ||
                        section <= 0 ||
                        voltage <= 0
                    ) {

                        error = "تحقق من القيم المدخلة."
                        result = ""

                    } else {

                        // مقاومة النحاس التقريبية عند 20°C
                        val resistivity = 0.0175

                        // دائرة أحادية الطور: ذهاب + إياب
                        val resistance =
                            resistivity * (2.0 * length) / section

                        val drop = current * resistance

                        val percentage =
                            (drop / voltage) * 100.0

                        error = ""

                        result =
                            "هبوط الجهد: ${formatNumber(drop)} V\n" +
                            "نسبة الهبوط: ${formatNumber(percentage)} %"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("احسب هبوط الجهد")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (error.isNotEmpty()) {
            ErrorBox(error)
        }

        if (result.isNotEmpty()) {
            ResultBox(
                title = "النتيجة",
                value = result
            )
        }
    }
}

/* ---------------------------------------------------------
   الأدوات المشتركة
--------------------------------------------------------- */

@Composable
fun SectionBox(
    title: String,
    content: @Composable () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
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

@Composable
fun NumberField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {

    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->

            // السماح بالأرقام والفاصلة والنقطة فقط
            val cleaned = newValue
                .replace(',', '.')
                .filter {
                    it.isDigit() || it == '.'
                }

            onValueChange(cleaned)
        },
        label = {
            Text(label)
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Composable
fun ErrorBox(
    message: String
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = message,
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ResultBox(
    title: String,
    value: String
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

/* ---------------------------------------------------------
   تنسيق الأرقام
--------------------------------------------------------- */

fun formatNumber(value: Double): String {

    return if (value % 1.0 == 0.0) {
        value.toInt().toString()
    } else {
        String.format("%.2f", value)
            .replace(',', '.')
    }
}
