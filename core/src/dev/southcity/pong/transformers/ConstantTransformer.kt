package dev.southcity.pong.transformers

class ConstantTransformer(val constant: Float) : Transformer {
    override val expired: Boolean
        get() = false

    override fun modify(delta: Float, factor: Float): Float {
        return factor * constant
    }
}