package org.example.student.battleshipgame

import uk.ac.bournemouth.ap.battleshiplib.*
import uk.ac.bournemouth.ap.battleshiplib.test.BattleshipTest
import uk.ac.bournemouth.ap.lib.matrix.BooleanMatrix
import uk.ac.bournemouth.ap.lib.matrix.MutableBooleanMatrix
import kotlin.random.Random

class StudentBattleshipTest : BattleshipTest() {
    override fun createOpponent(
        columns: Int,
        rows: Int,
        ships: List<Ship>
    ): StudentBattleshipOpponent {
        val studentShips = ships.map {
            ship -> StudentShip(ship.top, ship.left, ship.bottom, ship.right)
            //done("create an instance of StudentShip that maps the given ship data")
        }
        //DONE("Create an instance of StudentBattleshipOpponent with the dimensions and ships")
        return StudentBattleshipOpponent(rows,columns,studentShips)
    }

    override fun createOpponent(
        columns: Int,
        rows: Int,
        shipSizes: IntArray,
        random: Random
    ): StudentBattleshipOpponent {
        // Note that the passing of random allows for repeatable testing
        //done("Create an instance of StudentBattleshipOpponent for the given game size, " + "target ship sizes and random generator")
        return StudentBattleshipOpponent(rows,columns,shipSizes,random)
    }

    override fun createGrid(
        grid: BooleanMatrix,
        opponent: BattleshipOpponent
    ): StudentBattleshipGrid {
        // If the opponent is not a StudentBattleshipOpponent, create it based upon the passed in data
        val studentOpponent =
            opponent as? StudentBattleshipOpponent
                ?: createOpponent(opponent.columns, opponent.rows, opponent.ships)

        return StudentBattleshipGrid(studentOpponent)
    }

    override fun createGame(grids: List<BattleshipGrid>): BattleshipGame {
        // This will make sure that the grid has the expected type.
        val studentGrids = grids.map { grid ->
            grid as? StudentBattleshipGrid
                ?: createGrid(MutableBooleanMatrix(grid.columns, grid.rows), grid.opponent)
        }

        return StudentBattleshipGame(studentGrids) //DONE("Create a new StudentBattleshipGame with the studentGrids as parameter")
    }
}
