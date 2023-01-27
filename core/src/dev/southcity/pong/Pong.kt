package dev.southcity.pong

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.utils.ScreenUtils

class Pong : ApplicationAdapter() {
    override fun create() {}

    override fun render() {
        ScreenUtils.clear(1f, 0f, 0f, 1f)
    }

    override fun dispose() {}
}