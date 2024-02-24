package com.biometric.jo.logic.network.usercases


import com.biometric.jo.data.entities.UserDB
import com.biometric.jo.data.repositories.AutenticatioRepository

class CreateUserWithEmailAndPasswordUserCase {

    suspend fun invoke(email:String , password : String): UserDB?{
        var user : UserDB?=null
        AutenticatioRepository().createUserWithEmailAndPassword(
            email, password
        )
            .onSuccess {
              user = it

            }.onFailure {
                user = null
            }
        return user
    }
}