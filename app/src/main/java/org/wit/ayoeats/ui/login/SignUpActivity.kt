package org.wit.ayoeats.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import org.wit.ayoeats.databinding.ActivitySignUpBinding
import org.wit.ayoeats.main.MainApp
import org.wit.ayoeats.models.User
import timber.log.Timber.i

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    lateinit var app: MainApp
    var user = User()
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
    }
}