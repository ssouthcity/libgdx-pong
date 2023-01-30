package dev.southcity.pong

import kotlin.math.absoluteValue

const val BOT_PADDLE_SPEED = 64f

class BotPaddle() : Paddle(SCREEN_WIDTH - PADDLE_WIDTH - PADDLE_MARGIN, SCREEN_HEIGHT / 2 - PADDLE_HEIGHT / 2) {
    private lateinit var ball: Ball

    fun trackBall(ball: Ball) {
        this.ball = ball
    }

    override fun update(delta: Float) {
        if ((ball.y - centerY()).absoluteValue > 1f) {
            y += BOT_PADDLE_SPEED * ball.y.compareTo(centerY()) * delta
            y = y.coerceIn(0f, SCREEN_HEIGHT - height)
        }
    }
}