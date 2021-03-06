package com.riyandifirman.movie.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import com.riyandifirman.movie.MovieDetailActivity
import com.riyandifirman.movie.checkout.CheckoutActivity
import com.riyandifirman.movie.R
import com.riyandifirman.movie.home.HomeActivity
import com.riyandifirman.movie.home.dashboard.ComingSoonAdapter
import com.riyandifirman.movie.home.dashboard.DashboardFragment
import com.riyandifirman.movie.home.dashboard.NowPlayingAdapter
import com.riyandifirman.movie.model.Checkout
import com.riyandifirman.movie.model.Movie
import com.riyandifirman.movie.model.Plays
import com.riyandifirman.movie.utils.Preferences
import kotlinx.android.synthetic.main.activity_choose_seat.*
import kotlinx.android.synthetic.main.fragment_dashboard.*

class ChooseSeatActivity : AppCompatActivity() {

    var statusA3:Boolean = false
    var statusA4:Boolean = false
    var total:Int = 0

    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_seat)

        val data = intent.getParcelableExtra<Movie>("data")
        tv_seat.text = data?.title

        a3.setOnClickListener {
            if (statusA3) {
                a3.setImageResource(R.drawable.ic_rectangle_line_empty)
                statusA3 = false
                total -=1
                belitiket(total)

                // delete data
                dataList.remove(Checkout("A3", "70000"))

            } else {
                a3.setImageResource(R.drawable.ic_rectangle_line_selected)
                statusA3 = true
                total +=1
                belitiket(total)

                val data = Checkout("A3", "70000")
                dataList.add(data)
            }
        }

        a4.setOnClickListener {
            if (statusA4) {
                a4.setImageResource(R.drawable.ic_rectangle_line_empty)
                statusA4 = false
                total -=1
                belitiket(total)

                // delete data
                dataList.remove(Checkout("A4", "70000"))

            } else {
                a4.setImageResource(R.drawable.ic_rectangle_line_selected)
                statusA4 = true
                total +=1
                belitiket(total)

                val data = Checkout("A4", "70000")
                dataList.add(data)
            }
        }


        btn_checkout.setOnClickListener {

            var intent = Intent(
                this,
                CheckoutActivity::class.java
            ).putExtra("data", dataList).putExtra("datas", data)
            startActivity(intent)
        }

        iv_back.setOnClickListener{
            startActivity(Intent(this@ChooseSeatActivity, HomeActivity::class.java))
        }
    }

    private fun belitiket(total:Int) {
        if (total == 0) {
            btn_checkout.setText("Buy Ticket")
            btn_checkout.visibility = View.INVISIBLE
        } else {
            btn_checkout.setText("Buy Ticket ("+total+")")
            btn_checkout.visibility = View.VISIBLE
        }

    }
}
