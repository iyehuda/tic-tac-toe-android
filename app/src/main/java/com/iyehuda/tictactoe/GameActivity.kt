package com.iyehuda.tictactoe

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GameActivity : AppCompatActivity() {
    private val game = TicTacToeGame()
    private lateinit var gameStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        gameStatus = findViewById(R.id.game_status)
        setupTiles()
    }

    private fun setupTiles() {
        val tiles = listOf(
            listOf(R.id.tile_1, R.id.tile_2, R.id.tile_3),
            listOf(R.id.tile_4, R.id.tile_5, R.id.tile_6),
            listOf(R.id.tile_7, R.id.tile_8, R.id.tile_9)
        )

        for ((row, tileIds) in tiles.withIndex()) {
            for ((col, tileId) in tileIds.withIndex()) {
                val tile: ImageButton = findViewById(tileId)
                tile.setOnClickListener {
                    playTurn(tile, row, col)
                }
            }
        }
    }

    private fun playTurn(tile: ImageButton, row: Int, col: Int) {
        if (game.hasFinished() || game.isTileSet(row, col)) return

        updateTile(tile, game.currentPlayer)
        game.play(row, col)

        if (game.hasFinished()) {
            updateWhoWon()
        } else {
            updateWhoPlays()
        }
    }

    private fun updateTile(tile: ImageButton, player: Player) {
        tile.setBackgroundResource(
            when (player) {
                Player.X -> R.drawable.x_tile
                Player.O -> R.drawable.o_tile
                Player.NONE -> throw IllegalArgumentException("Invalid player")
            }
        )
    }

    private fun updateWhoPlays() {
        gameStatus.text = when (game.currentPlayer) {
            Player.X -> getString(R.string.game_status_x_turn)
            Player.O -> getString(R.string.game_status_o_turn)
            else -> throw IllegalArgumentException("Invalid player")
        }
    }

    private fun updateWhoWon() {
        gameStatus.text = when (game.winner!!) {
            Player.X -> getString(R.string.game_status_x_won)
            Player.O -> getString(R.string.game_status_o_won)
            Player.NONE -> getString(R.string.game_status_tie)
        }
    }
}
