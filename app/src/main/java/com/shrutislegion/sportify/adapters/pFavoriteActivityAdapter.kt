package com.shrutislegion.sportify.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.shrutislegion.sportify.R
import com.shrutislegion.sportify.modules.ComplexInfo

class pFavoriteActivityAdapter(options: FirebaseRecyclerOptions<ComplexInfo>)
    : FirebaseRecyclerAdapter<ComplexInfo, pFavoriteActivityAdapter.myViewHolder>(options){

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var image = itemView.findViewById<ImageView>(R.id.complexImage)
        val name = itemView.findViewById<TextView>(R.id.nameOfComplex)
        val type = itemView.findViewById<TextView>(R.id.sportType)
        val location = itemView.findViewById<TextView>(R.id.complexLocation)
        var description = itemView.findViewById<TextView>(R.id.complexDescription)
        var progressBarPCard = itemView.findViewById<ProgressBar>(R.id.progressBarPCard)
        var bookmarkButton = itemView.findViewById<ShapeableImageView>(R.id.bookmarkButton)
        var ratingTextView = itemView.findViewById<TextView>(R.id.ratingTextView)
        var pricePerHourInfo = itemView.findViewById<TextView>(R.id.pricePerHourInfo)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): pFavoriteActivityAdapter.myViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_pfavoritecomplex, parent, false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int, model: ComplexInfo) {

        holder.name.setText(model.complexName)
        holder.type.setText(model.typeOfSport)
        holder.location.setText(model.location)
        holder.description.setText(model.description)

        holder.pricePerHourInfo.setText("Rs.${model.pricePerHour} per hour")

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

        // calculation of the average rating implemented
        val ratingRef = FirebaseDatabase.getInstance().reference
            .child("Ratings")
        var storeRatings: MutableList<Float> = mutableListOf<Float>()

        ratingRef.get().addOnSuccessListener {
            if(it.child(getRef(position).key.toString()).exists()){

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

                holder.ratingTextView.setText("${averageRating.toFloat()}")

            }
            else{

                holder.ratingTextView.setText(" -")

            }
        }


        holder.bookmarkButton.setOnClickListener {

            MaterialAlertDialogBuilder(holder.name.context).also {
                // set title for dailog box
                it.setTitle("Remove from favourites")
                // set icon for dialog box
                it.setIcon(R.drawable.ic_baseline_bookmark_remove_24)

                // perform positive action which deletes details from the lender activity and player activity
                it.setPositiveButton("YES") { dialogInterface, which ->

                    FirebaseDatabase.getInstance().reference
                        .child("Favorite Complexes")
                        .child(FirebaseAuth.getInstance().currentUser!!.uid)
                        .child(getRef(position).key.toString()).removeValue()
                        .addOnCompleteListener {
                            holder.bookmarkButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                            holder.bookmarkButton.tag = R.drawable.ic_baseline_favorite_border_24
                            Toast.makeText(
                                holder.name.context,
                                "Favorite removed!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }

                // performs neutral action
                it.setNeutralButton("CANCEL"){
                        dialogInterface, which->
                }

                // performs negative/NO action
                it.setNegativeButton("NO"){
                        dialogInterface, which->
                    Toast.makeText(holder.name.context, "Operation cancelled!", Toast.LENGTH_SHORT).show()
                }

                // create the AlertDialogBox
                val alertDialog: androidx.appcompat.app.AlertDialog = it.create()
                alertDialog.setCancelable(false)
                alertDialog.show()

            }

        }

    }
}