package com.ug.taskaya.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.ug.taskaya.R
import com.ug.taskaya.ui.theme.LightGreen
import com.ug.taskaya.ui.theme.Ment
import com.ug.taskaya.utils.SharedState


@Composable
fun LabelsBar(labels: List<String>, onClickLabel: () -> Unit, currentSelectedLabel: String) {

    val displayLabels = listOf("All") + labels

    var selectedLabel by remember { mutableStateOf(currentSelectedLabel) }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 48.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(displayLabels) { label ->
            LabelChip(
                label = label,
                isSelected = selectedLabel == label,
                onClick = {
                    selectedLabel = label
                    onClickLabel()
                    SharedState.updateCurrentLabel(label)
                }
            )
        }
    }
}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LabelChip(label: String, isSelected: Boolean, onClick: () -> Unit) {

    Chip(
        onClick = onClick,
        colors = ChipDefaults.chipColors(
            backgroundColor = if (isSelected) Ment else LightGreen
        ),
        modifier = Modifier.height(40.dp)
    ) {
        Text(
          text = label,
          color = if (isSelected) Color.White else Color.Black,
          fontFamily = FontFamily(Font(R.font.inter))
        )
    }
}