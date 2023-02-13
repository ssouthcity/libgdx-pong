package dev.southcity.pong.targeting

import dev.southcity.pong.PADDLE_MOVE_THRESHOLD
import kotlin.math.absoluteValue

abstract class TargetFinder {
    abstract fun getTarget(): Float?

    fun directionalVectorFrom(position: Float): Float {
        val target = getTarget() ?: return 0f

        if ((target - position).absoluteValue < PADDLE_MOVE_THRESHOLD) {
            return 0f
        }

        return target.compareTo(position).toFloat()
    }
}