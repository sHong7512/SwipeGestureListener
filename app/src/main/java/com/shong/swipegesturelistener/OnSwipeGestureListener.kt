package com.shong.swipegesturelistener

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

open class OnSwipeGestureListener(context: Context) : View.OnTouchListener {

    private val gestureDetector: GestureDetector

    companion object {
        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100
    }

    init {
        gestureDetector = GestureDetector(context, GestureListener())
    }

    var moveX = 0f
    var moveY = 0f
    var defaultX = 0f
    var defaultY = 0f
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        // 뷰가 살짝 따라오게 애니메이션 넣은부분
        // 없어도 무방함
        if(v != null){
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    defaultX = v.x
                    defaultY = v.y
                    moveX = v.x - event.rawX/5
                    moveY = v.y - event.rawY/5
                }

                MotionEvent.ACTION_MOVE -> {
                    v.animate()
                        .x(event.rawX/5 + moveX)
                        .y(event.rawY/5 + moveY)
                        .setDuration(0)
                        .start()
                }

                MotionEvent.ACTION_UP -> {
                    v.animate()
                        .x(defaultX)
                        .y(defaultY)
                        .setDuration(150L)
                        .start()
                }
            }
        }

        // 제스처 받아오는 부분
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

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            var result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight()
                        } else {
                            onSwipeLeft()
                        }
                        result = true
                    }
                } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom()
                    } else {
                        onSwipeTop()
                    }
                    result = true
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }

            return result
        }


    }

    open fun onSwipeRight() {}

    open fun onSwipeLeft() {}

    open fun onSwipeTop() {}

    open fun onSwipeBottom() {}
}