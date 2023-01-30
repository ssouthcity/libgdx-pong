package dev.southcity.pong

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle

abstract class Paddle(x: Float, y: Float) : Rectangle(x, y, PADDLE_WIDTH, PADDLE_HEIGHT) {
    abstract fun update(delta: Float)

    fun centerY(): Float {
        return y + height / 2
    }

    fun draw(shapeRenderer: ShapeRenderer) {
        shapeRenderer.rect(x, y, width, height)
    }
}