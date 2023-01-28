package dev.southcity.pong

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*

const val BALL_RADIUS: Float = 16f

class Ball(world: World, x: Float, y: Float) {
    val body: Body

    init {
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(x, y)

        body = world.createBody(bodyDef)

        val shape = CircleShape()
        shape.radius = BALL_RADIUS

        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.friction = 0f
        fixtureDef.restitution = 1f // our ball should not lose speed on collision

        body.createFixture(fixtureDef)

        body.linearVelocity = Vector2(-48f, -64f)
    }
}