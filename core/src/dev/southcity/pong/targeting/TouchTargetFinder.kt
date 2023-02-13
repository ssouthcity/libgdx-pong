package dev.southcity.pong.targeting

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector3
import dev.southcity.pong.Cameras

class TouchTargetFinder : TargetFinder() {
    override fun getTarget(): Float? {
        if (!Gdx.input.isTouched) {
            return null
        }

        val input = Cameras.Game.unproject(Vector3(0f, Gdx.input.y.toFloat(), 0f))

        return input.y
    }
}