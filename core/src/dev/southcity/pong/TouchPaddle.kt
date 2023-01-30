package dev.southcity.pong

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector3
import kotlin.math.absoluteValue

const val TOUCH_PADDLE_SPEED = 128f

class TouchPaddle(private val camera: OrthographicCamera) : Paddle(PADDLE_MARGIN, SCREEN_HEIGHT / 2 - PADDLE_HEIGHT / 2) {
    override fun update(delta: Float) {
        if (Gdx.input.isTouched) {
            val touchY = camera.unproject(Vector3(0f, Gdx.input.y.toFloat(), 0f)).y

            if ((touchY - centerY()).absoluteValue > 1f) {
                y += touchY.compareTo(centerY()) * TOUCH_PADDLE_SPEED * delta
                y = y.coerceIn(0f, SCREEN_HEIGHT - height)
            }
        }
    }
}