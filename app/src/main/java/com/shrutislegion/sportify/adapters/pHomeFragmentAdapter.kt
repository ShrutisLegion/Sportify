package com.shrutislegion.sportify.adapters

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
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
import com.shrutislegion.sportify.playeractivities.PlayerRatingActivity
import com.shrutislegion.sportify.playeractivities.PlayerSharedActivity
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
        var email = itemView.findViewById<TextView>(R.id.emailId)
        var share = itemView.findViewById<ImageView>(R.id.shareButton)
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
        holder.email.setText(model.emailId)
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

        //on click on card and start the Lender Share activity
        holder.card.setOnClickListener{

            val intent = Intent(holder.name.context, PlayerSharedActivity::class.java)

            // Transfer all the required data to the Shared activity by putExtra
            intent.putExtra(PlayerSharedActivity.EXTRA_IMAGEURI, model.imageUri.toString())
            intent.putExtra(PlayerSharedActivity.EXTRA_NAME, holder.name.text.toString())
            intent.putExtra(PlayerSharedActivity.EXTRA_PHONE, holder.phone.text.toString())
            intent.putExtra(PlayerSharedActivity.EXTRA_SPORT, holder.type.text.toString())
            intent.putExtra(PlayerSharedActivity.EXTRA_LOCATION, holder.location.text.toString())
            intent.putExtra(PlayerSharedActivity.EXTRA_DESCRIPTION, holder.description.text.toString())
            intent.putExtra(PlayerSharedActivity.EXTRA_PRICE, holder.price.text.toString())
            intent.putExtra(PlayerSharedActivity.EXTRA_COURTS, holder.courts.text.toString())
            intent.putExtra(PlayerSharedActivity.EXTRA_EMAILID, holder.email.text.toString())
            intent.putExtra(PlayerSharedActivity.EXTRA_RATING, holder.ratingBar.rating.toString())

            // create pairs of View and String for the transition effect to take place
            val p1 = Pair.create(holder.image as View, "image")
            val p2 = Pair.create<View, String>(holder.name, "complexName")
            val p3 = Pair.create<View, String>(holder.phone, "phoneNumber")
            val p4 = Pair.create<View, String>(holder.type, "sportType")
            val p5 = Pair.create<View, String>(holder.location, "location")
            val p6 = Pair.create<View, String>(holder.description, "description")
            val p7 = Pair.create<View, String>(holder.price, "price")
            val p8 = Pair.create<View, String>(holder.courts, "courts")
            val p9 = Pair.create<View, String>(holder.email, "email")
            val p10 = Pair.create<View, String>(holder.ratingBar, "ratingBar")

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(holder.name.context as Activity, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10)

            // Start the Shared activity with the transition
            holder.name.context.startActivity(intent, options.toBundle())
        }

        holder.share.setOnClickListener{
            val intent = Intent(holder.name.context, PlayerRatingActivity::class.java)

            intent.putExtra(PlayerRatingActivity.EXTRA_KEYID, getRef(position).key.toString())
            intent.putExtra(PlayerRatingActivity.EXTRA_NAME, holder.name.text.toString())

            holder.name.context.startActivity(intent)
        }

    }

}