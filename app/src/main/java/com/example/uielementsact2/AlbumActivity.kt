package com.example.uielementsact2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class AlbumActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        val GridView = findViewById<GridView>(R.id.gridView) as GridView

        GridView.adapter = AlbumAdapter(applicationContext)

        GridView.onItemClickListener = AdapterView.OnItemClickListener{parent, v, position, id ->
            val intent = Intent(this, AlbumDetailsActivity::class.java)
            var songsToBeDisplayed = arrayListOf<String>()
            var uri: String = ""
            if (position == 0) {
                uri = "@drawable/first_album"
                songsToBeDisplayed.clear()
                songsToBeDisplayed.addAll(resources.getStringArray(R.array.first_album))
            } else if (position == 1) {
                uri = "@drawable/second_album"
                songsToBeDisplayed.clear()
                songsToBeDisplayed.addAll(resources.getStringArray(R.array.second_album))
            } else {
                uri = "@drawable/third_album"
                songsToBeDisplayed.clear()
                songsToBeDisplayed.addAll(resources.getStringArray(R.array.third_album))
            }
            intent.putStringArrayListExtra("songs", songsToBeDisplayed )
            intent.putExtra("imageUri",  uri)
            intent.putExtra("position", position)
            startActivity(intent)


        }

    }

}