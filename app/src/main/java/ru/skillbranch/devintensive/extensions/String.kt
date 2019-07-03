package ru.skillbranch.devintensive.extensions

fun String.truncate(num:Int = 16) = if (this.length > num) this.substring(0,num).trim() + "..." else this

//*String.stripHtml
//Необходимо реализовать метод stripHtml для очистки строки от лишних пробелов, html тегов, escape последовательностей
//+1
//
//Реализуй extension позволяющий очистить строку от html тегов и html escape последовательностей ("& < > ' ""), а так же удалить пустые символы (пробелы) между словами если их больше 1. Необходимо вернуть модифицированную строку
//Пример:
//"<p class="title">Образовательное IT-сообщество Skill Branch</p>".stripHtml() //Образовательное IT-сообщество Skill Branch
//"<p>Образовательное       IT-сообщество Skill Branch</p>".stripHtml() //Образовательное IT-сообщество Skill Branch

fun String.stripHtml():String {
    var s = this
    s = Regex("""<[^>]*>""").replace(s,"")
    s = Regex("""&[^>]*;""").replace(s, "")

    var s1 = s
    do {
        s = s1
        s1 = Regex("""  """).replace(s, " ")
    } while (s != s1)
    s = s1

    return s
}