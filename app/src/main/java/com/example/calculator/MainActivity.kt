package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    //Variables Flags de validación.
    var lastNumeric:Boolean=false
    var lastDot:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //Función para la captura de digitos.
    fun onDigit(view: View) {
        txt_view.append((view as Button).text)
        lastNumeric=true
    }

    //Función para el boton limpiar.
    fun clearBtn(view: View){
        txt_view.text="";
        lastNumeric=false
        lastDot=false
    }

    //Función para el punto.
    fun decimalPoint(view: View){
        if(lastNumeric&&!lastDot){
            txt_view.append(".")
            lastNumeric=false
            lastDot=true
        }
    }

    //Función de asignación de operador.
    fun onOperator(view: View){
        if(lastNumeric && !isOperatorAdded(txt_view.text.toString()))
            txt_view.append((view as Button).text)
            lastNumeric=false
            lastDot=false

    }

    //Función de validación de operadores añadidos.
    private fun isOperatorAdded(value:String):Boolean{
        return  if(value.startsWith("-")){
            false
        }else{
            value.contains("/")||value.contains("*") || value.contains("+")
                    || value.contains("-")
        }
    }

    //Función para la eliminación del .0 resultado de la operación natural (2-3=1.0)
    private fun removeZero(result: String):String{
        var value=result
        if(result.contains(".0")){
            value=result.substring(0,result.length-2)
        }
        return value
    }

    //Función de ejecución de las operaciones.
    fun onEqual(view: View){
        //Valida si la bandera de númerico esta activa.
        if(lastNumeric){
            //Captura el texto del TextView
            var txtValue=txt_view.text.toString();
            var prefix=""

            //Bloque manejador de errores para la ejecución de las operaciones.
            try{
                //Bloque de operación Resta
                if(txtValue.startsWith("-")){
                    prefix="-"
                    txtValue=txtValue.substring(1)
                }
                if(txtValue.contains("-")){
                    val splitValue=txtValue.split("-")
                    var one=splitValue[0]
                    val two=splitValue[1]

                    if(!prefix.isEmpty()){
                        one=prefix+one
                    }
                    //TextView de manejo de de visualización
                    txt_view.text=removeZero((one.toDouble()-two.toDouble()).toString())
                }
                //Bloque de operación Suma
                if(txtValue.contains("+")){
                    val splitValue=txtValue.split("+")
                    var one=splitValue[0]
                    val two=splitValue[1]

                    if(!prefix.isEmpty()){
                        one=prefix+one
                    }
                    //TextView de manejo de de visualización
                    txt_view.text=removeZero((one.toDouble()+two.toDouble()).toString())
                }

                //Bloque de operación Multiplicación
                if(txtValue.contains("*")){
                    val splitValue=txtValue.split("*")
                    var one=splitValue[0]
                    val two=splitValue[1]

                    if(!prefix.isEmpty()){
                        one=prefix+one
                    }
                    //TextView de manejo de de visualización
                    txt_view.text=removeZero((one.toDouble()*two.toDouble()).toString())
                }

                //Bloque de operación División
                if(txtValue.contains("/")){
                    var splitValue=txtValue.split("/")
                    var one=splitValue[0]
                    var two=splitValue[1]

                    if(!prefix.isEmpty()){
                        one=prefix+one
                    }
                    //TextView de manejo de de visualización
                    txt_view.text=if(two.toDouble()!=0.0) removeZero((one.toDouble()/two.toDouble()).toString()) else "AnsError"
                }
            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }
}