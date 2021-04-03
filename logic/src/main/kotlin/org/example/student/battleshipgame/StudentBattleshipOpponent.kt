package org.example.student.battleshipgame

import uk.ac.bournemouth.ap.battleshiplib.BattleshipOpponent
import uk.ac.bournemouth.ap.battleshiplib.BattleshipOpponent.ShipInfo
import uk.ac.bournemouth.ap.battleshiplib.Ship
import uk.ac.bournemouth.ap.lib.matrix.MutableBooleanMatrix
import kotlin.random.Random

/**
 * Suggested starting point for implementing an opponent class. Please note that your constructor
 * should test that it gets correct parameters. These tests should not be done in the test driver,
 * but actually here.
 *
 * done Create a constructor that creates a game given dimensions and a list of placed ships
 * TODO Create a way to generate a random game
 */
open class StudentBattleshipOpponent
constructor(override val rows: Int, override val columns: Int, override var ships: List<Ship>) : BattleshipOpponent {
    constructor(rows: Int, columns: Int, shipSizes: IntArray, random: Random) : this(rows, columns, ships = mutableListOf()) {

        println("Generate random opponent")
        ships = randShips(random, shipSizes)
    }

    /**
     * Determine whether there is a ship at the given coordinate. If so, provide the shipInfo (index+ship)
     * otherwise `null`.
     */
    override fun shipAt(column: Int, row: Int): ShipInfo<StudentShip>? {
        TODO("find which ship is at the coordinate. You can either search through the ships or look it up in a precalculated matrix")
    }

    fun randShips(random: Random, shipSizes: IntArray): List<Ship> {
        var ships: List<Ship> = mutableListOf()
        var newShip: StudentShip

        val occupiedCells: MutableBooleanMatrix = MutableBooleanMatrix(columns, rows) //used to test for overlap

        for (size in shipSizes) {
            newShip = makeShip(random, size, occupiedCells)
            for (coord in newShip.getCoords()) {
                occupiedCells.set(coord.x, coord.y, true)
            }
            ships += newShip
        }
        return ships
    }

    fun makeShip(random: Random, size: Int, cells: MutableBooleanMatrix): StudentShip {
        var newShip: StudentShip
        val x: Int = random.nextInt(columns)
        val y: Int = random.nextInt(rows)

        if (random.nextBoolean()) {
            //place vertical
            newShip = StudentShip(y, x, (y + (size - 1)), x)
        } else {
            //place horizontal
            newShip = StudentShip(y, x, y, (x + (size - 1)))
        }
        //check bounds
        if (newShip.top < 0 || newShip.right > columns || newShip.bottom > rows || newShip.left < 0) {
            println("invalid ship, ship out of bounds")
            newShip = makeShip(random, size, cells)
        }
        //check overlapp
        for (coord in newShip.getCoords()){

            if (cells.get(coord.x, coord.y)){
                println("invalid ship, ship overlaps")
                newShip = makeShip(random,size,cells)
            }
        }
        return newShip
    }
}
