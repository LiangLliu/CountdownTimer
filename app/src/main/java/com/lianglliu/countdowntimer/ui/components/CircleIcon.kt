package com.lianglliu.countdowntimer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.lianglliu.countdowntimer.ui.theme.bgColorCenter

@Composable
fun CircleIcon(
    imageVector: ImageVector,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = Modifier.padding(horizontal = 5.dp),
        onClick = onClick
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = "start",
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(bgColorCenter)
                .padding(10.dp)
        )
    }
}
