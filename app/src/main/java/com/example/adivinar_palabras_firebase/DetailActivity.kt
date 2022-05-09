package com.example.adivinar_palabras_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class DetailActivity : AppCompatActivity() {
    lateinit var ref : DatabaseReference
    lateinit var listaPalabras : MutableList<String>


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

        GlobalVariables.listaDePalabrasDB = mutableListOf()
        listaPalabras = mutableListOf()

        //buscarPalabras que coincidan con la categoría y que no
        //hubieran sido ya solucionadas por el usuario las que encuentre serán
        //para llenar la lista
        actualizarListaDePalabras()



        var numeroDePalabras : Int = GlobalVariables.listaDePalabrasDB.size
    }

    /**}
     * Actualiza la información en el view del número de palabras
     */
    private fun actualizarNumeroDePalabras() {
        val numPalabrasEditText : TextView = findViewById(R.id.word_count)

        numPalabrasEditText.setText("1 de " + listaPalabras.size)
    }

    /**
     * Busca las palabras según la categoría seleccionada
      */
    private fun actualizarListaDePalabras()  {
        ref = FirebaseDatabase.getInstance().getReference("diccionario")

        /**
         * video guía https://www.youtube.com/watch?v=GWaN3iHfnro
         */
        ref.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()){
                    listaPalabras.clear()
                    GlobalVariables.listaDePalabrasDB.clear()

                    //email del usuario logueaddo
                    //var email = "alopezorozcofirebase@gmail.com"
                    var email = GlobalVariables.email


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
                            GlobalVariables.listaDePalabrasDB.add(plb.child("palabra").getValue().toString())
                        }
                    }

                    //GlobalVariables.listaDePalabrasDB = listaPalabras.toCollection(mutableListOf())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }//fin del método buscarPalabras


}