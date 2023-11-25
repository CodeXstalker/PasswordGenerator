package com.zenspace.passwordgenerator

import android.animation.ObjectAnimator
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.nulabinc.zxcvbn.Zxcvbn
import com.zenspace.passwordgenerator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var finalCombination: String
    private lateinit var generatedPassword: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val weakColor = ContextCompat.getColor(this, R.color.white)
        binding.cardView.setCardBackgroundColor(ColorStateList.valueOf(weakColor))

        val imageView: ImageView = binding.refresh
        finalCombination = "qwertyuiopasdfghjklzxcvbnm"
        generatedPassword = ""

        binding.refresh.setOnClickListener {
            if (binding.password.text.length > 1) {
                rotateImage(imageView)
            }
            textResetAfterDealy()
        }

        setSliderStepSize()

        binding.passwordLength.addOnChangeListener { _, value, _ ->
            setTheLengthOfPasswordAndGenerate(value)
            Log.i("8826", value.toString())
        }

        checkDefaultCheckBox()

        binding.generatePassword.setOnClickListener {
            binding.password.text = generatePassword()
            finalCombination = "qwertyuiopasdfghjklzxcvbnm"

            val generatedPassword = binding.password.text.toString()
            when (checkPasswordStrength(generatedPassword)) {
                in 0..1 -> {
                    binding.indicator.text = "Weak"
                    val weakColor = ContextCompat.getColor(this, R.color.colorWeak)
                    binding.cardView.setCardBackgroundColor(ColorStateList.valueOf(weakColor))
                }
                in 2..3 -> {
                    binding.indicator.text = "Moderate"
                    val moderateColor = ContextCompat.getColor(this, R.color.colorModerate)
                    binding.cardView.setCardBackgroundColor(ColorStateList.valueOf(moderateColor))
                }
                4 -> {
                    binding.indicator.text = "Strong"
                    val strongColor = ContextCompat.getColor(this, R.color.colorStrong)
                    binding.cardView.setCardBackgroundColor(ColorStateList.valueOf(strongColor))
                }
            }

        }
        binding.copy.setOnClickListener {
            if (binding.password.text.length > 1) {
                copyToClipboard(binding.password.text.toString())
            }
        }

    }

    private fun textResetAfterDealy() {
        val handler = Handler(Looper.getMainLooper())
        val delayMillis = 1000
        handler.postDelayed({
            binding.password.text = ""
            val weakColor = ContextCompat.getColor(this, R.color.white)
            binding.cardView.setCardBackgroundColor(ColorStateList.valueOf(weakColor))
        }, delayMillis.toLong())
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


    private fun copyToClipboard(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Password", text)
        clipboard.setPrimaryClip(clip)

        Toast.makeText(this, "Password copied to clipboard", Toast.LENGTH_SHORT).show()
    }


    fun checkPasswordStrength(password: String): Int {
        val result = Zxcvbn().measure(password)
        return result.score
    }
}
