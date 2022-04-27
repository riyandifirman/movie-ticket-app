package com.riyandifirman.movie.home.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riyandifirman.movie.R
import com.riyandifirman.movie.model.Movie

class ComingSoonAdapter(private var data: List<Movie>,
                        private val listener: (Movie) -> Unit)
    : RecyclerView.Adapter<ComingSoonAdapter.LeagueViewHolder>() {

    lateinit var contextAdapter: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LeagueViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.row_item_coming_soon, parent, false)
        
        return LeagueViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
    }

    override fun getItemCount(): Int = data.size

    class LeagueViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val tvTitle:TextView = view.findViewById(R.id.tv_seat)
        private val tvGenre:TextView = view.findViewById(R.id.tv_genre)
        private val tvRate:TextView = view.findViewById(R.id.tv_rate)

        private val ivImage: ImageView = view.findViewById(R.id.iv_poster_image)

        fun bindItem(data:Movie, listener: (Movie) -> Unit, context: Context, position: Int){
            tvTitle.text = data.title
            tvGenre.text = data.genre
            tvRate.text = data.rating

            Glide.with(context)
                .load(data.poster)
                .into(ivImage)

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }
}
