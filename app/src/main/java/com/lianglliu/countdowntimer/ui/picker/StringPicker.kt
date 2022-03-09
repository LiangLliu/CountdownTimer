package com.lianglliu.countdowntimer.ui.picker

import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun StringPicker(
    modifier: Modifier = Modifier,
    label: (String) -> String = {
        it
    },
    value: String,
    onValueChange: (String) -> Unit,
    dividersColor: Color = MaterialTheme.colors.primary,
    range: List<String>,
    textStyle: TextStyle = LocalTextStyle.current,
) {
    ListItemPicker(
        modifier = modifier,
        label = label,
        value = value,
        onValueChange = onValueChange,
        dividersColor = dividersColor,
        list = range,
        textStyle = textStyle
    )
}