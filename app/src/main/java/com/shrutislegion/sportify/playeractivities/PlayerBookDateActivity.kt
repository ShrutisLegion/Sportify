package com.shrutislegion.sportify.playeractivities

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.VISIBLE
import android.widget.Toast
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.shrutislegion.sportify.R
import com.shrutislegion.sportify.lenderactivities.LenderHomeActivity
import com.shrutislegion.sportify.modules.ComplexInfo
import kotlinx.android.synthetic.main.activity_player_book_court.*
import android.view.View

class PlayerBookDateActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val EXTRA_NAME = "name_extra"
        const val EXTRA_PHONE = "phone_extra"
        const val EXTRA_SPORT = "sport_extra"
        const val EXTRA_LOCATION = "location_extra"
        const val EXTRA_DESCRIPTION = "description_extra"
        const val EXTRA_PRICE = "price_extra"
        const val EXTRA_COURTS = "courts_extra"
        const val EXTRA_IMAGEURI = "image_extra"
        const val EXTRA_EMAILID = "email_extra"
        const val EXTRA_RATING = "rating_extra"
        const val EXTRA_KEYID = "keyed_extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.shrutislegion.sportify.R.layout.activity_player_book_court)

        // To get the shared intent data
        var name = intent.getStringExtra(PlayerBookDateActivity.EXTRA_NAME)
        var phone = intent.getStringExtra(PlayerBookDateActivity.EXTRA_PHONE)
        var sport = intent.getStringExtra(PlayerBookDateActivity.EXTRA_SPORT)
        var location = intent.getStringExtra(PlayerBookDateActivity.EXTRA_LOCATION)
        var description = intent.getStringExtra(PlayerBookDateActivity.EXTRA_DESCRIPTION)
        var price = intent.getStringExtra(PlayerBookDateActivity.EXTRA_PRICE)
        var courts = intent.getStringExtra(PlayerBookDateActivity.EXTRA_COURTS)
        var imageUri = intent.getStringExtra(PlayerBookDateActivity.EXTRA_IMAGEURI)
        var email = intent.getStringExtra(PlayerBookDateActivity.EXTRA_EMAILID)
        var key = intent.getStringExtra(PlayerBookDateActivity.EXTRA_KEYID)

        var ComplexInfo = ComplexInfo(
            name,
            sport,
            price,
            courts,
            location,
            imageUri,
            phone,
            description,
            FirebaseAuth.getInstance().currentUser!!.uid,
            email
        )

        // Makes only dates from today forward selectable.
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())

        // Allows user to select date for the booking   x
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("SELECT DATE OF BOOKING")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintsBuilder.build())
                .setTheme(R.style.ThemeOverlay_MaterialComponents_MaterialCalendar_Fullscreen)
                .build()

//        MaterialDatePicker.Builder.datePicker().setCalendarConstraints(constraintsBuilder.build())

        datePicker.show(supportFragmentManager, "DATE_PICKER")

        datePicker.addOnPositiveButtonClickListener {
            // Respond to positive button click.

            bookingDate.text = datePicker.headerText
            confirmBookingButton.visibility = VISIBLE
            bookingDate.visibility = VISIBLE

            // store both-> headerText and selection
            button1.setOnClickListener {
                if (button1.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#FFFFFF"))) {
                    button1.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                    button1.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                }
                else{
                    button1.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button1.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                }
            }

        }

        datePicker.addOnNegativeButtonClickListener {
            // Respond to negative button click.
            Toast.makeText(this, "Booking Cancelled !", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, PlayerHomeActivity::class.java))
        }
        datePicker.addOnCancelListener {
            // Respond to cancel button click.
            startActivity(Intent(this, PlayerHomeActivity::class.java))
        }
        datePicker.addOnDismissListener {
            // Respond to dismiss events.
        }

//        confirmBookingButton.setOnClickListener{
//
//            FirebaseDatabase.getInstance().reference
//                .child("Booked Complexes")
//                .child(FirebaseAuth.getInstance().currentUser!!.uid)
//                .child(key.toString())
//                .setValue(ComplexInfo).addOnSuccessListener {
//                    Toast.makeText(this, "Complex booking successful !!", Toast.LENGTH_LONG).show()
//                }.addOnFailureListener {
//                    Toast.makeText(this,"Complex booking failed !!", Toast.LENGTH_LONG).show()
//                }
//
//        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, PlayerHomeActivity::class.java))
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

}