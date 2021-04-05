package org.example.student.battleshipgame

import uk.ac.bournemouth.ap.battleshiplib.BattleshipGrid
import uk.ac.bournemouth.ap.battleshiplib.BattleshipGrid.BattleshipGridListener
import uk.ac.bournemouth.ap.battleshiplib.GuessCell
import uk.ac.bournemouth.ap.battleshiplib.GuessResult
import uk.ac.bournemouth.ap.lib.matrix.Matrix
import uk.ac.bournemouth.ap.lib.matrix.MutableMatrix

/**
 * This grid class describes the state of current guesses. It records which ships were sunk, where
 * shots were placed (with  what results). It also records the [opponent](StudentBattleshipOpponent)
 *
 * @constructor Constructor that represents the actual state, this is needed when saving/loading
 *   the state.
 * @param guesses   The information on the ships in the grid.
 * @param opponent The actual opponent information.
 */
open class StudentBattleshipGrid protected constructor(
    guesses: Matrix<GuessCell>,
    override val opponent: StudentBattleshipOpponent,
) : BattleshipGrid {

    /**
     * Helper constructor for a fresh new game
     */
    constructor(opponent: StudentBattleshipOpponent) : this(
        MutableMatrix(
            opponent.columns,
            opponent.rows
        ) { _, _ -> GuessCell.UNSET }, opponent
    )

    /**
     * A list of listeners that should be informed if the game state changes.
     */
    private val onGridChangeListeners = mutableListOf<BattleshipGridListener>()

    /**
     * An array determining whether the ship with the given index was sunk. This can be used for
     * various purposes, including to determine whether the game has been won.
     *
     * @return An array with the status of sinking of each ship
     */
    override val shipsSunk: BooleanArray by lazy { BooleanArray(opponent.ships.size) }
    // This property is lazy to resolve issues with order of initialization.

    /**
     * A matrix with all guesses made in the game
     */
    private val guesses: MutableMatrix<GuessCell> = MutableMatrix(guesses)

     /**
     * Helper property to get the width of the game.
     */
    override val columns: Int get() = opponent.columns

    /**
     * Helper property to get the height of the game.
     */
    override val rows: Int get() = opponent.rows

    /*
     * Infrastructure to allow listening to game change events (and update the display
     * correspondingly)
     */

    /**
     * Register a listener for game changes
     *
     * @param listener The listener to register.
     */
    override fun addOnGridChangeListener(listener: BattleshipGridListener) {
        if (!onGridChangeListeners.contains(listener)) onGridChangeListeners.add(listener)
    }

    /**
     * Unregister a listener so that it no longer receives notifications of game changes
     *
     * @param listener The listener to unregister.
     */
    override fun removeOnGridChangeListener(listener: BattleshipGridListener) {
        onGridChangeListeners.remove(listener)
    }

    /**
     * Send a game change event to all registered listeners.
     *
     * @param column The column changed
     * @param row    The row changed
     */
    protected fun fireOnGridChangeEvent(column: Int, row: Int) {
        for (listener in onGridChangeListeners) {
            listener.onGridChanged(this, column, row)
        }
    }

    /**
     * The get operator allows retrieving the guesses at a location. You probably want to just look
     * the value up from a property you create (of type `MutableMatrix<GuessCell>`)
     * TODO("Look up the value from state")
     */
    override operator fun get(column: Int, row: Int): GuessCell = guesses[column,row]

    /**
     * This method is core to the game as it implements the actual gameplay (after initial setup).
     */
    override fun shootAt(column: Int, row: Int): GuessResult {
        var guessResult: GuessResult = GuessResult.MISS
        var sunk: Boolean = false
        var hitCounter: Int = 0

        if(column > columns || row > rows){
            throw Exception("coords out of range")
        }
        else{
            if (guesses[column, row] == GuessCell.UNSET){
                if (opponent.shipAt(column, row) == null){
                    //Miss
                    guesses[column, row] = GuessCell.MISS
                    guessResult = GuessResult.MISS
                }
                else{
                    //Hit
                    val hitShip = opponent.shipAt(column, row)
                    val hitShipIndex = hitShip!!.index
                    val hitShipCoords = hitShip.ship.getCoords()

                    guesses[column, row] = GuessCell.HIT(hitShipIndex)
                    guessResult = GuessResult.HIT(hitShipIndex)

                    //check if ship has sunk
                    for (coord in hitShipCoords){
                        if(guesses[coord.x, coord.y] == GuessCell.HIT(hitShipIndex)){
                            hitCounter += 1
                        }
                    }
                    //if sunk set all cells to SUNK
                    if (hitCounter == hitShip.ship.size){
                        for (coord in hitShipCoords){
                            guesses[coord.x, coord.y] = GuessCell.SUNK(hitShipIndex)
                        }
                        this.shipsSunk[hitShipIndex] = true
                        guessResult = GuessResult.SUNK(hitShipIndex)
                    }
                }
            }
            else{
                throw Exception("cell already guessed")
            }
        }
        fireOnGridChangeEvent(column, row)
        return guessResult
    }
}
