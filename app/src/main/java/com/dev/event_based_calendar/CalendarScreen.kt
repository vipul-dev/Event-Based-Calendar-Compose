package com.dev.event_based_calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev.event_based_calendar.ui.theme.CasualLeaveColor
import com.dev.event_based_calendar.ui.theme.EarnedLeaveColor
import com.dev.event_based_calendar.ui.theme.ExtraordinaryLeaveColor
import com.dev.event_based_calendar.ui.theme.HalfPayLeaveColor
import com.dev.event_based_calendar.ui.theme.PublicLeaveColor
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalTime::class)
@Composable
@Preview
fun CalendarScreen(modifier: Modifier = Modifier) {
    val sampleEvents = listOf(
        CalendarEvent(LocalDate(2026, 1, 1), "New Year", CalendarEventType.PUBLIC_HOLIDAY),
        CalendarEvent(LocalDate(2026, 1, 14), "Makar Snakranti", CalendarEventType.PUBLIC_HOLIDAY),
        CalendarEvent(LocalDate(2026, 1, 26), "Republic Day", CalendarEventType.PUBLIC_HOLIDAY),
        CalendarEvent(LocalDate(2026, 3, 19), "Ugadi", CalendarEventType.PUBLIC_HOLIDAY),
        CalendarEvent(LocalDate(2026, 4, 3), "Good Friday", CalendarEventType.PUBLIC_HOLIDAY),
        CalendarEvent(LocalDate(2026, 5, 1), "May Day", CalendarEventType.PUBLIC_HOLIDAY),
        CalendarEvent(LocalDate(2026, 10, 2), "Gandhi Jayanti", CalendarEventType.PUBLIC_HOLIDAY),
        CalendarEvent(LocalDate(2026, 10, 20), "Vijyadashmi", CalendarEventType.PUBLIC_HOLIDAY),
        CalendarEvent(LocalDate(2026, 11, 9), "Deepawali", CalendarEventType.PUBLIC_HOLIDAY),
        CalendarEvent(LocalDate(2026, 12, 25), "Christmas", CalendarEventType.PUBLIC_HOLIDAY),
    )

    val leaveType = mapOf(
        "Casual Leave" to CasualLeaveColor,
        "Public Holiday" to PublicLeaveColor,
        "Extraordinary Leave" to ExtraordinaryLeaveColor,
        "Half Pay Leave" to HalfPayLeaveColor,
        "Earned Leave" to EarnedLeaveColor,
    )

    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    var currentMonth by remember { mutableIntStateOf(now.month.number) }


    Column(
        modifier
            .fillMaxSize()
            .background(color = White)
    ) {

        AppTopBar(
            title = "Event Calendar",
        )
        EventCalendar(
            yearMonth = YearMonth(now.year, currentMonth),
            currentDate = LocalDate(year = now.year, month = now.month.number, day = now.day),
            events = sampleEvents.groupBy { it.date })

        Spacer(Modifier.height(20.dp))

        HorizontalDivider(Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp))

        Spacer(Modifier.height(20.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            Modifier.fillMaxSize()
        ) {

            items(leaveType.entries.toList()) { entry ->
                LeaveTypeCells(entry.key, entry.value)
            }


        }
    }

}

@Composable
fun LeaveTypeCells(leaveType: String, leaveColor: Color) {

    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier
            .size(16.dp)
            .background(color = leaveColor, CircleShape))
        Spacer(Modifier.width(8.dp))
        Text(text = leaveType, fontWeight = FontWeight.Medium, fontSize = 14.sp)
    }


}