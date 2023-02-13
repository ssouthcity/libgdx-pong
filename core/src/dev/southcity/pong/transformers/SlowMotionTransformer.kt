package dev.southcity.pong.transformers

class SlowMotionTransformer(var duration: Float = 3f) : Transformer {
    override val expired
        get() = this.duration <= 0f

    override fun modify(delta: Float, factor: Float): Float {
        duration -= delta

        duration = duration.coerceAtLeast(0f)

        return factor * (1 / (1 + duration))
    }
}