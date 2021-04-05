package org.example.student.battleshipgame

import uk.ac.bournemouth.ap.battleshiplib.Ship
import uk.ac.bournemouth.ap.lib.matrix.ext.Coordinate

/** A simple implementation of ship. You could change this if you want to, or add functionality.*/
open class StudentShip(override val top: Int, override val left: Int, override val bottom: Int, override val right: Int): Ship {
    init {
        //check for inverted dimensions
        if (this.left > this.right || this.top > this.bottom){
            throw Exception("invalid ship, inverted dimensions")
        }
        //check width
        else if(!(width == 1 || height == 1)){
            throw Exception("invalid ship, too wide")
        }
    }
    fun getCoords(): List<Coordinate>{
        val x = this.columnIndices
        val y = this.rowIndices

        var listOut: List<Coordinate> = mutableListOf()

        if(x.count() > y.count()){
            //horizontal
            for (i in 0 until this.size){
                val coord = Coordinate(x.elementAt(i),y.first)
                listOut = listOut + coord
            }
        }
        else{
            //vertical
            for (i in 0 until this.size){
                val coord = Coordinate(x.first,y.elementAt(i))
                listOut = listOut + coord
            }
        }
        return listOut
    }
}