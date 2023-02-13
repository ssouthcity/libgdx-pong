package dev.southcity.pong.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.physics.box2d.World
import dev.southcity.pong.FRAME_RATE
import dev.southcity.pong.PPM
import dev.southcity.pong.components.BodyComponent
import dev.southcity.pong.components.SpriteComponent

class PhysicsSystem(
    private val world: World
) : IteratingSystem(Family.all(
    BodyComponent::class.java,
    SpriteComponent::class.java,
).get()) {

    private val bodyMapper = ComponentMapper.getFor(BodyComponent::class.java)
    private val transformMapper = ComponentMapper.getFor(SpriteComponent::class.java)

    private var accumulator = 0f

    override fun update(deltaTime: Float) {
        accumulator += deltaTime

        while (accumulator >= FRAME_RATE) {
            world.step(FRAME_RATE, 6, 2)
            accumulator -= FRAME_RATE
        }

        super.update(deltaTime) // calls processEntity with each entity
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val body = bodyMapper.get(entity)
        val transform = transformMapper.get(entity)

        transform.position.x = body.body.position.x * PPM
        transform.position.y = body.body.position.y * PPM
    }
}