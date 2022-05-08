package com.example.adivinar_palabras_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adivinar_palabras_firebase.databinding.ActivityCategoriaBinding

class CategoriaActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categoria)

        //inflate permite  añadir las vistas a los diseños.
        val binding = ActivityCategoriaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        recyclerView = binding.recyclerView

        //establecemos el tipo de layout para mostrar los botones.
        recyclerView.layoutManager = LinearLayoutManager(this)

        //llamamos al adaptador correspondiente para mostrar las categorías.
        recyclerView.adapter = CategoriaAdapter(this)
    }
}