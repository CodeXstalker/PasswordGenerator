package com.zenspace.passwordgenerator

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView


class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.yourImageViewId)

        imageView.setOnClickListener {
            rotateImage(imageView)
        }

    }

    private fun rotateImage(imageView: ImageView) {
        val rotateAnimator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f)
        rotateAnimator.duration= 1000
        rotateAnimator.start()
    }
}