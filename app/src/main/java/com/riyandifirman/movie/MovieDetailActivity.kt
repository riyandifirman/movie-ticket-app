package com.riyandifirman.movie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.riyandifirman.movie.home.dashboard.PlaysAdapter
import com.riyandifirman.movie.checkout.ChooseSeatActivity
import com.riyandifirman.movie.home.HomeActivity
import com.riyandifirman.movie.model.Movie
import com.riyandifirman.movie.model.Plays
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    private var dataList = ArrayList<Plays>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val data = intent.getParcelableExtra<Movie>("data")

        mDatabase = FirebaseDatabase.getInstance().getReference("Movie")
            .child(data?.title.toString())
            .child("play")

        tv_seat.text = data?.title
        tv_genre.text = data?.genre
        tv_desc.text = data?.desc
        tv_rate.text = data?.rating

        Glide.with(this)
            .load(data?.poster)
            .into(iv_poster)

        rv_who_played.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        getData()

        btn_choose_seat.setOnClickListener {
            startActivity(Intent(this@MovieDetailActivity, ChooseSeatActivity::class.java).putExtra("data", data))
        }

        iv_back.setOnClickListener {
            startActivity(Intent(this@MovieDetailActivity, HomeActivity::class.java))
        }
    }



    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()
                for (getdataSnapshot in dataSnapshot.children) {

                    val film = getdataSnapshot.getValue(Plays::class.java!!)
                    dataList.add(film!!)
                }

                rv_who_played.adapter = PlaysAdapter(dataList) {

                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MovieDetailActivity, ""+error.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}