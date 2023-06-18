package com.leonel.pruebasmoviedbbottom.adaptes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.leonel.pruebasmoviedbbottom.model.movie
import com.leonel.pruebasmoviedbbottom.utils.Constant.URL_IMAGE
import com.leonel.pruebasmoviedbbottom.R
import com.squareup.picasso.Picasso

class MovieAdapter(private val mList: List<movie>):RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        //holder.imageView.setImageResource(ItemsViewModel.poster_path)
        Picasso.get()
            .load(URL_IMAGE + ItemsViewModel.poster_path)
            .resize(150,200)
            .into(holder.img_movie);

        // sets the text to the textview from our itemHolder class
        holder.txt_title.text = ItemsViewModel.title
        holder.txt_release_date.text = ItemsViewModel.release_date
/*        holder.txt_popularity.text = ItemsViewModel.vote_average

        holder.txt_overview.text = ItemsViewModel.overview
        holder.txt_lenguaje.text = ItemsViewModel.original_language
        holder.txt_tipo.text = ItemsViewModel.popularity*/


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }


    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val img_movie: ImageView = itemView.findViewById(R.id.img_movie)
        val txt_title: TextView = itemView.findViewById(R.id.txt_title_row)
        val txt_release_date: TextView = itemView.findViewById(R.id.txt_subtitle_row)
//        val txt_popularity: TextView = itemView.findViewById(R.id.txt_popularity)
//
//        val txt_overview: TextView = itemView.findViewById(R.id.txt_overview)
//        val txt_lenguaje: TextView = itemView.findViewById(R.id.txt_lenguaje)
//        val txt_tipo: TextView = itemView.findViewById(R.id.txt_tipo)




    }
}