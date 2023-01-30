package dev.southcity.pong

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import dev.southcity.pong.screens.IntermediaryScreen
import dev.southcity.pong.screens.PlayScreen

const val SCREEN_WIDTH: Float = 640f
const val SCREEN_HEIGHT: Float = SCREEN_WIDTH * 9 / 16

const val BALL_SIZE: Float = 16f
const val BALL_SPEED: Float = 172f
const val BALL_SPEED_MODIFIER: Float = 0.001f

const val PADDLE_WIDTH: Float = BALL_SIZE
const val PADDLE_HEIGHT: Float = 4 * BALL_SIZE
const val PADDLE_MARGIN: Float = PADDLE_WIDTH

const val WINNING_SCORE: Int = 21

class PongGame : Game() {
    lateinit var shapeRenderer: ShapeRenderer
    lateinit var batch: Batch
    lateinit var font: BitmapFont
    lateinit var camera: OrthographicCamera

    override fun create() {
        shapeRenderer = ShapeRenderer()
        batch = SpriteBatch()
        font = BitmapFont()
        camera = OrthographicCamera()
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT)

        setScreen(IntermediaryScreen(this, "Pong!"))
    }

    override fun render() {
        shapeRenderer.projectionMatrix = camera.combined
        batch.projectionMatrix = camera.combined

        super.render()
    }

    override fun dispose() {
        shapeRenderer.dispose()
        font.dispose()
        batch.dispose()
    }
}