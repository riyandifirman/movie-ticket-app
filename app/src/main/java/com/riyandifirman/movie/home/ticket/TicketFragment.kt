package com.riyandifirman.movie.home.ticket

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.riyandifirman.movie.R
import com.riyandifirman.movie.home.dashboard.ComingSoonAdapter
import com.riyandifirman.movie.model.Movie
import com.riyandifirman.movie.utils.Preferences
import kotlinx.android.synthetic.main.fragment_ticket.*

/**
 * A simple [Fragment] subclass.
 * Use the [TicketFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TicketFragment : Fragment() {

    private lateinit var preferences: Preferences
    lateinit var mDatabase : DatabaseReference
    private var dataList = ArrayList<Movie>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ticket, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(requireContext().applicationContext)
        mDatabase = FirebaseDatabase.getInstance().getReference("Movie")

        rv_ticket.layoutManager = LinearLayoutManager(requireContext().applicationContext)
        getData()
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()
                for (getdataSnapshot in dataSnapshot.getChildren()) {
                    val movie = getdataSnapshot.getValue(Movie::class.java)
                    dataList.add(movie!!)
                }

                rv_ticket.adapter = ComingSoonAdapter(dataList) {
                    startActivity(Intent(context, TicketActivity::class.java).putExtra("data", it))
                }

                tv_total.setText("${dataList.size.toString()} Movies")
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, ""+error.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}