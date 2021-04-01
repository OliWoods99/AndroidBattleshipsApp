package org.example.student.battleshipgame

import uk.ac.bournemouth.ap.battleshiplib.BattleshipOpponent
import uk.ac.bournemouth.ap.battleshiplib.BattleshipOpponent.ShipInfo
import uk.ac.bournemouth.ap.battleshiplib.Ship
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
    constructor(override val rows: Int, override val columns: Int, override var ships: List<Ship>): BattleshipOpponent {
    constructor(rows: Int, columns: Int, shipSizes: IntArray, random: Random):this(rows, columns, ships = mutableListOf()){

        println("Generate random opponent")
        ships = randShips(random,shipSizes)
    }

    /**
     * Determine whether there is a ship at the given coordinate. If so, provide the shipInfo (index+ship)
     * otherwise `null`.
     */
    override fun shipAt(column: Int, row: Int): ShipInfo<StudentShip>? {
        TODO("find which ship is at the coordinate. You can either search through the ships or look it up in a precalculated matrix")
    }

    fun randShips(random: Random, shipSizes: IntArray): List<Ship>{

        var ships: List<Ship> = mutableListOf()
        var x: Int
        var y: Int

        //ship(top,left,bottom,right)
        for(size in shipSizes){
            //place ship and add to list

            var ships: List<Ship> = mutableListOf()
            x = random.nextInt(this.columns)
            y = random.nextInt(this.rows)

            if(random.nextBoolean()){
                //place vertical
                ships + StudentShip(y,x,(y + size),x,this.rows,this.columns)
            }
            else{
                //place horizontal
                ships = ships + StudentShip(y,x,y,(x + size),this.rows,this.columns)
            }
        }
        return ships
    }
}

