package com.lianglliu.countdowntimer.ui.components.picker

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.lianglliu.countdowntimer.ui.viewmodels.TimerViewState

@Composable
fun MinutesSecondsPicker(
    modifier: Modifier = Modifier,
    value: TimerViewState,
    onValueChange: (TimerViewState) -> Unit,
    dividersColor: Color,
    textColor: Color,
) {
    val hoursRange: List<String> = initHoursData()
    val minutesRange: List<String> = initMinutesData()
    val secondsRange: List<String> = initMinutesData()
    val textStyle = TextStyle(
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
        color = textColor
    )

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        val pickerSelectStyle = Modifier.weight(1f)

        PickerSelect(
            pickerSelectStyle,
            value = String.format("%02d", value.hours),
            onValueChange = {
                onValueChange(value.copy(hours = it.toInt()))
            },
            dividersColor = dividersColor,
            textStyle = textStyle,
            range = hoursRange,
            "时",
            textColor
        )

        PickerSelect(
            pickerSelectStyle,
            value = String.format("%02d", value.minutes),
            onValueChange = {
                onValueChange(value.copy(minutes = it.toInt()))
            },
            dividersColor = dividersColor,
            textStyle = textStyle,
            range = minutesRange,
            "分",
            textColor
        )

        PickerSelect(
            pickerSelectStyle,
            value = String.format("%02d", value.seconds),
            onValueChange = {
                onValueChange(value.copy(seconds = it.toInt()))
            },
            dividersColor,
            textStyle,
            secondsRange, "秒", textColor
        )
    }
}

@Composable
fun PickerSelect(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    dividersColor: Color,
    textStyle: TextStyle,
    range: List<String>,
    smallText: String,
    textColor: Color,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        StringPicker(
            value = value,
            onValueChange = onValueChange,
            dividersColor = dividersColor,
            textStyle = textStyle,
            range = range
        )

        SmallText(smallText, textColor)
    }
}

@Composable
fun SmallText(
    text: String,
    textColor: Color
) {
    Text(
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        text = text,
        fontSize = 12.sp,
        color = textColor
    )
}

private fun initHoursData(): List<String> {
    return listOf(
        "00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
        "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
        "20", "21", "22", "23",
    )
}

private fun initMinutesData(): List<String> {
    return listOf(
        "00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
        "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
        "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
        "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
        "40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
        "50", "51", "52", "53", "54", "55", "56", "57", "58", "59",
    )
}

