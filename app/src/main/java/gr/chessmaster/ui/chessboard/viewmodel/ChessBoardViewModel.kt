package gr.chessmaster.ui.chessboard.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.chessmaster.ui.chessboard.model.ChessBoardPlace
import gr.chessmaster.ui.chessboard.model.ChessBoardUiState
import javax.inject.Inject

@HiltViewModel
class ChessBoardViewModel @Inject constructor() : ViewModel(){

    private val _uiState: MutableState<ChessBoardUiState> = mutableStateOf(ChessBoardUiState())
    val uiState: State<ChessBoardUiState> = _uiState

    init {
        generateChessBoard()
    }

    private fun generateChessBoard() {
        _uiState.value.chessBoard.clear()
        for (i in 0 until uiState.value.chessBoardSize.value) {
            for (j in 0 until uiState.value.chessBoardSize.value) {
                _uiState.value.chessBoard.add(
                    ChessBoardPlace(
                        xPositions = i,
                        yPositions = j,
                    )
                )
            }
        }
    }

    fun onSizeOfTheBoardSpinnerValueChange(value: Int) {
        _uiState.value = _uiState.value.copy(chessBoardSize = mutableStateOf(value))
        generateChessBoard()
        updateSeeResultButtonState()
    }

    fun onMaxNumberOfMovesEditTextValueChange(value: String) {
        value.toIntOrNull()?.let {
            _uiState.value = _uiState.value.copy(hasMaxNumberOfMovesError = mutableStateOf(false))
        } ?: run {
            _uiState.value = _uiState.value.copy(hasMaxNumberOfMovesError = mutableStateOf(true))
        }
        _uiState.value = _uiState.value.copy(maxNumberOfMoves = mutableStateOf(value.toIntOrNull()))
        updateSeeResultButtonState()
    }

    fun onChessBoardPlaceClicked(chessBoardPlace: ChessBoardPlace) {
        if(_uiState.value.startChessBoardPlace.value == null){
            _uiState.value.startChessBoardPlace.value = chessBoardPlace
            _uiState.value.chessBoard.find { it == chessBoardPlace }?.color?.value = Color.Blue
        }else if(_uiState.value.endChessBoardPlace.value == null && _uiState.value.startChessBoardPlace.value != chessBoardPlace){
            _uiState.value.endChessBoardPlace.value = chessBoardPlace
            _uiState.value.chessBoard.find { it == chessBoardPlace }?.color?.value = Color.DarkGray
        }
        updateSeeResultButtonState()
    }

    fun onSeeTheResultsButtonClicked() {
        //TODO
    }

    fun onResetTheBoardButtonClicked() {
        _uiState.value.startChessBoardPlace.value = null
        _uiState.value.endChessBoardPlace.value = null
        generateChessBoard()
        updateSeeResultButtonState()
    }

    fun updateSeeResultButtonState() {
        _uiState.value.isSeeTheResultsButtonEnabled.value = _uiState.value.startChessBoardPlace.value != null
                && _uiState.value.endChessBoardPlace.value != null && _uiState.value.maxNumberOfMoves.value != null
                && !_uiState.value.hasMaxNumberOfMovesError.value
    }

}