package com.biometric.jo.data.repositories



import com.biometric.jo.data.entities.UserDB
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class UsersRepository {

    private val db = Firebase.firestore
    suspend fun saveUsersDB(id: String, email: String, name:String):Result<UserDB> = runCatching{
        val us = UserDB(id,email,name)
        db.collection("Users").add(us).await()
        return@runCatching us

    }

    suspend fun getUserByID(id: String) =runCatching {
        val us = UserDB(id, "", "")
        return@runCatching db.collection("Users")
            .document(us.id)
            .get()
            .await<DocumentSnapshot?>()?.toObject<UserDB>(UserDB::class.java)

    }

    suspend fun updateUserByID(id: String) =runCatching {
        val us = UserDB(id, "", "")
        val v = db.collection("Users")
            .document(us.id)
            .get()
            .await<DocumentSnapshot?>()?.toObject<UserDB>(UserDB::class.java)
        if(v!= null) {
            v!!.email = "dddd"
            db.collection("Users").document(v!!.id).set(v)
        }
    }
}