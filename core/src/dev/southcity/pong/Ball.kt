package dev.southcity.pong

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import kotlin.math.absoluteValue
import kotlin.random.Random

class Ball : Rectangle(SCREEN_WIDTH / 2 - BALL_SIZE / 2, SCREEN_HEIGHT / 2 - BALL_SIZE / 2, BALL_SIZE, BALL_SIZE) {
    var velocity: Vector2 = Vector2(
        Random.nextFloat() - 0.5f,
        Random.nextFloat() - 0.5f,
    ).setLength(BALL_SPEED)

    var timeAlive: Float = 0f

    fun update(delta: Float) {
        timeAlive += delta

        velocity.setLength(velocity.len() + timeAlive * BALL_SPEED_MODIFIER)

        x += velocity.x * delta
        y += velocity.y * delta

        if (y < 0f || y > SCREEN_HEIGHT - BALL_SIZE) {
            y = y.coerceIn(0f, SCREEN_HEIGHT - BALL_SIZE)
            velocity.y *= -1
        }

        // prevent soft lock
        if (velocity.x < 0.00001 && velocity.x > -0.00001) {
            velocity.x = 1f
        }

        // speed up if too slow
        if (velocity.x.absoluteValue < 16f) {
            velocity.x *= 1.25f
        }
    }

    fun draw(shapeRenderer: ShapeRenderer) {
        shapeRenderer.rect(x, y, width, height)
    }
}