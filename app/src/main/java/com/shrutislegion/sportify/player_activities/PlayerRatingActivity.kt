package com.shrutislegion.sportify.player_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthSettings
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.shrutislegion.sportify.R
import com.shrutislegion.sportify.modules.ComplexRating
import kotlinx.android.synthetic.main.activity_player_rating.*
import java.lang.Float.parseFloat

class PlayerRatingActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_NAME= "name_extra"
        const val EXTRA_KEYID = "keyid_extra"
        const val EXTRA_RATING = "rating_extra"
        const val EXTRA_REVIEW = "review_extra"
        const val EXTRA_SPORT = "sport_extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_rating)

        // To get the shared intent data
        var name = intent.getStringExtra(PlayerRatingActivity.EXTRA_NAME)
        var keyid = intent.getStringExtra(PlayerRatingActivity.EXTRA_KEYID)
        var rating = intent.getStringExtra(PlayerRatingActivity.EXTRA_RATING)
        var review = intent.getStringExtra(PlayerRatingActivity.EXTRA_REVIEW)
        var sport = intent.getStringExtra(PlayerRatingActivity.EXTRA_SPORT)

        var lenderId = ""
        FirebaseDatabase.getInstance().reference.child("All Complexes")
            .child("$keyid").child("uid").get().addOnSuccessListener {

                lenderId = it.getValue().toString()

            }

        // set the values to fields
        titleHeading.setText("$name")

        if(rating!="0" && review!=""){
            Toast.makeText(this, "Previous rating shown !!", Toast.LENGTH_LONG).show()
            complexRatingBar.rating = parseFloat(rating)
            complexCommentText.setText("$review")
            showStars.visibility = View.VISIBLE
            var stars = complexRatingBar.rating
            showStars.setText("$stars STAR")
        }
        else{
            showStars.visibility = View.VISIBLE
            showStars.setText("Please give your rating")
        }

        complexRatingBar.setOnRatingBarChangeListener { ratingBar, rating, p2 ->

            var s = "0"
            showStars.visibility = View.VISIBLE

            if(rating === parseFloat(s)) showStars.setText("Please give your rating")
            else{
                var stars = complexRatingBar.rating
                showStars.setText("$stars STAR")
            }

        }

        submitButtton.setOnClickListener {

            var complexName = "$name"

            if (complexRatingBar.rating === parseFloat("0")) {
                Toast.makeText(this, "Please give your rating !", Toast.LENGTH_LONG).show()
            } else if (complexCommentText.text!!.isEmpty()) {
                Toast.makeText(this, "Please give your review !", Toast.LENGTH_LONG).show()
            } else {

                val User = ComplexRating(
                    complexRatingBar.rating.toString()+"",
                    complexCommentText.text.toString()+"",
                    complexName+"",
                    FirebaseAuth.getInstance().currentUser!!.displayName+"",
                    FirebaseAuth.getInstance().currentUser!!.photoUrl.toString()+"",
                    FirebaseAuth.getInstance().currentUser!!.email+"",
                    lenderId.toString(),
                    sport+""

                )

                FirebaseDatabase.getInstance().getReference("Ratings")
                    .child("$keyid")
                    .child(FirebaseAuth.getInstance().currentUser!!.uid.toString())
                    .setValue(User).addOnSuccessListener {
                        Toast.makeText(this, "Complex rating added !!", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, PlayerHomeActivity::class.java))
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Complex rating unable to add !!", Toast.LENGTH_LONG)
                            .show()
                    }

                Handler(Looper.getMainLooper())
                    .postDelayed({

                        // Calculate the average of rating
                        val ratingRef = FirebaseDatabase.getInstance().reference
                            .child("Ratings")
                            .child("$keyid")

                        var storeRatings: MutableList<Float> = mutableListOf<Float>()

                        val postListener = object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                // Get Post object and use the values to update the UI

                                val info = dataSnapshot.getValue()

                                if(info == null){
                                }
                                else{
                                    for(i in dataSnapshot.children){
                                        val compinfo: ComplexRating = i.getValue<ComplexRating>()!!
                                        storeRatings.add(compinfo.rating!!.toFloat())
                                    }
                                    var averageRating = 0F
                                    var sum = 0.0

                                    for(i in storeRatings){
                                        sum += i
                                    }

                                    averageRating = (sum/storeRatings.size).toFloat()

                                    FirebaseDatabase.getInstance().reference
                                        .child("All Complexes")
                                        .child("$keyid")
                                        .child("complexRating")
                                        .setValue(averageRating)

                                }

                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                // Getting Post failed, log a message
                            }
                        }
                        ratingRef.addValueEventListener(postListener)


                    },2000)


            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, PlayerHomeActivity::class.java))
//        supportFragmentManager.beginTransaction().replace(R.id.fragment_ratingContainer, pSearchFragment()).commitAllowingStateLoss()
//        finish()

    }
}