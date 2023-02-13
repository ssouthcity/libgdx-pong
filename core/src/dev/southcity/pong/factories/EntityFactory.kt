package dev.southcity.pong.factories

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.World

abstract class EntityFactory(
    protected val engine: Engine,
    protected val world: World,
) {
    abstract fun createBall(): Entity
    abstract fun disposeBall(ball: Entity)
    abstract fun createPlayerPaddle(): Entity
    abstract fun createOpponentPaddle(ballBody: Body): Entity
}