package com.example.pc.finalproject.Adapter

import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.pc.finalproject.Model.Album
import java.net.URL

/**
 * Created by PC on 3/21/2018.
 */
class AlbumAdapter: RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {
    lateinit var listAlbums: ArrayList<Album>

    override fun getItemCount(): Int {
        return listAlbums.size
    }

    override fun onBindViewHolder(holder: AlbumViewHolder?, position: Int) {
        var count = listAlbums[position]
        val title = count.name
        val url = count.image
        val ins = URL(url).openStream()
        var mIcon = BitmapFactory.decodeStream(ins)
        holder!!.titlev?.setImageBitmap(mIcon)
        holder.descript?.text =  title.capitalize()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int):{
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        val titlev: ImageView? = null
        val descript: TextView? = null
    }
}