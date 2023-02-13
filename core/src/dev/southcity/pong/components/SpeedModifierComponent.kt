package dev.southcity.pong.components

import com.badlogic.ashley.core.Component
import dev.southcity.pong.transformers.Pipe

class SpeedModifierComponent(val pipe: Pipe = Pipe()) : Component {
}