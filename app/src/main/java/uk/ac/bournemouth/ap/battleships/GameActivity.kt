package uk.ac.bournemouth.ap.battleships

import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import org.example.student.battleshipgame.StudentBattleshipGrid
import org.example.student.battleshipgame.StudentBattleshipOpponent
import org.example.student.battleshipgame.StudentShip
import uk.ac.bournemouth.ap.battleshiplib.BattleshipGrid
import uk.ac.bournemouth.ap.battleshiplib.BattleshipOpponent
import uk.ac.bournemouth.ap.battleshiplib.Ship
import kotlin.random.Random

var opponent: BattleshipOpponent? = null
var player: BattleshipOpponent? = null
var opGrid: BattleshipGrid? = null
var playerGrid: BattleshipGrid? = null
var gridList: List<BattleshipGrid>? = null
var playerTurn: Boolean = false

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)

        val intent: Intent = getIntent()
        val ships = intent.getIntArrayExtra("shipData")
        var playerShips: List<StudentShip> = mutableListOf()

        for(i in 0 until ships!!.size step 4){
            val top: Int = ships[i]
            val bottom: Int = ships[i+1]
            val left: Int = ships[i+2]
            val right: Int = ships[i+3]

            playerShips += StudentShip(top,left,bottom,right)
        }

        opponent = StudentBattleshipOpponent(10,10, BattleshipGrid.DEFAULT_SHIP_SIZES , Random)
        player = StudentBattleshipOpponent(10,10, playerShips)

        opGrid = StudentBattleshipGrid(opponent as StudentBattleshipOpponent)
        playerGrid = StudentBattleshipGrid(player as StudentBattleshipOpponent)

        setContentView(R.layout.game_layout)
    }
}