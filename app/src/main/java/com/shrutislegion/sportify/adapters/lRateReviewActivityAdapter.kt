// Adapter is used in the Rate and Review Activity
// Lender can go through the ratings that the players gave, which helps in improving the facilities

package com.shrutislegion.sportify.adapters

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.content.Context
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shrutislegion.sportify.R
import com.shrutislegion.sportify.modules.ComplexRating
import kotlinx.android.synthetic.main.item_l_rate_review.*
import org.w3c.dom.Text

class lRateReviewActivityAdapter(var storeRR: ArrayList<ComplexRating>, val context: Context) :
    RecyclerView.Adapter<lRateReviewActivityAdapter.viewHolder>(){

    // Creating a viewHolder which takes the fields from the xml layout
    class viewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        var playerProfilePicture = itemView.findViewById<ImageView>(R.id.playerProfilePicture)
        var playerName = itemView.findViewById<TextView>(R.id.playerName)
        var playerRating = itemView.findViewById<RatingBar>(R.id.playerRating)
        var playerReview = itemView.findViewById<TextView>(R.id.playerReview)
        var lenderRateReviewCardView = itemView.findViewById<CardView>(R.id.lenderRateReviewCardView)
        var constraintLayoutRR = itemView.findViewById<ConstraintLayout>(R.id.constraintLayoutRR)
        var complexName = itemView.findViewById<TextView>(R.id.complexName)
        var sportType = itemView.findViewById<TextView>(R.id.sportType)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): lRateReviewActivityAdapter.viewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_l_rate_review, parent, false)

        return viewHolder(view)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: lRateReviewActivityAdapter.viewHolder, position: Int) {

        var model = storeRR[position]

        holder.complexName.text = model.complexName
        holder.sportType.text = model.typeOfSport
        holder.playerName.text = model.displayName
        holder.playerRating.rating = model.rating!!.toFloat()
        holder.playerReview.text = model.review
        Glide.with(holder.playerProfilePicture.context).load(model.photoUrl).override(600,400)
            .placeholder(R.drawable.loading_image)
            .into(holder.playerProfilePicture)

        holder.constraintLayoutRR.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

        // Transition which makes the cardView expandable on clicking it
        holder.lenderRateReviewCardView.setOnClickListener {

            if(holder.playerReview.visibility == View.GONE){

                TransitionManager.beginDelayedTransition(holder.constraintLayoutRR, AutoTransition())
                holder.playerReview.visibility = VISIBLE

            }
            else{

                TransitionManager.beginDelayedTransition(holder.constraintLayoutRR, AutoTransition())
                holder.playerReview.visibility = GONE

            }



        }

    }

    // returns the size
    override fun getItemCount(): Int {
        return  storeRR.size
    }
}