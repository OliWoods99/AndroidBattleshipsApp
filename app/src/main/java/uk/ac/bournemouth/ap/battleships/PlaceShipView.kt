
package uk.ac.bournemouth.ap.battleships

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import org.example.student.battleshipgame.StudentShip

class PlaceShipView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    val cols = 10
    val rows = 10
    var cellSize: Float = 0f

    var vert: Boolean = true

    var shipTop: Int = -1
    var shipBottom :Int = -1
    var shipLeft  :Int = -1
    var shipRight :Int = -1

    private val gridPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
    }
    private val shipPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLACK
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

                //draw current ship
                if (col in shipLeft..shipRight){
                    if (row in shipTop..shipBottom){
                        cellColour = shipPaint
                    }
                    else{
                        cellColour = gridPaint
                    }
                }
                else{
                    cellColour = gridPaint
                }

                canvas!!.drawRect(cellX, cellY, (cellX + cellSize), (cellY + cellSize), cellColour)
            }
        }
        println("onDraw")
    }

    /** Gesture detection **/
    private val gestureDetector = GestureDetectorCompat(context, object:
            GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            if (vert){
                vert = false
            }
            else if(!vert){
                vert = true
            }
            println(vert.toString())
            return super.onDoubleTap(e)
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

            val shipSize = sizes[count]

            val x = getCol()
            val y = getRow()

            if (vert){
                shipTop = y
                shipBottom = y + (shipSize - 1)
                shipLeft = x
                shipRight = x
            }
            else{
               shipTop = y
               shipBottom = y
               shipLeft = x
               shipRight = x + (shipSize - 1)
            }

            currentShip = intArrayOf(shipTop, shipBottom, shipLeft, shipRight)
            return super.onSingleTapUp(e)
        }
    })
    override fun onTouchEvent(event: MotionEvent): Boolean {
        invalidate()
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
    }
}