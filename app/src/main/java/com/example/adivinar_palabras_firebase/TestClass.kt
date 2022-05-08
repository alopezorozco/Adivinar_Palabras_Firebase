package com.example.adivinar_palabras_firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.HashMap

class TestClass {
    lateinit var listaPalabras:MutableList<String>
    lateinit var ref : DatabaseReference


    fun Convert() {
        ref = FirebaseDatabase.getInstance().getReference("diccionario")
        val userMap = HashMap<String, String>()

    }
}