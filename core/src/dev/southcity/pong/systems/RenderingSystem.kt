package dev.southcity.pong.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import dev.southcity.pong.components.SpriteComponent

class RenderingSystem(
    private val shapeRenderer: ShapeRenderer,
) : IteratingSystem(Family.all(
    SpriteComponent::class.java,
).get()) {

    private val spriteMapper = ComponentMapper.getFor(SpriteComponent::class.java)

    override fun update(deltaTime: Float) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        super.update(deltaTime)
        shapeRenderer.end()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val sprite = spriteMapper.get(entity)

        shapeRenderer.rect(
            sprite.position.x - sprite.size.x / 2,
            sprite.position.y - sprite.size.y / 2,
            sprite.size.x,
            sprite.size.y,
        )
    }
}