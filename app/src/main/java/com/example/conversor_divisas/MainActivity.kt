package com.example.conversor_divisas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.conversor_divisas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val divisas = listOf<String>("usd", "eur","clp")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.selectFrom.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, divisas)
        binding.selectTo.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, divisas)

        initListeners()
        setContentView(binding.root)
    }

    private fun initListeners() {
        binding.btnConvert.setOnClickListener{
            val amount = binding.etInput.text.toString().toDoubleOrNull()
            val toCurrency = binding.selectTo.selectedItem.toString().toLowerCase()
            val fromCurrency = binding.selectFrom.selectedItem.toString().toLowerCase()

            if (amount != null) {
                val result = convertCurrency(fromCurrency, toCurrency, amount)
                binding.tvResult.text = result.toString()
            }
        }

        binding.resetBtn.setOnClickListener{
            resetItems()
        }
    }

    fun convertCurrency(fromCurrency: String, toCurrency: String, amount: Double): String {

        val symbols = mapOf(
            "clp" to "CLP\$",
            "usd" to "\$",
            "eur" to "€"
        )
        val rates = mapOf(
            Pair("clp", "usd") to 0.0013,
            Pair("clp", "eur") to 0.0011,
            Pair("usd", "clp") to 767.75,
            Pair("usd", "eur") to 0.85,
            Pair("eur", "clp") to 903.35,
            Pair("eur", "usd") to 1.18
        )

        val result = rates.getOrDefault(Pair(fromCurrency, toCurrency), 1.0) * amount
        val symbol = symbols.getOrDefault(toCurrency, "")
        return "$symbol${String.format("%.2f", result)}"
    }

    fun resetItems() {
        binding.selectTo.setSelection(0)
        binding.selectFrom.setSelection(0)
        binding.etInput.setText("")
        binding.tvResult.text = ""
    }

}