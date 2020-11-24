package com.example.uielementsact2


import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import com.google.android.material.snackbar.Snackbar
import android.os.Bundle
import android.util.Log
import android.view.*


class MainActivity : AppCompatActivity() {
    val queuedSongs = ArrayList<String>()
    val songsArray = arrayOf("Song_1", "Song_2", "Song_3", "Song_4", "Song_5",
            "Song_6", "Song_7", "Song_8", "Song_9", "Song_10",
            "Song_11", "Song_12", "Song_13", "Song_14", "Song_15")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songsArray)
        val songsListView = findViewById<ListView>(R.id.songsListView)
        songsListView.adapter = adapter

        registerForContextMenu(songsListView)
    }

    override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val menuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.add_song_to_queue -> {
                queuedSongs.add(songsArray[menuInfo.position])
                val snackbar = Snackbar.make(findViewById(R.id.songsListView), "${songsArray[menuInfo.position]} is added ", Snackbar.LENGTH_LONG)
                snackbar.setAction("Queue", View.OnClickListener { //Lamda function
                    val intent = Intent(this, SongsQueueActivity::class.java)
                    intent.putStringArrayListExtra("songs", queuedSongs)
                    startActivity(intent)
                })
                snackbar.show()
                true
            }
            else -> {
                return super.onContextItemSelected(item)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.go_to_songs -> {
                startActivity(Intent(this, MainActivity::class.java))
                true
            }
            R.id.go_to_album -> {
                startActivity(Intent(this, AlbumActivity::class.java))
                true
            }
            R.id.go_to_queue -> {
                val intent = Intent(this, SongsQueueActivity::class.java)
                intent.putStringArrayListExtra("songs", queuedSongs)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)

        }


    }
}