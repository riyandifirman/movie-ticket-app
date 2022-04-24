package com.riyandifirman.movie.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.riyandifirman.movie.R
import kotlinx.android.synthetic.main.activity_my_wallet_top_up.*

class MyWalletTopUpActivity : AppCompatActivity() {

    private var status10K : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wallet_top_up)

        var inputAmount = et_amount.text.toString()

        tv_10k.setOnClickListener {
            if (status10K) {
                deselectedOption(tv_10k)
            } else {
                selectedOption(tv_10k)
            }
        }

        btn_top_up.setOnClickListener {
            startActivity(Intent(this@MyWalletTopUpActivity, MyWalletSuccessActivity::class.java))
        }
    }

    private fun selectedOption(textView: TextView) {
        textView.setTextColor(resources.getColor(R.color.colorPink))
        textView.setBackgroundResource(R.drawable.shape_line_pink)
        status10K = true
        btn_top_up.visibility = View.VISIBLE
    }

    private fun deselectedOption(textView: TextView) {
        textView.setTextColor(resources.getColor(R.color.white))
        textView.setBackgroundResource(R.drawable.shape_line_white)
        status10K = false
        btn_top_up.visibility = View.INVISIBLE
    }


}