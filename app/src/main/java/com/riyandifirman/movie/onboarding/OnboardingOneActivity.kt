package com.riyandifirman.movie.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.riyandifirman.movie.R


class OnboardingOneActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_one)

        val btn_next = findViewById<View>(R.id.btn_next)
        val btn_skip_intro = findViewById<View>(R.id.btn_skip_intro)

        btn_next.setOnClickListener {
            startActivity(Intent(this@OnboardingOneActivity, OnboardingTwoActivity::class.java))
        }

        btn_skip_intro.setOnClickListener {
            startActivity(Intent(this@OnboardingOneActivity, OnboardingThreeActivity::class.java))
        }

    }
}