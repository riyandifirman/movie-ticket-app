package com.riyandifirman.movie.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.riyandifirman.movie.R
import com.riyandifirman.movie.model.Checkout
import com.riyandifirman.movie.model.Movie
import com.riyandifirman.movie.utils.Preferences
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckoutActivity : AppCompatActivity() {

    private var dataList = ArrayList<Checkout>()
    private var total = 0
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        preferences = Preferences(this)
        dataList = intent.getSerializableExtra("data") as ArrayList<Checkout>

        for (a in dataList.indices) {
            total += dataList[a].price!!.toInt()
        }

        dataList.add(Checkout("Total", total.toString()))

        rv_checkout.layoutManager = LinearLayoutManager(this)
        rv_checkout.adapter = CheckoutAdapter(dataList) {
        }

        btn_pay_now.setOnClickListener{
            startActivity(Intent(this, CheckoutSuccessActivity::class.java))
        }

        btn_cancel.setOnClickListener{
            startActivity(Intent(this, ChooseSeatActivity::class.java))
        }

        iv_back.setOnClickListener{
            startActivity(Intent(this, ChooseSeatActivity::class.java))
        }
    }
}
