package com.riyandifirman.movie.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.riyandifirman.movie.R
import com.riyandifirman.movie.home.HomeActivity
import com.riyandifirman.movie.home.ticket.TicketActivity
import kotlinx.android.synthetic.main.activity_checkout_success.*

class CheckoutSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout_success)

        btn_ticket.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this, TicketActivity::class.java))
        }

        btn_home.setOnClickListener {
            finishAffinity()

            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}