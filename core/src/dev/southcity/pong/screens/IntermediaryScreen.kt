package dev.southcity.pong.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.utils.ScreenUtils
import dev.southcity.pong.PongGame
import dev.southcity.pong.SCREEN_HEIGHT
import dev.southcity.pong.SCREEN_WIDTH

class IntermediaryScreen(private val game: PongGame, val message: String) : Screen {
    override fun render(delta: Float) {
        if (Gdx.input.justTouched()) {
            game.screen = PlayScreen(game)
        }

        ScreenUtils.clear(Color.BLACK)

        val layout = GlyphLayout(game.font, "")

        game.batch.begin()

        game.font.data.setScale(2f)
        layout.setText(game.font, message)
        game.font.draw(game.batch, layout, SCREEN_WIDTH / 2 - layout.width / 2, SCREEN_HEIGHT / 3 * 2 - layout.height / 2)

        game.font.data.setScale(1f)
        layout.setText(game.font, "Press anywhere to play")
        game.font.draw(game.batch, layout, SCREEN_WIDTH / 2 - layout.width / 2, SCREEN_HEIGHT / 3 - layout.height / 2)

        game.batch.end()
    }

    override fun dispose() {

    }

    override fun resize(width: Int, height: Int) {}
    override fun pause() {}
    override fun resume() {}
    override fun show() {}
    override fun hide() {}
}