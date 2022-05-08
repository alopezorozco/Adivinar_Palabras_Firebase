package com.example.adivinar_palabras_firebase

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lateinit var auth: FirebaseAuth

        auth = Firebase.auth

        val loginButton: Button = findViewById(R.id.loginButton)

        loginButton.setOnClickListener { signInUser(auth) }

        /*var database = FirebaseDatabase.getInstance().reference.child("Users")

        val mName = "Norma Viramontes Herrera"
        val mDireccion = "Calle Juarez"
        val mTelefono = "4639533935"

        val userMap = HashMap<String, String>()

        userMap.put("name", mName)
        userMap.put("Direccion", mDireccion)
        userMap.put("email", mTelefono)

        database.push().setValue(userMap)*/

        /*val agregarBoton: Button = findViewById(R.id.btnAgregar)
        val modificarBoton: Button = findViewById(R.id.btnModificar)

        agregarBoton.setOnClickListener{
            agregarUsuario()
        }

        modificarBoton.setOnClickListener{modificarUsuario()}*/
    }

    private fun signInUser(auth: FirebaseAuth) {
        val email : EditText = findViewById(R.id.editEmail)
        val password : EditText = findViewById(R.id.editTextTextPassword)

        val emailString = email.text.toString()
        val passwordString = password.text.toString()


        auth.signInWithEmailAndPassword(emailString, passwordString)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    Toast.makeText(baseContext, "Authentication Success.",
                        Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser

                    val intent = Intent(this, EstadisticaActivity::class.java)
                    intent.putExtra("user", user)
                    startActivity(intent)

                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //aloupdateUI(null)
                }
            }
    }



    /*private fun modificarUsuario() {
        val editNombre: EditText = findViewById(R.id.editNombrePersona)
        val editApellido: EditText = findViewById(R.id.editApellidoPaterno)

        val nombre = editNombre.text.toString()
        val apellido = editApellido.text.toString()

        var database = FirebaseDatabase.getInstance()
            .reference.child("Users")

        database.child(nombre).child("Apellido").setValue(apellido)
    }

    private fun agregarUsuario() {
        val editNombre: EditText = findViewById(R.id.editNombrePersona)
        val editApellido: EditText = findViewById(R.id.editApellidoPaterno)

        val nombre = editNombre.text.toString()
        val apellido = editApellido.text.toString()
        val email = "alopezorozcouazedumx"

        val user = User(nombre, apellido)

        var database = FirebaseDatabase.getInstance().reference

        try {
            database.child("Users").child(email).setValue(user)
        }catch(e : Exception){
            e.printStackTrace()
        }

        /*var database = FirebaseDatabase.getInstance().reference.child("Users")

        val userMap = HashMap<String, String>()

        //agregamos los datos
        userMap.put("email", "alopezorozco@uaz.edu.mx")
        userMap.put("Nombre", nombre)
        userMap.put("Apellido", apellido)

        database.child("email").push().setValue(userMap)*/


    }*/
}