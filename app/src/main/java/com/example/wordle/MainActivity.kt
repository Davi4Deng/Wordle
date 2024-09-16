package com.example.wordle

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.text.Spannable


class MainActivity : AppCompatActivity() {

    private lateinit var guess1: TextView
    private lateinit var check1: TextView
    private lateinit var guess2: TextView
    private lateinit var check2: TextView
    private lateinit var guess3: TextView
    private lateinit var check3: TextView
    private lateinit var bottomEditText: EditText
    private lateinit var answer: TextView
    private lateinit var restartButton: Button

    private var attempts = 0
    private var correctWord = ""  // Will be set to a random word

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        guess1 = findViewById(R.id.enter1)
        check1 = findViewById(R.id.check1)
        guess2 = findViewById(R.id.enter2)
        check2 = findViewById(R.id.check2)
        guess3 = findViewById(R.id.enter3)
        check3 = findViewById(R.id.check3)
        bottomEditText = findViewById(R.id.bottomEditText)
        answer = findViewById(R.id.answer)
        restartButton = findViewById(R.id.button)

        // Set a random correct word using FourLetterWordList
        correctWord = FourLetterWordList.getRandomFourLetterWord()

        // Set listener for user input
        bottomEditText.setOnEditorActionListener { _, _, _ ->
            val userInput = bottomEditText.text.toString().uppercase()
            if (userInput.length == 4) {
                attempts++
                displayUserInput(userInput)
            }
            bottomEditText.text.clear()
            true
        }

        // Restart button logic
        restartButton.setOnClickListener {
            resetGame()
        }
    }

    private fun displayUserInput(userInput: String) {
        when (attempts) {
            1 -> {
                guess1.visibility = View.VISIBLE
                check1.visibility = View.VISIBLE
                guess1.setText("Guess #1             $userInput")
                check1.setText("Guess #1 Check")  // 显示 "Guess #1 Check"
                highlightCorrectLetters(userInput, check1)  // 只对 check1 高亮用户输入
            }
            2 -> {
                guess2.visibility = View.VISIBLE
                check2.visibility = View.VISIBLE
                guess2.setText("Guess #2             $userInput")
                check2.setText("Guess #2 Check")
                highlightCorrectLetters(userInput, check2)
            }
            3 -> {
                guess3.visibility = View.VISIBLE
                check3.visibility = View.VISIBLE
                guess3.setText("Guess #3             $userInput")
                check3.setText("Guess #3 Check")
                highlightCorrectLetters(userInput, check3)
                answer.visibility = View.VISIBLE
                answer.setText("$correctWord")
            }
        }
    }

    private fun highlightCorrectLetters(userInput: String, checkView: TextView) {
        val spannableText = SpannableStringBuilder(checkView.text.toString() + " $userInput")  // 仅对用户输入进行处理
        correctWord.forEachIndexed { index, char ->
            if (index < userInput.length && userInput[index] == char) {
                spannableText.setSpan(
                    ForegroundColorSpan(Color.GREEN),
                    spannableText.length - userInput.length + index,  // 确保高亮的是用户输入的部分
                    spannableText.length - userInput.length + index + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        checkView.text = spannableText
    }


    private fun resetGame() {
        // Reset game state and choose a new correct word
        guess1.visibility = View.VISIBLE
        check1.visibility = View.GONE
        guess1.setText("Guess #1")
        check1.setText("")
        guess2.visibility = View.GONE
        check2.visibility = View.GONE
        guess2.setText("")
        check2.setText("Guess #2 Check")
        guess3.visibility = View.GONE
        check3.visibility = View.GONE
        guess3.setText("")
        check3.setText("Guess #3 Check")
        answer.visibility = View.GONE
        attempts = 0
        correctWord = FourLetterWordList.getRandomFourLetterWord()
    }
}
