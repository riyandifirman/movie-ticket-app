package com.riyandifirman.movie.sign.signin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.riyandifirman.movie.HomeActivity
import com.riyandifirman.movie.R
import com.riyandifirman.movie.sign.signup.SignUpActivity
import com.riyandifirman.movie.utils.Preferences


class SignInActivity : AppCompatActivity() {

    lateinit var iUsername: String
    lateinit var iPassword: String
    lateinit var mDatabase: DatabaseReference
    lateinit var preference: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val btn_sign_in = findViewById<View>(R.id.btn_sign_in)
        val btn_sign_up = findViewById<View>(R.id.btn_sign_up)
        var inputUsername = findViewById<EditText>(R.id.et_username)
        var inputPassword = findViewById<EditText>(R.id.et_password)
        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        preference = Preferences(this)

        preference.setValue("onboarding", "1")
        if(preference.getValue("status").equals("1")){
            finishAffinity()
            startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
        }

        btn_sign_in.setOnClickListener {
            iUsername = inputUsername.text.toString()
            iPassword = inputPassword.text.toString()

            if(iUsername.equals("")){
                inputUsername.error = "Username is required"
                inputUsername.requestFocus()
            } else if(iPassword.equals("")) {
                inputPassword.error = "Password is required"
                inputPassword.requestFocus()
            } else {
                pushLogin(iUsername, iPassword)
            }
        }

        btn_sign_up.setOnClickListener {
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(intent)
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

                    preference.setValue("name", user.name.toString())
                    preference.setValue("username", user.username.toString())
                    preference.setValue("url", user.url.toString())
                    preference.setValue("email", user.email.toString())
                    preference.setValue("balance", user.balance.toString())
                    preference.setValue("status", "1")

                        var intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@SignInActivity, "Password is wrong", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
}