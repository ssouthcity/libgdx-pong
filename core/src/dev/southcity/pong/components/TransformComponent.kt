package dev.southcity.pong.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2

class TransformComponent(
    val position: Vector2 = Vector2.Zero.cpy(),
) : Component {
}