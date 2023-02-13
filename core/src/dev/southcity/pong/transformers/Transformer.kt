package dev.southcity.pong.transformers

interface Transformer {
    val expired: Boolean
    fun modify(delta: Float, factor: Float): Float
}