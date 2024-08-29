package gr.chessmaster.ui.chessboard.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotMutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import gr.chessmaster.util.LOWER_LIMIT_SIZE_OF_CHESSBOARD
import androidx.compose.ui.graphics.Color

data class ChessBoardUiState(
    val chessBoard: SnapshotStateList<ChessBoardPlace> = mutableStateListOf(),
    val chessBoardSize: MutableState<Int> = mutableIntStateOf(LOWER_LIMIT_SIZE_OF_CHESSBOARD),
    val maxNumberOfMoves: MutableState<Int?> = mutableStateOf(null),
    val hasMaxNumberOfMovesError: MutableState<Boolean> = mutableStateOf(false),
    val startChessBoardPlace: MutableState<ChessBoardPlace?> = mutableStateOf(null),
    val endChessBoardPlace: MutableState<ChessBoardPlace?> = mutableStateOf(null),
    val isSeeTheResultsButtonEnabled: MutableState<Boolean> = mutableStateOf(false)
)

data class ChessBoardPlace(
    val xPositions: Int,
    val yPositions: Int,
    val selectedState: MutableState<Boolean> = mutableStateOf(false),
    val color: MutableState<Color> = mutableStateOf(if ((xPositions + yPositions) % 2 == 0) Color.Black else Color.White)
)