package org.wit.ayoeats.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.material.snackbar.Snackbar
import org.wit.ayoeats.R
import org.wit.ayoeats.databinding.ActivityLoginBinding
import org.wit.ayoeats.main.MainApp
import org.wit.ayoeats.models.User
import org.wit.ayoeats.views.meallocationlist.MealLocationListView
import timber.log.Timber.i

class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding
    lateinit var app: MainApp
    var user = User()
    private lateinit var toSignupIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var toMealLocationListIntentLauncher: ActivityResultLauncher<Intent>
    var currentUser = User()

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen() // used for splashScreen
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp


        binding.btnLogin.setOnClickListener {
            app = application as MainApp
            user.email = binding.editTextTextEmailAddress.text.toString()
            user.password = binding.editTextTextPassword2.text.toString()

            if (user.email.isNotEmpty() && user.password.isNotEmpty()){
                if (app.users.checkCredentials(user) == "Wrong Password") {
                    Snackbar.make(it, R.string.wrong_password, Snackbar.LENGTH_LONG).show()
                }else if (app.users.checkCredentials(user) == "User Not Found") {
                    Snackbar.make(it, R.string.user_not_found, Snackbar.LENGTH_LONG).show()
                }else if (app.users.checkCredentials(user) == "User Found") {
                    i("Button Clicked")
                    i("logged in $user ")
                    currentUser = app.users.loggedInUser(user)!!
//                    app.userLoggedIn = true
//
//                    var allUsers = app.users.findAll()
//                    var foundCurrentUser = findByEmail(user.email, allUsers)
//                    i("${foundCurrentUser} from foundCurrent User")
//                    i("${user.email} testing to see user.email")
//                    i("${user.password} testing to see user password")
//                    i("testing to see ${app.users}")
//                    i("testing to see ${allUsers}")
//
//                    if (foundCurrentUser != null) {
//                        app.updateCurrentUser(foundCurrentUser)
//                    }
//
//                    i("${app.currentUser} second one")

                    i("${currentUser} - showing this  from the button login pressed")
                    val launcherIntent = Intent(this, MealLocationListView::class.java)
                    launcherIntent.putExtra("current_user1" , currentUser)
                    toMealLocationListIntentLauncher.launch(launcherIntent)
                    finish()

                }
            } else {
                Snackbar.make(it, R.string.fill_all_fields, Snackbar.LENGTH_LONG).show()
            }
        }
        registerToMealLocationListCallback()



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
    private fun registerToMealLocationListCallback() {
        toMealLocationListIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { }
    }

    fun findByEmail(email: String , users : List<User> ) :User? {
        return users.find{ it.email == email }

    }
}