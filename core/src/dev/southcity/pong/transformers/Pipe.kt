package dev.southcity.pong.transformers

class Pipe {
    private val transformers = mutableListOf<Transformer>()

    fun <T: Transformer> addTransformer(transformer: T) {
        transformers.add(transformer)
    }

    fun removeExpiredTransformers() {
        transformers.removeAll { it.expired }
    }

    fun modify(delta: Float, factor: Float): Float {
        var x = factor

        for (transformer in transformers) {
            x = transformer.modify(delta, factor)
        }

        return x
    }
}