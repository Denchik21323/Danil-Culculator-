package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var NumberOr = false
    var checkingError = false
    var lastDot = false
    private lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun  onAllclearClick(view:View){
        binding.input.text=""
        binding.output.text=""
        checkingError=false
        lastDot=false
        NumberOr=false
        binding.output.visibility=View.GONE

    }

    fun onEqualClick(view:View){
        onEqual()
        binding.output.text=binding.output.text.toString().drop(1)

    }

    fun onDigitClick(view: View){
        if(checkingError){
            binding.input.text=(view as Button).text
            checkingError= false
        }else{

            binding.input.append((view as Button).text)

        }
        NumberOr=true
        onEqual()
    }

    fun onOperatorClick(view: View){

        if (!checkingError &&  NumberOr){
            binding.input.append((view as Button).text)
            lastDot=false
            NumberOr= false
            onEqual()


        }
    }

    fun onBackClick(view: View){
        binding.input.text=binding.input.text.toString().dropLast(1)

        try {
            var lastChar=binding.input.toString().last()

            if(lastChar.isDigit()){
                onEqual()
            }

        }catch (e:java.lang.Exception){

            binding.output.text=""
            binding.output.visibility=View.GONE
            Log.e("last char error",e.toString())

        }

    }

    fun onClearClick(view: View){
        binding.input.text=""
        NumberOr=false

    }



    fun onEqual(){
        if( NumberOr && !checkingError){
            var txt = binding.input.text.toString()
            expression=ExpressionBuilder(txt).build()

            try {
                var result = expression.evaluate()
                binding.output.visibility=View.VISIBLE
                binding.output.text="="+result.toString()

            }catch (ex:java.lang.ArithmeticException){

                Log.e("evaluate error", ex.toString())
                binding.output.text="Ошибка!"
                checkingError= true
                NumberOr= false


            }
        }
    }



}