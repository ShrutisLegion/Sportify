package com.shrutislegion.sportify.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.shrutislegion.sportify.R
import com.shrutislegion.sportify.modules.ComplexInfo
import javax.sql.DataSource

class pHomeFragmentAdapter(options: FirebaseRecyclerOptions<ComplexInfo>)
    : FirebaseRecyclerAdapter<ComplexInfo, pHomeFragmentAdapter.myViewHolder>(options){

    lateinit var databaseReference: DatabaseReference

    inner class myViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        // creating viewHolder and getting all the required views by their Ids
        val name = itemView.findViewById<TextView>(R.id.nameOfComplex)
        val type = itemView.findViewById<TextView>(R.id.sportType)
        val courts = itemView.findViewById<TextView>(R.id.courtsCount)
        val price = itemView.findViewById<TextView>(R.id.hourPrice)
        val location = itemView.findViewById<TextView>(R.id.complexLocation)
        var image = itemView.findViewById<ImageView>(R.id.complexImage)
        var phone = itemView.findViewById<TextView>(R.id.phoneNumber)
        var description = itemView.findViewById<TextView>(R.id.complexDescription)
        var card = itemView.findViewById<CardView>(R.id.card)
        var ratingBar = itemView.findViewById<RatingBar>(R.id.complexRatingBar)
        var progressBarPCard = itemView.findViewById<ProgressBar>(R.id.progressBarPCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_pcomplexdetails, parent, false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int, model: ComplexInfo) {

        holder.name.setText(model.complexName)
        holder.type.setText(model.typeOfSport)
        holder.courts.setText(model.numberOfCourts)
        holder.price.setText(model.pricePerHour)
        holder.location.setText(model.location)
        holder.description.setText(model.description)
        holder.phone.setText(model.phone)
        // rounds off to 3 if isIndicator is true
        holder.ratingBar.setRating(2.5f)

        // Glide used to load the image from the uri stored in firebase and progress bar added
        Glide.with(holder.image.context).load(model.imageUri).listener(object :
            RequestListener<Drawable> {
            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: com.bumptech.glide.load.DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                holder.progressBarPCard.visibility = View.GONE
                return false
            }

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }
        })
            .override(600,400)
            .placeholder(R.drawable.loading_image)
            .into(holder.image)

    }

}