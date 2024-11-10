package com.CompanyName.calculator

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.CompanyName.calculator.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    lateinit var sharedPreferences: SharedPreferences
    var number : String? = null // any number pressed before?
    var lastPressed : String? = "0"

    // prevent to pressed more than one time.
    private var dotPressed : Boolean = false
    private var equalPressed : Boolean = false

    private var firstNumber : Double = 0.0
    private var lastNumber : Double = 0.0

    private val operations = mapOf( "addition" to "+",
                                    "subtraction" to "-",
                                    "multiplication" to "*",
                                    "division" to "/",
                                    "dot" to ".",
                                    "equal" to "=")

    private var operator : Boolean = false // if doesn't any number pressed before operator is false
    private var status : String? = null // if operator is true then status represent the operator
    private val myFormatter : DecimalFormat = DecimalFormat("######.######") // limits the numbers can be pressed

    var history : String = ""
    var currentResult : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
//        enableEdgeToEdge()
        setContentView(view)
//        setContentView(R.layout.activity_chainge_theme)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        mainBinding.textViewResult.text = lastPressed
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
            val operatorName = "addition"
//            onNumberClicked("+")
            history = mainBinding.textViewHistory.text.toString()
            currentResult = mainBinding.textViewResult.text.toString()
//            mainBinding.textViewHistory.text = history.plus(currentResult).plus("+")
            mainBinding.textViewHistory.text = "$history $currentResult ${operations[operatorName]}"
            operatorExtraction(operatorName)

            status = operatorName
            number = null
        }
        mainBinding.btnMinus.setOnClickListener {
            val operatorName = "subtraction"
//            onNumberClicked("-")
            history = mainBinding.textViewHistory.text.toString()
            currentResult = mainBinding.textViewResult.text.toString()
//            mainBinding.textViewHistory.text = history.plus(currentResult).plus("-")
            mainBinding.textViewHistory.text = "$history $currentResult ${operations[operatorName]}"
            operatorExtraction(operatorName)

            status = operatorName
            number = null
        }
        mainBinding.btnMulti.setOnClickListener {
            val operatorName = "multiplication"
//            onNumberClicked("*")
            history = mainBinding.textViewHistory.text.toString()
            currentResult = mainBinding.textViewResult.text.toString()
//            mainBinding.textViewHistory.text = history.plus(currentResult).plus("*")
            mainBinding.textViewHistory.text = "$history $currentResult ${operations[operatorName]}"
            operatorExtraction(operatorName)

            status = operatorName
            number = null
        }
        mainBinding.btnDivide.setOnClickListener {
            val operatorName = "division"
//            onNumberClicked("/")
            history = mainBinding.textViewHistory.text.toString()
            currentResult = mainBinding.textViewResult.text.toString()
//            mainBinding.textViewHistory.text = history.plus(currentResult).plus("/")

            mainBinding.textViewHistory.text = "$history $currentResult ${operations[operatorName]}"
            operatorExtraction(operatorName)

            status = operatorName
            number = null
        }
        mainBinding.btnEqual.setOnClickListener {
            if (status == null)
                return@setOnClickListener

            val operatorName = "equal"

            if (!equalPressed){
//            onNumberClicked("=")
                history = mainBinding.textViewHistory.text.toString()
                lastPressed = mainBinding.textViewResult.text.toString()
                operatorExtraction(operatorName)
                currentResult = mainBinding.textViewResult.text.toString()
//            mainBinding.textViewHistory.text = history.plus(currentResult).plus(operations[operatorName]).plus(currentResult)
                mainBinding.textViewHistory.text = "$history $lastPressed ${operations[operatorName]} $currentResult"
            }

            equalPressed = true
        }
        mainBinding.btnDot.setOnClickListener {
            var dotPressed = mainBinding.textViewResult.text.toString().contains(".")
            if (!dotPressed) {
                number = if (number == null) {
                    "0."
                }
                else if (equalPressed){
                    if (dotPressed) {
                        mainBinding.textViewResult.text.toString()
                    } else {
                        mainBinding.textViewResult.text.toString().plus(".")
                    }
                } else {
                    "${mainBinding.textViewResult.text}."
                }

                mainBinding.textViewResult.text = number
            }
        }
        mainBinding.btnDel.setOnClickListener {
            number?.let {
                if (it.length == 1)
                    onACClicked()
                else {
                    number = number?.dropLast(1)
//                    number = it.substring(0, it.length - 1)
                    mainBinding.textViewResult.text = number
                }
            }
        }
        mainBinding.btnAC.setOnClickListener {
            onACClicked()
        }

        // settings menu
        mainBinding.toolbar.setOnMenuItemClickListener { item ->

            when (item.itemId) {
                R.id.settings_item -> {
                    val intent = Intent(this@MainActivity, ChaingeThemeActivity::class.java)
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }
                else -> {
                    return@setOnMenuItemClickListener false
                }
            }

        }

    }

    override fun onResume() {
        super.onResume()
        sharedPreferences = this.getSharedPreferences("Dark Theme", MODE_PRIVATE)
        val isDarkTheme = sharedPreferences.getBoolean("switch", false)
        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onPause() {
        // saving the important global data
        super.onPause()
        sharedPreferences = this.getSharedPreferences("Calculations", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val result = mainBinding.textViewResult.text.toString()
        val history = mainBinding.textViewHistory.text.toString()
        val dotPressed = dotPressed
        val equalPressed = equalPressed
        val firstNumber = firstNumber.toString()
        val lastNumber = lastNumber.toString()
        val number = number
        val operator = operator
        val status = status
        val lastPressed = lastPressed

        editor.putString("result", result)
        editor.putString("history", history)
        editor.putBoolean("dotPressed", dotPressed)
        editor.putBoolean("equalPressed", equalPressed)
        editor.putString("firstNumber", firstNumber)
        editor.putString("lastNumber", lastNumber)
        editor.putString("number", number)
        editor.putBoolean("operator", operator)
        editor.putString("status", status)
        editor.putString("lastPressed", lastPressed)

        editor.apply()
    }

    override fun onStart() {
        // loading the important global data
        // you can create second "sharedPreferences2" variable to load
        // you can also load onResume Method
        super.onStart()
        sharedPreferences = this.getSharedPreferences("Calculations", MODE_PRIVATE)
        mainBinding.textViewResult.text = sharedPreferences.getString("result", "0")
        mainBinding.textViewHistory.text = sharedPreferences.getString("history", "")
        number = sharedPreferences.getString("number", null)
        dotPressed = sharedPreferences.getBoolean("dotPressed", false)
        equalPressed = sharedPreferences.getBoolean("equalPressed", false)
        firstNumber = sharedPreferences.getString("firstNumber", "0.0")!!.toDouble()
        lastNumber = sharedPreferences.getString("lastNumber", "0.0")!!.toDouble()
        operator = sharedPreferences.getBoolean("operator", false)
        status = sharedPreferences.getString("status", null)
        lastPressed = sharedPreferences.getString("lastPressed", "0")

    }

    private fun operatorExtraction(operatorName:String?) {
        if (operator) {
            when (status) {
                "+", "addition" -> plus()
                "-", "subtraction" -> minus()
                "*", "multiplication" -> multiply()
                "/", "division" -> divide()
                else -> {
                    firstNumber = mainBinding.textViewResult.text.toString().toDouble()
                }
            }
        }

        operator = false // it is false because we pressed an operator and waiting for second number

        equalPressed = false
    }

    fun onNumberClicked(clickedNumber: String) {
        if (number == null){
            number = clickedNumber
        } else if(equalPressed){
            number = if (!dotPressed) {
                clickedNumber
            } else {
                mainBinding.textViewResult.text.toString().plus(clickedNumber)
            }

            firstNumber = number!!.toDouble()
            lastNumber = 0.0
            status = null
            mainBinding.textViewHistory.text = ""
        } else{
            number += clickedNumber
        }
        mainBinding.textViewResult.text = number

        operator = true
        equalPressed = false
    }
    fun onACClicked(){
        mainBinding.textViewResult.text = "0"
        mainBinding.textViewHistory.text = ""
        firstNumber = 0.0
        lastNumber = 0.0
        number = null
        status = null
        operator = false
        dotPressed = false
        equalPressed = false
    }
    fun plus(){
        lastNumber = mainBinding.textViewResult.text.toString().toDouble()
        firstNumber += lastNumber
//        mainBinding.textViewResult.text = firstNumber.toString()
        mainBinding.textViewResult.text = myFormatter.format(firstNumber)
    }
    fun minus(){
        lastNumber = mainBinding.textViewResult.text.toString().toDouble()
        firstNumber -= lastNumber
//        mainBinding.textViewResult.text = firstNumber.toString()
        mainBinding.textViewResult.text = myFormatter.format(firstNumber)
    }
    fun multiply(){
        lastNumber = mainBinding.textViewResult.text.toString().toDouble()
        firstNumber *= lastNumber
//        mainBinding.textViewResult.text = firstNumber.toString()
        mainBinding.textViewResult.text = myFormatter.format(firstNumber)
    }
    fun divide(){
        lastNumber = mainBinding.textViewResult.text.toString().toDouble()
        if (lastNumber == 0.0){
            Toast.makeText(applicationContext, "The divisor cannot be zero", Toast.LENGTH_LONG).show()
//            mainBinding.textViewResult.text = "Error"
            return
        }
        firstNumber /= lastNumber
//        mainBinding.textViewResult.text = firstNumber.toString()
        mainBinding.textViewResult.text = myFormatter.format(firstNumber)
    }
}