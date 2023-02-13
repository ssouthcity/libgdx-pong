package dev.southcity.pong.components

import com.badlogic.ashley.core.Component
import dev.southcity.pong.transformers.Transformer

class SpeedModifierComponent : Component {
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