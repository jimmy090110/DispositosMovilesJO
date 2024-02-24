package com.biometric.jo.ui.activities.viewmodels

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biometric.jo.data.entities.UserDB

import com.biometric.jo.logic.network.usercases.CreateUserWithEmailAndPasswordUserCase
import com.biometric.jo.logic.network.usercases.SaveUserInDBUserCase
import com.biometric.jo.logic.network.usercases.SigInUserWithEmailAndPasswordUserCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    val user get()= _user
    private val _user = MutableLiveData<UserDB>()

    val error get()= _error
    private val _error = MutableLiveData<String>()


    val resultCheckBiometric = MutableLiveData<Int>()

    //No permkte chequear que se puede ver biometricos
     fun checkBiometric(context: Context) {

        val biometricManager = BiometricManager.from(context)
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                resultCheckBiometric.postValue(BiometricManager.BIOMETRIC_SUCCESS)

            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                resultCheckBiometric.postValue(BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE)

            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->{
                resultCheckBiometric.postValue( BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE)
               }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                resultCheckBiometric.postValue( BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED) //Devuelve un codigo

            }
        }
    }

    fun sigInUserWithEmailAndPassword(email: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            val us = SigInUserWithEmailAndPasswordUserCase().invoke(email,password)
            if (us!= null){
                _user.postValue(us!!)
            }else{
                _error.postValue("Ocurrio un error")
            }
        }
    }

    fun CreateUserWithEmailAndPassword(email: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            val us = CreateUserWithEmailAndPasswordUserCase().invoke(email,password)
            if (us!= null){
               val newUs = SaveUserInDBUserCase().invoke(us.id,us.email,"")
                _user.postValue(newUs!!)
            }else{
                _error.postValue("Ocurrio un error")
            }
        }
    }
}