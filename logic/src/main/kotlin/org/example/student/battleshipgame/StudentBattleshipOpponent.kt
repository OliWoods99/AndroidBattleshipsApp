package org.example.student.battleshipgame

import uk.ac.bournemouth.ap.battleshiplib.BattleshipOpponent
import uk.ac.bournemouth.ap.battleshiplib.BattleshipOpponent.ShipInfo
import uk.ac.bournemouth.ap.battleshiplib.Ship
import uk.ac.bournemouth.ap.lib.matrix.MutableBooleanMatrix
import uk.ac.bournemouth.ap.lib.matrix.ext.Coordinate
import kotlin.random.Random

/**
 * Suggested starting point for implementing an opponent class. Please note that your constructor
 * should test that it gets correct parameters. These tests should not be done in the test driver,
 * but actually here.
 *
 * done Create a constructor that creates a game given dimensions and a list of placed ships
 * done Create a way to generate a random game
 */
open class StudentBattleshipOpponent
constructor(override val rows: Int, override val columns: Int, override var ships: List<Ship>): BattleshipOpponent {
    init {
        for (ship in ships){

            //check bounds
            if (ship.top < 0 || ship.bottom > (rows - 1) || ship.left < 0 || ship.right > (columns - 1)){
                throw Exception("Invalid ship, out of bounds")
            }

            //check overlap
            var otherShips = ships.minusElement(ship)
            val space = MutableBooleanMatrix(columns, rows) //used to test for overlap

            for (_ship in otherShips){
                _ship as StudentShip

                for (coord in _ship.getCoords()){
                    space[coord.x,coord.y] = true
                }
            }

            for (coord in (ship as StudentShip).getCoords()){
                if (space[coord.x, coord.y]){
                    throw Exception("invalid ship, ships overlap")
                }
            }
        }
    }
    constructor(rows: Int, columns: Int, shipSizes: IntArray, random: Random) : this(rows, columns, ships = mutableListOf()) {

        println("Generate random opponent")
        ships = randShips(random, shipSizes)

    }

    /**
     * Determine whether there is a ship at the given coordinate. If so, provide the shipInfo (index+ship)
     * otherwise `null`.
     */

    override fun shipAt(column: Int, row: Int): ShipInfo<StudentShip>? {
        val targetCoord = Coordinate(column, row)
        var out: ShipInfo<StudentShip>? = null

        for (ship in ships) {
            ship as StudentShip

            if (targetCoord in ship.getCoords()){
                out = ShipInfo(ships.indexOf(ship), ship)
            }
        }

        return out
    }

    fun randShips(random: Random, shipSizes: IntArray): List<Ship> {
        var ships: List<Ship> = mutableListOf()
        var newShip: StudentShip

        val occupiedCells = MutableBooleanMatrix(columns, rows) //used to test for overlap

        for (size in shipSizes) {
            var placed = false

            while (!placed){
                try {
                    newShip = makeShip(random, size, occupiedCells)
                    placed = true

                    for (coord in newShip.getCoords()) {
                        occupiedCells[coord.x, coord.y] = true
                    }

                    ships = ships + newShip
                }
                catch (e: java.lang.Exception){
                    placed = false
                }
            }
        }
        return ships
    }

    fun makeShip(random: Random, size: Int, cells: MutableBooleanMatrix): StudentShip {
        val newShip: StudentShip
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
        if (newShip.top < 0 || newShip.right > (cells.width - 1) || newShip.bottom > (cells.height - 1) || newShip.left < 0) {
            throw Exception("invalid ship, ship out of bounds")
        }
        //check overlapp
        for (coord in newShip.getCoords()){
            if (cells[coord.x, coord.y]){
                throw Exception("invalid ship, ships overlap")
            }
        }
        return newShip
    }
}
