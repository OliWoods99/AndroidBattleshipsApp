package uk.ac.bournemouth.ap.battleships

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import org.example.student.battleshipgame.StudentBattleshipOpponent
import kotlin.random.Random

var difficulty: String =""

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
    }
    fun setupGame(view: View) {
        val intent = Intent(this, PlaceShipActivity::class.java)
        difficulty = "easy"
        startActivity(intent)
    }

    fun setupGameHard(view: View) {
        val intent = Intent(this, PlaceShipActivity::class.java)
        difficulty = "hard"
        startActivity(intent)
    }
}