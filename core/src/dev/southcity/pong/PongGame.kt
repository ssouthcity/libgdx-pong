package dev.southcity.pong

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import dev.southcity.pong.screens.IntermediaryScreen

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