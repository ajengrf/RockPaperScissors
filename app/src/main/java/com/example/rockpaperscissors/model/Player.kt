package com.example.rockpaperscissors.model

import com.example.rockpaperscissors.enum.PlayerChoice
import com.example.rockpaperscissors.enum.PlayerSide

data class Player(
    val playerSide: PlayerSide,
    var playerChoice: PlayerChoice? = null
)
