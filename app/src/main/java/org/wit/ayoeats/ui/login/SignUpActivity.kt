package org.wit.ayoeats.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.wit.ayoeats.R

import org.wit.ayoeats.databinding.ActivitySignUpBinding
import org.wit.ayoeats.main.MainApp
import org.wit.ayoeats.models.User
import org.wit.ayoeats.views.meallocationlist.MealLocationListView
import timber.log.Timber.i

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    lateinit var app: MainApp
    var user = User()
    private lateinit var toLoginIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var toMealLocationListIntentLauncher : ActivityResultLauncher<Intent>


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
            if (user.firstname.isNotEmpty() && user.surname.isNotEmpty() && user.email.isNotEmpty() && user.password.isNotEmpty()) {
                app.users.create(user)
                i("Button signup clicked")
                i("added $user to ${app.users}")
                val launcherIntent = Intent(this, MealLocationListView::class.java)
                toMealLocationListIntentLauncher.launch(launcherIntent)
                i("Moving to the Meal Location List Page")
                finish()
            } else {
                Snackbar.make(it, R.string.fill_all_fields, Snackbar.LENGTH_LONG).show()
            }
        }
        registerToMealLocationListCallback()

        binding.textViewLoginLink.setOnClickListener {
            val launcherIntent = Intent(this, LoginActivity::class.java)
            toLoginIntentLauncher.launch(launcherIntent)
            i("Moving to the Login Page")
            finish()
        }
        registerToLoginCallback()
    }

    private fun registerToLoginCallback() {
        toLoginIntentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { }

    }

    private fun registerToMealLocationListCallback() {
        toMealLocationListIntentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { }

    }

}