package com.shong.swipegesturelistener

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.blockView).setOnTouchListener(object : OnSwipeGestureListener(this@MainActivity){
            override fun onSwipeLeft() {
                SingleToast.show_Short(this@MainActivity, "Left Swipe!")
            }
            override fun onSwipeRight() {
                SingleToast.show_Short(this@MainActivity, "Right Swipe!")
            }
            override fun onSwipeTop() {
                SingleToast.show_Short(this@MainActivity, "Up Swipe!")
            }
            override fun onSwipeBottom() {
                SingleToast.show_Short(this@MainActivity, "Bottom Swipe!")
            }
        })
    }

    class SingleToast{
        companion object{
            var toast: Toast? = null
            fun show_Short(context: Context, msg: String){
                toast?.cancel()
                toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT).apply { show() }
            }
        }
    }

}