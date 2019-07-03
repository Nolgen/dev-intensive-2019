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

// Функция возвращает количество по-русски
private fun russianNum(num: Long, list: List<String>): String {
    val long = abs(num)
    val oneDigit = long % 10
    val twoDigit = long % 100
    return when {
        (twoDigit >= 5) && (twoDigit <= 19) -> "$long ${list[0]}"
        oneDigit == 0L -> "$long ${list[0]}"
        oneDigit == 1L -> "$long ${list[1]}"
        (oneDigit >= 2) && (oneDigit <= 4) -> "$long ${list[2]}"
        (oneDigit >= 5) && (oneDigit <= 9) -> "$long ${list[0]}"
        else -> "Ошибка c числом $long"
    }
}

private val secondsList = listOf<String>("секунд", "секунду", "секунды")
private val minutesList = listOf<String>("минут", "минуту", "минуты")
private val hoursList = listOf<String>("часов", "час", "часа")
private val daysList = listOf<String>("дней", "день", "дня")

//private fun russianSeconds(num: Long) = russianNum(num, secondsList)
private fun russianMinutes(num: Long) = russianNum(num, minutesList)
private fun russianHours(num: Long) = russianNum(num, hoursList)
private fun russianDays(num: Long) = russianNum(num, daysList)


fun Date.humanizeDiff(date:Date = Date()): String {
    val diff = (this.time - date.time)
    return when {
        (diff < -360* DAY) -> "более года назад"
        (diff >= -360* DAY && diff < -26* HOUR) -> "${russianDays(diff / DAY)} назад"
        (diff >= -26* HOUR && diff < -22* HOUR) -> "день назад"
        (diff >= -22* HOUR && diff < -75* MINUTE) -> "${russianHours(diff / HOUR)} назад"
        (diff >= -75* MINUTE && diff < -45* MINUTE) -> "час назад"
        (diff >= -45* MINUTE && diff < -75* SECOND) -> "${russianMinutes(diff / MINUTE)} назад"
        (diff >= -75* SECOND && diff < -45* SECOND) -> "минуту назад"
        (diff >= -45* SECOND && diff < -1* SECOND) -> "несколько секунд назад"

        (diff >= -1* SECOND && diff <= 1* SECOND) -> "только что"

        (diff > 1* SECOND && diff <= 45* SECOND)  -> "через несколько секунд"
        (diff > 45* SECOND && diff <= 75* SECOND)  -> "через минуту"
        (diff > 75* SECOND && diff <= 45* MINUTE) -> "через ${russianMinutes(diff / MINUTE)}"
        (diff > 45* MINUTE && diff <= 75* MINUTE) -> "через час"
        (diff > 75* MINUTE && diff <= 22* HOUR) -> "через ${russianHours(diff / HOUR)}"
        (diff > 22* HOUR && diff <= 26* HOUR) -> "через день"
        (diff > 26* HOUR && diff <= 360* DAY) -> "через ${russianDays(diff / DAY)}"
        (diff > 360* DAY) -> "более чем через год"
        else -> "Ошибка с числом $diff"
    }
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;
    fun plural(value:Int) =
        when (this) {
            SECOND -> russianNum(value.toLong(), secondsList)
            MINUTE -> russianNum(value.toLong(), minutesList)
            HOUR -> russianNum(value.toLong(), hoursList)
            DAY -> russianNum(value.toLong(), daysList)
        }
}