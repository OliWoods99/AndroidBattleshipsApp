package uk.ac.bournemouth.ap.battleships

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import org.example.student.battleshipgame.StudentBattleshipOpponent
import kotlin.random.Random


class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
    }
    fun setupGame(view: View) {
        val intent = Intent(this, PlaceShipActivity::class.java)
        startActivity(intent)
    }
}