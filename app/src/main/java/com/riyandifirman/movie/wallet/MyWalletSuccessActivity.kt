package com.riyandifirman.movie.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.riyandifirman.movie.R
import com.riyandifirman.movie.home.HomeActivity
import kotlinx.android.synthetic.main.activity_checkout_success.*

class MyWalletSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_up_success)

        btn_home.setOnClickListener {
            finishAffinity()

            startActivity(Intent(this@MyWalletSuccessActivity, HomeActivity::class.java))
        }
    }
}