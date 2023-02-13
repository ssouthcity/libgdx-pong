package dev.southcity.pong.screens

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.ScreenUtils
import dev.southcity.pong.*
import dev.southcity.pong.components.BodyComponent
import dev.southcity.pong.factories.EntityFactory
import dev.southcity.pong.factories.NormalEntityFactory
import dev.southcity.pong.systems.*

class PlayScreen(private val game: PongGame) : Screen {

    private val world = World(Vector2.Zero, true)

    private val engine = PooledEngine()

    private val entityFactory: EntityFactory = NormalEntityFactory(engine, world)

    private val b2dr = Box2DDebugRenderer()

    init {
        Scoreboard.reset()

        spawnWalls()

        val ball = entityFactory.createBall()
        engine.addEntity(ball)
        engine.addEntity(entityFactory.createPlayerPaddle())
        engine.addEntity(entityFactory.createOpponentPaddle(ball.getComponent(BodyComponent::class.java).body))

        engine.addSystem(PhysicsSystem(world))
        engine.addSystem(PaddleMovementSystem())
        engine.addSystem(SpeedTransformerSystem()) // has to run after velocities have been set
        engine.addSystem(RenderingSystem(game.shapeRenderer))

        val scoreboardSystem = ScoreboardSystem(game.batch, game.font, entityFactory)
        world.setContactListener(scoreboardSystem)
        engine.addSystem(scoreboardSystem)
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
        playerGoal.userData = Player.Left
        edge.set(0f, 0f, 0f, SCREEN_HEIGHT / PPM)
        playerGoal.createFixture(edge, 0f)

        val enemyGoal = world.createBody(BodyDef())
        enemyGoal.userData = Player.Right
        edge.set(SCREEN_WIDTH / PPM, 0f, SCREEN_WIDTH / PPM, SCREEN_HEIGHT / PPM)
        enemyGoal.createFixture(edge, 0f)

        edge.dispose()
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(Color.BLACK)

        engine.update(delta)

        //b2dr.render(world, Cameras.Box2DDebug.combined)

        val winner = Scoreboard.winner() ?: return

        game.screen = IntermediaryScreen(game, if (winner == Player.Left) {
            "You win!"
        } else {
            "You lose :("
        })
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