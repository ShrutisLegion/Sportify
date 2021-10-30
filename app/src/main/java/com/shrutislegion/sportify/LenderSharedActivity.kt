package com.shrutislegion.sportify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_lender_shared.*
import kotlinx.android.synthetic.main.item_complexdetails.*
import kotlinx.android.synthetic.main.item_complexdetails.complexLocation
import kotlinx.android.synthetic.main.item_complexdetails.nameOfComplex
import kotlinx.android.synthetic.main.item_complexdetails.hourPrice
import kotlinx.android.synthetic.main.item_complexdetails.courtsCount
import kotlinx.android.synthetic.main.item_complexdetails.complexDescription

class LenderSharedActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_NAME= "name_extra"
//        const val EXTRA_PHONE= "phone_extra"
        const val EXTRA_SPORT= "sport_extra"
        const val EXTRA_LOCATION= "location_extra"
        const val EXTRA_DESCRIPTION= "description_extra"
        const val EXTRA_PRICE= "price_extra"
        const val EXTRA_COURTS= "courts_extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lender_shared)

        val name = intent.getStringExtra(EXTRA_NAME)
//        val phone = intent.getStringExtra(EXTRA_PHONE)
        val sport = intent.getStringExtra(EXTRA_SPORT)
        val location = intent.getStringExtra(EXTRA_LOCATION)
        val description = intent.getStringExtra(EXTRA_DESCRIPTION)
        val price = intent.getStringExtra(EXTRA_PRICE)
        val courts = intent.getStringExtra(EXTRA_COURTS)
        nameOfComplex.setText("$name")
//        phoneNumber.setText("$phone")
        nameOfComplex.setText("$sport")
        complexLocation.setText("$location")
        complexDescription.setText("$description")
        hourPrice.setText("$price")
        courtsCount.setText("$courts")
    }
}