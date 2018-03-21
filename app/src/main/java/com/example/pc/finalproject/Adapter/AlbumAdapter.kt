package com.example.pc.finalproject.Adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.pc.finalproject.Model.Album
import com.example.pc.finalproject.R
import kotlinx.android.synthetic.main.card_view.view.*
import java.net.URL

/**
 * Created by PC on 3/21/2018.
 */
class AlbumAdapter(list : ArrayList<Album>,context: Context): RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {
    var listAlbums: ArrayList<Album> = list

    override fun getItemCount(): Int {
        return listAlbums.size
    }

    override fun onBindViewHolder(holder: AlbumViewHolder?, position: Int) {
        val pmon = listAlbums[position]
        val name = pmon.name
        val artist = pmon.artist
        val url = pmon.image
        if(url != "" || url != null) {
            val ins = URL(url).openStream()
            var mIcon = BitmapFactory.decodeStream(ins)
            holder!!.titlev.setImageBitmap(mIcon)
            holder!!.descript.setText(name + " \n" + name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AlbumViewHolder? {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.card_view, parent, false)
        return AlbumViewHolder(view)
    }

    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {

        }

        val titlev: ImageView
        val descript: TextView


        init {
            titlev = itemView.findViewById<ImageView>(R.id.image)
            descript = itemView.findViewById<TextView>(R.id.Album)
        }

    }
}