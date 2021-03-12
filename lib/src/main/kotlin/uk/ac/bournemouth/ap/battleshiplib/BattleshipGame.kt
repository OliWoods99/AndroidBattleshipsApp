package uk.ac.bournemouth.ap.battleshiplib

interface BattleshipGame {
    val columns: Int get() = grids.first().columns
    val rows: Int get() = grids.first().rows

    val grids: List<BattleshipGrid>


    /**
     * Interface implemented by listeners to game change events.
     */
    interface BattleshipGameListener<G : BattleshipGame> {
        /**
         * When the game is changed this method is called.
         *
         * @param game   The game object that is the source of the event.
         * @param column The column changed
         * @param row    The row changed
         */
        fun onGameChanged(game: G, column: Int, row: Int)
    }

}
