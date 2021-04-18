package org.example.student.battleshipgame
import uk.ac.bournemouth.ap.battleshiplib.BattleshipGrid
import uk.ac.bournemouth.ap.battleshiplib.GuessCell
import uk.ac.bournemouth.ap.battleshiplib.GuessResult
import uk.ac.bournemouth.ap.lib.matrix.ext.Coordinate
import java.lang.Exception
import kotlin.random.Random

class AI(val difficulty: String) {
    var foundShip = false
    var lastHit = Coordinate(-1,-1)
    var nextHit: Int? = null
    var stuckCounter = 0 //useed to check if the ai has boxed itself in with guessed cells
    var counter = 0

     fun shootCell(grid: BattleshipGrid){

        if (difficulty == "easy"){
            //random searching
            var foundUnset = false
            while (!foundUnset){
                try {
                    val x = Random.nextInt(grid.columns)
                    val y = Random.nextInt(grid.rows)

                    grid.shootAt(x,y)
                    foundUnset = true
                }
                catch (e: Exception){
                    println("cell guessed")
                }
            }
        }
        else{
            //random search
            if(!foundShip){
                var foundUnset = false
                while (!foundUnset){
                    try {
                        //val cols = grid.columns.takeIf { it % 2 == 0}
                        //val rows = grid.rows.takeIf { it % 2 == 0 }
                        val x = Random.nextInt(grid.columns)
                        val y = Random.nextInt(grid.rows)

                        if(grid.shootAt(x,y) !== GuessResult.MISS){
                            foundShip = true //flag to move from search to target
                            lastHit = Coordinate(x,y)
                        }

                        foundUnset = true
                    }
                    catch (e: Exception){
                        println("cell guessed")
                    }
                }
            }
            //if ship found, target ship
            else{
                //target ship
                var foundUnset = false
                while (!foundUnset){
                    try {
                        val moves: List<Coordinate> = listOf(
                                Coordinate(lastHit.x + 1, lastHit.y),
                                Coordinate(lastHit.x - 1, lastHit.y),
                                Coordinate(lastHit.x, lastHit.y + 1),
                                Coordinate(lastHit.x, lastHit.y - 1)
                        )
                        var target: Coordinate? = null
                        val i = Random.nextInt(4)

                        if (nextHit != null){
                            target = moves[nextHit!!]
                        }
                        else{
                            target = moves[i]
                        }

                        val result = grid.shootAt(target.x, target.y)

                        if (result::class == GuessResult.HIT::class){
                            lastHit = target
                            nextHit = i
                        }
                        if (result::class == GuessResult.MISS::class){
                            nextHit = null
                        }
                        if(result::class == GuessResult.SUNK::class){
                            foundShip = false
                            nextHit = null
                        }
                        foundUnset = true
                    }
                    catch (e: Exception){
                        println("cell guessed")
                        println(stuckCounter)
                        stuckCounter += 1
                        if (stuckCounter > 6){
                            foundShip = false
                            nextHit = null
                            foundUnset = true
                            stuckCounter = 0
                        }
                    }
                }
            }
        }
    }
}