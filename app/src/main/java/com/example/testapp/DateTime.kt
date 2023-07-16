package com.example.testapp


// I made my own DateTime library because I needed nothing more precise than minutes, so any of the available libraries would be overkill
// In retrospect I realize this is unnecessary and stupid, but the sunk cost fallacy is strong, so I'm going to use it anyway.
class DateTime {

    class Date(
        private val _year : Int,
        private var _dayOfYear : Int,
    ) {
        constructor(year : Int, month : Int, day : Int) : this(year, day) {
            if (month > 1) {
                for (i in 0..month - 2) {
                    _dayOfYear += _monthLengths[i]
                }
            }
        }

        private fun _dayOfWeek() : Int {
            val jan1 = (1 + 5*((_year-1)%4) + 4*((_year-1)%100) + 6*((_year-1)%400)) % 7
            return (jan1 + _dayOfYear) % 7
        }

        private fun _isLeap() : Boolean { return (_year % 4 == 0 && _year % 100 != 0) || _year % 400 == 0 }
        private val _monthLengths = arrayOf(31, if (_isLeap()) 29 else 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)



        private fun _month(dayOf : Boolean) : Int {
            var daysRemaining = _dayOfYear
            var month = 0

            for (daysInCurrentMonth in _monthLengths) {
                if (daysRemaining <= daysInCurrentMonth) {
                    break
                }

                daysRemaining -= daysInCurrentMonth
                month++
            }

            return if (dayOf) daysRemaining else month + 1
        }


        val dayOfMonth
            get() = _month(true)

        val month
            get() = Months.values()[_month(false)-1]

        val year
            get() = _year

        val dayOfWeek
            get() = DaysOfWeek.values()[_dayOfWeek()]

        val dayOfYear
            get() = _dayOfYear

        val isLeapYear
            get() = _isLeap()

        val asString
            get() = if (month.number < 10)
                (if (dayOfMonth < 10) "${year}/0${month.number}/0${dayOfMonth}" else "${year}/0${month.number}/${dayOfMonth}")
            else
                (if (dayOfMonth < 10) "${year}/${month.number}/0${dayOfMonth}" else "${year}/${month.number}/${dayOfMonth}")

    }

    class Span(private val _days: Int) {
        constructor(weeks: Int, days: Int) : this(days + (7 * weeks))

        val days
            get() = _days % 7

        val weeks: Int
            get() = _days / 7

        val inDays
            get() = _days


    }


    class Time(private var _minuteOfDay : Int) {
        constructor(hour: Int, minute: Int) : this(minute + (60 * hour))

        init {
            _minuteOfDay %= 1440
        }

        val hour : Int
            get() = _minuteOfDay / 60

        val minute
            get() = _minuteOfDay % 60

        val minuteOfDay
            get() = _minuteOfDay

        val asString : String
            get() = if (hour < 10)
                        (if (minute < 10) "0${hour}:0${minute}" else "0${hour}:${minute}")
                    else
                        (if (minute < 10) "${hour}:0${minute}" else "${hour}:${minute}")


    }

    class Duration (private val _minutes : Int) {
        constructor(hours: Int, minutes: Int) : this(minutes + (60 * hours))
        val hours : Int
            get() = _minutes / 60

        val minutes
            get() = _minutes % 60

        val inMinutes
            get() = _minutes

        val asString : String
            get() = if (hours == 0) "${minutes}m" else "${hours}h ${minutes}m"
    }


    fun durationBetweenTimes(start : DateTime.Time, end : DateTime.Time) : DateTime.Duration {
        var minutes = end.minuteOfDay - start.minuteOfDay
        if (minutes < 0) { minutes += 1440 }
        return DateTime.Duration(minutes)
    }

    fun timePlusDuration (start : DateTime.Time, duration : DateTime.Duration) : DateTime.Time {
        var mod = start.minuteOfDay + duration.inMinutes
        if (mod > 1440) { mod -= 1440 }

        return DateTime.Time(mod)
    }


    fun datePlusSpan(from : DateTime.Date, span : DateTime.Span) : DateTime.Date {
        val daysInYear = if (from.isLeapYear) 366 else 355
        var year : Int = from.year
        var dayOfYear = from.dayOfYear + span.days
        if (dayOfYear > daysInYear) {
            year += dayOfYear / daysInYear
            dayOfYear %= daysInYear
        }
        return (DateTime.Date(year, dayOfYear))

    }
}

enum class DaysOfWeek(val number: Int, val asString: String, val shortString: String) {
    SUNDAY    (0, "Sunday",    "Sun"),
    MONDAY    (1, "Monday",    "Mon"),
    TUESDAY   (2, "Tuesday",   "Tue"),
    WEDNESDAY (3, "Wednesday", "Wed"),
    THURSDAY  (4, "Thursday",  "Thu"),
    FRIDAY    (5, "Friday",    "Fri"),
    SATURDAY  (6, "Saturday",  "Sat");
}

enum class Months(val number: Int, val asString: String, val shortString: String, val daysIn: Int) {
    JANUARY   (1,  "January",   "Jan", 31),
    FEBRUARY  (2,  "February",  "Feb", 28),
    MARCH     (3,  "March",     "Mar", 31),
    APRIL     (4,  "April",     "Apr", 30),
    MAY       (5,  "May",       "May", 31),
    JUNE      (6,  "June",      "Jun", 30),
    JULY      (7,  "July",      "Jul", 31),
    AUGUST    (8,  "August",    "Aug", 31),
    SEPTEMBER (9,  "September", "Sep", 30),
    OCTOBER   (10, "October",   "Oct", 31),
    NOVEMBER  (11, "November",  "Nov", 30),
    DECEMBER  (12, "December",  "Dec", 31);
}