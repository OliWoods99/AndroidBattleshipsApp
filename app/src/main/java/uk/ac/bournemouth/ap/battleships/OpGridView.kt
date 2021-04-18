package uk.ac.bournemouth.ap.battleships

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import org.example.student.battleshipgame.AI
import org.example.student.battleshipgame.StudentBattleshipGame
import org.example.student.battleshipgame.StudentBattleshipGrid
import org.example.student.battleshipgame.StudentBattleshipOpponent
import uk.ac.bournemouth.ap.battleshiplib.BattleshipGame
import uk.ac.bournemouth.ap.battleshiplib.BattleshipGrid
import uk.ac.bournemouth.ap.battleshiplib.GuessCell
import kotlin.random.Random


class OpGridView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    val cols = player!!.columns
    val rows = player!!.rows
    var cellSize: Float = 0f

    /** instantiate AI class **/
    val ai = AI(difficulty)

    /** instantiate listners **/
    private val listener: BattleshipGrid.BattleshipGridListener =
            BattleshipGrid.BattleshipGridListener{
                grid, column, row -> invalidate()
            }
    init {
        playerGrid!!.addOnGridChangeListener(listener)
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

        println("OpGrid onDraw")
        var cellColour = gridPaint
        for (col in 0 until cols){
            for (row in 0 until rows){

                val cellX: Float = (cellSize * col)
                val cellY: Float = (cellSize * row)

                if (playerGrid!![col,row] == GuessCell.UNSET){
                    cellColour = gridPaint
                }
                else if (playerGrid!![col,row] == GuessCell.MISS){
                    cellColour = missPaint
                }
                else if (playerGrid!![col,row]::class == GuessCell.HIT::class){
                    cellColour = hitPaint
                }
                else if (playerGrid!![col,row]::class == GuessCell.SUNK::class){
                    cellColour = sunkPaint
                }
                canvas!!.drawRect(cellX, cellY, (cellX + cellSize), (cellY + cellSize), cellColour)
            }
        }
        //when player playTurn is called this view is redrawn so put AI turn here
        try {
            if(!playerTurn){
                playerTurn = true
                //playerGrid!!.shootAt(Random.nextInt(cols), Random.nextInt(rows))
                ai.shootCell(playerGrid!!)
            }
        }
        catch (e: Exception){
            println("cell already guessed")
        }
        invalidate()
    }
}