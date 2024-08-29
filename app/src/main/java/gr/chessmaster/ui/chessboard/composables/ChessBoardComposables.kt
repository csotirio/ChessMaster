package gr.chessmaster.ui.chessboard.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.NumberPicker
import gr.chessmaster.R
import gr.chessmaster.ui.chessboard.model.ChessBoardPlace
import gr.chessmaster.ui.chessboard.model.ChessBoardUiState
import gr.chessmaster.ui.theme.ChessMasterTheme
import gr.chessmaster.util.LOWER_LIMIT_SIZE_OF_CHESSBOARD
import gr.chessmaster.util.UPPER_LIMIT_SIZE_OF_CHESSBOARD

private typealias OnSizeOfTheBoardSpinnerValueChange = (Int) -> Unit
private typealias OnMaxNumberOfMovesEditTextValueChange = (String) -> Unit
private typealias OnChessBoardPlaceClicked = (ChessBoardPlace) -> Unit
private typealias OnSeeTheResultsButtonClicked = () -> Unit
private typealias OnResetTheBoardButtonClicked = () -> Unit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChessboardScreen(
    uiState: ChessBoardUiState = ChessBoardUiState(),
    onSizeOfTheBoardSpinnerValueChange: OnSizeOfTheBoardSpinnerValueChange,
    onMaxNumberOfMovesEditTextValueChange: OnMaxNumberOfMovesEditTextValueChange,
    onChessBoardPlaceClicked: OnChessBoardPlaceClicked,
    onSeeTheResultsButtonClicked: OnSeeTheResultsButtonClicked,
    onResetTheBoardButtonClicked: OnResetTheBoardButtonClicked
) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 22.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            )

        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                item {
                    Chessboard(
                        chestBoardSize = uiState.chessBoardSize.value,
                        places = uiState.chessBoard,
                        onChessBoardPlaceClicked = onChessBoardPlaceClicked
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = { onSeeTheResultsButtonClicked() },
                            enabled = uiState.isSeeTheResultsButtonEnabled.value
                        ) {
                            Text(
                                text = "See the results",
                                fontSize = 16.sp,
                                modifier = Modifier,
                                textAlign = TextAlign.Center
                            )
                        }
                        Button(
                            onClick = { onResetTheBoardButtonClicked() }
                        ) {
                            Text(
                                text = "Reset the board",
                                fontSize = 16.sp,
                                modifier = Modifier,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        SizeOfTheBoardSpinnerWithTitle(
                            pickerValue = uiState.chessBoardSize,
                            onSizeOfTheBoardSpinnerValueChange = onSizeOfTheBoardSpinnerValueChange,
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(10.dp)
                        )
                        MaxNumberOfMovesInputFieldWithTitle(
                            value = uiState.maxNumberOfMoves,
                            onMaxNumberOfMovesEditTextValueChange = onMaxNumberOfMovesEditTextValueChange,
                            hasError = uiState.hasMaxNumberOfMovesError,
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(10.dp)
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun Chessboard(
    chestBoardSize: Int,
    places: List<ChessBoardPlace>,
    onChessBoardPlaceClicked: OnChessBoardPlaceClicked
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .border(5.dp, Color.Red, RectangleShape)
            .padding(5.dp)
    ) {
        for (i in 0 until chestBoardSize) {
            Row {
                for (j in 0 until chestBoardSize) {
                    val place = places.firstOrNull { it.xPositions == j && it.yPositions == i }
                    place?.let {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .background(place.color.value)
                                .clickable { onChessBoardPlaceClicked(place) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SizeOfTheBoardSpinnerWithTitle(
    pickerValue: MutableState<Int>,
    onSizeOfTheBoardSpinnerValueChange: OnSizeOfTheBoardSpinnerValueChange,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Pick the size of the board",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        SizeOfTheBoardSpinner(
            pickerValue = pickerValue,
            onSizeOfTheBoardSpinnerValueChange = onSizeOfTheBoardSpinnerValueChange,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun SizeOfTheBoardSpinner(
    pickerValue: MutableState<Int>,
    onSizeOfTheBoardSpinnerValueChange: OnSizeOfTheBoardSpinnerValueChange,
    modifier: Modifier = Modifier
) {
    NumberPicker(
        value = pickerValue.value,
        range = LOWER_LIMIT_SIZE_OF_CHESSBOARD..UPPER_LIMIT_SIZE_OF_CHESSBOARD,
        onValueChange = { onSizeOfTheBoardSpinnerValueChange(it) },
        modifier = modifier
    )
}

@Composable
fun MaxNumberOfMovesInputFieldWithTitle(
    value: MutableState<Int?>,
    onMaxNumberOfMovesEditTextValueChange: OnMaxNumberOfMovesEditTextValueChange,
    hasError: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Pick the size of the board",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        MaxNumberOfMovesInputField(
            value = value,
            onMaxNumberOfMovesEditTextValueChange = onMaxNumberOfMovesEditTextValueChange,
            hasError = hasError,
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 20.dp)
        )
    }
}

@Composable
fun MaxNumberOfMovesInputField(
    value: MutableState<Int?>,
    onMaxNumberOfMovesEditTextValueChange: OnMaxNumberOfMovesEditTextValueChange,
    hasError: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value.value?.toString() ?: "",
        onValueChange = { onMaxNumberOfMovesEditTextValueChange(it) },
        label = { Text("Max Number of moves") },
        supportingText = { if (hasError.value) Text("Please set a number") },
        isError = hasError.value,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun ChessboardScreenPreview() {
    ChessMasterTheme {
        ChessboardScreen(
            ChessBoardUiState(),
            {}, {}, {}, {}, {}
        )
    }
}