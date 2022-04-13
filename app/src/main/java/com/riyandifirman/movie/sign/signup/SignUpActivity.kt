package com.riyandifirman.movie.sign.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.*
import com.riyandifirman.movie.R
import com.riyandifirman.movie.sign.signin.User
import com.riyandifirman.movie.sign.signup.SignUpPhotoscreenActivity

class SignUpActivity : AppCompatActivity() {

    lateinit var sUsername: String
    lateinit var sPassword: String
    lateinit var sName: String
    lateinit var sEmail: String

    lateinit var mDatabaseReference : DatabaseReference
    lateinit var mFirebaseInstance : FirebaseDatabase
    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val btn_sign_up = findViewById<View>(R.id.btn_sign_up)
        var inputUsername = findViewById<EditText>(R.id.et_username)
        var inputPassword = findViewById<EditText>(R.id.et_password)
        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference()
        mDatabaseReference = mFirebaseInstance.getReference("User")

        btn_sign_up.setOnClickListener {
            sUsername = inputUsername.text.toString()
            sPassword = inputPassword.text.toString()
            sName = inputUsername.text.toString()
            sEmail = inputUsername.text.toString()

            if(sUsername.equals("")){
                inputUsername.error = "Username cannot be empty"
                inputUsername.requestFocus()
            } else if (sPassword.equals("")){
                inputPassword.error = "Password cannot be empty"
                inputPassword.requestFocus()
            } else if (sName.equals("")){
                inputUsername.error = "Name cannot be empty"
                inputUsername.requestFocus()
            } else if (sEmail.equals("")){
                inputUsername.error = "Email cannot be empty"
                inputUsername.requestFocus()
            } else {
                saveUsername (sUsername, sPassword, sName, sEmail)
            }
        }
    }

    private fun saveUsername(sUsername: String, sPassword: String, sName: String, sEmail: String) {
        var user = User()
        user.email = sEmail
        user.name = sName
        user.username = sUsername
        user.password = sPassword

        if(sUsername != null) {
            checkingUsername(sUsername, user)
        }
    }

    private fun checkingUsername(sUsername: String, data: User) {
        mDatabaseReference.child(sUsername).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user = dataSnapshot.getValue(User::class.java)
                if (user == null) {
                    mDatabaseReference.child(sUsername).setValue(data)

                    startActivity(Intent(this@SignUpActivity, SignUpPhotoscreenActivity::class.java).putExtra("name", data.name))
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