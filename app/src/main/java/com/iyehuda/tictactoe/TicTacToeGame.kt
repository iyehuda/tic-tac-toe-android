package com.iyehuda.tictactoe

enum class Player {
    X, O, NONE
}

class TicTacToeGame {
    private val board = listOf(
        mutableListOf(Player.NONE, Player.NONE, Player.NONE),
        mutableListOf(Player.NONE, Player.NONE, Player.NONE),
        mutableListOf(Player.NONE, Player.NONE, Player.NONE)
    )
    var currentPlayer = Player.X
        private set
    var winner: Player? = null
        private set

    fun hasFinished(): Boolean {
        return winner != null
    }

    fun isTileSet(row: Int, col: Int): Boolean {
        return board[row][col] != Player.NONE
    }

    private fun isWinningMove(row: Int, col: Int): Boolean {
        val player = board[row][col]

        if (board[row].all { it == player }) {
            return true
        }

        if ((0 until 3).all { board[it][col] == player }) {
            return true
        }

        // Check diagonal
        if (row == col && (0 until 3).all { board[it][it] == player }) {
            return true
        }

        // Check anti-diagonal
        if (row + col == 2 && (0 until 3).all { board[it][2 - it] == player }) {
            return true
        }

        return false
    }

    fun play(row: Int, col: Int) {
        if (hasFinished()) {
            throw IllegalStateException("Game has already finished")
        }

        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            throw IllegalArgumentException("Invalid row or column")
        }

        if (isTileSet(row, col)) {
            throw IllegalArgumentException("Invalid move")
        }

        board[row][col] = currentPlayer

        if (isWinningMove(row, col)) {
            winner = currentPlayer
        } else if (board.all { r -> r.all { it != Player.NONE } }) {
            winner = Player.NONE
        } else {
            currentPlayer = if (currentPlayer == Player.X) Player.O else Player.X
        }
    }
}
