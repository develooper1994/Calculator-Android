package com.CompanyName.calculator

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.CompanyName.calculator.databinding.ActivityChaingeThemeBinding

class ChaingeThemeActivity : AppCompatActivity() {

    lateinit var switchBinding : ActivityChaingeThemeBinding
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        switchBinding = ActivityChaingeThemeBinding.inflate(layoutInflater)
        val view = switchBinding.root
//        enableEdgeToEdge()
        setContentView(view)
//        setContentView(R.layout.activity_chainge_theme)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        switchBinding.toolbar2.setNavigationOnClickListener {
            finish()
        }

        switchBinding.mySwitch.setOnCheckedChangeListener { buttonView, isChecked ->

            sharedPreferences = this.getSharedPreferences("Dark Theme", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("switch", isChecked)

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            editor.apply()
        }
    }

    override fun onResume() {
        super.onResume()
        sharedPreferences = this.getSharedPreferences("Dark Theme", Context.MODE_PRIVATE)
        switchBinding.mySwitch.isChecked = sharedPreferences.getBoolean("switch", false)
    }
}