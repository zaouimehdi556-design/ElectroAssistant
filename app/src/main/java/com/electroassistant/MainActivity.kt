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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ElectroAssistantApp()
                }
            }
        }
    }
}

@Composable
fun ElectroAssistantApp() {

    var selectedScreen by remember {
        mutableIntStateOf(0)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Electro Assistant",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },
        bottomBar = {
            NavigationBar {

                NavigationBarItem(
                    selected = selectedScreen == 0,
                    onClick = { selectedScreen = 0 },
                    icon = { Text("⌂") },
                    label = { Text("الرئيسية") }
                )

                NavigationBarItem(
                    selected = selectedScreen == 1,
                    onClick = { selectedScreen = 1 },
                    icon = { Text("⚡") },
                    label = { Text("القاطع") }
                )

                NavigationBarItem(
                    selected = selectedScreen == 2,
                    onClick = { selectedScreen = 2 },
                    icon = { Text("▣") },
                    label = { Text("الخطة") }
                )
            }
        }
    ) { padding ->

        when (selectedScreen) {

            0 -> HomeScreen(
                modifier = Modifier.padding(padding)
            )

            1 -> BreakerScreen(
                modifier = Modifier.padding(padding)
            )

            2 -> PlanScreen(
                modifier = Modifier.padding(padding)
            )
        }
    }
}

/* =========================================================
   HOME
   ========================================================= */

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "مساعد الكهرباء",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "أدوات سريعة للحسابات الكهربائية"
        )

        SectionBox(
            title = "⚡ حساب التيار"
        ) {

            Text(
                text = "يمكنك حساب التيار من القدرة والجهد."
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "I = P ÷ V",
                fontWeight = FontWeight.Bold
            )
        }

        SectionBox(
            title = "🔌 حساب القدرة"
        ) {

            Text(
                text = "القدرة الكهربائية:"
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "P = V × I",
                fontWeight = FontWeight.Bold
            )
        }

        SectionBox(
            title = "🛡 اختيار القاطع"
        ) {

            Text(
                text = "أدخل القدرة والجهد في صفحة القاطع للحصول على قيمة مقترحة."
            )
        }

        SectionBox(
            title = "📐 حساب هبوط الجهد"
        ) {

            Text(
                text = "أدخل طول الكابل والتيار والمقاومة لحساب هبوط الجهد."
            )
        }
    }
}

/* =========================================================
   BREAKER SCREEN
   ========================================================= */

@Composable
fun BreakerScreen(
    modifier: Modifier = Modifier
) {

    var powerText by remember {
        mutableStateOf("")
    }

    var voltageText by remember {
        mutableStateOf("230")
    }

    var result by remember {
        mutableStateOf<String?>(null)
    }

    var error by remember {
        mutableStateOf<String?>(null)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "⚡ حساب القاطع",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "حساب تقري للتيار واختيار قاطع مناسب."
        )

        SectionBox(
            title = "المعطيات"
        ) {

            NumberField(
                value = powerText,
                onValueChange = {
                    powerText = it
                    error = null
                },
                label = "القدرة (W)"
            )

            Spacer(modifier = Modifier.height(8.dp))

            NumberField(
                value = voltageText,
                onValueChange = {
                    voltageText = it
                    error = null
                },
                label = "الجهد (V)"
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {

                    val power = powerText.toDoubleOrNull()
                    val voltage = voltageText.toDoubleOrNull()

                    if (power == null || voltage == null) {

                        error = "أدخل أرقاماً صحيحة."

                        result = null

                    } else if (power <= 0 || voltage <= 0) {

                        error = "يجب أن تكون القيم أكبر من صفر."

                        result = null

                    } else {

                        val current = power / voltage

                        val recommended = when {
                            current <= 6 -> 6
                            current <= 10 -> 10
                            current <= 16 -> 16
                            current <= 20 -> 20
                            current <= 25 -> 25
                            current <= 32 -> 32
                            current <= 40 -> 40
                            current <= 50 -> 50
                            current <= 63 -> 63
                            else -> 80
                        }

                        result =
                            "التيار المحسوب: ${formatNumber(current)} A\n" +
                            "القاطع المقترح: ${recommended} A"

                        error = null
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("احسب")
            }
        }

        error?.let {
            ErrorBox(
                message = it
            )
        }

        result?.let {
            ResultBox(
                title = "النتيجة",
                message = it
            )
        }

        SectionBox(
            title = "⚠️ ملاحظة"
        ) {

            Text(
                text = "هذه نتيجة حسابية إرشادية وليست بديلاً عن اختيار القاطع والكابل حسب شروط التركيب والمعايير المحلية."
            )
        }
    }
}

/* =========================================================
   PLAN SCREEN
   ========================================================= */

@Composable
fun PlanScreen(
    modifier: Modifier = Modifier
) {

    var lengthText by remember {
        mutableStateOf("")
    }

    var currentText by remember {
        mutableStateOf("")
    }

    var resistanceText by remember {
        mutableStateOf("0.0175")
    }

    var result by remember {
        mutableStateOf<String?>(null)
    }

    var error by remember {
        mutableStateOf<String?>(null)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "📐 حساب هبوط الجهد",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "حساب تقري لهبوط الجهد في دائرة أحادية الطور."
        )

        SectionBox(
            title = "المعطيات"
        ) {

            NumberField(
                value = lengthText,
                onValueChange = {
                    lengthText = it
                    error = null
                },
                label = "طول الكابل (m)"
            )

            Spacer(modifier = Modifier.height(8.dp))

            NumberField(
                value = currentText,
                onValueChange = {
                    currentText = it
                    error = null
                },
                label = "التيار (A)"
            )

            Spacer(modifier = Modifier.height(8.dp))

            NumberField(
                value = resistanceText,
                onValueChange = {
                    resistanceText = it
                    error = null
                },
                label = "المقاومة النوعية Ω·mm²/m"
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {

                    val length = lengthText.toDoubleOrNull()
                    val current = currentText.toDoubleOrNull()
                    val resistance = resistanceText.toDoubleOrNull()

                    if (length == null ||
                        current == null ||
                        resistance == null
                    ) {

                        error = "أدخل جميع القيم."

                        result = null

                    } else if (
                        length <= 0 ||
                        current <= 0 ||
                        resistance <= 0
                    ) {

                        error = "يجب أن تكون القيم أكبر من صفر."

                        result = null

                    } else {

                        /*
                         * حساب مبسط لدائرة أحادية الطور:
                         *
                         * ΔV = 2 × L × I × ρ / S
                         *
                         * هنا نعرض المقاومة التقريبية
                         * لكل مقطع، بدون الحاجة لإدخال المقطع.
                         */

                        val section25 =
                            (2 * length * current * resistance) / 2.5

                        val section4 =
                            (2 * length * current * resistance) / 4.0

                        val section6 =
                            (2 * length * current * resistance) / 6.0

                        result =
                            "هبوط 2.5 mm²: ${formatNumber(section25)} V\n" +
                            "هبوط 4 mm²: ${formatNumber(section4)} V\n" +
                            "هبوط 6 mm²: ${formatNumber(section6)} V"

                        error = null
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("احسب")
            }
        }

        error?.let {
            ErrorBox(
                message = it
            )
        }

        result?.let {
            ResultBox(
                title = "النتيجة",
                message = it
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        SectionBox(
            title = "🔧 معلومات"
        ) {

            Text(
                text = "يمكن استعمال هذه الصفحة للمقارنة بين مقاطع الكابلات بشكل تقريبي."
            )
        }
    }
}

/* =========================================================
   SECTION BOX
   ========================================================= */

@Composable
fun SectionBox(
    title: String,
    content: @Composable () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
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

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            content()
        }
    }
}

/* =========================================================
   NUMBER FIELD
   ========================================================= */

@Composable
fun NumberField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {

    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->

            val filtered = newValue.filter {
                it.isDigit() || it == '.' || it == ','
            }

            onValueChange(
                filtered.replace(',', '.')
            )
        },
        label = {
            Text(label)
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

/* =========================================================
   ERROR BOX
   ========================================================= */

@Composable
fun ErrorBox(
    message: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme.colorScheme.errorContainer
        )
    ) {

        Text(
            text = "❌ $message",
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.onErrorContainer
        )
    }
}

/* =========================================================
   RESULT BOX
   ========================================================= */

@Composable
fun ResultBox(
    title: String,
    message: String
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

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Divider()

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

/* =========================================================
   FORMAT NUMBER
   ========================================================= */

fun formatNumber(
    value: Double
): String {

    if (value.isNaN() || value.isInfinite()) {
        return "0"
    }

    return if (value == value.toLong().toDouble()) {
        value.toLong().toString()
    } else {
        String.format(
            java.util.Locale.US,
            "%.2f",
            value
        )
    }
}
