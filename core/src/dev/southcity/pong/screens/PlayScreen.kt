package dev.southcity.pong.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.ScreenUtils
import dev.southcity.pong.*

class PlayScreen(private val game: PongGame) : Screen {
    lateinit var ball: Ball
    lateinit var playerPaddle: TouchPaddle
    lateinit var opponentPaddle: BotPaddle

    var playerScore = 0
    var opponentScore = 0

    private fun spawnBall() {
        ball = Ball()
        opponentPaddle.trackBall(ball)
    }

    private fun collide(paddle: Paddle, ball: Ball) {
        val collisionVec = Vector2(
            ball.x - paddle.centerX(),
            ball.y - paddle.centerY(),
        ).setLength(1f)

        while (ball.overlaps(paddle)) {
            ball.x += collisionVec.x
            ball.y += collisionVec.y
        }

        val oldSpeed = ball.velocity.len()

        collisionVec.setLength(oldSpeed)
        ball.velocity.x *= -1
        ball.velocity = ball.velocity.lerp(collisionVec, 0.5f).setLength(oldSpeed)
    }

    private fun update(delta: Float) {
        ball.update(delta)
        playerPaddle.update(delta)
        opponentPaddle.update(delta)

        if (playerPaddle.overlaps(ball)) {
            collide(playerPaddle, ball)
        }

        if (opponentPaddle.overlaps(ball)) {
            collide(opponentPaddle, ball)
        }

        if (ball.x < -ball.width) {
            opponentScore++
            spawnBall()
        }

        if (ball.x > SCREEN_WIDTH) {
            playerScore++
            spawnBall()
        }

        if (playerScore >= WINNING_SCORE) {
            game.screen = IntermediaryScreen(game, "You won!!!!")
        } else if (opponentScore >= WINNING_SCORE) {
            game.screen = IntermediaryScreen(game, "You lose :(((")
        }
    }

    override fun show() {
        playerPaddle = TouchPaddle(game.camera)
        opponentPaddle = BotPaddle()
        spawnBall()
    }

    override fun render(delta: Float) {
        val delta = Gdx.graphics.deltaTime

        update(delta)

        ScreenUtils.clear(Color.BLACK)

        game.batch.begin()
        game.batch.projectionMatrix = game.camera.combined
        game.font.data.setScale(2f)
        game.font.draw(game.batch, playerScore.toString(), SCREEN_WIDTH / 4, SCREEN_HEIGHT / 2)
        game.font.draw(game.batch, opponentScore.toString(), SCREEN_WIDTH / 4 * 3, SCREEN_HEIGHT / 2)
        game.batch.end()

        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        game.shapeRenderer.projectionMatrix = game.camera.combined
        ball.draw(game.shapeRenderer)
        playerPaddle.draw(game.shapeRenderer)
        opponentPaddle.draw(game.shapeRenderer)
        game.shapeRenderer.end()
    }

    override fun resize(width: Int, height: Int) {}
    override fun pause() {}
    override fun resume() {}
    override fun hide() {}
    override fun dispose() {}

}