package com.dev.event_based_calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    title: String = "Title",
    onBackClick: () -> Unit = {},
    onMenuIconClick: () -> Unit = {},
    menuIconVis: Boolean = false,
    backIconVis: Boolean = false,
    menuIcon: ImageVector = Icons.Filled.MoreVert,
    backIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(Color.White)
            .drawBehind {
                drawRect(
                    color = Color.Black.copy(alpha = 0.15f),
                    topLeft = Offset(0f, size.height),
                    size = Size(size.width, 2.dp.toPx())
                )
            }

    ) {
        // Back Button - Start
        if (backIconVis) {
            Icon(
                imageVector = backIcon,
                contentDescription = "BACK",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
                    .size(24.dp)
                    .clickable { onBackClick() },
                tint = Color.Unspecified
            )
        }


        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = Color.Black,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )

        }


        // Notification and Menu Icons - End
        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (menuIconVis) {
                Icon(
                    imageVector = menuIcon,
                    contentDescription = "MORE_OPTIONS",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(onClick = onMenuIconClick)
                )
            }
        }
    }
}
