package com.riyandifirman.movie.checkout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riyandifirman.movie.R
import com.riyandifirman.movie.model.Checkout
import java.text.NumberFormat
import java.util.*

class CheckoutAdapter(private var data: List<Checkout>,
                      private val listener: (Checkout) -> Unit)
    : RecyclerView.Adapter<CheckoutAdapter.LeagueViewHolder>() {

    lateinit var contextAdapter: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LeagueViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.row_item_checkout, parent, false)
        return LeagueViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
    }

    override fun getItemCount(): Int = data.size

    class LeagueViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val tvTitle:TextView = view.findViewById(R.id.tv_seat)
        private val tvPrice:TextView = view.findViewById(R.id.tv_price)

        fun bindItem(data:Checkout, listener: (Checkout) -> Unit, context: Context, position: Int) {

            val localID = Locale("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localID)
            tvPrice.setText(formatRupiah.format(data.price!!.toDouble()))

            if (data.seat!!.startsWith("Total")) {
                tvTitle.text = data.seat
                tvTitle.setCompoundDrawables(null, null, null, null)
            } else {
                tvTitle.text = "Seat No. ${data.seat}"
            }


            itemView.setOnClickListener {
                listener(data)
            }
        }
    }
}
