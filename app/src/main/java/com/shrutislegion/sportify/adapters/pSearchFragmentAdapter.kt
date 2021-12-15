package com.shrutislegion.sportify.adapters

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.shrutislegion.sportify.R
import com.shrutislegion.sportify.modules.BookedComplexInfo
import com.shrutislegion.sportify.modules.ComplexRating
import com.shrutislegion.sportify.player_activities.PlayerRatingActivity

class pSearchFragmentAdapter(options: FirebaseRecyclerOptions<BookedComplexInfo>)
    : FirebaseRecyclerAdapter<BookedComplexInfo, pSearchFragmentAdapter.myViewHolder>(options){


    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        // creating viewHolder and getting all the required views by their Ids
        val name = itemView.findViewById<TextView>(R.id.nameOfComplex)
        val type = itemView.findViewById<TextView>(R.id.sportType)
        val courts = itemView.findViewById<TextView>(R.id.courtsCount)
        val price = itemView.findViewById<TextView>(R.id.hourPrice)
        val location = itemView.findViewById<TextView>(R.id.complexLocation)
        var image = itemView.findViewById<ImageView>(R.id.complexImage)
        var phone = itemView.findViewById<TextView>(R.id.phoneNumber)
        var hoursBooked = itemView.findViewById<TextView>(R.id.hoursBookedInfo)
        var card = itemView.findViewById<CardView>(R.id.card)
        var ratingBar = itemView.findViewById<RatingBar>(R.id.complexRatingBar)
        var progressBarPCard = itemView.findViewById<ProgressBar>(R.id.progressBarPCard)
        var email = itemView.findViewById<TextView>(R.id.emailId)
        var ratingButton = itemView.findViewById<ImageView>(R.id.ratingButton)
        var noRating = itemView.findViewById<TextView>(R.id.noRatingView)
        var progressBarRating = itemView.findViewById<ProgressBar>(R.id.progressBarRating)
//        var bottomBookedSheet = itemView.findViewById<FrameLayout>(R.id.bottomBookedSheet)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): pSearchFragmentAdapter.myViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_pbookedcomplex, parent, false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: pSearchFragmentAdapter.myViewHolder,
        position: Int,
        model: BookedComplexInfo
    ) {
        holder.name.setText(model.complexName)
        holder.type.setText(model.typeOfSport)
//        holder.price.setText(model.pricePerHour)
        holder.location.setText(model.location)

//        BottomSheetBehavior.from(holder.bottomBookedSheet).apply {
//            peekHeight = 200
//            this.state = BottomSheetBehavior.STATE_COLLAPSED
//        }

        // Calculate the average of rating
        val ratingRef = FirebaseDatabase.getInstance().reference
            .child("Ratings")
            .child(getRef(position).key.toString())
        var storeRatings: MutableList<Float> = mutableListOf<Float>()

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI

                val info = dataSnapshot.getValue()

                if(info == null){
                    holder.ratingBar.visibility = View.INVISIBLE
                    holder.noRating.visibility = View.VISIBLE
                    holder.progressBarRating.visibility = View.GONE
                    holder.noRating.setText("New Complex")
                }
                else{
                    for(i in dataSnapshot.children){
                        val compinfo: ComplexRating = i.getValue<ComplexRating>()!!
                        storeRatings.add(compinfo.rating!!.toFloat())
                    }
                    var averageRating = 0.0
                    var sum = 0.0

                    for(i in storeRatings){
                        sum += i
                    }

                    averageRating = sum/storeRatings.size
                    holder.ratingBar.rating = averageRating.toFloat()
                    holder.ratingBar.visibility = View.VISIBLE
                    holder.noRating.visibility = View.INVISIBLE
                    holder.progressBarRating.visibility = View.GONE
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        ratingRef.addValueEventListener(postListener)
        

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

        var displayList: MutableList<Int>? = model.bookedHours
        var displaySet: MutableSet<Int>? = mutableSetOf<Int>()
        var displaymsg = ""

        if(displayList!!.size == 0){
            displaymsg = ""
            holder.hoursBooked.setText(displaymsg)
        }
        else{
            val ref = FirebaseDatabase.getInstance().reference
                .child("Booked Complexes")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child(getRef(position).key.toString())
                .child("bookedHours")

            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (dss in snapshot.children) {
                            val timeName = dss.getValue().toString()
                            displaySet!!.add(timeName.toInt())
                        }
                        for(i in displaySet!!){
                            displaymsg = displaymsg + i.toString() + " "
                        }
                        holder.hoursBooked.setText(displaymsg)
                    }
                    else{
                        displaymsg = "No Booked Hours"
                        holder.hoursBooked.setText(displaymsg)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }


        holder.ratingButton.setOnClickListener{
            // rate and review court

            // Ratings -> complexUid -> userUid -> snapshot -> rating exists
            val ref = FirebaseDatabase.getInstance().reference
                .child("Ratings")
                .child(getRef(position).key.toString())
                .child(FirebaseAuth.getInstance().currentUser!!.uid.toString())

            ref.get().addOnSuccessListener {

                var previt = it
                if (it.child("complexName").getValue() === null
                    || it.child("rating").getValue() === null
                    || it.child("review").getValue() === null
                ) {
                    var intent =
                        Intent(holder.name.context, PlayerRatingActivity::class.java)
                    intent.putExtra(
                        PlayerRatingActivity.EXTRA_RATING,
                        "0"
                    )
                    intent.putExtra(
                        PlayerRatingActivity.EXTRA_REVIEW,
                        ""
                    )
                    intent.putExtra(PlayerRatingActivity.EXTRA_NAME, holder.name.text.toString())
                    intent.putExtra(PlayerRatingActivity.EXTRA_KEYID, getRef(position).key.toString())
                    holder.name.context.startActivity(
                        intent
                    )
                } else {
                    MaterialAlertDialogBuilder(holder.name.context).also {
                        // set title for dailog box
                        it.setTitle("You have already reviewed the complex")
                        // set message for dialog box
                        it.setMessage("Are you sure you want to edit your previous review?")
                        // set icon for dialog box
                        it.setIcon(R.drawable.ic_baseline_warning_24)

                        // perform positive action which shares some info and starts the PlayerRatingActivity
                        it.setPositiveButton("YES") { dialogInterface, which ->

                            var intent =
                                Intent(holder.name.context, PlayerRatingActivity::class.java)
                            intent.putExtra(
                                PlayerRatingActivity.EXTRA_RATING,
                                previt.child("rating").getValue().toString()
                            )
                            intent.putExtra(
                                PlayerRatingActivity.EXTRA_REVIEW,
                                previt.child("review").getValue().toString()
                            )
                            intent.putExtra(PlayerRatingActivity.EXTRA_NAME, holder.name.text.toString())
                            intent.putExtra(PlayerRatingActivity.EXTRA_KEYID, getRef(position).key.toString())

                            holder.name.context.startActivity(intent)
                        }

                        // performs neutral action
                        it.setNeutralButton("CANCEL") { dialogInterface, which ->
                        }

                        // performs negative/NO action
                        it.setNegativeButton("NO") { dialogInterface, which ->
                            Toast.makeText(
                                holder.name.context,
                                "Process cancelled !!",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        // create the AlertDialogBox
                        val alertDialog: androidx.appcompat.app.AlertDialog = it.create()
                        alertDialog.setCancelable(false)
                        alertDialog.show()
                    }

                }

            }

        }


    }
}