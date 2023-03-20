package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.Color
//import android.view.LayoutInflater
import android.widget.TextView
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var calendarView: CalendarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendarView = findViewById(R.id.calendarView)
        setupCalendar()
    }

    private fun setupCalendar() {
        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(6)
        val endMonth = currentMonth.plusMonths(6)

        calendarView.setup(startMonth, endMonth, WeekFields.of(Locale.getDefault()).firstDayOfWeek)
        calendarView.scrollToMonth(currentMonth)

        calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)

            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                container.textView.text = day.date.dayOfMonth.toString()

                // Show event indicator if there is an event on this day
                container.eventIndicator.visibility = if (hasEventOnDate(day.date)) View.VISIBLE else View.INVISIBLE

                // Change the text color based on the day owner (current month or not)
                container.textView.setTextColor(
                    if (day.owner == DayOwner.THIS_MONTH) Color.BLACK else Color.LTGRAY
                )
            }
        }
    }

    private fun hasEventOnDate(date: LocalDate): Boolean {
        // Replace this with your own logic to check for events on the given date
        return date.dayOfMonth % 5 == 0
    }

    inner class DayViewContainer(view: View) : ViewContainer(view) {
        val textView: TextView = view.findViewById(R.id.calendarDayText)
        val eventIndicator: View = view.findViewById(R.id.eventIndicator)

        lateinit var day: CalendarDay

        init {
            view.setOnClickListener {
                // Handle day click, if needed
            }
        }
    }
}
