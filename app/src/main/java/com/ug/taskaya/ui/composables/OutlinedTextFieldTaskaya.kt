package com.ug.taskaya.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.ug.taskaya.R
import com.ug.taskaya.ui.theme.Ment
import com.ug.taskaya.utils.AuthState

@Composable
fun OutlinedTextFieldTaskaya(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    trailingIconId: Int,
    onValueChange: (String) -> Unit,
    authState: AuthState
){

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = value,
        label = {
            Text(text = label, fontFamily = FontFamily(Font(R.font.inter)))
        },
        trailingIcon = {
            Icon(
                painter = painterResource(id = trailingIconId),
                contentDescription = "")
        },
        singleLine = true,
        onValueChange = { onValueChange(it) },
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = Ment,
            focusedTrailingIconColor = Ment,
            focusedLabelColor = Ment,
            focusedBorderColor = Ment
        ),
        maxLines = 1,
        shape = RoundedCornerShape(5.dp),
        isError = (authState == AuthState.Error)
    )
}