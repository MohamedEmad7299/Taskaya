package com.ug.taskaya.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.ug.taskaya.R
import com.ug.taskaya.ui.theme.Ment

@Composable
fun OutlinedPasswordFieldTaskaya(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit
){

    var visibility by remember { mutableStateOf(false) }

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
                modifier = Modifier
                    .clickable { visibility = !visibility },
                painter = if (visibility) painterResource(id = R.drawable.visibility_off)
                else painterResource(id = R.drawable.visibility),
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
        visualTransformation = if (visibility) VisualTransformation.None else PasswordVisualTransformation()
    )
}