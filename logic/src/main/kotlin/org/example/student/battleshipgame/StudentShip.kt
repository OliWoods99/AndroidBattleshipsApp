package org.example.student.battleshipgame

import uk.ac.bournemouth.ap.battleshiplib.Ship

/** A simple implementation of ship. You could change this if you want to, or add functionality.*/
open class StudentShip(override val top: Int, override val left: Int, override val bottom: Int, override val right: Int, private val rows: Int, private val columns: Int): Ship {
    init {
        /* TODO Make sure to check that the arguments are valid: left<=right, top<=bottom and the ship is only 1 wide */
        //check for inverted dimensions
        if (this.left > this.right || this.top > this.bottom){
            throw Exception("invalid ship, inverted dimensions")
        }
        //check width
        else if(!(width == 1 || height == 1)){
            throw Exception("invalid ship, too wide")
        }
        //check bounds
        else if (this.top > rows || this.right > columns || this.bottom < 0 || this.left < 0){
            throw Exception("invalid ship, ship out of bounds")
        }
        //check for overlap?
        else{
            println("valid")
        }
    }
    fun debug(){
        val colsAt = this.columnIndices
        val rowsAt = this.rowIndices
        println(rows)
        println(columns)
        println("TOP "+ top + " RIGHT "+ right + " LEFT "+ left + " BOT "+ bottom)
        println("WIDTH " + width + " HEIGHT " + height + " size " + size)
        println("cols " + colsAt)
        println("rowsAt " + rowsAt)
    }
}