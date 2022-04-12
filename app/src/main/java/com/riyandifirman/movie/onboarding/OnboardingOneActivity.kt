package com.riyandifirman.movie.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.riyandifirman.movie.R
import com.riyandifirman.movie.sign.signin.SignInActivity
import com.riyandifirman.movie.utils.Preferences


class OnboardingOneActivity : AppCompatActivity() {

    lateinit var preference:Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_one)

        preference = Preferences(this)

        if (preference.getValue("onboarding").equals("1")) {
            finishAffinity()

            startActivity(Intent(this@OnboardingOneActivity, SignInActivity::class.java))
        }

        val btn_next = findViewById<View>(R.id.btn_next)
        val btn_skip_intro = findViewById<View>(R.id.btn_skip_intro)

        btn_next.setOnClickListener {
            startActivity(Intent(this@OnboardingOneActivity, OnboardingTwoActivity::class.java))
        }

        btn_skip_intro.setOnClickListener {
            preference.setValue("onboarding", "1")
            finishAffinity()

            startActivity(Intent(this@OnboardingOneActivity, SignInActivity::class.java))
        }

    }
}