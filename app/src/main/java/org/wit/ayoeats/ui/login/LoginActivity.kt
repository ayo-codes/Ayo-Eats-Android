package org.wit.ayoeats.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import org.wit.ayoeats.databinding.ActivityLoginBinding
import org.wit.ayoeats.main.MainApp
import org.wit.ayoeats.models.User
import timber.log.Timber.i

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    lateinit var app: MainApp
    var user = User()
    private lateinit var toSignupIntentLauncher : ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp


        binding.btnLogin.setOnClickListener {
            user.email = binding.editTextTextEmailAddress.text.toString()
            user.password = binding.editTextTextPassword2.text.toString()
            app.users.loginUser(user)
            i("Button Clicked")
            i("added $user to ${app.users}")
        }

        binding.textViewSignupLink.setOnClickListener {
            val launcherIntent = Intent (this, SignUpActivity::class.java)
            toSignupIntentLauncher.launch(launcherIntent)
            finish()
        }
        registerToSignupCallback()
    }


    private fun registerToSignupCallback() {
        toSignupIntentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { }
        i("Moving to SignUp Page")
    }

}