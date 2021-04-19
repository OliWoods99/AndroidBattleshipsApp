package uk.ac.bournemouth.ap.battleships

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class endGameScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_end_game_screen)

        val tv = findViewById<TextView>(R.id.textView)
        tv.text = win
    }

    fun restartGame(view: View) {
        opponent = null
        player = null
        opGrid = null
        playerGrid = null
        gridList = null
        playerTurn = false
        win = ""
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }
}