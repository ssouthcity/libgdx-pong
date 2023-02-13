package dev.southcity.pong

enum class Player {
    Left,
    Right;

    fun opposite(): Player {
        return when(this) {
            Left -> Right
            Right -> Left
        }
    }
}