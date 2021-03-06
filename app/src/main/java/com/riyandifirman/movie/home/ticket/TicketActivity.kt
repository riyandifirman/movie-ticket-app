package com.riyandifirman.movie.home.ticket

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.riyandifirman.movie.R
import com.riyandifirman.movie.checkout.CheckoutAdapter
import com.riyandifirman.movie.model.Checkout
import com.riyandifirman.movie.model.Movie
import kotlinx.android.synthetic.main.activity_ticket.*

class TicketActivity : AppCompatActivity() {

    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)

        val data = intent.getParcelableExtra<Movie>("data")

        tv_title.text = data?.title
        tv_genre.text = data?.genre
        tv_rate.text = data?.rating

        Glide.with(this)
            .load(data?.poster)
            .into(iv_poster_image)

        rv_checkout.layoutManager = LinearLayoutManager(this)
        dataList.add(Checkout("A3", ""))
        dataList.add(Checkout("A4", ""))

        rv_checkout.adapter = TicketAdapter(dataList) {

        }

        iv_back.setOnClickListener {
            finish()
        }

        iv_barcode.setOnClickListener {
            showDialog("Please do a scan at the\nnnearest ticket counter")
        }
    }

    private fun showDialog(title: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_qr)
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        val tvDesc = dialog.findViewById(R.id.tv_desc) as TextView
        tvDesc.text = title

        val btnClose = dialog.findViewById(R.id.btn_close) as Button
        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}