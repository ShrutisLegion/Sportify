package com.shrutislegion.sportify.playeractivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.shrutislegion.sportify.R
import kotlinx.android.synthetic.main.activity_player_shared.*

class PlayerSharedActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_NAME= "name_extra"
        const val EXTRA_PHONE= "phone_extra"
        const val EXTRA_SPORT= "sport_extra"
        const val EXTRA_LOCATION= "location_extra"
        const val EXTRA_DESCRIPTION= "description_extra"
        const val EXTRA_PRICE= "price_extra"
        const val EXTRA_COURTS= "courts_extra"
        const val EXTRA_IMAGEURI = "image_extra"
        const val EXTRA_EMAILID = "email_extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_shared)

        // To get the shared intent data
        var name = intent.getStringExtra(PlayerSharedActivity.EXTRA_NAME)
        var phone = intent.getStringExtra(PlayerSharedActivity.EXTRA_PHONE)
        var sport = intent.getStringExtra(PlayerSharedActivity.EXTRA_SPORT)
        var location = intent.getStringExtra(PlayerSharedActivity.EXTRA_LOCATION)
        var description = intent.getStringExtra(PlayerSharedActivity.EXTRA_DESCRIPTION)
        var price = intent.getStringExtra(PlayerSharedActivity.EXTRA_PRICE)
        var courts = intent.getStringExtra(PlayerSharedActivity.EXTRA_COURTS)
        var imageUri = intent.getStringExtra(PlayerSharedActivity.EXTRA_IMAGEURI)
        var email = intent.getStringExtra(PlayerSharedActivity.EXTRA_EMAILID)

        // To update the textViews and load image in the Shared activity
        // Used glide library to load image from the Uri stored in Firebase Realtime database
        Glide.with(this).load(imageUri).placeholder(R.drawable.loading_image).into(complexImage)

        nameOfComplex.setText("$name")
        phoneNumber.setText("$phone")
        sportType.setText("$sport")
        complexLocation.setText("$location")
        complexDescription.setText("$description")
        hourPrice.setText("₹ $price")
        courtsCount.setText("$courts")
        emailId.setText("$email")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, PlayerHomeActivity::class.java))
    }
}