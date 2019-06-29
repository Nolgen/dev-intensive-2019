package ru.skillbranch.devintensive.utils

import android.os.Build
import android.support.annotation.RequiresApi

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? = fullName?.split(" ")
        var firstName = parts?.getOrNull(0) 
        var lastName = parts?.getOrNull(1)

        if (firstName != null) {
            firstName = firstName.trim()
            if (firstName == "") firstName = null
        }

        if (lastName != null) {
            lastName = lastName.trim()
            if (lastName == "") lastName = null
        }

        return Pair(firstName, lastName)
    }

    fun initials(firstName: String?, lastName: String?): String? {
        var res = ""
        res += if ((firstName != null) && (firstName.length > 0)) firstName[0].toString().trim().toUpperCase() else ""
        res += if ((lastName != null) && (lastName.length > 0)) lastName[0].toString().trim().toUpperCase() else ""
        return if (res == "") null else res
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun transliteration(payload: String, divider: String = " "): String {
        val transTable: Map<String,String> = mapOf("а" to "a","б" to "b","в" to "v","г" to "g","д" to "d","е" to "e",
            "ё" to "e","ж" to "zh","з" to "z","и" to "i","й" to "i","к" to "k","л" to "l","м" to "m","н" to "n",
            "о" to "o","п" to "p","р" to "r","с" to "s","т" to "t","у" to "u","ф" to "f","х" to "h","ц" to "c",
            "ч" to "ch","ш" to "sh","щ" to "sh'","ъ" to "","ы" to "i","ь" to "","э" to "e","ю" to "yu","я" to "ya",
            " " to divider)
        var res = ""
        for (c in payload.toList()) {
            val s = c.toString()
            val sl = s.toLowerCase()
            res += if (sl !in transTable.keys) s else
                        if (s == sl) transTable.getValue(s)
                        else transTable.getValue(sl).capitalize()
        }
        return res
    }

    private val minutesList = listOf<String>("минут","минута","минуты")
    private val hoursList = listOf<String>("часов","час","часа")
    private val daysList = listOf<String>("дней","день","дня")

    // Функция возвращает количество по-русски
    private fun russianNum(num:Long, list:List<String>):String {
        val long = Math.abs(num)
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

    fun russianMinutes(num:Long) = russianNum(num, minutesList)
    fun russianHours(num:Long) = russianNum(num, hoursList)
    fun russianDays(num:Long) = russianNum(num, daysList)
}