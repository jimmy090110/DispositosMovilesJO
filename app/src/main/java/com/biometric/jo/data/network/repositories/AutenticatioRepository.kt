package com.biometric.jo.data.repositories

import android.annotation.SuppressLint

import com.biometric.jo.data.entities.UserDB

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

class AutenticatioRepository {

    // Initialize Firebase Auth
    private val auth = Firebase.auth

    // creando usuarios con tareas
    @SuppressLint("SuspiciousIndentation")
    suspend fun createUserWithEmailAndPassword(user: String, password: String) =
        runCatching {
            var  userdb: UserDB? = null

        val usFirebase = auth.createUserWithEmailAndPassword(
            user,
            password
        ).await().user
            if (usFirebase != null ){
                userdb=
                    UserDB(usFirebase.uid, usFirebase.email!!,usFirebase.displayName.orEmpty())
            }
            return@runCatching userdb!!

    }

    suspend fun signInUsers(email: String, password: String): Result<UserDB> =
        runCatching{

            var  userdb: UserDB? = null
            val  usFirebase = auth.signInWithEmailAndPassword(email, password).await().user
            if (usFirebase != null  ){
                userdb=
                    UserDB(usFirebase.uid, usFirebase.email!!,usFirebase.displayName.orEmpty())
            }
            return@runCatching userdb!!
        }
}