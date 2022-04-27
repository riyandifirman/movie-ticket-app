package com.riyandifirman.movie.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.riyandifirman.movie.R
import com.riyandifirman.movie.sign.signin.SignInActivity
import kotlinx.android.synthetic.main.activity_onboarding_three.*

class OnboardingThreeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_three)

        btn_start.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this@OnboardingThreeActivity, SignInActivity::class.java))
        }
    }
}