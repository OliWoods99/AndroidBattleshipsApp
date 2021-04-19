package uk.ac.bournemouth.ap.battleships 

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.GestureDetectorCompat
import org.example.student.battleshipgame.AI
import org.example.student.battleshipgame.StudentBattleshipGame
import org.example.student.battleshipgame.StudentBattleshipGrid
import org.example.student.battleshipgame.StudentBattleshipOpponent
import uk.ac.bournemouth.ap.battleshiplib.BattleshipGame
import uk.ac.bournemouth.ap.battleshiplib.BattleshipGrid
import uk.ac.bournemouth.ap.battleshiplib.GuessCell
import uk.ac.bournemouth.ap.battleshiplib.GuessResult
import java.nio.file.Paths.get
import kotlin.random.Random

class PlayerGridView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    val cols = opponent!!.columns
    val rows = opponent!!.rows
    var cellSize: Float = 0f

    /** instantiate listners **/
    private val listener: BattleshipGrid.BattleshipGridListener =
            BattleshipGrid.BattleshipGridListener{
                grid, column, row -> invalidate()
            }
    init {
        opGrid!!.addOnGridChangeListener(listener)
    }

    /** define paint styles **/
    private val gridPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
    }
    private val hitPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.RED
    }
    private val missPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.DKGRAY
    }
    private val sunkPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLUE
    }

    private val gestureDetector = GestureDetectorCompat(context, object:
            GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            val eX = e.x  //is e.x the same as x?
            val eY = e.y

            // get column (might need to put these in funs)
            fun getCol(): Int{
                var colCounter: Int = -1
                var out: Int = 999
                for ( i in 0 until (cellSize * cols).toInt() step cellSize.toInt()){
                    colCounter += 1
                    if( i <= eX && eX <= (i + cellSize)){
                        out = colCounter
                    }
                }
                return out
            }
            // get row
            fun getRow(): Int{
                var rowCounter: Int = -1
                var out: Int = 999
                for ( i in 0 until (cellSize * rows).toInt() step cellSize.toInt()){
                    rowCounter += 1
                    if( i <= eY && eY <= (i + cellSize)){
                        out = rowCounter
                    }
                }
                return out
            }
            try {
                //println(opGrid!!.shootAt(getCol(),getRow()).toString()
                if(playerTurn){
                    playerTurn = false
                    opGrid!!.shootAt(getCol(), getRow())

                    if(opGrid!!.shipsSunk.all { it }){
                        win = "CONGRATULATIONS YOU WIN!"

                        val intent = Intent(context, endGameScreen::class.java)
                        context.startActivity(intent)
                    }
                }
            }
            catch (e: Exception){
                println("guessed already")
            }
            return super.onSingleTapUp(e)
        }
    })
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        //get dimensions for grid and change cell size accordingly
        if(cols >= rows){
            cellSize = ((w / cols).toFloat())
        }
        else{
            cellSize = ((h / rows).toFloat())
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var cellColour = gridPaint

        for (col in 0 until cols){
            for (row in 0 until rows){

                val cellX: Float = (cellSize * col)
                val cellY: Float = (cellSize * row)

                if (opGrid!![col,row] == GuessCell.UNSET){
                    cellColour = gridPaint
                }
                else if (opGrid!![col,row] == GuessCell.MISS){
                    cellColour = missPaint
                }
                else if (opGrid!![col,row]::class == GuessCell.HIT::class){
                    cellColour = hitPaint
                }
                else if (opGrid!![col,row]::class == GuessCell.SUNK::class){
                    cellColour = sunkPaint
                }
                canvas!!.drawRect(cellX, cellY, (cellX + cellSize), (cellY + cellSize), cellColour)
            }
        }
    }
}
