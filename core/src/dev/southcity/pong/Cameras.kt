package dev.southcity.pong

import com.badlogic.gdx.graphics.OrthographicCamera

object Cameras {
    val Game = OrthographicCamera()
    val Box2DDebug = OrthographicCamera()

    init {
        Game.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT)
        Box2DDebug.setToOrtho(false, SCREEN_WIDTH / PPM, SCREEN_HEIGHT / PPM)
    }
}