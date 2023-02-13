package dev.southcity.pong.screens

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.ScreenUtils
import dev.southcity.pong.*
import dev.southcity.pong.components.BodyComponent
import dev.southcity.pong.components.SpeedModifierComponent
import dev.southcity.pong.components.TargetComponent
import dev.southcity.pong.transformers.GrowingTransformer
import dev.southcity.pong.components.TransformComponent
import dev.southcity.pong.systems.PaddleMovementSystem
import dev.southcity.pong.systems.PhysicsSystem
import dev.southcity.pong.systems.ScoreboardSystem
import dev.southcity.pong.systems.SpeedTransformerSystem
import dev.southcity.pong.targeting.BodyTargetFinder
import dev.southcity.pong.targeting.TouchTargetFinder
import dev.southcity.pong.transformers.SlowMotionTransformer

class PlayScreen(private val game: PongGame) : Screen {

    private val world = World(Vector2.Zero, true)

    private val engine = PooledEngine()

    private val b2dr = Box2DDebugRenderer()

    init {
        spawnWalls()

        val ballBody = createBallBody()
        engine.addEntity(createBall(ballBody))
        engine.addEntity(createPlayerPaddle())
        engine.addEntity(createOpponentPaddle(ballBody))

        engine.addSystem(PhysicsSystem(world))
        engine.addSystem(PaddleMovementSystem())
        engine.addSystem(SpeedTransformerSystem()) // has to run after velocities have been set

        val scoreboardSystem = ScoreboardSystem(game.batch, game.font)
        world.setContactListener(scoreboardSystem)
        engine.addSystem(scoreboardSystem)
    }

    private fun createOpponentPaddle(ball: Body): Entity {
        val body = createPaddleBody(SCREEN_WIDTH / PPM - PADDLE_MARGIN / PPM, SCREEN_HEIGHT / 2 / PPM)
        val entity = createPaddle(body)
        entity.add(TargetComponent(BodyTargetFinder(ball)))

        return entity
    }

    private fun createPlayerPaddle(): Entity {
        val body = createPaddleBody(PADDLE_MARGIN / PPM, SCREEN_HEIGHT / 2 / PPM)
        val entity = createPaddle(body)
        entity.add(TargetComponent(TouchTargetFinder()))

        return entity
    }

    private fun createPaddle(body: Body): Entity {
        val entity = engine.createEntity()
        entity.add(BodyComponent(body))
        entity.add(TransformComponent())
        return entity
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

    private fun createBall(body: Body): Entity {
        val entity = engine.createEntity()
        entity.add(BodyComponent(body))
        entity.add(TransformComponent())

        val speedModifierComponent = SpeedModifierComponent()
        speedModifierComponent.addTransformer(GrowingTransformer())
        speedModifierComponent.addTransformer(SlowMotionTransformer())
        entity.add(speedModifierComponent)

        return entity
    }

    private fun createBallBody(): Body {
        val bDef = BodyDef().apply {
            type = BodyDef.BodyType.DynamicBody
            fixedRotation = true
            position.set(SCREEN_WIDTH / 2 / PPM, SCREEN_HEIGHT / 2 / PPM)
            linearVelocity.set(1f, 1f).setLength(BALL_SPEED)
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

    private fun spawnWalls() {
        val edge = EdgeShape()

        val body = world.createBody(BodyDef())
        // top
        edge.set(0f, SCREEN_HEIGHT / PPM, SCREEN_WIDTH / PPM, SCREEN_HEIGHT / PPM)
        body.createFixture(edge, 5f)
        // bottom
        edge.set(0f, 0f, SCREEN_WIDTH / PPM, 0f)
        body.createFixture(edge, 5f)

        val playerGoal = world.createBody(BodyDef())
        playerGoal.userData = Goal.Player
        edge.set(0f, 0f, 0f, SCREEN_HEIGHT / PPM)
        playerGoal.createFixture(edge, 0f)

        val enemyGoal = world.createBody(BodyDef())
        enemyGoal.userData = Goal.Opponent
        edge.set(SCREEN_WIDTH / PPM, 0f, SCREEN_WIDTH / PPM, SCREEN_HEIGHT / PPM)
        enemyGoal.createFixture(edge, 0f)

        edge.dispose()
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(Color.BLACK)

        engine.update(delta)

        b2dr.render(world, Cameras.Box2DDebug.combined)
    }

    override fun dispose() {
        world.dispose()
        b2dr.dispose()
    }

    override fun resize(width: Int, height: Int) {}
    override fun pause() {}
    override fun resume() {}
    override fun show() {}
    override fun hide() {}
}