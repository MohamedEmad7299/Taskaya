package com.ug.taskaya.ui.labels_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ug.taskaya.R
import com.ug.taskaya.ui.theme.Ment
import com.ug.taskaya.utils.SharedState

@Composable
fun LabelsScreen(
    navController: NavController,
    viewModel: LabelsViewModel = hiltViewModel()
){

    val screenState by viewModel.screenState.collectAsState()
    val context = LocalContext.current
    val selectedLabels by SharedState.selectedLabels.collectAsState()

    LaunchedEffect(Unit){
        viewModel.updateSelectedLabels(selectedLabels)
    }

    if (screenState.message.isNotEmpty()){
        LaunchedEffect(key1 = screenState.launchedEffectKey){
            Toast.makeText(context, screenState.message, Toast.LENGTH_SHORT).show()
        }
    }

    LabelsContent(
        screenState = screenState,
        labels = screenState.labels,
        addLabel = viewModel::addLabel,
        onSearchInputChange = viewModel::onChangeSearchInput,
        onClickBackButton = {
            navController.popBackStack()
        },
        onCheckedChange = viewModel::changeCheckState
    )
}



@Composable
fun LabelsContent(
    screenState: LabelsState,
    labels: List<String>,
    onClickBackButton: () -> Unit,
    onSearchInputChange: (String) -> Unit,
    addLabel: (String) -> Unit,
    onCheckedChange: (String) -> Unit,
) {

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        focusManager.clearFocus()
                    },
                    onDrag = { change, dragAmount ->
                        // Handle drag logic here if needed
                    }
                )
            }
    ) {
        ConstraintLayout(
            Modifier.fillMaxSize()
        ) {
            val (backArrow, labelList, addButton, searchBar) = createRefs()

            // Back Button
            IconButton(
                modifier = Modifier.constrainAs(backArrow) {
                    top.linkTo(parent.top, 16.dp)
                    start.linkTo(parent.start)
                },
                onClick = onClickBackButton
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = null
                )
            }

            // Search Bar
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .constrainAs(searchBar) {
                        start.linkTo(backArrow.end)
                        end.linkTo(parent.end)
                        top.linkTo(backArrow.top)
                        bottom.linkTo(backArrow.bottom)
                    },
                value = screenState.searchInput,
                onValueChange = onSearchInputChange,
                colors = TextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color.Black,
                ),
                placeholder = {
                    Text(
                        text = "Enter label name",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF777777),
                        )
                    )
                }
            )

            // Add Button
            if (screenState.searchInput.isNotBlank() &&
                labels.none { it.equals(screenState.searchInput, ignoreCase = true) }) {

                Row(
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            addLabel(screenState.searchInput)
                        }
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                        .constrainAs(addButton) {
                            top.linkTo(searchBar.bottom, 16.dp)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(id = R.drawable.baseline_add_24),
                        contentDescription = null,
                        tint = Color.Black
                    )

                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = "Create “${screenState.searchInput}”",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF090909),
                        )
                    )
                }
            }

            // Labels List
            LabelsList(
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .constrainAs(labelList) {
                        top.linkTo(if (screenState.searchInput.isNotBlank() && labels.none { it.equals(screenState.searchInput, ignoreCase = true) }) addButton.bottom else searchBar.bottom, 16.dp)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.fillToConstraints
                    },
                labels = labels.filter { it.contains(screenState.searchInput, ignoreCase = true) },
                selectedLabels = screenState.selectedLabels,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

@Composable
fun LabelsList(
    modifier: Modifier = Modifier,
    labels: List<String>,
    selectedLabels: List<String>,
    onCheckedChange: (String) -> Unit,
){


    LazyColumn(
        modifier = modifier
            .background(Color.White),
    ){
        items(labels){ label ->

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal =  16.dp , vertical = 8.dp)
            ){

                val (labelIcon,labelName,checkBox) = createRefs()

                Icon(
                    modifier = Modifier
                        .constrainAs(labelIcon){
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            bottom.linkTo(parent.bottom)
                        }
                        .size(24.dp),
                    painter = painterResource(R.drawable.label_icon),
                    contentDescription = "Tag Icon"
                )

                Text(
                    modifier = Modifier
                        .constrainAs(labelName){
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(labelIcon.end,16.dp)
                        },
                    text = label,
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF090909),
                    )
                )

                Checkbox(
                    modifier = Modifier
                        .constrainAs(checkBox){
                            bottom.linkTo(parent.bottom)
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        },
                    checked = selectedLabels.contains(label),
                    onCheckedChange = { onCheckedChange(label) },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Ment
                    )
                )
            }
        }
    }
}