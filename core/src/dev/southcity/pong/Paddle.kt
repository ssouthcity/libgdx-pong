package dev.southcity.pong

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import kotlin.math.absoluteValue

const val PADDLE_WIDTH: Float = 24f
const val PADDLE_HEIGHT: Float = 72f
const val PADDLE_SPEED: Float = 128f

open class Paddle(world: World, x: Float, y: Float, val camera: Camera) {
    val body: Body

    init {
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(x, y)

        body = world.createBody(bodyDef)

        val shape = PolygonShape()
        shape.setAsBox(PADDLE_WIDTH / 2f, PADDLE_HEIGHT / 2f)

        body.createFixture(shape, 10000f)

        shape.dispose()
    }

    open fun update() {}
}