import com.inspiredandroid.braincup.AppController
import com.inspiredandroid.braincup.Color
import com.inspiredandroid.braincup.Shape
import com.inspiredandroid.braincup.games.*
import com.inspiredandroid.braincup.getName
import platform.posix.sleep
import kotlin.system.getTimeMillis

fun main() {
    MacMain()
}

class MacMain : AppController.Interface {

    private val gameMaster = AppController(this)

    init {
        gameMaster.start()
    }

    override fun showMainMenu(title: String, description: String, games: List<Game>, callback: (Game) -> Unit) {
        println("------------")
        println("- $title -")
        println("------------")
        println(description)
        println()

        games.forEachIndexed { index, game ->
            println("${index + 1}. ${game.getName()}")
        }

        val index = (readLine()?.toIntOrNull() ?: 0) + -1
        val choice = games.getOrNull(index) ?: Game.SHERLOCK_CALCULATION
        callback(choice)
    }

    override fun showInstructions(title: String, description: String, start: (Long) -> Unit) {
        println("-----------------------")
        println("- $title  -")
        println("-----------------------")
        println(description)
        println("You can type \"quit\" and press enter at anytime to go back to the menu.")
        println()
        println("Press enter to start.")
        val input = readLine() ?: ""
        start(getTimeMillis())
    }

    override fun showMentalCalculation(
        game: MentalCalculation,
        answer: (String) -> Unit,
        next: (Long) -> Unit
    ) {
        printDivider()
        println(game.calculation)
        println()

        answer(readLine() ?: "")
        sleep(1u)
        next(getTimeMillis())
    }

    override fun showColorConfusion(
        game: ColorConfusion,
        answer: (String) -> Unit,
        next: (Long) -> Unit
    ) {
        printDivider()

        when (game.displayedShape) {
            Shape.SQUARE -> printSquare(game.displayedColor)
            Shape.TRIANGLE -> printTriangle(game.displayedColor)
            Shape.CIRCLE -> printCircle(game.displayedColor)
            Shape.HEART -> printHeart(game.displayedColor)
        }

        println("${game.shapePoints} = " + game.answerShape.getName())
        println("${game.colorPoints} = " + game.answerColor.getName().color(game.stringColor))
        println()

        answer(readLine() ?: "")
        sleep(1u)
        next(getTimeMillis())
    }

    override fun showSherlockCalculation(
        game: FindCalculation,
        answer: (String) -> Unit,
        next: (Long) -> Unit
    ) {
        printDivider()
        println("Goal: ${game.result}")
        println("Numbers: ${game.numbers.joinToString()}")
        println()

        answer(readLine() ?: "")
        sleep(1u)
        next(getTimeMillis())
    }

    override fun showFinishFeedback(rank: String, plays: Int, random: () -> Unit) {
        println("")
        println("-----------------------")
        println("You scored better than $rank% of the other players.")
    }

    override fun showCorrectAnswerFeedback() {
        println("√ :)".color(Color.GREEN))
    }

    override fun showWrongAnswerFeedback() {
        println("x :(".color(Color.RED))
    }

    private fun printSquare(color: Color) {
        println(" _________".color(color))
        println(" |       |".color(color))
        println(" |       |".color(color))
        println(" |       |".color(color))
        println(" |_______|".color(color))
    }

    private fun printTriangle(color: Color) {
        println("    /\\  ".color(color))
        println("   /  \\".color(color))
        println("  /    \\".color(color))
        println(" /      \\".color(color))
        println(" --------".color(color))
    }

    private fun printCircle(color: Color) {
        println("    *  *    ".color(color))
        println("  *      *  ".color(color))
        println(" *        * ".color(color))
        println("  *      *  ".color(color))
        println("    *  *    ".color(color))
    }

    private fun printHeart(color: Color) {
        println("   *     *    ".color(color))
        println(" *    *    * ".color(color))
        println("  *       *  ".color(color))
        println("    *   *    ".color(color))
        println("      *      ".color(color))
    }

    private fun printDivider() {
        println()
        println("-------------------------")
        println()
    }

    companion object {
        internal const val ESCAPE = '\u001B'
        internal const val RESET = "$ESCAPE[0m"
    }

    private fun String.color(color: Color): String {
        return when (color) {
            Color.RED -> "$ESCAPE[31m$this$RESET"
            Color.GREEN -> "$ESCAPE[32m$this$RESET"
            Color.BLUE -> "$ESCAPE[34m$this$RESET"
            Color.PURPLE -> "$ESCAPE[35m$this$RESET"
        }
    }

}