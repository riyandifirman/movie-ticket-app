package com.riyandifirman.movie.home.ticket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        var data = intent.getParcelableExtra<Movie>("data")

        tv_title.text = data?.title
        tv_genre.text = data?.genre
        tv_rate.text = data?.rating

        Glide.with(this)
            .load(data?.poster)
            .into(iv_poster_image)

        rv_checkout.layoutManager = LinearLayoutManager(this)
        dataList.add(Checkout("A3", ""))
        dataList.add(Checkout("A3", ""))

        rv_checkout.adapter = TicketAdapter(dataList) {

        }
    }
}