
package com.example.tccfinanceiro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.gestaofinanceira.screens.AppNavigation
import com.example.tccfinanceiro.ui.theme.TCCfinanceiroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TCCfinanceiroTheme {
                AppNavigation()
            }
        }
    }
}
