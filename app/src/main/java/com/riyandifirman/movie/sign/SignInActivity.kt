package com.riyandifirman.movie.sign

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.riyandifirman.movie.R


class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val btn_sign_in = findViewById<View>(R.id.btn_sign_in)

        btn_sign_in.setOnClickListener {
            // Write a message to the database
            val database = Firebase.database
            val myRef = database.getReference("message")

            myRef.setValue("SIIIIIUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU!")
        }
    }
}