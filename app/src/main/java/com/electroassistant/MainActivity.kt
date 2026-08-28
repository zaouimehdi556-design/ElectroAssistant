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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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

    var page by remember {
        mutableStateOf("home")
    }

    MaterialTheme {

        Surface(
            modifier = Modifier.fillMaxSize()
        ) {

            when (page) {

                "home" -> HomeScreen(
                    onChute = {
                        page = "chute"
                    },
                    onSection = {
                        page = "section"
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
            }
        }
    }
}

@Composable
fun HomeScreen(
    onChute: () -> Unit,
    onSection: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "⚡ ElectroAssistant",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Assistant technique – Électricité bâtiment"
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Button(
            onClick = onChute,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("📐 Chute de tension")
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Button(
            onClick = onSection,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("🔌
