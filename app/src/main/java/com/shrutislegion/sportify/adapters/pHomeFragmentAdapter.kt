package com.shrutislegion.sportify.adapters

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.*
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
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.shrutislegion.sportify.R
import com.shrutislegion.sportify.modules.ComplexInfo
import com.shrutislegion.sportify.modules.ComplexRating
import com.shrutislegion.sportify.playeractivities.PlayerBookDateActivity
import com.shrutislegion.sportify.playeractivities.PlayerSharedActivity

class pHomeFragmentAdapter(options: FirebaseRecyclerOptions<ComplexInfo>)
    : FirebaseRecyclerAdapter<ComplexInfo, pHomeFragmentAdapter.myViewHolder>(options) {

    lateinit var databaseReference: DatabaseReference

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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
        var bookCourt = itemView.findViewById<Button>(R.id.bookCourtButton)
        var noRating = itemView.findViewById<TextView>(R.id.noRatingView)
        var progressBarRating = itemView.findViewById<ProgressBar>(R.id.progressBarRating)
        var bookmarkButton = itemView.findViewById<ShapeableImageView>(R.id.bookmarkButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_pcomplexdetails, parent, false)
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
        holder.email.setText(model.emailId)
        holder.bookmarkButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        holder.bookmarkButton.tag = R.drawable.ic_baseline_favorite_border_24
        
        // Calculate the average of rating
        val ratingRef = FirebaseDatabase.getInstance().reference
            .child("Ratings")
        var storeRatings: MutableList<Float> = mutableListOf<Float>()

        ratingRef.get().addOnSuccessListener {
            if(!it.child(getRef(position).key.toString()).exists()){
                holder.ratingBar.visibility = INVISIBLE
                holder.noRating.visibility = VISIBLE
                holder.progressBarRating.visibility = GONE
                holder.noRating.setText("New Complex")
            }
            else{
                var snapshot = it.child(getRef(position).key.toString())
                for(dss in snapshot.children){
                    val rate = dss.child("rating").getValue().toString()
                    storeRatings.add(rate.toFloat())
                }
                var averageRating = 0.0
                var sum = 0.0

                for(i in storeRatings){
                    sum += i
                }

                averageRating = sum/storeRatings.size
                holder.ratingBar.rating = averageRating.toFloat()
                holder.ratingBar.visibility = VISIBLE
                holder.noRating.visibility = INVISIBLE
                holder.progressBarRating.visibility = GONE
            }
        }

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
            .override(600, 400)
            .placeholder(R.drawable.loading_image)
            .into(holder.image)

        // favorites image check--> if snapshot exists then red heart else white border heart


        //on click on card and start the Lender Share activity
        holder.card.setOnClickListener {

            val intent = Intent(holder.name.context, PlayerSharedActivity::class.java)

            // Transfer all the required data to the Shared activity by putExtra
            intent.putExtra(PlayerSharedActivity.EXTRA_IMAGEURI, model.imageUri.toString())
            intent.putExtra(PlayerSharedActivity.EXTRA_NAME, holder.name.text.toString())
            intent.putExtra(PlayerSharedActivity.EXTRA_PHONE, holder.phone.text.toString())
            intent.putExtra(PlayerSharedActivity.EXTRA_SPORT, holder.type.text.toString())
            intent.putExtra(PlayerSharedActivity.EXTRA_LOCATION, holder.location.text.toString())
            intent.putExtra(
                PlayerSharedActivity.EXTRA_DESCRIPTION,
                holder.description.text.toString()
            )
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

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                holder.name.context as Activity,
                p1,
                p2,
                p3,
                p4,
                p5,
                p6,
                p7,
                p8,
                p9,
                p10
            )

            // Start the Shared activity with the transition
            holder.name.context.startActivity(intent, options.toBundle())
        }

        holder.bookCourt.setOnClickListener {

            // if already rated by the user then the button should be not clickable with text = Booked
            FirebaseDatabase.getInstance().reference
                .child("Booked Complexes")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child("")

            var intent = Intent(holder.name.context, PlayerBookDateActivity::class.java)

            // Transfer all the required data to the PlayerBookCourt Activity by putExtra
            intent.putExtra(PlayerBookDateActivity.EXTRA_IMAGEURI, model.imageUri.toString())
            intent.putExtra(PlayerBookDateActivity.EXTRA_NAME, holder.name.text.toString())
            intent.putExtra(PlayerBookDateActivity.EXTRA_PHONE, holder.phone.text.toString())
            intent.putExtra(PlayerBookDateActivity.EXTRA_SPORT, holder.type.text.toString())
            intent.putExtra(PlayerBookDateActivity.EXTRA_LOCATION, holder.location.text.toString())
            intent.putExtra(
                PlayerBookDateActivity.EXTRA_DESCRIPTION,
                holder.description.text.toString()
            )
            intent.putExtra(PlayerBookDateActivity.EXTRA_PRICE, holder.price.text.toString())
            intent.putExtra(PlayerBookDateActivity.EXTRA_COURTS, holder.courts.text.toString())
            intent.putExtra(PlayerBookDateActivity.EXTRA_EMAILID, holder.email.text.toString())
            intent.putExtra(PlayerBookDateActivity.EXTRA_RATING, holder.ratingBar.rating.toString())
            intent.putExtra(PlayerBookDateActivity.EXTRA_KEYID, getRef(position).key.toString())

            holder.name.context.startActivity(intent)
        }

        var ref = FirebaseDatabase.getInstance().reference
            .child("Favorite Complexes")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child(getRef(position).key.toString())

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    holder.bookmarkButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                    holder.bookmarkButton.tag = R.drawable.ic_baseline_favorite_24
                }
                else{
                    holder.bookmarkButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    holder.bookmarkButton.tag = R.drawable.ic_baseline_favorite_border_24
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

        holder.bookmarkButton.setOnClickListener {
            ref.get().addOnSuccessListener {
                if(it.exists()){
                    ref.removeValue().addOnCompleteListener {
                            holder.bookmarkButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                            holder.bookmarkButton.tag = R.drawable.ic_baseline_favorite_border_24
                            Toast.makeText(holder.name.context, "Favorite removed!", Toast.LENGTH_SHORT).show()
                        }
                }
                else{
                    ref.setValue(model).addOnSuccessListener {
                            holder.bookmarkButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                            holder.bookmarkButton.tag = R.drawable.ic_baseline_favorite_24
                            Toast.makeText(holder.name.context, "Favorite added!", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }

//        holder.bookmarkButton.setOnClickListener {
//            if(holder.bookmarkButton.tag == R.drawable.ic_baseline_favorite_border_24) {
////                holder.bookmarkButton.strokeWidth = 2F
////                holder.bookmarkButton.strokeColor = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
//
//                ref.child(FirebaseAuth.getInstance().currentUser!!.uid)
//                    .child(getRef(position).key.toString())
//                    .setValue(model).addOnSuccessListener {
//                        holder.bookmarkButton.setImageResource(R.drawable.ic_baseline_favorite_24)
//                        holder.bookmarkButton.tag = R.drawable.ic_baseline_favorite_24
//                        Toast.makeText(holder.name.context, "Favorite added!", Toast.LENGTH_SHORT).show()
//                    }
//
//            }
//            else{
//
//                ref.child(FirebaseAuth.getInstance().currentUser!!.uid)
//                    .child(getRef(position).key.toString())
//                    .removeValue().addOnCompleteListener {
//                        holder.bookmarkButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
//                        holder.bookmarkButton.tag = R.drawable.ic_baseline_favorite_border_24
//
//                    }
//            }
//        }

    }

}