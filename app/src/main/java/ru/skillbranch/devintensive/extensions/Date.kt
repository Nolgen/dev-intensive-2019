package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.utils.Utils
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*

fun Date.format(pattern:String="HH:mm:ss dd.MM.yy"):String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time
    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}



fun Date.humanizeDiff(date:Date = Date()): String {
    val diff = (this.time - date.time)
    return when {
        (diff < -360* DAY) -> "более года назад"
        (diff >= -360* DAY && diff < -26* HOUR) -> "${Utils.russianDays(diff / DAY)} назад"
        (diff >= -26* HOUR && diff < -22* HOUR) -> "день назад"
        (diff >= -22* HOUR && diff < -75* MINUTE) -> "${Utils.russianHours(diff / HOUR)} назад"
        (diff >= -75* MINUTE && diff < -45* MINUTE) -> "час назад"
        (diff >= -45* MINUTE && diff < -75* SECOND) -> "${Utils.russianMinutes(diff / MINUTE)} назад"
        (diff >= -75* SECOND && diff < -4* SECOND) -> "минуту назад"
        (diff >= -45* SECOND && diff < -1* SECOND) -> "несколько секунд назад"

        (diff >= -1* SECOND && diff <= 1* SECOND) -> "только что"

        (diff > 1* SECOND && diff <= 45* SECOND)  -> "через несколько секунд"
        (diff > 45* SECOND && diff <= 75* SECOND)  -> "через минуту"
        (diff > 75* SECOND && diff <= 45* MINUTE) -> "через ${Utils.russianMinutes(diff / MINUTE)}"
        (diff > 45* MINUTE && diff <= 75* MINUTE) -> "через час"
        (diff >= 75* MINUTE && diff <= 22* HOUR) -> "через ${Utils.russianHours(diff / HOUR)}"
        (diff >= 22* HOUR && diff <= 26* HOUR) -> "через день"
        (diff >= 26* HOUR && diff <= 360* DAY) -> "через ${Utils.russianDays(diff / DAY)}"
        (diff > 360* DAY) -> "более чем через год"
        else -> "Ошибка с числом $diff"
    }
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY
}