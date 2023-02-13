package dev.southcity.pong.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.physics.box2d.*
import dev.southcity.pong.*
import dev.southcity.pong.components.BallComponent
import dev.southcity.pong.factories.EntityFactory

class ScoreboardSystem(
    private val batch: Batch,
    private val font: BitmapFont,
    private val entityFactory: EntityFactory,
) : EntitySystem(), ContactListener {

    private var respawnBall = false

    override fun update(deltaTime: Float) {
        val layout = GlyphLayout(font, "")

        batch.projectionMatrix = Cameras.Game.combined
        font.data.setScale(2f)
        font.color = Color.WHITE

        batch.begin()

        layout.setText(font, Scoreboard.readScoreFor(Player.Left).toString())
        font.draw(batch, layout, SCREEN_WIDTH / 4 - layout.width / 2, SCREEN_HEIGHT / 2 + layout.height / 2)

        layout.setText(font, Scoreboard.readScoreFor(Player.Right).toString())
        font.draw(batch, layout, SCREEN_WIDTH / 4 * 3 - layout.width / 2, SCREEN_HEIGHT / 2 + layout.height / 2)

        batch.end()

        if (respawnBall) {
            val balls = engine.getEntitiesFor(Family.all(BallComponent::class.java).get())
            for (ball in balls) {
                entityFactory.disposeBall(ball)
            }
            engine.addEntity(entityFactory.createBall())
            respawnBall = false
        }
    }

    override fun beginContact(contact: Contact) {
        val bodyA = contact.fixtureA.body
        val bodyB = contact.fixtureB.body

        val wall = if (bodyA.userData is Player) {
            bodyA.userData as Player
        } else if (bodyB.userData is Player) {
            bodyB.userData as Player
        } else {
            return
        }

        Scoreboard.score(wall)
        respawnBall = true
    }

    override fun endContact(contact: Contact?) {}
    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {}
    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {}
}