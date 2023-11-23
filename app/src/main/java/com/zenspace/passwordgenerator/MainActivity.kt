package com.zenspace.passwordgenerator

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.zenspace.passwordgenerator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val imageView: ImageView = binding.refresh

        binding.refresh.setOnClickListener {
            rotateImage(imageView)
        }

        setSliderStepsize()

        binding.passwordLength.addOnChangeListener { _, value, _ ->
            setTheLengthOfPassword(value)
        }

    }

    private fun setTheLengthOfPassword(value: Float) {
        binding.lengthIndicator.setTextColor(Color.BLACK)
        binding.lengthIndicator.text = "Password Length = " + value.toInt().toString()
    }

    private fun setSliderStepsize() {
        binding.passwordLength.stepSize = 1.0f
    }

    private fun rotateImage(imageView: ImageView) {
        val rotateAnimator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f)
        rotateAnimator.duration= 1000
        rotateAnimator.start()
    }
}