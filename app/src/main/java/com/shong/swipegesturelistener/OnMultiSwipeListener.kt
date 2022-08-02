package com.shong.swipegesturelistener

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

open class OnMultiSwipeListener(context: Context) : View.OnTouchListener {

    private val gestureDetector: GestureDetector

    companion object {
        private const val SLOW_MOVE = 3
        private const val SWIPE_THRESHOLD = 70 / SLOW_MOVE
        private const val SWIPE_INITHOLD = 20 / SLOW_MOVE
    }

    init {
        gestureDetector = GestureDetector(context, GestureListener())
    }

    var moveX = 0f
    var moveY = 0f
    var defaultX = 0f
    var defaultY = 0f
    var blockingX = false
    var blockingY = false
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (v != null) {
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    defaultX = v.x
                    defaultY = v.y
                    moveX = v.x - event.rawX / SLOW_MOVE
                    moveY = v.y - event.rawY / SLOW_MOVE
                }

                MotionEvent.ACTION_MOVE -> {
                    v.animate()
                        .x(event.rawX / SLOW_MOVE + moveX)
                        .y(event.rawY / SLOW_MOVE + moveY)
                        .setDuration(0)
                        .start()

                    val diffX = defaultX - (moveX + event.rawX / SLOW_MOVE)
                    Log.d("_sHong", "default $defaultX diff $diffX move $moveX event ${event.x}")
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && !blockingX) {
                        if (diffX > 0) onSwipeLeft()
                        else onSwipeRight()
                        blockingX = true
                    } else if (Math.abs(diffX) < SWIPE_INITHOLD) {
                        blockingX = false
                    }

                    val diffY = defaultY - (moveY + event.rawY / SLOW_MOVE)
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && !blockingY) {
                        if (diffY > 0) onSwipeTop()
                        else onSwipeBottom()
                        blockingY = true
                    } else if (Math.abs(diffY) < SWIPE_INITHOLD) {
                        blockingY = false
                    }
                }

                MotionEvent.ACTION_UP -> {
                    v.animate()
                        .x(defaultX)
                        .y(defaultY)
                        .setDuration(150L)
                        .start()
                    blockingX = false
                    blockingY = false
                }
            }
        }

        try {
            return gestureDetector.onTouchEvent(event)
        } catch (e: Exception) {
            // Error Handling
        }
        return false
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            return super.onFling(e1, e2, velocityX, velocityY)
        }
    }

    open fun onSwipeRight() {}

    open fun onSwipeLeft() {}

    open fun onSwipeTop() {}

    open fun onSwipeBottom() {}

}