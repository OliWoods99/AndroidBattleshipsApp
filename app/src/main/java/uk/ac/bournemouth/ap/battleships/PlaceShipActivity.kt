package uk.ac.bournemouth.ap.battleships

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import uk.ac.bournemouth.ap.battleshiplib.BattleshipGrid

var sizes = BattleshipGrid.DEFAULT_SHIP_SIZES
var count = 0
var currentShip: IntArray = intArrayOf()
var placedShips: IntArray = intArrayOf()

class PlaceShipActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)
        sizes = BattleshipGrid.DEFAULT_SHIP_SIZES
        count = 0
        currentShip = intArrayOf()
        placedShips = intArrayOf()

        setContentView(R.layout.sample_place_ship_view)
    }

    fun confirmShip(view: View) {
        placedShips += currentShip

        if(count == (sizes.size - 1)){
            //ships full
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("shipData",placedShips)
            startActivity(intent)
        }
        else{
            count += 1
        }
    }
}