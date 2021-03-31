package org.example.student.battleshipgame

import uk.ac.bournemouth.ap.battleshiplib.BattleshipOpponent
import uk.ac.bournemouth.ap.battleshiplib.BattleshipOpponent.ShipInfo
import kotlin.random.Random

/**
 * Suggested starting point for implementing an opponent class. Please note that your constructor
 * should test that it gets correct parameters. These tests should not be done in the test driver,
 * but actually here.
 *
 * done Create a constructor that creates a game given dimensions and a list of placed ships
 * TODO Create a way to generate a random game
 */
open class StudentBattleshipOpponent(override val rows: Int, override val columns: Int, override val ships: List<StudentShip>): BattleshipOpponent {

    //override val rows:Int get() = DONE("Determine the rows for the grid in which the ships are hidden")
    //override val columns:Int get() = DONW("Determine the columns for the grid in which the ships are hidden")
    //override val ships:List<StudentShip> = DONE("Record the ships that are placed for this opponent")

    /**
     * Determine whether there is a ship at the given coordinate. If so, provide the shipInfo (index+ship)
     * otherwise `null`.
     */
    override fun shipAt(column: Int, row: Int): ShipInfo<StudentShip>? {
        TODO("find which ship is at the coordinate. You can either search through the ships or look it up in a precalculated matrix")
    }
}

