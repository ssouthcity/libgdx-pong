package dev.southcity.pong

object Scoreboard {
    private val goals = mutableMapOf(
        Player.Left to 0,
        Player.Right to 0,
    )

    fun readScoreFor(side: Player): Int {
        return goals[side.opposite()]!!
    }

    fun score(side: Player) {
        goals[side] = goals[side]!!.inc()
    }

    fun reset() {
        goals[Player.Left] = 0
        goals[Player.Right] = 0
    }

    fun winner(): Player? {
        goals.forEach { (player, score) ->
            if (score >= WINNING_SCORE) {
                return player.opposite()
            }
        }
        return null
    }
}