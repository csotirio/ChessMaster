package gr.chessmaster.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import gr.chessmaster.ui.chessboard.composables.ChessboardScreen
import gr.chessmaster.ui.chessboard.viewmodel.ChessBoardViewModel
import gr.chessmaster.ui.theme.ChessMasterTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChessMasterTheme {
                Box{
                    val viewModel = hiltViewModel<ChessBoardViewModel>()
                    ChessboardScreen(
                        viewModel.uiState.value,
                        onSizeOfTheBoardSpinnerValueChange = viewModel::onSizeOfTheBoardSpinnerValueChange,
                        onMaxNumberOfMovesEditTextValueChange = viewModel::onMaxNumberOfMovesEditTextValueChange,
                        onChessBoardPlaceClicked = viewModel::onChessBoardPlaceClicked,
                        onSeeTheResultsButtonClicked = viewModel::onSeeTheResultsButtonClicked,
                        onResetTheBoardButtonClicked = viewModel::onResetTheBoardButtonClicked,
                    )
                }
            }
        }
    }
}