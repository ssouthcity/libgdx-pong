package dev.southcity.pong

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World

abstract class Paddle(world: World) {

    protected val body: Body

    init {
        val bDef = BodyDef().apply {
            type = BodyDef.BodyType.KinematicBody
            fixedRotation = true
        }

        body = world.createBody(bDef)

        val box = PolygonShape()
        box.setAsBox(PADDLE_WIDTH / 2 / PPM, PADDLE_HEIGHT / 2 / PPM)

        val fDef = FixtureDef().apply {
            shape = box
            density = 0f
            friction = 0f
            restitution = 1f
        }

        body.createFixture(fDef)

        box.dispose()
    }

    abstract fun update(delta: Float)

    fun draw(shapeRenderer: ShapeRenderer) {
        shapeRenderer.rect(
           body.position.x * PPM - PADDLE_WIDTH / 2,
            body.position.y * PPM - PADDLE_HEIGHT / 2,
            PADDLE_WIDTH,
            PADDLE_HEIGHT,
        )
    }
}