package gr.chessmaster.ui.chessboard.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import gr.chessmaster.util.LOWER_LIMIT_SIZE_OF_CHESSBOARD
import androidx.compose.ui.graphics.Color

data class ChessBoardUiState(
    val chessBoard: MutableList<ChessBoardPlaces> = mutableListOf(),
    val chessBoardSize: MutableState<Int> = mutableStateOf(LOWER_LIMIT_SIZE_OF_CHESSBOARD),
    val maxNumberOfMoves: MutableState<Int> = mutableStateOf(0),
    val hasMaxNumberOfMovesError: MutableState<Boolean> = mutableStateOf(false),
    val startPosition: MutableState<Pair<Int,Int>?> = mutableStateOf(null),
    val endPosition: MutableState<Pair<Int,Int>?> = mutableStateOf(null)
)

data class ChessBoardPlaces(
    val xPositions: Int,
    val yPositions: Int,
    val selectedState: MutableState<Boolean> = mutableStateOf(false),
    val color: MutableState<Color> = mutableStateOf(if ((xPositions + yPositions) % 2 == 0) Color.Black else Color.White)
)