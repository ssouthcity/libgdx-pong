package dev.southcity.pong

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.EdgeShape
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.ScreenUtils

const val SCREEN_WIDTH: Float = 640f
const val SCREEN_HEIGHT: Float = SCREEN_WIDTH * 9 / 16

class Pong : ApplicationAdapter() {
    lateinit var shapeRenderer: ShapeRenderer
    lateinit var camera: OrthographicCamera
    lateinit var playerPaddle: PlayerPaddle
    lateinit var botPaddle: BotPaddle
    lateinit var ball: Ball
    lateinit var world: World
    lateinit var edges: Body
    lateinit var debugRenderer: Box2DDebugRenderer

    override fun create() {
        shapeRenderer = ShapeRenderer()
        camera = OrthographicCamera()
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT)
        world = World(Vector2.Zero, true)
        debugRenderer = Box2DDebugRenderer()
        ball = Ball(world, SCREEN_WIDTH / 2f, SCREEN_HEIGHT / 2f)
        playerPaddle = PlayerPaddle(world, 32f, SCREEN_HEIGHT / 2f, camera)
        botPaddle = BotPaddle(world, SCREEN_WIDTH - 32f, SCREEN_HEIGHT / 2f, camera, ball)

        val edgeBodyDef = BodyDef()
        edgeBodyDef.type = BodyDef.BodyType.StaticBody
        edgeBodyDef.position.set(0f, 0f)

        edges =  world.createBody(edgeBodyDef)

        val edge = EdgeShape()
        edge.set(0f, 0f, SCREEN_WIDTH, 0f)
        edges.createFixture(edge, 0f)
        edge.set(0f, SCREEN_HEIGHT, SCREEN_WIDTH, SCREEN_HEIGHT)
        edges.createFixture(edge, 0f)
        edge.dispose()
    }

    override fun render() {
        playerPaddle.update()
        botPaddle.update()
        world.step(1f / 60f, 6, 2)

        shapeRenderer.projectionMatrix = camera.combined

        ScreenUtils.clear(0f, 0f, 0f, 1f)
        debugRenderer.render(world, camera.combined)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        //playerPaddle.draw(shapeRenderer)
        //ball.draw(shapeRenderer)
        shapeRenderer.end()
    }

    override fun dispose() {
        shapeRenderer.dispose()
    }
}