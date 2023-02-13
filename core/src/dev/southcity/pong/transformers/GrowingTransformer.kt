package dev.southcity.pong.transformers

class GrowingTransformer(val growingFactor: Float = 0.05f) : Transformer {
    override val expired = false

    private var timeAlive = 0f

    override fun modify(delta: Float, factor: Float): Float {
        timeAlive += delta

        return factor + (timeAlive * growingFactor)
    }
}