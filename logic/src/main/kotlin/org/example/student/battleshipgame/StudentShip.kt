package org.example.student.battleshipgame

import uk.ac.bournemouth.ap.battleshiplib.Ship

/** A simple implementation of ship. You could change this if you want to, or add functionality.*/
open class StudentShip(override val top:Int, override val left:Int, override val bottom:Int, override val right:Int, private val row:Int, private val column:Int): Ship {
    init {
        /* TODO Make sure to check that the arguments are valid: left<=right, top<=bottom and the ship is only 1 wide */
        //check for: overlapping, inverted dimensions, off grid, width must be 1 (columns and the rows)

    }
}