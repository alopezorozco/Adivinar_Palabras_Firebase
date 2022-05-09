package com.example.adivinar_palabras_firebase

import android.app.Application

class GlobalVariables : Application () {
    companion object{
        lateinit var email : String
        lateinit var listaDePalabrasDB : MutableList<String>
    }
}