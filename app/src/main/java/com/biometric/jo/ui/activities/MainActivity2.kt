package com.biometric.jo.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.biometric.jo.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity2 : AppCompatActivity() {

    //Proveedor de autenticación
    private lateinit var  auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        auth=Firebase.auth
        getUsersData()
        initListeners()
    }

    private fun initListeners(){
        val btnSalir = findViewById<Button>(R.id.btnSalir)
        btnSalir.setOnClickListener {
            logOut()
        }

        val btnBuscar = findViewById<Button>(R.id.btnBuscar)
        btnBuscar.setOnClickListener {
            startActivity(Intent(this, ItemActivity::class.java))
        }
    }
    private fun getUsersData(){
        val user= Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl

            // Check if user's email is verified
            val emailVerified = it.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            val uid = it.uid

            Log.d("TAG",email.toString())
            Log.d("TAG",uid.toString())
        }
    }
    private fun logOut(){
        auth.signOut()
        startActivity(Intent(this, MainActivity::class.java))
    }
}
