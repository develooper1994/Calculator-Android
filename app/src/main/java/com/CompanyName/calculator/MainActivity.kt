package com.CompanyName.calculator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.CompanyName.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    var number : String? = null

    var firstNumber : Double = 0.0
    var lastNumber : Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
//        enableEdgeToEdge()
        setContentView(view)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        mainBinding.textViewResult.text = "0"
        mainBinding.btnZero.setOnClickListener {
            onNumberClicked("0")
        }
        mainBinding.btnOne.setOnClickListener {
            onNumberClicked("1")
        }
        mainBinding.btnTwo.setOnClickListener {
            onNumberClicked("2")
        }
        mainBinding.btnThree.setOnClickListener {
            onNumberClicked("3")
        }
        mainBinding.btnFour.setOnClickListener {
            onNumberClicked("4")
        }
        mainBinding.btnFive.setOnClickListener {
            onNumberClicked("5")
        }
        mainBinding.btnSix.setOnClickListener {
            onNumberClicked("6")
        }
        mainBinding.btnSeven.setOnClickListener {
            onNumberClicked("7")
        }
        mainBinding.btnEight.setOnClickListener {
            onNumberClicked("8")
        }
        mainBinding.btnNine.setOnClickListener {
            onNumberClicked("9")
        }
        mainBinding.btnPlus.setOnClickListener {
            onNumberClicked("+")
        }
        mainBinding.btnMinus.setOnClickListener {
            onNumberClicked("-")
        }
        mainBinding.btnMulti.setOnClickListener {
            onNumberClicked("*")
        }
        mainBinding.btnDivide.setOnClickListener {
            onNumberClicked("/")
        }
        mainBinding.btnEqual.setOnClickListener {
            onNumberClicked("=")
        }
        mainBinding.btnDel.setOnClickListener {
            onNumberClicked("C")
        }
        mainBinding.btnDot.setOnClickListener {
            onNumberClicked(".")
        }
        mainBinding.btnAC.setOnClickListener {
            onNumberClicked("")
        }
    }

    fun onNumberClicked(clickedNumber: String) {
        if (number == null){
            number = clickedNumber
        } else{
            number += clickedNumber
        }
        if (clickedNumber.isEmpty()){
            number = ""
        }

        mainBinding.textViewResult.text = number
    }
    fun plus(){
        lastNumber = mainBinding.textViewResult.text.toString().toDouble()
        firstNumber += lastNumber
        mainBinding.textViewResult.text = firstNumber.toString()
    }
    fun minus(){
        lastNumber = mainBinding.textViewResult.text.toString().toDouble()
        firstNumber -= lastNumber
        mainBinding.textViewResult.text = firstNumber.toString()
    }
    fun multiply(){
        lastNumber = mainBinding.textViewResult.text.toString().toDouble()
        firstNumber *= lastNumber
        mainBinding.textViewResult.text = firstNumber.toString()
    }
    fun divide(){
        lastNumber = mainBinding.textViewResult.text.toString().toDouble()
        if (lastNumber == 0.0){
            Toast.makeText(applicationContext, "The divisor cannot be zero", Toast.LENGTH_LONG).show()
            mainBinding.textViewResult.text = "Error"
            return
        }
        firstNumber /= lastNumber
        mainBinding.textViewResult.text = firstNumber.toString()
    }
}