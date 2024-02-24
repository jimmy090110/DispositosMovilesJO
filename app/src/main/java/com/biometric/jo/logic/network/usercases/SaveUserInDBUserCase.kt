package com.biometric.jo.logic.network.usercases

import com.biometric.jo.data.entities.UserDB
import com.biometric.jo.data.repositories.UsersRepository

class SaveUserInDBUserCase {
    suspend fun invoke(id:String, email:String, name:String):UserDB?{
      return UsersRepository().saveUsersDB(id,email,name).getOrNull()

    }
}