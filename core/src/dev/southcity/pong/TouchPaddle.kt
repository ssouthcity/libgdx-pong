package dev.southcity.pong

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.World
import kotlin.math.absoluteValue

class TouchPaddle(world: World) : Paddle(world) {

    init {
        body.setTransform(PADDLE_MARGIN / PPM, SCREEN_HEIGHT / 2 / PPM, 0f)
    }

    override fun update(delta: Float) {
        if (Gdx.input.isTouched) {
            val touch = Cameras.Game.unproject(Vector3(0f, Gdx.input.y.toFloat(), 0f))

            if ((touch.y - body.position.y * PPM).absoluteValue > PADDLE_MOVE_THRESHOLD) {
                val sign = touch.y.compareTo(body.position.y * PPM)
                body.setLinearVelocity(0f, TOUCH_PADDLE_SPEED * sign)
            } else {
                body.setLinearVelocity(0f, 0f)
            }
        } else {
            body.setLinearVelocity(0f, 0f)
        }
    }

}