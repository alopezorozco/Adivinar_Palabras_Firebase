package com.example.adivinar_palabras_firebase

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class DetailActivity : AppCompatActivity() {
    lateinit var ref : DatabaseReference
    lateinit var listaPalabras : MutableList<String>
    lateinit var email : String

    private val sharedPrefFile = "listaDePalabras"


    //almacena de manera temporal el email del usuario
    lateinit var emailDB : String

    //almacena la categoria de la base de datos de firebase
    lateinit var categoriaDB : String

    //almacena la categoria de palabra seleccionada en la aplicación
    lateinit var categoria : String

    lateinit var palabrasArrayList : ArrayList<String>

    lateinit var list : List<String>

    companion object{
        const val CATEGORIA = "categoria"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        categoria = intent?.extras?.getString(CATEGORIA).toString().uppercase()

        listaPalabras = mutableListOf()

        email = GlobalVariables.email

        ref = FirebaseDatabase.getInstance().getReference("diccionario")

        palabrasArrayList = ArrayList<String>(1)

        val numeroDeElementos = palabrasArrayList.size

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)



        /**
         * video guía https://www.youtube.com/watch?v=GWaN3iHfnro
         */
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()){
                    listaPalabras.clear()

                    //email del usuario logueaddo


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
                            palabrasArrayList.add(plb.child("palabra").getValue().toString())
                        }
                    }//fin del for
                }//fin del if
                savePalabrasArrayList(palabrasArrayList, sharedPreferences)
            }//fin del método onDataChange

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        val submitBoton : Button = findViewById(R.id.submit)

        submitBoton.setOnClickListener { obtenerPalabra(sharedPreferences) }

    }//fin del método oncreate

    /**
     * Obtiene la primer palabra de la lista
     */
    private fun obtenerPalabra(sharedPreferences: SharedPreferences) {
        val string = sharedPreferences.getString("set", "")

        try{
            list = Gson().fromJson(string,
                object: TypeToken<List<String>>() {}.type)
        }catch(e : Exception ){
            e.printStackTrace()
        }
        Toast.makeText(this, list.size.toString(), Toast.LENGTH_SHORT)
    }

    private fun savePalabrasArrayList(
        palabrasArrayList: java.util.ArrayList<String>,
        sharedPreferences: SharedPreferences
    ) {
        val gson = Gson().toJson(palabrasArrayList)
        sharedPreferences.edit().putString("set", Gson().toJson(palabrasArrayList)).apply()


        /*val editor:SharedPreferences.Editor = sharedPreferences.edit()
        for (palabra in palabrasArrayList){
            editor.putString(palabra, palabra)
        }
        editor.apply()
        editor.commit()*/
    }

    /*fun getPalabrasArrayList(sharedPreferences: SharedPreferences): String? {
        val data = sharedPreferences.getString("set", null)
        if (data == null){
            return null
        }
        return Gson().fromJson(data, String::class.java)
    }*/
}