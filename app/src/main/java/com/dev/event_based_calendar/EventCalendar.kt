package com.dev.event_based_calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dev.event_based_calendar.ui.theme.CasualLeaveColor
import com.dev.event_based_calendar.ui.theme.EarnedLeaveColor
import com.dev.event_based_calendar.ui.theme.ExtraordinaryLeaveColor
import com.dev.event_based_calendar.ui.theme.HalfPayLeaveColor
import com.dev.event_based_calendar.ui.theme.PublicLeaveColor
import com.dev.event_based_calendar.ui.theme.WeekDaysColor
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.plus


@Composable
fun EventCalendar(
    yearMonth: YearMonth,
    events: Map<LocalDate, List<CalendarEvent>>,
    currentDate: LocalDate
) {

    var currentYearMonth by remember {
        mutableStateOf(YearMonth(yearMonth.year, yearMonth.month))
    }
    val days = generateYearMonthDays(currentYearMonth)
    val months = listOf(
        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )
    val weekDays = listOf("S", "M", "T", "W", "T", "F", "S")

    Column {


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            IconButton(
                onClick = {
                    currentYearMonth = currentYearMonth.previousMonth()
                }
            ) {
                Text("<")
            }

            Text(
                text = " ${months[currentYearMonth.month - 1]} ${currentYearMonth.year}",
                style = MaterialTheme.typography.titleMedium
            )

            IconButton(
                onClick = {
                    currentYearMonth = currentYearMonth.nextMonth()
                }
            ) {
                Text(">")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            weekDays.forEach { weekDay ->
                Box(
                    modifier = Modifier.background(
                        color = Color.Transparent, CircleShape
                    ).padding(4.dp).size(40.dp), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = weekDay,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        color = WeekDaysColor
                    )
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.wrapContentSize()
        ) {
            items(days.size) { index ->

                val date = days[index]

                if (date != null) {
                    val daysEvent = events[date] ?: emptyList()
                    DayCell(date = date, currentDate = currentDate, events = daysEvent)
                } else {
                    Box(Modifier.size(48.dp))
                }
            }
        }
    }

}

@Composable
fun DayCell(date: LocalDate, events: List<CalendarEvent>, currentDate: LocalDate) {
    Column(
        modifier = Modifier.padding(4.dp).size(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Box(
            Modifier.wrapContentSize()
                .background(
                    color = if (currentDate.day == date.day && currentDate.month == date.month) CasualLeaveColor else Color.Transparent,
                    shape = RoundedCornerShape(6.dp)
                ).padding(6.dp)
        ) {
            Text(
                text = date.day.toString(),
                color = if (currentDate.day == date.day && currentDate.month == date.month) Color.White else Color.Black,
            )
        }


        Row {
            events.take(3).forEach { event ->

                Box(
                    Modifier.size(12.dp).padding(1.dp).background(
                        color = when (event.type) {
                            CalendarEventType.CASUAL_LEAVE -> CasualLeaveColor
                            CalendarEventType.PUBLIC_HOLIDAY -> PublicLeaveColor
                            CalendarEventType.EXTRAORDINARY_LEAVE -> ExtraordinaryLeaveColor
                            CalendarEventType.HALF_PAY_LEAVE -> HalfPayLeaveColor
                            CalendarEventType.EARNED_LEAVE -> EarnedLeaveColor
                        },
                        shape = CircleShape
                    )
                )

            }
        }

    }

}

enum class CalendarEventType {
    CASUAL_LEAVE,
    PUBLIC_HOLIDAY,
    EXTRAORDINARY_LEAVE,
    HALF_PAY_LEAVE,
    EARNED_LEAVE
}

data class CalendarEvent(
    val date: LocalDate,
    val title: String,
    val type: CalendarEventType
)

data class YearMonth(
    val year: Int,
    val month: Int
)


fun YearMonth.nextMonth(): YearMonth {
    return if (month == 12) {
        YearMonth(year + 1, 1)
    } else {
        YearMonth(year, month + 1)
    }
}

fun YearMonth.previousMonth(): YearMonth {
    return if (month == 1) {
        YearMonth(year - 1, 12)
    } else {
        YearMonth(year, month - 1)
    }
}

fun LocalDate.lengthOfMonth(): Int {

    val firstDayNextMonth = if (this.monthNumber == 12) {
        LocalDate(this.year + 1, 1, 1)
    } else {
        LocalDate(this.year, this.monthNumber + 1, 1)
    }

    return this.daysUntil(firstDayNextMonth)
}

fun generateYearMonthDays(yearMonth: YearMonth): List<LocalDate?> {

    val firstDayOfMonth = LocalDate(yearMonth.year, yearMonth.month, 1)
    val daysInMonth = firstDayOfMonth.lengthOfMonth()

    val firstDaysOfWeek = firstDayOfMonth.dayOfWeek.isoDayNumber % 7

    val totalDays = firstDaysOfWeek + daysInMonth

    return List(totalDays) { index ->
        if (index < firstDaysOfWeek) null
        else firstDayOfMonth.plus(DatePeriod(days = index - firstDaysOfWeek))

    }

}