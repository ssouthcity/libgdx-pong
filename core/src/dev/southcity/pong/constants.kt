package dev.southcity.pong

const val SCREEN_WIDTH: Float = 640f
const val SCREEN_HEIGHT: Float = SCREEN_WIDTH * 9 / 16

const val BALL_SIZE: Float = 16f
const val BALL_SPEED: Float = 4f
const val BALL_SPEED_MODIFIER: Float = 0.1f

const val PADDLE_WIDTH: Float = BALL_SIZE
const val PADDLE_HEIGHT: Float = 4 * BALL_SIZE
const val PADDLE_MARGIN: Float = PADDLE_WIDTH
const val PADDLE_MOVE_THRESHOLD: Float = 10f

const val TOUCH_PADDLE_SPEED = 4f
const val BOT_PADDLE_SPEED = 4f

const val WINNING_SCORE: Int = 21

const val FRAME_RATE: Float = 1 / 60f
const val PPM: Float = 64f