package dev.southcity.pong.targeting

import com.badlogic.gdx.physics.box2d.Body
import dev.southcity.pong.PPM

class BodyTargetFinder(val body: Body) : TargetFinder() {
    override fun getTarget(): Float {
        return body.position.y * PPM
    }
}