package gr.chessmaster.ui.chessboard.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.chargemap.compose.numberpicker.NumberPicker
import gr.chessmaster.R
import gr.chessmaster.ui.theme.ChessMasterTheme
import gr.chessmaster.util.LOWER_LIMIT_SIZE_OF_CHESSBOARD
import gr.chessmaster.util.UPPER_LIMIT_SIZE_OF_CHESSBOARD

private typealias OnPickerValueChange = (Int) -> Unit
private typealias OnMaxNumberOfMovesEditTextValueChange = (String) -> Unit
private typealias OnSeeTheResultsButtonClicked = () -> Unit
private typealias OnResetTheBoardButtonClicked = () -> Unit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent() {
    val navController = rememberNavController()
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
                        boardSize = 8,
                        selectedPlaces = listOf(Pair(4,5), Pair(7,4))
                    )
                    Row {
                        Button(
                            onClick = { /*TODO*/ },
                        ) {
                            Text(
                                text = "See the results",
                                fontSize = 16.sp,
                                modifier = Modifier,
                                textAlign = TextAlign.Center
                            )
                        }
                        Button(
                            onClick = { /*TODO*/ },
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
                            pickerValue = 6,
                            onPickerValueChange = { lal -> },
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(10.dp)
                        )
                        MaxNumberOfMovesInputFieldWithTitle(
                            value = remember { mutableStateOf("0") },
                            onMaxNumberOfMovesEditTextValueChange = { lala -> },
                            hasError = remember { mutableStateOf(false) },
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
    boardSize: Int,
    selectedPlaces: List<Pair<Int, Int>>
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .border(5.dp, Color.Red, RectangleShape)
        .padding(5.dp)
    ) {
        for (i in 0 until boardSize) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (j in 0 until boardSize) {
                    val placeColor = if (selectedPlaces.contains(Pair(i,j))) {
                        if(selectedPlaces.indexOf(Pair(i,j)) == 0 ){
                            Color.Red
                        }else {
                            Color.Blue
                        }
                    }else {
                        if ((i + j) % 2 == 0) Color.White else Color.Black
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .background(placeColor)
                    )
                }
            }
        }
    }
}

@Composable
fun SizeOfTheBoardSpinnerWithTitle(
    pickerValue: Int,
    onPickerValueChange: OnPickerValueChange,
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
            onPickerValueChange = onPickerValueChange,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun SizeOfTheBoardSpinner(
    pickerValue: Int,
    onPickerValueChange: OnPickerValueChange,
    modifier: Modifier = Modifier
) {
    NumberPicker(
        value = pickerValue,
        range = LOWER_LIMIT_SIZE_OF_CHESSBOARD..UPPER_LIMIT_SIZE_OF_CHESSBOARD,
        onValueChange = onPickerValueChange,
        modifier = modifier
    )
}

@Composable
fun MaxNumberOfMovesInputFieldWithTitle(
    value: MutableState<String>,
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
    value: MutableState<String>,
    onMaxNumberOfMovesEditTextValueChange: OnMaxNumberOfMovesEditTextValueChange,
    hasError: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value.value,
        onValueChange = onMaxNumberOfMovesEditTextValueChange,
        label = { Text("Max Number of moves") },
        supportingText = { Text("Max Number of moves") },
        isError = hasError.value,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChessMasterTheme {
        MainScreenContent()
    }
}