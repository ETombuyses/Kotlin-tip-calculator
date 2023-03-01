package com.example.tipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.tipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    // défini (grâce à l'option dans build.gradle : viewBinding = true)
    // qu'on crée une instance de classe pour la view activity_main
    // on l'utilise donc mnt comme une instance de class
    // plus simple que de devoir appeler getViewById pour chaque élément de la vue
    private lateinit var binding: ActivityMainBinding
    // lateinit -> sert à dire au code d'attendre l'instanciation de la class avant de l'utiliser
    // (un peu un await dans ce cas ci)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calculateTip() }
    }

    private fun calculateTip() {
        val costOfService: Double? = binding.costOfServiceText.text.toString().toDoubleOrNull()

        if (costOfService === null ||costOfService === 0.0) {
            displayTip(0.0)
            return
        }

        val percentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.2
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        var tip: Double = costOfService * percentage

        if (binding.roundUpSwitch.isChecked) {
            tip = ceil(tip)
        }

        displayTip(tip)
    }

    private fun displayTip(tip : Double) {

        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_result, formattedTip)
    }
}