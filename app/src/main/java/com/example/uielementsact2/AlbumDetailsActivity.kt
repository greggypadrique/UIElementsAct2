package com.example.uielementsact2

import android.content.DialogInterface
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import java.util.*

class AlbumDetailsActivity : AppCompatActivity() {
    lateinit var songsToBeDisplayed: MutableList<String>
    lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_details)

        val tv = findViewById<TextView>(R.id.albumTitleTextView) as TextView
        tv.text = "Album"

        val uri = intent.getStringExtra("imageUri")

        songsToBeDisplayed = intent.getStringArrayListExtra("songs")!!.toMutableList()
        val AlbumCover = findViewById<ImageView>(R.id.albumCover)
        val albumDetailsListView = findViewById<ListView>(R.id.albumDetailsListView)

        
        var imageResource = getResources().getIdentifier(uri, null, getPackageName())
        var res = getResources().getDrawable(imageResource)
        AlbumCover.setImageDrawable(res)



        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songsToBeDisplayed)
        albumDetailsListView.adapter = adapter
        registerForContextMenu(albumDetailsListView)

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
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setMessage("Are you sure you want to remove this?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", DialogInterface.OnClickListener {
                            dialog, which ->
                            val song = songsToBeDisplayed[menuInfo.position]
                            songsToBeDisplayed.removeAt(menuInfo.position)
                            adapter.notifyDataSetChanged()
                        }).setNegativeButton( "No", DialogInterface.OnClickListener {
                            dialog, which ->
                            dialog.cancel()
                        })
                val alert = dialogBuilder.create()
                alert.setTitle("Confirmation")
                alert.show()


                true
            }
            else -> {
                return super.onContextItemSelected(item)
            }

        }

    }

}