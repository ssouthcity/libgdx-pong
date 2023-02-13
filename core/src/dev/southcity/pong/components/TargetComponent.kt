package dev.southcity.pong.components

import com.badlogic.ashley.core.Component
import dev.southcity.pong.targeting.TargetFinder

class TargetComponent(val finder: TargetFinder) : Component {
}