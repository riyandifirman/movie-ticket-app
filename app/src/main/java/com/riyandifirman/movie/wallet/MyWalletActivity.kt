package com.riyandifirman.movie.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.riyandifirman.movie.R
import com.google.firebase.database.*
import com.riyandifirman.movie.home.HomeActivity
import com.riyandifirman.movie.model.Plays
import com.riyandifirman.movie.wallet.adapter.WalletAdapter
import com.riyandifirman.movie.wallet.model.Wallet
import kotlinx.android.synthetic.main.activity_my_wallet.*

class MyWalletActivity : AppCompatActivity() {

    private var dataList = ArrayList<Wallet>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wallet)

        loadDummyData()
        }

    private fun initListener() {
        rv_transaction.layoutManager = LinearLayoutManager(this)
        rv_transaction.adapter = WalletAdapter(dataList){

        }

        btn_top_up.setOnClickListener {
            startActivity(Intent(this@MyWalletActivity, MyWalletTopUpActivity::class.java))
        }

        iv_back.setOnClickListener {
            startActivity(Intent(this@MyWalletActivity, HomeActivity::class.java))
        }
    }

    private fun loadDummyData() {
        dataList.add(
            Wallet(
                "Adventure at Split",
                "Saturday, January 11, 2022",
                70000.0,
                "0"
            )
        )
        dataList.add(
            Wallet(
                "Top Up",
                "Saturday, January 11, 2022",
                300000.0,
                "1"
            )
        )
        dataList.add(
            Wallet(
                "Icebox Parody",
                "Saturday, January 4, 2022",
                70000.0,
                "0"
            )
        )

        initListener()
    }
}