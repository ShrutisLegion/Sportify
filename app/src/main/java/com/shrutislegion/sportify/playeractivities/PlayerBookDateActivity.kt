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

            // store both-> headerText and selection
            bookingDate.text = datePicker.headerText
            confirmBookingButton.visibility = VISIBLE
            bookingDate.visibility = VISIBLE

            //Count the number of selected hours and store the selected hours
//            var cnt = 0;
            var storeHours: MutableSet<Int> = mutableSetOf<Int>();

            // onClickListener on all the time buttons
            button1.setOnClickListener {
                if (button1.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#FFFFFF"))) {
                    button1.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                    button1.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(0);
                }
                else {
                    button1.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button1.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(0);
                }
                Toast.makeText(this, "There are ${storeHours.size} selections", Toast.LENGTH_LONG).show()
            }
            button2.setOnClickListener {
                if (button2.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#FFFFFF"))) {
                    button2.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                    button2.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(1);
                }
                else{
                    button2.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button2.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(1);
                }
                Toast.makeText(this, "There are ${storeHours.size} selections", Toast.LENGTH_LONG).show()
            }
            button3.setOnClickListener {
                if (button3.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#FFFFFF"))) {
                    button3.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                    button3.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(2);
                }
                else{
                    button3.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button3.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(2);
                }
                Toast.makeText(this, "There are ${storeHours.size} selections", Toast.LENGTH_LONG).show()
            }
            button4.setOnClickListener {
                if (button4.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#FFFFFF"))) {
                    button4.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                    button4.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(3);
                }
                else{
                    button4.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button4.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(3);
                }
                Toast.makeText(this, "There are ${storeHours.size} selections", Toast.LENGTH_LONG).show()
            }
            button5.setOnClickListener {
                if (button5.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#FFFFFF"))) {
                    button5.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                    button5.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(4);
                }
                else{
                    button5.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button5.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(4);
                }
                Toast.makeText(this, "There are ${storeHours.size} selections", Toast.LENGTH_LONG).show()
            }
            button6.setOnClickListener {
                if (button6.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#FFFFFF"))) {
                    button6.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                    button6.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(5);
                }
                else{
                    button6.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button6.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(5);
                }
                Toast.makeText(this, "There are ${storeHours.size} selections", Toast.LENGTH_LONG).show()
            }
            button7.setOnClickListener {
                if (button7.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#FFFFFF"))) {
                    button7.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                    button7.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(6);
                }
                else{
                    button7.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button7.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(6);
                }
                Toast.makeText(this, "There are ${storeHours.size} selections", Toast.LENGTH_LONG).show()
            }
            button8.setOnClickListener {
                if (button8.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#FFFFFF"))) {
                    button8.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                    button8.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(7);
                }
                else{
                    button8.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button8.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(7);
                }
                Toast.makeText(this, "There are ${storeHours.size} selections", Toast.LENGTH_LONG).show()
            }
            button9.setOnClickListener {
                if (button9.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#FFFFFF"))) {
                    button9.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                    button9.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(8);
                }
                else{
                    button9.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button9.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(8);
                }
                Toast.makeText(this, "There are ${storeHours.size} selections", Toast.LENGTH_LONG).show()
            }
            button10.setOnClickListener {
                if (button10.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#FFFFFF"))) {
                    button10.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                    button10.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(9);
                }
                else{
                    button10.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button10.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(9);
                }
                Toast.makeText(this, "There are ${storeHours.size} selections", Toast.LENGTH_LONG).show()
            }
            button11.setOnClickListener {
                if (button11.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#FFFFFF"))) {
                    button11.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                    button11.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(10);
                }
                else{
                    button11.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button11.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(10);
                }
                Toast.makeText(this, "There are ${storeHours.size} selections", Toast.LENGTH_LONG).show()
            }
            button12.setOnClickListener {
                if (button12.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#FFFFFF"))) {
                    button12.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                    button12.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(11);
                }
                else{
                    button12.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button12.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(11);
                }
                Toast.makeText(this, "There are ${storeHours.size} selections", Toast.LENGTH_LONG).show()
            }
            button13.setOnClickListener {
                if (button13.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#FFFFFF"))) {
                    button13.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                    button13.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(12);
                }
                else{
                    button13.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button13.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(12);
                }
                Toast.makeText(this, "There are ${storeHours.size} selections", Toast.LENGTH_LONG).show()
            }
            button14.setOnClickListener {
                if (button14.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#FFFFFF"))) {
                    button14.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                    button14.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(13);
                }
                else{
                    button14.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button14.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(13);
                }
                Toast.makeText(this, "There are ${storeHours.size} selections", Toast.LENGTH_LONG).show()
            }
            button15.setOnClickListener {
                if (button15.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#FFFFFF"))) {
                    button15.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                    button15.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(14);
                }
                else{
                    button15.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button15.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(14);
                }
                Toast.makeText(this, "There are ${storeHours.size} selections", Toast.LENGTH_LONG).show()
            }
            button16.setOnClickListener {
                if (button16.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#FFFFFF"))) {
                    button16.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                    button16.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(15);
                }
                else{
                    button16.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button16.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(15);
                }
                Toast.makeText(this, "There are ${storeHours.size} selections", Toast.LENGTH_LONG).show()
            }
            button17.setOnClickListener {
                if (button17.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#FFFFFF"))) {
                    button17.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                    button17.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(16);
                }
                else{
                    button17.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button17.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(16);
                }
                Toast.makeText(this, "There are ${storeHours.size} selections", Toast.LENGTH_LONG).show()
            }
            button18.setOnClickListener {
                if (button18.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#FFFFFF"))) {
                    button18.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                    button18.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(17);
                }
                else{
                    button18.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button18.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(17);
                }
                Toast.makeText(this, "There are ${storeHours.size} selections", Toast.LENGTH_LONG).show()
            }
            button19.setOnClickListener {
                if (button19.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#FFFFFF"))) {
                    button19.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                    button19.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(18);
                }
                else{
                    button19.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button19.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(18);
                }
                Toast.makeText(this, "There are ${storeHours.size} selections", Toast.LENGTH_LONG).show()
            }
            button20.setOnClickListener {
                if (button20.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#FFFFFF"))) {
                    button20.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                    button20.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(19);
                }
                else{
                    button20.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button20.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(19);
                }
                Toast.makeText(this, "There are ${storeHours.size} selections", Toast.LENGTH_LONG).show()
            }
            button21.setOnClickListener {
                if (button21.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#FFFFFF"))) {
                    button21.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                    button21.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(20);
                }
                else{
                    button21.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button21.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(20);
                }
                Toast.makeText(this, "There are ${storeHours.size} selections", Toast.LENGTH_LONG).show()
            }
            button22.setOnClickListener {
                if (button22.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#FFFFFF"))) {
                    button22.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                    button22.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(21);
                }
                else{
                    button22.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button22.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(21);
                }
                Toast.makeText(this, "There are ${storeHours.size} selections", Toast.LENGTH_LONG).show()
            }
            button23.setOnClickListener {
                if (button23.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#FFFFFF"))) {
                    button23.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                    button23.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(22);
                }
                else{
                    button23.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button23.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(22);
                }
                Toast.makeText(this, "There are ${storeHours.size} selections", Toast.LENGTH_LONG).show()
            }
            button24.setOnClickListener {
                if (button24.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#FFFFFF"))) {
                    button24.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                    button24.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(23);
                }
                else{
                    button24.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button24.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(23);
                }
                Toast.makeText(this, "There are ${storeHours.size} selections", Toast.LENGTH_LONG).show()
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