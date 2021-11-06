package com.shrutislegion.sportify.playeractivities

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import android.widget.Toast
import com.shrutislegion.sportify.R
import kotlinx.android.synthetic.main.activity_player_rating.*
import java.lang.Float.parseFloat

class PlayerRatingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_rating)

        complexRatingBar.setOnRatingBarChangeListener { ratingBar, rating, p2 ->

            var s = "0"
            showStars.visibility = View.VISIBLE

            if(rating === parseFloat(s)) showStars.setText("Please give your rating")
            else{
                var stars = complexRatingBar.rating
                showStars.setText("$stars STAR")
            }

        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, PlayerHomeActivity::class.java))
    }
}