package com.example.mypack.myquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var quizItemList: List<QuizItem>
    private lateinit var startButton: Button
    private var activeQuiz: MutableMap<QuizItem, AnswerType>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        quizItemList = createQuestionList()
        startButton = findViewById(R.id.btn_quiz_start)
        startButton.setOnClickListener {
            if (activeQuiz == null) {
                startNewQuiz()
            } else {
                TODO()
            }
            var currentQuizItem: QuizItem? = null
            activeQuiz!!.forEach { (key, value) ->
                if (value == AnswerType.UNDEFINED) {
                    currentQuizItem = key
                }
                val newIntent = Intent(applicationContext, QuestionActivity::class.java)
                newIntent.putExtra(IntentKeys.KEY_QUESTION, currentQuizItem!!.question)//TODO
                newIntent.putExtra(IntentKeys.KEY_ANSWER0, currentQuizItem!!.answer.get(0))
                newIntent.putExtra(IntentKeys.KEY_ANSWER1, currentQuizItem!!.answer.get(1))
                newIntent.putExtra(IntentKeys.KEY_CORRECT_INDEX, currentQuizItem!!.correctId)
                startActivity(newIntent)
            }
        }


    }

    private fun startNewQuiz() {
        activeQuiz = mutableMapOf()
        for (index in 0..3) {
            activeQuiz!!.put(quizItemList.get(index), AnswerType.UNDEFINED)
        }
    }


//data class QuizItem(val question: String, val answer: List<String>, val correctId: Int)

    fun createQuestionList(): List<QuizItem> {
        val quizItemList: MutableList<QuizItem> = mutableListOf()
        quizItemList.add(QuizItem("Котлин статический язык?", listOf("yes", "no"), 0))
        quizItemList.add(
            QuizItem(
                question = "Сколько примитивных типов в Котлине?",
                listOf("8", "0"),
                correctId = 1
            )
        )
        quizItemList.add(
            QuizItem(
                "Является ли Котлин функциональным языком?",
                listOf("yes", "no"),
                1
            )
        )
        quizItemList.add(
            QuizItem(
                "Чем являются функции в Котлин?",
                listOf("object", "variable"),
                0
            )
        )
        return quizItemList
    }

    enum class AnswerType {
        CORRECT, INCORRECT, UNDEFINED
    }
}

data class QuizItem(
    val question: String = "how are you?",
    val answer: List<String>,
    val correctId: Int
)

class IntentKeys {

    companion object {
        val KEY_QUESTION: String = "question"
        val KEY_ANSWER0: String = "answer0"
        val KEY_ANSWER1: String = "answer1"
        val KEY_CORRECT_INDEX: String = "correctIndex"
    }
}


