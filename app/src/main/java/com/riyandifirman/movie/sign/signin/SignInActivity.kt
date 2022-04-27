package com.riyandifirman.movie.sign.signin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.riyandifirman.movie.home.HomeActivity
import com.riyandifirman.movie.R
import com.riyandifirman.movie.sign.signup.SignUpActivity
import com.riyandifirman.movie.utils.Preferences
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : AppCompatActivity() {

    lateinit var iUsername: String
    lateinit var iPassword: String
    lateinit var mDatabase: DatabaseReference
    lateinit var preference: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        preference = Preferences(this)

        preference.setValue("onboarding", "1")
        if(preference.getValue("status").equals("1")){
            finishAffinity()
            startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
        }

        btn_sign_in.setOnClickListener {
            iUsername = et_username.text.toString()
            iPassword = et_password.text.toString()

            if(iUsername.equals("")){
                et_username.error = "Username is required"
                et_username.requestFocus()
            } else if(iPassword.equals("")) {
                et_password.error = "Password is required"
                et_password.requestFocus()
            } else {

                var statusUsername = iUsername.indexOf(".")
                if (statusUsername >= 0) {
                    et_username.error = "Username can't contain dot(.)"
                    et_username.requestFocus()
                } else {
                    pushLogin(iUsername, iPassword)
                }
            }
        }

        btn_sign_up.setOnClickListener {
            startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
        }
    }

    private fun pushLogin(iUsername: String, iPassword: String) {
        mDatabase.child(iUsername).addValueEventListener(object : ValueEventListener {

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignInActivity, databaseError.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                if(user == null) {
                    Toast.makeText(this@SignInActivity, "Username is not found", Toast.LENGTH_LONG).show()
                } else {

                    if (user.password.equals(iPassword)) {
                        Toast.makeText(this@SignInActivity, "Welcome", Toast.LENGTH_LONG).show()

                        preference.setValue("name", user.name.toString())
                        preference.setValue("username", user.username.toString())
                        preference.setValue("url", user.url.toString())
                        preference.setValue("email", user.email.toString())
                        preference.setValue("balance", user.balance.toString())
                        preference.setValue("status", "1")

                        finishAffinity()

                        startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
                    } else {
                        Toast.makeText(this@SignInActivity, "Password is wrong", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
}