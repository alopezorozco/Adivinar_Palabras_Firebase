package com.example.adivinar_palabras_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*

class DetailActivity : AppCompatActivity() {
    lateinit var ref : DatabaseReference
    lateinit var listaPalabras:MutableList<String>

    //almacena de manera temporal el email del usuario
    lateinit var emailDB : String

    //almacena la categoria de la base de datos de firebase
    lateinit var categoriaDB : String

    //almacena la categoria de palabra seleccionada en la aplicación
    lateinit var categoria : String


    companion object{
        const val CATEGORIA = "categoria"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        categoria = intent?.extras?.getString(CATEGORIA).toString().uppercase()

        listaPalabras = mutableListOf()
        ref = FirebaseDatabase.getInstance().getReference("diccionario")

        /**
         * video guía https://www.youtube.com/watch?v=GWaN3iHfnro
         */
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               if (snapshot!!.exists()){
                   listaPalabras.clear()

                   //email del usuario logueaddo
                   var email = "alopezorozcofirebase@gmail.com"

                   //se recorre toda la base de datos para buscar las palabras por categoria y que no
                   //hubieran sido adivinadas por el usuario
                   for (plb in snapshot.children){

                       //se obtiene la categoria y el email por registro de la bd
                       categoriaDB = plb.child("categoria").getValue().toString().uppercase()
                       emailDB = plb.child("adivinadapor").getValue().toString()

                       //se comparan para saber si se agrega la palabra a la lista o no
                       if (!emailDB.contains(email)&&categoriaDB.equals(categoria))
                       {
                               listaPalabras.add(plb.child("palabra").getValue().toString())
                       }
                   }
               }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })




    }
}