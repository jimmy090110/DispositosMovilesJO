package com.biometric.jo.logic.network.usercases

import com.biometric.jo.data.entities.UserDB
import com.biometric.jo.data.repositories.UsersRepository

class GetUserByIDUserCase {
    suspend fun invoke(id:String):UserDB?{
       return UsersRepository().getUserByID(id).getOrNull()
    }
}