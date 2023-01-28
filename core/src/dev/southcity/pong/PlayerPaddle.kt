package dev.southcity.pong

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.World
import kotlin.math.absoluteValue

class PlayerPaddle(world: World, x: Float, y: Float, camera: Camera) : Paddle(world, x, y, camera) {
    override fun update() {
        if (Gdx.input.isTouched) {
            val touch = camera.unproject(Vector3(0f, Gdx.input.y.toFloat(), 0f))

            if ((touch.y - body.position.y).absoluteValue < 8f) {
                body.setLinearVelocity(0f, 0f)
            } else {
                val y = touch.y.compareTo(body.position.y)
                body.setLinearVelocity(0f, y * PADDLE_SPEED)
            }
        } else {
            body.setLinearVelocity(0f, 0f)
        }
    }
}