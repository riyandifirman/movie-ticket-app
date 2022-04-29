package com.riyandifirman.movie.checkout

import android.app.Notification
import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.riyandifirman.movie.R
import com.riyandifirman.movie.home.ticket.TicketActivity
import com.riyandifirman.movie.model.Checkout
import com.riyandifirman.movie.model.Movie
import com.riyandifirman.movie.utils.Preferences
import kotlinx.android.synthetic.main.activity_checkout.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class CheckoutActivity : AppCompatActivity() {

    private var dataList = ArrayList<Checkout>()
    private var total = 0
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        preferences = Preferences(this)
        dataList = intent.getSerializableExtra("data") as ArrayList<Checkout>
        val data = intent.getParcelableExtra<Movie>("datas")

        for (a in dataList.indices) {
            total += dataList[a].price!!.toInt()
        }

        dataList.add(Checkout("Total to be paid", total.toString()))

        rv_checkout.layoutManager = LinearLayoutManager(this)
        rv_checkout.adapter = CheckoutAdapter(dataList) {
        }

        btn_pay_now.setOnClickListener {
            startActivity(Intent(this, CheckoutSuccessActivity::class.java))

            showNotif(data!!)
        }

        btn_cancel.setOnClickListener {
            startActivity(Intent(this, ChooseSeatActivity::class.java))
        }

        iv_back.setOnClickListener {
            startActivity(Intent(this, ChooseSeatActivity::class.java))
        }

        if (preferences.getValue("balance")!!.isNotEmpty()) {
            val localeID = Locale("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
            tv_balance.setText(formatRupiah.format(preferences.getValue("balance")!!.toDouble()))
            btn_pay_now.visibility = View.VISIBLE
            tv_alert.visibility = View.INVISIBLE

        } else {
            tv_balance.setText("Rp0")
            btn_pay_now.visibility = View.INVISIBLE
            tv_alert.visibility = View.VISIBLE
            tv_alert.text = "Insufficient balance in your e-wallet\n" +
                    "to make a transaction"
        }
    }

    private fun showNotif(datas: Movie) {
        val NOTIFICATION_CHANNEL_ID = "channel_01"
        val context = this.applicationContext
        var notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as android.app.NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channelName = "Movie Notif Channel"
            val importance = android.app.NotificationManager.IMPORTANCE_HIGH

            val mChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance)
            notificationManager.createNotificationChannel(mChannel)
        }

//        val mIntent = Intent(this, CheckoutSuccessActivity::class.java)
//        val bundle = Bundle()
//        bundle.putString("id", "id_film")
//        mIntent.putExtras(bundle)

        val mIntent = Intent(this, TicketActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("data", datas)
        mIntent.putExtras(bundle)

        val pendingIntent = PendingIntent.getActivity(this, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        builder.setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.small_icon_notif)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    this.resources,
                    R.drawable.large_icon_notif
                )
            )
            .setTicker("Notification Movie Starting")
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setLights(Color.RED, 3000, 3000)
            .setDefaults(Notification.DEFAULT_SOUND)
            .setContentTitle("Checkout Success")
            .setContentText("Ticket " + datas.title + " has been booked. Enjoy the movie!")

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as android.app.NotificationManager
        notificationManager.notify(115, builder.build())

    }
}
