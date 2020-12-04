package com.example.uielementsact2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.uielementsact2.databaseHandler.SongsDatabaseHandler
import com.example.uielementsact2.models.AlbumSong
import com.example.uielementsact2.models.Song

class AddSongToAlbumActivity : AppCompatActivity() {
    lateinit var albumSongTitle: EditText
    lateinit var addAlbumSong: Button
    lateinit var albumSong: AlbumSong


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_song_to_album)

        val albumSong_id = intent.getIntExtra("albumSong_id", 0)

        albumSongTitle = findViewById(R.id.albumSongTitleEditText)
        addAlbumSong = findViewById(R.id.addSongToAlbumBtn)

        val databaseHandler =
            SongsDatabaseHandler(this)

        addAlbumSong.setOnClickListener{
            //get the field from the forms
            val title_string = albumSongTitle.text.toString()
            //assign it to a book model
            val albumSong = AlbumSong(albumSong = title_string)
            //save it to the database
            if (databaseHandler.createAlbumSongs(albumSong)){
                Toast.makeText(this, "Song added to the Album.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Oooops!", Toast.LENGTH_SHORT).show()
            }
            albumSongsArrayAdapter.notifyDataSetChanged()
            clear()
        }

    }


    fun clear(){

        albumSongTitle.text.clear()
    }
}