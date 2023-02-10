package dev.southcity.pong.screens

import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.EdgeShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.ScreenUtils
import dev.southcity.pong.*

class PlayScreen(private val game: PongGame) : Screen {

    private val b2dr = Box2DDebugRenderer()

    private val world = World(Vector2.Zero, true)

    private val ball = Ball(world)

    private val playerPaddle = TouchPaddle(world, game.camera)

    private val opponentPaddle = BotPaddle(world)

    private var playerScore = 0
    private var opponentScore = 0

    private var accumulator: Float = 0f

    init {
        spawnWalls()
        opponentPaddle.trackBall(ball)
    }

    private fun spawnWalls() {
        val body = world.createBody(BodyDef())

        val edge = EdgeShape()
        // top
        edge.set(0f, SCREEN_HEIGHT / PPM, SCREEN_WIDTH / PPM, SCREEN_HEIGHT / PPM)
        body.createFixture(edge, 5f)
        // bottom
        edge.set(0f, 0f, SCREEN_WIDTH / PPM, 0f)
        body.createFixture(edge, 5f)

        edge.dispose()
    }

    private fun update(delta: Float) {
        accumulator += delta

        while (accumulator >= FRAME_RATE) {
            ball.update(delta)
            playerPaddle.update(delta)
            opponentPaddle.update(delta)

            world.step(FRAME_RATE, 6, 2)

            accumulator -= FRAME_RATE
        }

        val ballPos = ball.getPositionPixels()
        if (ballPos.x < 0f) {
            opponentScore++
            ball.reset()
        } else if (ballPos.x > SCREEN_WIDTH) {
            playerScore++
            ball.reset()
        }

        if (playerScore >= WINNING_SCORE) {
            game.screen = IntermediaryScreen(game, "You won!!!!")
        } else if (opponentScore >= WINNING_SCORE) {
            game.screen = IntermediaryScreen(game, "You lose :(((")
        }
    }

    override fun render(delta: Float) {
        update(delta)

        ScreenUtils.clear(Color.BLACK)

        val layout = GlyphLayout(game.font, "")

        game.batch.begin()
        game.batch.projectionMatrix = game.camera.combined
        game.font.data.setScale(2f)
        layout.setText(game.font, playerScore.toString())
        game.font.draw(game.batch, layout, SCREEN_WIDTH / 4 - layout.width / 2, SCREEN_HEIGHT / 2 + layout.height / 2)
        layout.setText(game.font, opponentScore.toString())
        game.font.draw(game.batch, layout, SCREEN_WIDTH / 4 * 3 - layout.width / 2, SCREEN_HEIGHT / 2 + layout.height / 2)
        game.batch.end()


        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        game.shapeRenderer.projectionMatrix = game.camera.combined
        ball.draw(game.shapeRenderer)
        playerPaddle.draw(game.shapeRenderer)
        opponentPaddle.draw(game.shapeRenderer)
        game.shapeRenderer.end()

        b2dr.render(world, game.camera.combined.cpy().scale(PPM, PPM, 1f))
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