package dev.southcity.pong.factories

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import dev.southcity.pong.*
import dev.southcity.pong.components.*
import dev.southcity.pong.targeting.BodyTargetFinder
import dev.southcity.pong.targeting.TouchTargetFinder
import dev.southcity.pong.transformers.ConstantTransformer
import dev.southcity.pong.transformers.GrowingTransformer
import dev.southcity.pong.transformers.SlowMotionTransformer

class NormalEntityFactory(engine: Engine, world: World) : EntityFactory(engine, world) {
    override fun createBall(): Entity {
        val entity = engine.createEntity()
        entity.add(BallComponent())
        entity.add(BodyComponent(createBallBody()))
        entity.add(SpriteComponent(Vector2(BALL_SIZE, BALL_SIZE)))

        val speedModifierComponent = SpeedModifierComponent().apply {
            addTransformer(GrowingTransformer())
            addTransformer(SlowMotionTransformer())
        }
        entity.add(speedModifierComponent)

        return entity
    }

    override fun disposeBall(ball: Entity) {
        world.destroyBody(ball.getComponent(BodyComponent::class.java).body)
        engine.removeEntity(ball)
    }

    override fun createPlayerPaddle(): Entity {
        val body = createPaddleBody(PADDLE_MARGIN / PPM, SCREEN_HEIGHT / 2 / PPM)
        val entity = createPaddle(body)
        entity.add(TargetComponent(TouchTargetFinder()))

        return entity
    }

    override fun createOpponentPaddle(ballBody: Body): Entity {
        val body = createPaddleBody(SCREEN_WIDTH / PPM - PADDLE_MARGIN / PPM, SCREEN_HEIGHT / 2 / PPM)
        val entity = createPaddle(body)
        entity.add(TargetComponent(BodyTargetFinder(ballBody)))
        entity.add(SpeedModifierComponent().apply {
            addTransformer(ConstantTransformer(0.8f))
        })

        return entity
    }

    private fun createPaddle(body: Body): Entity {
        val entity = engine.createEntity()
        entity.add(BodyComponent(body))
        entity.add(SpriteComponent(Vector2(PADDLE_WIDTH, PADDLE_HEIGHT)))
        return entity
    }

    private fun createBallBody(): Body {
        val velocity = Vector2(
            arrayOf(-1f, 1f).random(),
            arrayOf(-1f, 1f).random(),
        )

        val bDef = BodyDef().apply {
            type = BodyDef.BodyType.DynamicBody
            fixedRotation = true
            position.set(SCREEN_WIDTH / 2 / PPM, SCREEN_HEIGHT / 2 / PPM)
            linearVelocity.set(velocity.x, velocity.y).setLength(BALL_SPEED)
        }

        val body = world.createBody(bDef)

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

        return body
    }

    private fun createPaddleBody(x: Float, y: Float): Body {
        val bDef = BodyDef().apply {
            type = BodyDef.BodyType.KinematicBody
            position.set(x, y)
            fixedRotation = true
        }

        val body = world.createBody(bDef)

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

        return body
    }
}