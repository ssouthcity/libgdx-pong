package dev.southcity.pong.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Vector2
import dev.southcity.pong.PPM
import dev.southcity.pong.PADDLE_SPEED
import dev.southcity.pong.components.BodyComponent
import dev.southcity.pong.components.TargetComponent

class PaddleMovementSystem : IteratingSystem(Family.all(
    BodyComponent::class.java,
    TargetComponent::class.java,
).get()) {

    private val bodyMapper = ComponentMapper.getFor(BodyComponent::class.java)
    private val targetMapper = ComponentMapper.getFor(TargetComponent::class.java)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val body = bodyMapper.get(entity)
        val target = targetMapper.get(entity)

        val direction = target.finder.directionalVectorFrom(body.body.position.y * PPM)

        body.body.linearVelocity = Vector2(0f, direction).setLength(PADDLE_SPEED)
    }
}