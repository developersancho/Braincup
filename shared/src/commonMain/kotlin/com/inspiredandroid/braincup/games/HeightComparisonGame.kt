package com.inspiredandroid.braincup.games

import com.inspiredandroid.braincup.games.tools.Calculator
import kotlin.random.Random

/**
 * Generates each round 2-4 mathematical formals.
 *
 * Logic:
 * - Round 1 is always addition
 * - Round 2+ is always division or multiplication
 * - Round 1-3 = 2 answers
 * - Round 4-6 = 3 answers
 * - Round >= 7 = 4 answers
 */
class HeightComparisonGame : Game() {

    private var resultIndex = 0
    var answers = mutableListOf<String>()
    var types = listOf(Type.FRACTION, Type.MULTIPLICATION)

    override fun nextRound() {
        answers.clear()
        val type = if (round == 0) {
            Type.ADDITION
        } else {
            types.random()
        }
        val results = mutableListOf<Double>()
        val expectedAnswersCount = getExpectedAnswersCount()
        while (answers.count() < expectedAnswersCount) {
            val answer = when (type) {
                Type.ADDITION -> {
                    val n1 = Random.nextInt(2, 12)
                    val n2 = Random.nextInt(2, 12)
                    "$n1+$n2"
                }
                Type.FRACTION -> {
                    val n1 = Random.nextInt(2, 12)
                    val n2 = Random.nextInt(2, 12)
                    "$n1/$n2"
                }
                Type.MULTIPLICATION -> {
                    val n1 = Random.nextInt(2, 12)
                    val n2 = Random.nextInt(2, 12)
                    "$n1*$n2"
                }
            }
            val result = Calculator.calculate(answer)
            if (results.none { it == result }) {
                answers.add(answer)
                results.add(result)
                if (results.max() == result) {
                    resultIndex = results.size - 1
                }
            }
        }
    }

    override fun isCorrect(input: String): Boolean {
        return try {
            input.toInt() == (resultIndex + 1)
        } catch (ignore: NumberFormatException) {
            false
        }
    }

    override fun solution(): String {
        return answers[resultIndex]
    }

    override fun hint(): String? {
        return null
    }

    override fun getGameType(): GameType {
        return GameType.HEIGHT_COMPARISON
    }

    private fun getExpectedAnswersCount(): Int {
        return when {
            round > 6 -> 4
            round > 3 -> 3
            else -> 2
        }
    }

    enum class Type {
        ADDITION,
        FRACTION,
        MULTIPLICATION
    }
}