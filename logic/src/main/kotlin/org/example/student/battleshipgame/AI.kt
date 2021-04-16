package org.example.student.battleshipgame
import uk.ac.bournemouth.ap.battleshiplib.BattleshipGrid
import uk.ac.bournemouth.ap.battleshiplib.GuessCell
import uk.ac.bournemouth.ap.battleshiplib.GuessResult
import uk.ac.bournemouth.ap.lib.matrix.ext.Coordinate
import java.lang.Exception
import kotlin.random.Random

class AI(val difficulty: String) {
    var foundShip = false
    var firstHit = Coordinate(-1,-1)
    var lastHit = Coordinate(-1,-1)
    var nextHit = Coordinate(-1, -1)
    var counter = 0 //useed to check if the ai has boxed itself in with guessed cells

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
                        val x = Random.nextInt(grid.columns)
                        val y = Random.nextInt(grid.rows)

                        if(grid.shootAt(x,y) !== GuessResult.MISS){
                            foundShip = true //flag to move from search to target
                            lastHit = Coordinate(x,y)
                            firstHit = Coordinate(x,y)
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

                        val i = Random.nextInt(4)
                        val target = moves[i]

                        val result = grid.shootAt(target.x, target.y)
                        println(result.toString())

                        if (result::class == GuessResult.HIT::class){
                            println("HIT HIT HIT HIT")
                            lastHit = target
                            nextHit = moves[i]
                        }
                        if(result::class == GuessResult.SUNK::class){
                            foundShip = false
                            println("SUNK SUNK SUNK SUNK")

                        }
                        foundUnset = true
                    }
                    catch (e: Exception){
                        println("cell guessed")
                        counter += 1

                        if (counter > 10){
                            foundShip = false
                            counter = 0
                        }
                    }
                }

            }
        }
    }
}