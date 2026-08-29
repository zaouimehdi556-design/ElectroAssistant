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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

/* =========================================================
   APP
   ========================================================= */

@Composable
fun ElectroAssistantApp() {

    var screen by remember {
        mutableStateOf("home")
    }

    when (screen) {

        "home" -> HomeScreen(
            onBreaker = {
                screen = "breaker"
            },
            onPlan = {
                screen = "plan"
            },
            onCable = {
                screen = "cable"
            }
        )

        "breaker" -> BreakerScreen(
            onBack = {
                screen = "home"
            }
        )

        "plan" -> PlanScreen(
            onBack = {
                screen = "home"
            }
        )

        "cable" -> CableScreen(
            onBack = {
                screen = "home"
            }
        )
    }
}

/* =========================================================
   HOME
   ========================================================= */

@Composable
fun HomeScreen(
    onBreaker: () -> Unit,
    onPlan: () -> Unit,
    onCable: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ElectroAssistant",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = "المساعد الكهربائي",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "أدوات بسيطة للحسابات الكهربائية"
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            MenuCard(
                title = "حساب القاطع",
                description = "احسب التيار والقاطع المناسب حسب القدرة والجهد.",
                buttonText = "فتح",
                onClick = onBreaker
            )

            MenuCard(
                title = "حساب القدرة والتيار",
                description = "احسب القدرة، التيار، والقدرة الظاهرية.",
                buttonText = "فتح",
                onClick = onPlan
            )

            MenuCard(
                title = "حساب مقطع الكابل",
                description = "اقتراح مقطع كابل حسب التيار.",
                buttonText = "فتح",
                onClick = onCable
            )
        }
    }
}

@Composable
fun MenuCard(
    title: String,
    description: String,
    buttonText: String,
    onClick: () -> Unit
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
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = description
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(buttonText)
            }
        }
    }
}

/* =========================================================
   BREAKER SCREEN
   ========================================================= */

@Composable
fun BreakerScreen(
    onBack: () -> Unit
) {

    var powerText by remember {
        mutableStateOf("")
    }

    var voltageText by remember {
        mutableStateOf("230")
    }

    var result by remember {
        mutableStateOf("")
    }

    var error by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("حساب القاطع")
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            SectionBox(
                title = "بيانات الحمل"
            ) {

                NumberField(
                    value = powerText,
                    label = "القدرة بالواط W",
                    onValueChange = {
                        powerText = it
                        error = ""
                    }
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                NumberField(
                    value = voltageText,
                    label = "الجهد بالفولت V",
                    onValueChange = {
                        voltageText = it
                        error = ""
                    }
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Button(
                    onClick = {

                        val power = powerText.toDoubleOrNull()
                        val voltage = voltageText.toDoubleOrNull()

                        if (power == null || voltage == null || power <= 0 || voltage <= 0) {

                            error = "أدخل قدرة وجهد صحيحين."

                            result = ""

                        } else {

                            val current = power / voltage
                            val recommended = chooseBreaker(current)

                            result =
                                "التيار المحسوب: ${formatNumber(current)} A\n" +
                                "القاطع المقترح: ${formatNumber(recommended)} A"

                            error = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("احسب")
                }
            }

            if (error.isNotEmpty()) {
                ErrorBox(error)
            }

            if (result.isNotEmpty()) {
                ResultBox(
                    title = "النتيجة",
                    text = result
                )
            }

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("رجوع")
            }
        }
    }
}

/* =========================================================
   PLAN / POWER SCREEN
   ========================================================= */

@Composable
fun PlanScreen(
    onBack: () -> Unit
) {

    var voltageText by remember {
        mutableStateOf("230")
    }

    var currentText by remember {
        mutableStateOf("")
    }

    var powerFactorText by remember {
        mutableStateOf("1")
    }

    var result by remember {
        mutableStateOf("")
    }

    var error by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("حساب القدرة والتيار")
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            SectionBox(
                title = "بيانات الشبكة"
            ) {

                NumberField(
                    value = voltageText,
                    label = "الجهد V",
                    onValueChange = {
                        voltageText = it
                        error = ""
                    }
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                NumberField(
                    value = currentText,
                    label = "التيار A",
                    onValueChange = {
                        currentText = it
                        error = ""
                    }
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                NumberField(
                    value = powerFactorText,
                    label = "معامل القدرة cos φ",
                    onValueChange = {
                        powerFactorText = it
                        error = ""
                    }
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

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

                            error =
                                "تحقق من الجهد والتيار ومعامل القدرة."

                            result = ""

                        } else {

                            val power = voltage * current * pf
                            val apparent = voltage * current

                            result =
                                "القدرة الفعلية: ${formatNumber(power)} W\n" +
                                "القدرة الظاهرية: ${formatNumber(apparent)} VA\n" +
                                "القدرة بالكيلوواط: ${formatNumber(power / 1000.0)} kW"

                            error = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("احسب")
                }
            }

            if (error.isNotEmpty()) {
                ErrorBox(error)
            }

            if (result.isNotEmpty()) {
                ResultBox(
                    title = "النتيجة",
                    text = result
                )
            }

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("رجوع")
            }
        }
    }
}

/* =========================================================
   CABLE SCREEN
   ========================================================= */

@Composable
fun CableScreen(
    onBack: () -> Unit
) {

    var currentText by remember {
        mutableStateOf("")
    }

    var lengthText by remember {
        mutableStateOf("")
    }

    var result by remember {
        mutableStateOf("")
    }

    var error by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("حساب مقطع الكابل")
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            SectionBox(
                title = "بيانات الكابل"
            ) {

                NumberField(
                    value = currentText,
                    label = "التيار A",
                    onValueChange = {
                        currentText = it
                        error = ""
                    }
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                NumberField(
                    value = lengthText,
                    label = "طول الخط بالمتر",
                    onValueChange = {
                        lengthText = it
                        error = ""
                    }
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Button(
                    onClick = {

                        val current = currentText.toDoubleOrNull()
                        val length = lengthText.toDoubleOrNull()

                        if (
                            current == null ||
                            length == null ||
                            current <= 0 ||
                            length <= 0
                        ) {

                            error = "أدخل التيار والطول بشكل صحيح."
                            result = ""

                        } else {

                            val section = chooseCableSection(
                                current = current,
                                length = length
                            )

                            result =
                                "التيار: ${formatNumber(current)} A\n" +
                                "الطول: ${formatNumber(length)} m\n" +
                                "المقطع المقترح: ${formatNumber(section)} mm²"

                            error = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("احسب")
                }
            }

            if (error.isNotEmpty()) {
                ErrorBox(error)
            }

            if (result.isNotEmpty()) {
                ResultBox(
                    title = "النتيجة",
                    text = result
                )
            }

            Text(
                text = "ملاحظة: اختيار المقطع النهائي يجب أن يأخذ في الاعتبار طريقة التمديد، طول الخط، هبوط الجهد ودرجة الحرارة.",
                fontSize = 13.sp
            )

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("رجوع")
            }
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
        onValueChange = { newValue ->

            val filtered = newValue.filter {
                it.isDigit() || it == '.' || it == ','
            }.replace(',', '.')

            onValueChange(filtered)
        },
        label = {
            Text(label)
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        )
    )
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
                fontSize = 20.sp,
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
   ERROR BOX
   ========================================================= */

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

/* =========================================================
   RESULT BOX
   ========================================================= */

@Composable
fun ResultBox(
    title: String,
    text: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = title,
                fontSize = 20.sp,
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
                text = text,
                fontSize = 17.sp,
                lineHeight = 25.sp
            )
        }
    }
}

/* =========================================================
   CALCULATIONS
   ========================================================= */

fun chooseBreaker(
    current: Double
): Double {

    val breakers = listOf(
        2.0,
        4.0,
        6.0,
        10.0,
        16.0,
        20.0,
        25.0,
        32.0,
        40.0,
        50.0,
        63.0,
        80.0,
        100.0,
        125.0,
        160.0,
        200.0,
        250.0,
        315.0,
        400.0
    )

    val designCurrent = current * 1.25

    return breakers.firstOrNull {
        it >= designCurrent
    } ?: breakers.last()
}

fun chooseCableSection(
    current: Double,
    length: Double
): Double {

    /*
     * جدول تقريبي مبسط للكابلات النحاسية.
     * القيمة النهائية يجب مراجعتها حسب طريقة التركيب
     * وهبوط الجهد والظروف الفعلية.
     */

    val sectionByCurrent = when {
        current <= 10 -> 1.5
        current <= 16 -> 2.5
        current <= 25 -> 4.0
        current <= 32 -> 6.0
        current <= 40 -> 10.0
        current <= 50 -> 10.0
        current <= 63 -> 16.0
        current <= 80 -> 25.0
        current <= 100 -> 35.0
        current <= 125 -> 50.0
        current <= 160 -> 70.0
        current <= 200 -> 95.0
        current <= 250 -> 120.0
        current <= 315 -> 150.0
        current <= 400 -> 240.0
        else -> 300.0
    }

    /*
     * تعويض تقريبي لطول الخط.
     * إذا كان الخط طويلاً جداً نرفع المقطع.
     */

    return when {
        length <= 20 -> sectionByCurrent
        length <= 40 -> nextCableSection(sectionByCurrent)
        length <= 60 -> nextCableSection(
            nextCableSection(sectionByCurrent)
        )
        length <= 100 -> nextCableSection(
            nextCableSection(
                nextCableSection(sectionByCurrent)
            )
        )
        else -> nextCableSection(
            nextCableSection(
                nextCableSection(
                    nextCableSection(sectionByCurrent)
                )
            )
        )
    }
}

fun nextCableSection(
    section: Double
): Double {

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
        240.0,
        300.0
    )

    val index = sections.indexOfFirst {
        it >= section
    }

    if (index == -1) {
        return sections.last()
    }

    if (index + 1 >= sections.size) {
        return sections.last()
    }

    return sections[index + 1]
}

/* =========================================================
   FORMAT NUMBER
   ========================================================= */

fun formatNumber(
    value: Double
): String {

    if (!value.isFinite()) {
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
