package dev.southcity.pong

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import kotlin.random.Random

class Ball(world: World) {

    private val body: Body

    private var timeAlive: Float = 0f

    init {
        val bDef = BodyDef().apply {
            type = BodyDef.BodyType.DynamicBody
            fixedRotation = true
        }

        body = world.createBody(bDef)

        val box = PolygonShape()
        box.setAsBox(BALL_SIZE / 2 / PPM, BALL_SIZE / 2 / PPM)

        val fDef = FixtureDef().apply {
            shape = box
            density = 0f
            friction = 0f
            restitution = 1f
        }

        body.createFixture(fDef)

        box.dispose()

        reset()
    }

    fun reset() {
        timeAlive = 0f
        body.setTransform(SCREEN_WIDTH / 2 / PPM, SCREEN_HEIGHT / 2 / PPM, 0f)
        val floatX = floatArrayOf(-1f, 1f).random() // prevent infinite bouncing by excluding zero
        val floatY = floatArrayOf(-1f, 1f).random()
        body.linearVelocity = Vector2(floatX, floatY).setLength(BALL_SPEED)
    }

    fun distanceVector(point: Vector2): Vector2 {
        return Vector2(
            point.x - body.position.x,
            point.y - body.position.y,
        )
    }

    fun getPositionPixels(): Vector2 {
        return Vector2(
            body.position.x * PPM,
            body.position.y * PPM,
        )
    }

    fun getDirection(): Vector2 {
        return body.linearVelocity
    }

    fun update(delta: Float) {
        timeAlive += delta

        body.linearVelocity = body.linearVelocity.setLength(BALL_SPEED + timeAlive * BALL_SPEED_MODIFIER)

        println(body.linearVelocity.len())
    }

    fun draw(shapeRenderer: ShapeRenderer) {
        shapeRenderer.rect(
            body.position.x * PPM - BALL_SIZE / 2,
            body.position.y * PPM - BALL_SIZE / 2,
            BALL_SIZE,
            BALL_SIZE,
        )
    }
}