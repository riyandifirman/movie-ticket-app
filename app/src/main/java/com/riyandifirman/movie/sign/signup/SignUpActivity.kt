package com.riyandifirman.movie.sign.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.*
import com.riyandifirman.movie.R
import com.riyandifirman.movie.sign.signin.SignInActivity
import com.riyandifirman.movie.sign.signin.User
import com.riyandifirman.movie.sign.signup.SignUpPhotoscreenActivity
import com.riyandifirman.movie.utils.Preferences
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    lateinit var sUsername: String
    lateinit var sPassword: String
    lateinit var sName: String
    lateinit var sEmail: String

    lateinit var mDatabaseReference : DatabaseReference
    lateinit var mFirebaseInstance : FirebaseDatabase
    lateinit var mDatabase: DatabaseReference
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

//        val btn_sign_up = findViewById<View>(R.id.btn_sign_up)
//        var inputUsername = findViewById<EditText>(R.id.et_username)
//        var inputPassword = findViewById<EditText>(R.id.et_password)
        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference()
        mDatabaseReference = mFirebaseInstance.getReference("User")

        preferences = Preferences(this)

        btn_sign_up.setOnClickListener {
            sUsername = et_username.text.toString()
            sPassword = et_password.text.toString()
            sName = et_name.text.toString()
            sEmail = et_email.text.toString()

            if(sUsername.equals("")){
                et_username.error = "Username cannot be empty"
                et_username.requestFocus()
            } else if (sPassword.equals("")){
                et_password.error = "Password cannot be empty"
                et_password.requestFocus()
            } else if (sName.equals("")){
                et_name.error = "Name cannot be empty"
                et_name.requestFocus()
            } else if (sEmail.equals("")){
                et_email.error = "Email cannot be empty"
                et_email.requestFocus()
            } else {
                var statusUsername = sUsername.indexOf(".")
                if (statusUsername >= 0){
                    et_username.error = "Username cannot contain dot(.)"
                    et_username.requestFocus()
                } else {
                    saveUsername(sUsername, sPassword, sName, sEmail)
                }
            }
        }

        iv_back.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
        }
    }

    private fun saveUsername(sUsername: String, sPassword: String, sName: String, sEmail: String) {
        val user = User()
        user.email = sEmail
        user.name = sName
        user.username = sUsername
        user.password = sPassword

        if(sUsername != null) {
            checkingUsername(sUsername, user)
        }
    }

    private fun checkingUsername(iUsername: String, data: User) {
        mDatabaseReference.child(iUsername).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                if (user == null) {
                    mDatabaseReference.child(iUsername).setValue(data)

                    preferences.setValue("name", data.name.toString())
                    preferences.setValue("username", data.username.toString())
                    preferences.setValue("balance", "")
                    preferences.setValue("url", "")
                    preferences.setValue("email", data.email.toString())
                    preferences.setValue("status", "1")

                    startActivity(Intent(this@SignUpActivity, SignUpPhotoscreenActivity::class.java).putExtra("data", data))
                } else {
                    Toast.makeText(this@SignUpActivity, "User already exist", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignUpActivity, "Database Error", Toast.LENGTH_LONG).show()
            }

        })
    }
}