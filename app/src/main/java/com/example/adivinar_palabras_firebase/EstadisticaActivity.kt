package com.example.adivinar_palabras_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.example.adivinar_palabras_firebase.databinding.ActivityEstadisticaBinding

class EstadisticaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estadistica)
        lateinit var auth: FirebaseAuth

        auth = FirebaseAuth.getInstance()

        val adivinarPalabrasButton: Button = findViewById(R.id.adivinar_palabras_btn)

        adivinarPalabrasButton.setOnClickListener { abrirCategorias() }


    }

    private fun abrirCategorias() {
        val intent = Intent(this, CategoriaActivity::class.java)
        startActivity(intent)
    }

    public override fun onStart(){
        super.onStart()
    }
}