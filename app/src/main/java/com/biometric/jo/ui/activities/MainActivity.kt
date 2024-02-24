package com.biometric.jo.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.biometric.jo.R
import com.biometric.jo.databinding.ActivityMainBinding
import com.biometric.jo.ui.activities.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {

    //El Executor permite ejecutar procesos en segundo plano
    private lateinit var executor: Executor

    //Va a mostrar los eventos del biometrico
    private lateinit var biometricPrompt: BiometricPrompt

    //EL dialogo que se va a mostrar
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private lateinit var btnFinger: ImageView
    private lateinit var txtInfo: TextView

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        initListener()
        initObservables()
        AuteticationVariables()
        mainViewModel.checkBiometric(this)

    }

    override fun onStart() {
        super.onStart()


        // Check if user is signed in (non-null) and update UI accordingly.
       /*
        PARA OCUPAR EL BIOMETRICO
        val currentUser = auth.currentUser
        if (currentUser != null) {
            //startActivity(Intent(this, MainActivity2::class.java))
            //Se autentique con la huella digital
            binding.editextUser.visibility = View.GONE
            binding.editextPassword.visibility = View.GONE

            binding.imgFinger.visibility = View.VISIBLE
            binding.textInfo.text = getString(R.string.biometric_success)
        } else {
            binding.imgFinger.visibility = View.GONE
            binding.textInfo.text = getString(R.string.no_user)
        }*/
    }

    private fun initListener() {
        binding.imgFinger.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
        binding.btnSaveUsers.setOnClickListener {
            mainViewModel.CreateUserWithEmailAndPassword(
                binding.editextUser.text.toString(),
                binding.editextPassword.text.toString()
            )
        }
        binding.btnSignUser.setOnClickListener {
                mainViewModel.sigInUserWithEmailAndPassword(
                binding.editextUser.text.toString(),
                binding.editextPassword.text.toString()
            )
        }

    }

    /*
    esto si no tuviera los listener

    private fun signInUsers(toString: String, toString1: String) {
        mainViewModel.sigInUserWithEmailAndPassword(
            binding.editextUser.text.toString(),
            binding.editextPassword.text.toString()
        )
    }

     */



    private fun AuteticationVariables() {
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    startActivity(Intent(this@MainActivity, MainActivity2::class.java))
                }
            })
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            //.setNegativeButtonText("Use account password")
            .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
            //.setNegativeButtonText("Cancelar")
            .build()
    }

    private fun initObservables() {

        mainViewModel.user.observe(this){
            startActivity(Intent(this, MainActivity2::class.java))
        }

        mainViewModel.error.observe(this){
            Snackbar.make(
                this,
                binding.editextUser,
                it,
                Snackbar.LENGTH_LONG
            ).show()
        }

        mainViewModel.resultCheckBiometric.observe(this) { code ->

            //Itero sobre lo que tengo en mi MainViewModel
            when (code) {
                BiometricManager.BIOMETRIC_SUCCESS -> {
                    binding.imgFinger.visibility = View.VISIBLE
                    binding.textInfo.text = getString(R.string.biometric_success)
                    Log.d("MY_APP_TAG", "App can authenticate using biometrics.")
                }

                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                    binding.textInfo.text = getString(R.string.biometric_no_hardware)
                    Log.e("MY_APP_TAG", "No biometric features available on this device.")
                }

                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                    Log.e("MY_APP_TAG", "Biometric features are currently unavailable.")
                }

                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    // Prompts the user to create credentials that your app accepts.
                    val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                        putExtra(
                            Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                            BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
                        )
                    }
                    startActivityForResult(enrollIntent, 100)
                }

            }
        }
    }

  





}