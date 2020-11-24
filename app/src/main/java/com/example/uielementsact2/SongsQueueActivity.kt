package com.example.uielementsact2

import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import android.os.Bundle
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.widget.ArrayAdapter
import android.widget.ListView
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build

class SongsQueueActivity : AppCompatActivity() {

    lateinit var notifManager: NotificationManager
    lateinit var notifChannel: NotificationChannel
    lateinit var build : Notification.Builder
    private val channelId = "i.apps.notifications"
    private val desc = "notification"

    lateinit var songsToBeDisplayedList: MutableList<String>
    lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_songs_queue)

        songsToBeDisplayedList = intent.getStringArrayListExtra("songs")!!.toMutableList()
        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songsToBeDisplayedList)
        val queuedSongsListView = findViewById<ListView>(R.id.queueListView)
        queuedSongsListView.adapter = adapter

        registerForContextMenu(queuedSongsListView)
    }


    override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.remove_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val menuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.removeSong -> {
                val song = songsToBeDisplayedList[menuInfo.position]
                songsToBeDisplayedList.removeAt(menuInfo.position)
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "$song is removed ", Toast.LENGTH_SHORT).show()

                if (songsToBeDisplayedList.size <= 0) {
                    notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                    val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        notifChannel = NotificationChannel(
                                channelId,desc,NotificationManager.IMPORTANCE_HIGH)
                        notifChannel.enableLights(true)
                        notifChannel.lightColor = Color.BLUE
                        notifChannel.enableVibration(false)
                        notifManager.createNotificationChannel(notifChannel)

                        build = Notification.Builder(this,channelId)
                                .setContentTitle("Song Queue")
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentIntent(pendingIntent)
                                .setContentText("There is no queued song")
                    } else {
                        build = Notification.Builder(this)
                                .setContentTitle("Song Queue")
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentIntent(pendingIntent)
                                .setContentText("There is no queued song")
                    }
                    notifManager.notify(1234, build.build())
                }
                true
            }
            else -> {
                return super.onContextItemSelected(item)
            }

        }


    }
}

