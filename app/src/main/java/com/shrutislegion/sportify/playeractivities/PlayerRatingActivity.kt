package com.shrutislegion.sportify.playeractivities

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_rating)

        // To get the shared intent data
        var name = intent.getStringExtra(PlayerRatingActivity.EXTRA_NAME)
        var keyid = intent.getStringExtra(PlayerRatingActivity.EXTRA_KEYID)
        var rating = intent.getStringExtra(PlayerRatingActivity.EXTRA_RATING)
        var review = intent.getStringExtra(PlayerRatingActivity.EXTRA_REVIEW)

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
                    complexRatingBar.rating.toString(),
                    complexCommentText.text.toString(),
                    complexName

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
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, PlayerHomeActivity::class.java))
    }
}