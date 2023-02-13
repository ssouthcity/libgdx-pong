package dev.southcity.pong.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import dev.southcity.pong.Cameras
import dev.southcity.pong.Goal
import dev.southcity.pong.SCREEN_HEIGHT
import dev.southcity.pong.SCREEN_WIDTH

class ScoreboardSystem(
    private val batch: Batch,
    private val font: BitmapFont,
) : EntitySystem(), ContactListener {
    private var scores = mutableMapOf(
        Goal.Player to 0,
        Goal.Opponent to 0,
    )

    override fun update(deltaTime: Float) {
        val layout = GlyphLayout(font, "")

        batch.projectionMatrix = Cameras.Game.combined

        batch.begin()
        font.data.setScale(2f)
        layout.setText(font, scores[Goal.Opponent].toString())
        font.draw(batch, layout, SCREEN_WIDTH / 4 - layout.width / 2, SCREEN_HEIGHT / 2 + layout.height / 2)
        layout.setText(font, scores[Goal.Player].toString())
        font.draw(batch, layout, SCREEN_WIDTH / 4 * 3 - layout.width / 2, SCREEN_HEIGHT / 2 + layout.height / 2)
        batch.end()
    }

    override fun beginContact(contact: Contact) {
        val bodyA = contact.fixtureA.body
        val bodyB = contact.fixtureB.body

        val wall = if (bodyA.userData is Goal) {
            bodyA.userData as Goal
        } else if (bodyB.userData is Goal) {
            bodyB.userData as Goal
        } else {
            return
        }

        scores[wall] = scores[wall]!!.inc()
    }

    override fun endContact(contact: Contact?) {}
    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {}
    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {}
}