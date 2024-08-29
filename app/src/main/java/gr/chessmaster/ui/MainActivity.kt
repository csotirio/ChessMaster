package gr.chessmaster.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import gr.chessmaster.ui.chessboard.composables.MainScreenContent
import gr.chessmaster.ui.theme.ChessMasterTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChessMasterTheme {
                MainScreenContent()
            }
        }
    }
}