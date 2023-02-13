package dev.southcity.pong.targeting

abstract class TargetFinder {
    abstract fun getTarget(): Float?

    fun directionalVectorFrom(position: Float): Float {
        val target = getTarget() ?: return 0f

        return target.compareTo(position).toFloat()
    }
}