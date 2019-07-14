package ru.skillbranch.devintensive.models

import androidx.core.text.isDigitsOnly

class Bender(var status:Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion():String = when(question){
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer:String): Pair<String, Triple<Int,Int,Int>> {
        if (question == Question.IDLE) return question.question to status.color

        // Валидация
        val validate = validateAnswer(answer,question)
        if(validate != "") return validate to status.color

        if(question.answers.contains(answer.toLowerCase())) {
            question = question.nextQuestion()
            return "Отлично - ты справился\n${question.question}" to status.color
        } else {
            status = status.nextStatus()
            if(status == Status.NORMAL) {
                question = Question.NAME
                return "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            } else {
                return "Это неправильный ответ\n${question.question}" to status.color
            }
        }
    }


    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus():Status{
            return if(this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }

        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION

        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL

            },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion():Question
    }

    fun validateAnswer(answer: String, question: Question):String {
        return when(question) {
            Question.NAME -> if (answer.isNotEmpty() && answer[0] == answer[0].toUpperCase()) ""
                else "Имя должно начинаться с заглавной буквы\nКак меня зовут?"

            Question.PROFESSION -> if (answer.isNotEmpty() && answer[0] != answer[0].toUpperCase()) ""
                else "Профессия должна начинаться со строчной буквы\nНазови мою профессию?"

            Question.MATERIAL -> if (answer.isNotEmpty() && ! answer.contains(Regex("[0-9]") )) ""
                else "Материал не должен содержать цифр\nИз чего я сделан?"

            Question.BDAY -> if (answer.isNotEmpty() && answer.isDigitsOnly()) ""
                else "Год моего рождения должен содержать только цифры\nКогда меня создали?"

            Question.SERIAL -> if (answer.isNotEmpty() && answer.isDigitsOnly() && answer.length == 7) ""
                else "Серийный номер содержит только цифры, и их 7\nМой серийный номер?"

            Question.IDLE ->  ""
        }

    }
}