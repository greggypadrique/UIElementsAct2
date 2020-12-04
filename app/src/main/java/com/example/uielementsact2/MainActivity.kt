package com.example.uielementsact2


import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import android.os.Bundle
import android.view.*
import android.widget.Toast
import com.example.uielementsact2.databaseHandler.SongsDatabaseHandler
import com.example.uielementsact2.models.Song

val queuedSongs = ArrayList<String>()
lateinit var songsArray: MutableList<Song>
lateinit var songsAdapter: ArrayAdapter<Song>

class MainActivity : AppCompatActivity() {

    lateinit var songsDatabaseHandler: SongsDatabaseHandler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        songsDatabaseHandler =
            SongsDatabaseHandler(this)
        //get the records from the database
        songsArray = songsDatabaseHandler.read()

        val songsListView = findViewById<ListView>(R.id.songsListView)
        songsAdapter = ArrayAdapter<Song>(this, android.R.layout.simple_list_item_1, songsArray)
        songsListView.adapter = songsAdapter

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
                val song = songsArray[menuInfo.position].title
                queuedSongs.add(song)
                val snackbar = Snackbar.make(findViewById(R.id.songsListView), "$song is added to the Queue.", Snackbar.LENGTH_LONG)
                snackbar.setAction("Queue", View.OnClickListener { //Lamda function
                    val intent = Intent(this, SongsQueueActivity::class.java)
                    intent.putStringArrayListExtra("songs", queuedSongs)
                    startActivity(intent)
                })
                snackbar.show()
                true
            }
            R.id.editSong -> {
                val intent = Intent (this, EditSongActivity::class.java)
                val song_id = songsArray[menuInfo.position].id
                intent.putExtra("song_id", song_id)
                startActivity(intent)
                true
            }
            R.id.deleteSong -> {
                val song = songsArray[menuInfo.position]
                if(songsDatabaseHandler.delete(song)){
                    songsArray.removeAt(menuInfo.position)
                    songsAdapter.notifyDataSetChanged()
                    Toast.makeText(this, "Song deleted.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Something went wrong.", Toast.LENGTH_SHORT).show()
                }
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
            R.id.addASong -> {
                val intent = Intent(this, AddSongActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)

        }


    }
}