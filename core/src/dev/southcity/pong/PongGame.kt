package dev.southcity.pong

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import dev.southcity.pong.screens.IntermediaryScreen

const val SCREEN_WIDTH: Float = 640f
const val SCREEN_HEIGHT: Float = SCREEN_WIDTH * 9 / 16

const val BALL_SIZE: Float = 16f
const val BALL_SPEED: Float = 4f
const val BALL_SPEED_MODIFIER: Float = 0.1f

const val PADDLE_WIDTH: Float = BALL_SIZE
const val PADDLE_HEIGHT: Float = 4 * BALL_SIZE
const val PADDLE_MARGIN: Float = PADDLE_WIDTH
const val PADDLE_MOVE_THRESHOLD: Float = 10f

const val TOUCH_PADDLE_SPEED = 4f
const val BOT_PADDLE_SPEED = 4f

const val WINNING_SCORE: Int = 21

const val FRAME_RATE: Float = 1 / 60f
const val PPM: Float = 64f

class PongGame : Game() {
    lateinit var shapeRenderer: ShapeRenderer
    lateinit var batch: Batch
    lateinit var font: BitmapFont

    override fun create() {
        shapeRenderer = ShapeRenderer()
        batch = SpriteBatch()
        font = BitmapFont(Gdx.files.internal("minecraft.fnt"), false)
        setScreen(IntermediaryScreen(this, "Pong!"))
    }

    override fun render() {
        shapeRenderer.projectionMatrix = Cameras.Game.combined
        batch.projectionMatrix = Cameras.Game.combined

        super.render()
    }

    override fun dispose() {
        shapeRenderer.dispose()
        font.dispose()
        batch.dispose()
    }
}