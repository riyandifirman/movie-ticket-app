package com.riyandifirman.movie.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.riyandifirman.movie.R
import kotlinx.android.synthetic.main.activity_onboarding_two.*

class OnboardingTwoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_two)

        btn_next.setOnClickListener {
            startActivity(Intent(this@OnboardingTwoActivity, OnboardingThreeActivity::class.java))
        }
    }
}