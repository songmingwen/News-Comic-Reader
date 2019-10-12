package com.bubblepicker.physics

import com.bubblepicker.rendering.Item
import com.bubblepicker.sqr
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.World
import java.util.Random

object Engine {

    val selectedBodies: List<CircleBody>
        get() = bodies.filter { (it.increased&& !it.toBeDecreased) || it.toBeIncreased || it.isIncreasing }

    var maxSelectedCount: Int? = null

    const val DEFAULT_RADIUS = 25
    const val TOTAL_AMOUNT = 100
    var radius = DEFAULT_RADIUS
        set(value) {
            field = value
            bubbleRadius = interpolate(0.1f, 0.25f, value / TOTAL_AMOUNT.toFloat())
            gravity = interpolate(20f, 80f, value / TOTAL_AMOUNT.toFloat())
            standardIncreasedGravity = interpolate(500f, 800f, value / TOTAL_AMOUNT.toFloat())
        }

    var centerImmediately = false

    private var bubbleRadius = interpolate(0.1f, 0.25f, DEFAULT_RADIUS / TOTAL_AMOUNT.toFloat())
    private var gravity = interpolate(20f, 80f, DEFAULT_RADIUS / TOTAL_AMOUNT.toFloat())
    private var standardIncreasedGravity = interpolate(500f, 800f, DEFAULT_RADIUS / TOTAL_AMOUNT.toFloat())

    private val world = World(Vec2(0f, 0f), false)
    private const val STEP = 0.0005f
    private const val RESIZE_STEP = 0.005f

    private val bodies: ArrayList<CircleBody> = ArrayList()
    private var borders: ArrayList<Border> = ArrayList()

    private var scaleX = 0f
    private var scaleY = 0f

    private var touch = false

    private var increasedGravity = 55f
    private var gravityCenter = Vec2(0f, 0f)
    private val currentGravity: Float
        get() = if (touch) increasedGravity else gravity

    private val toBeResized = ArrayList<Item>()
    /**
     * 气泡开始时的位置，值越大越远离圆心
     */
    private val startX
        get() = if (centerImmediately) 0.5f else 1.5f
    private var stepsCount = 0

    private const val DENSITY_START_VALUE = 0.8f
    private const val DENSITY_END_VALUE = 0.2f

    fun build(bodiesCount: Int, scaleX: Float, scaleY: Float): List<CircleBody> {
        val density = interpolate(DENSITY_START_VALUE, DENSITY_END_VALUE, radius / TOTAL_AMOUNT.toFloat())
        for (i in 0 until bodiesCount) {
            val x = if (Random().nextBoolean()) -startX else startX
            val y = if (Random().nextBoolean()) -0.5f / scaleY else 0.5f / scaleY
            bodies.add(CircleBody(world, Vec2(x, y), bubbleRadius * scaleX, (bubbleRadius * scaleX) * 1.3f, density))
        }
        Engine.scaleX = scaleX
        Engine.scaleY = scaleY
        createBorders()

        return bodies
    }

    fun move() {
        toBeResized.forEach { it.circleBody.resize(RESIZE_STEP) }
        world.step(if (centerImmediately) 0.035f else STEP, 11, 11)
        bodies.forEach { move(it) }
        toBeResized.removeAll(toBeResized.filter { it.circleBody.finished })
        stepsCount++
        if (stepsCount >= 10) {
            centerImmediately = false
        }
    }

    fun swipe(x: Float, y: Float) {
        if (Math.abs(gravityCenter.x) < 2) gravityCenter.x += -x
        if (Math.abs(gravityCenter.y) < 0.5f / scaleY) gravityCenter.y += y
        increasedGravity = standardIncreasedGravity * Math.abs(x * 13) * Math.abs(y * 13)
        touch = true
    }

    fun release() {
        gravityCenter.setZero()
        touch = false
        increasedGravity = standardIncreasedGravity
    }

    fun clear() {
        borders.forEach { world.destroyBody(it.itemBody) }
        bodies.forEach { world.destroyBody(it.physicalBody) }
        borders.clear()
        bodies.clear()
    }

    fun resize(item: Item): Boolean {
        if (selectedBodies.size >= maxSelectedCount ?: bodies.size && !item.circleBody.increased) return false

        if (item.circleBody.isBusy) return false

        item.circleBody.defineState()

        toBeResized.add(item)

        return true
    }

    private fun createBorders() {
        borders = arrayListOf(
                Border(world, Vec2(0f, 0.5f / scaleY), Border.HORIZONTAL),
                Border(world, Vec2(0f, -0.5f / scaleY), Border.HORIZONTAL)
        )
    }

    private fun move(body: CircleBody) {
        body.physicalBody.apply {
            body.isVisible = centerImmediately.not()
            val direction = gravityCenter.sub(position)
            val distance = direction.length()
            val gravity = if (body.increased) 1.3f * currentGravity else currentGravity
            if (distance > STEP * 200) {
                applyForce(direction.mul(gravity / distance.sqr()), position)
            }
        }
    }

    private fun interpolate(start: Float, end: Float, f: Float) = start + f * (end - start)

}