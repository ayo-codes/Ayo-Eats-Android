package org.wit.ayoeats.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

import org.wit.ayoeats.databinding.ActivitySignUpBinding
import org.wit.ayoeats.main.MainApp
import org.wit.ayoeats.models.User
import timber.log.Timber.i

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    lateinit var app: MainApp
    var user = User()
    private lateinit var toLoginIntentLauncher : ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        binding.btnSignup.setOnClickListener {
            user.firstname = binding.editTextFirstnameSignup.text.toString()
            user.surname = binding.editTextSurnameSignup.text.toString()
            user.email = binding.editTextTextEmailAddressSignup.text.toString()
            user.password = binding.editTextTextPasswordSignup.text.toString()
            app.users.create(user)
            i("Button signup clicked")
            i("added $user to ${app.users}")
        }

        binding.textViewLoginLink.setOnClickListener {
            val launcherIntent = Intent(this, LoginActivity::class.java)
            toLoginIntentLauncher.launch(launcherIntent)
            finish()
        }
        registerToLoginCallback()
    }

    private fun registerToLoginCallback() {
        toLoginIntentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { }
        i("Moving to the Login Page")
    }

}