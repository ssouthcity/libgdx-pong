package dev.southcity.pong.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import dev.southcity.pong.BALL_SPEED
import dev.southcity.pong.components.BodyComponent
import dev.southcity.pong.components.SpeedModifierComponent

class SpeedTransformerSystem : IteratingSystem(Family.all(
    BodyComponent::class.java,
    SpeedModifierComponent::class.java,
).get()) {

    private val bodyMapper = ComponentMapper.getFor(BodyComponent::class.java)
    private val speedModifierMapper = ComponentMapper.getFor(SpeedModifierComponent::class.java)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val body = bodyMapper.get(entity)
        val speedModifier = speedModifierMapper.get(entity)

        speedModifier.removeExpiredTransformers()

        val factor = speedModifier.modify(deltaTime, 1f)
        body.body.linearVelocity = body.body.linearVelocity.setLength(BALL_SPEED * factor)
    }
}