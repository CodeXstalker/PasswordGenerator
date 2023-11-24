package com.zenspace.passwordgenerator

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.zenspace.passwordgenerator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var finalCombination: String
    private lateinit var generatedPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageView: ImageView = binding.refresh
        finalCombination = "qwertyuiopasdfghjklzxcvbnm"
        generatedPassword = ""

        binding.refresh.setOnClickListener {
            rotateImage(imageView)
        }

        setSliderStepSize()

        binding.passwordLength.addOnChangeListener { _, value, _ ->
            setTheLengthOfPasswordAndGenerate(value)
        }

        checkDefaultCheckBox()

        binding.generatePassword.setOnClickListener {
            binding.password.text = generatePassword()
        }
    }

    private fun generatePassword(): String {
        getDataFromCheckBox()
        Log.i("8825", finalCombination)
        return generateRandomPassword(finalCombination, binding.passwordLength.value.toInt())
    }

    private fun generateRandomPassword(finalCombination: String, length: Int): String {
        val password = StringBuilder()
        val random = java.util.Random()

        repeat(length) {
            val index = random.nextInt(finalCombination.length)
            password.append(finalCombination[index])
        }

        return password.toString()
    }

    private fun checkDefaultCheckBox() {
        binding.includeLowerCase.isChecked = true
        binding.includeLowerCase.isClickable = false
    }

    private fun setTheLengthOfPasswordAndGenerate(value: Float) {
        binding.lengthIndicator.setTextColor(Color.BLACK)
        binding.lengthIndicator.text = "Password Length = ${value.toInt()}"
        getDataFromCheckBox()
    }

    private fun getDataFromCheckBox() {
        if (binding.includeNumbers.isChecked) {
            finalCombination += "1234567890"
        }

        if (binding.includeUpperCase.isChecked) {
            finalCombination += "QWERTYUIOPASDFGHJKLZXCVBNM"
        }

        if (binding.includeSpecialCharacters.isChecked) {
            finalCombination += "!@#$%^&*()_+~`=-,./\\|][}{'\"?><"
        }
    }

    private fun setSliderStepSize() {
        binding.passwordLength.stepSize = 1.0f
    }

    private fun rotateImage(imageView: ImageView) {
        val rotateAnimator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f)
        rotateAnimator.duration = 1000
        rotateAnimator.start()
    }
}
