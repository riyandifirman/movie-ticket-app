package com.riyandifirman.movie.home.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import com.riyandifirman.movie.MovieDetailActivity
import com.riyandifirman.movie.R
import com.riyandifirman.movie.model.Movie
import com.riyandifirman.movie.utils.Preferences
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class DashboardFragment : Fragment() {

    private lateinit var preferences: Preferences
    lateinit var mDatabase: DatabaseReference

    private var dataList = ArrayList<Movie>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(requireActivity().applicationContext)
        mDatabase = FirebaseDatabase.getInstance().getReference("Movie")

        tv_name.setText(preferences.getValue("name"))
        if (!preferences.getValue("balance").equals("")) {
            currency(preferences.getValue("balance")!!.toDouble(), tv_balance)
        }

        Glide.with(this)
            .load(preferences.getValue("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(iv_profile)

        rv_now_playing.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_coming_soon.layoutManager = LinearLayoutManager(requireContext().applicationContext)
        getData()
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapShot: DataSnapshot) {
                dataList.clear()
                for (getdataSnapshot in dataSnapShot.children) {
                    val movie = getdataSnapshot.getValue(Movie::class.java!!)
                    dataList.add(movie!!)
                }

                if (dataList.isNotEmpty()) {
                    rv_now_playing.adapter = NowPlayingAdapter(dataList) {
                        val intent = Intent(
                            context,
                            MovieDetailActivity::class.java
                        ).putExtra("data", it)
                        startActivity(intent)
                    }

                    rv_coming_soon.adapter = ComingSoonAdapter(dataList) {
                        val intent = Intent(
                            context,
                            MovieDetailActivity::class.java
                        ).putExtra("data", it)
                        startActivity(intent)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, "Database error", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun currency(harga : Double, textView : TextView){
        val localID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localID)
        textView.setText(formatRupiah.format(harga as Double))
    }
}