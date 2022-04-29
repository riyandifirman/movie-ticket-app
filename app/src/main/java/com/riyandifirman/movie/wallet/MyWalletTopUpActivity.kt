package com.riyandifirman.movie.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import com.riyandifirman.movie.R
import com.riyandifirman.movie.home.HomeActivity
import kotlinx.android.synthetic.main.activity_my_wallet.*
import kotlinx.android.synthetic.main.activity_my_wallet_top_up.*
import kotlinx.android.synthetic.main.activity_my_wallet_top_up.btn_top_up
import kotlinx.android.synthetic.main.activity_my_wallet_top_up.iv_back
import java.lang.NumberFormatException

class MyWalletTopUpActivity : AppCompatActivity() {

    private var status10K : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wallet_top_up)

        initListener()
    }

    private fun initListener() {
        btn_top_up.setOnClickListener {
            startActivity(Intent(this, MyWalletSuccessActivity::class.java))
        }

        tv_10k.setOnClickListener {
            if (status10K) {
                deselectedOption(tv_10k)
            } else {
                selectedOption(tv_10k)
            }
        }

        iv_back.setOnClickListener {
            startActivity(Intent(this, MyWalletActivity::class.java))
        }

        et_amount.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) { }

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {  }

            override fun afterTextChanged(s: Editable) {
                try {
                    if (s.toString().toInt() >= 10000) {
                        btn_top_up.visibility = View.VISIBLE
                    } else {
                        tv_10k.setTextColor(resources.getColor(R.color.white))
                        tv_10k.setBackgroundResource(R.drawable.shape_line_white)
                        status10K = false
                        btn_top_up.visibility = View.INVISIBLE
                    }
                } catch (e : NumberFormatException) {
                    tv_10k.setTextColor(resources.getColor(R.color.white))
                    tv_10k.setBackgroundResource(R.drawable.shape_line_white)
                    status10K = false
                    btn_top_up.visibility = View.INVISIBLE
                }
            }
        })
    }

//    private fun initListener() {
//        TODO("Not yet implemented")
//    }
//    if (et_amount.text.toString().isEmpty()) {
//            btn_top_up.visibility = View.INVISIBLE
//        }
//
//        tv_10k.setOnClickListener {
//            if (status10K) {
//                deselectedOption(tv_10k)
//            } else {
//                selectedOption(tv_10k)
//            }
//        }
//
//        btn_top_up.setOnClickListener {
//            startActivity(Intent(this@MyWalletTopUpActivity, MyWalletSuccessActivity::class.java))
//        }
//
//        iv_back.setOnClickListener {
//            startActivity(Intent(this@MyWalletTopUpActivity, MyWalletActivity::class.java))
//        }


    private fun selectedOption(textView: TextView) {
        textView.setTextColor(resources.getColor(R.color.colorPink))
        textView.setBackgroundResource(R.drawable.shape_line_pink)
        status10K = true
        btn_top_up.visibility = View.VISIBLE
        et_amount.setText("10000")
    }

    private fun deselectedOption(textView: TextView) {
        textView.setTextColor(resources.getColor(R.color.white))
        textView.setBackgroundResource(R.drawable.shape_line_white)
        status10K = false
        btn_top_up.visibility = View.INVISIBLE
        et_amount.setText("")
    }
}