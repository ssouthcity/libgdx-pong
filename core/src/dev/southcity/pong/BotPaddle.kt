package dev.southcity.pong

import com.badlogic.gdx.physics.box2d.World
import kotlin.math.absoluteValue
import kotlin.math.sign

class BotPaddle(world: World) : Paddle(world) {
    private lateinit var ball: Ball

    init {
        body.setTransform(SCREEN_WIDTH / PPM - PADDLE_MARGIN / PPM, SCREEN_HEIGHT / 2 / PPM, 0f)
    }

    fun trackBall(ball: Ball) {
        this.ball = ball
    }

    override fun update(delta: Float) {
        val distanceToBall = ball.distanceVector(body.position)

        if (ball.getDirection().x > PADDLE_MOVE_THRESHOLD) {
            val diff = distanceToBall.y.sign * -1
            body.setLinearVelocity(0f, BOT_PADDLE_SPEED * diff)
        } else {
            body.setLinearVelocity(0f, 0f)
        }
    }
}