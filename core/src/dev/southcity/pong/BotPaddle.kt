package dev.southcity.pong

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.physics.box2d.World
import kotlin.math.absoluteValue

class BotPaddle(world: World, x: Float, y: Float, camera: Camera, var ball: Ball) : Paddle(world, x, y, camera) {
    override fun update() {
        println(ball.body.position.y)
        if ((ball.body.position.y - body.position.y).absoluteValue < 8f) {
            body.setLinearVelocity(0f, 0f)
        } else {
            val y = ball.body.position.y.compareTo(body.position.y)
            body.setLinearVelocity(0f, y * PADDLE_SPEED)
        }
    }
}