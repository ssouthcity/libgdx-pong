package dev.southcity.pong

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.utils.ScreenUtils

const val SCREEN_WIDTH: Float = 640f
const val SCREEN_HEIGHT: Float = SCREEN_WIDTH * 9 / 16

const val BALL_SIZE: Float = 16f
const val BALL_SPEED: Float = 172f
const val BALL_SPEED_MODIFIER: Float = 0.001f

const val PADDLE_WIDTH: Float = BALL_SIZE
const val PADDLE_HEIGHT: Float = 4 * BALL_SIZE
const val PADDLE_MARGIN: Float = PADDLE_WIDTH

class Pong : ApplicationAdapter() {
    lateinit var shapeRenderer: ShapeRenderer
    lateinit var batch: Batch
    lateinit var font: BitmapFont
    lateinit var camera: OrthographicCamera

    lateinit var ball: Ball
    lateinit var playerPaddle: TouchPaddle
    lateinit var opponentPaddle: BotPaddle

    var playerScore = 0
    var opponentScore = 0

    override fun create() {
        shapeRenderer = ShapeRenderer()
        batch = SpriteBatch()
        font = BitmapFont()
        camera = OrthographicCamera()
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT)

        playerPaddle = TouchPaddle(camera)
        opponentPaddle = BotPaddle()

        spawnBall()
    }

    fun spawnBall() {
        ball = Ball()
        opponentPaddle.trackBall(ball)
    }

    fun update(delta: Float) {
        ball.update(delta)
        playerPaddle.update(delta)
        opponentPaddle.update(delta)

        if (playerPaddle.overlaps(ball)) {
            ball.x = playerPaddle.x + playerPaddle.width
            ball.velocity.x *= -1
        }

        if (opponentPaddle.overlaps(ball)) {
            ball.x = opponentPaddle.x - ball.width
            ball.velocity.x *= -1
        }

        if (ball.x < -ball.width) {
            opponentScore++
            spawnBall()
        }

        if (ball.x > SCREEN_WIDTH) {
            playerScore++
            spawnBall()
        }
    }

    override fun render() {
        val delta = Gdx.graphics.deltaTime

        update(delta)

        ScreenUtils.clear(Color.BLACK)

        batch.begin()
        batch.projectionMatrix = camera.combined
        font.data.setScale(2f)
        font.draw(batch, playerScore.toString(), SCREEN_WIDTH / 4, SCREEN_HEIGHT / 2)
        font.draw(batch, opponentScore.toString(), SCREEN_WIDTH / 4 * 3, SCREEN_HEIGHT / 2)
        batch.end()

        shapeRenderer.begin(ShapeType.Filled)
        shapeRenderer.projectionMatrix = camera.combined
        ball.draw(shapeRenderer)
        playerPaddle.draw(shapeRenderer)
        opponentPaddle.draw(shapeRenderer)
        shapeRenderer.end()
    }

    override fun dispose() {
        shapeRenderer.dispose()
    }
}