package com.shrutislegion.sportify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_add_complex.*
import kotlinx.android.synthetic.main.activity_lender_shared.*
import kotlinx.android.synthetic.main.activity_lender_shared.complexDescription
import kotlinx.android.synthetic.main.activity_lender_shared.complexLocation
import kotlinx.android.synthetic.main.activity_lender_shared.phoneNumber

class LenderSharedActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_NAME= "name_extra"
        const val EXTRA_PHONE= "phone_extra"
        const val EXTRA_SPORT= "sport_extra"
        const val EXTRA_LOCATION= "location_extra"
        const val EXTRA_DESCRIPTION= "description_extra"
        const val EXTRA_PRICE= "price_extra"
        const val EXTRA_COURTS= "courts_extra"
        const val EXTRA_IMAGEURI = "image_extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lender_shared)

        var name = intent.getStringExtra(EXTRA_NAME)
        var phone = intent.getStringExtra(EXTRA_PHONE)
        var sport = intent.getStringExtra(EXTRA_SPORT)
        var location = intent.getStringExtra(EXTRA_LOCATION)
        var description = intent.getStringExtra(EXTRA_DESCRIPTION)
        var price = intent.getStringExtra(EXTRA_PRICE)
        var courts = intent.getStringExtra(EXTRA_COURTS)
        var imageUri = intent.getStringExtra(EXTRA_IMAGEURI)

        Glide.with(this).load(imageUri).placeholder(R.drawable.loading_image).into(complexImage)
        nameOfComplex.setText("$name")
        phoneNumber.setText("$phone")
        sportType.setText("$sport")
        complexLocation.setText("$location")
        complexDescription.setText("$description")
        hourPrice.setText("$price")
        courtsCount.setText("$courts")
    }
}