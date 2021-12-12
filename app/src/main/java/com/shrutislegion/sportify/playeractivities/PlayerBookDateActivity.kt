package com.shrutislegion.sportify.playeractivities

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.shrutislegion.sportify.R
import com.shrutislegion.sportify.modules.BookedComplexInfo
import kotlinx.android.synthetic.main.activity_player_book_court.*
import kotlinx.android.synthetic.main.activity_player_rating.*
import kotlinx.android.synthetic.main.fragment_p_home.view.*


class PlayerBookDateActivity: AppCompatActivity(){

    lateinit var countDownTimer: CountDownTimer

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

        var displayList: MutableList<Int> = mutableListOf<Int>()
        var storeHours: MutableSet<Int> = mutableSetOf<Int>()

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
        val animationView = findViewById<LottieAnimationView>(R.id.progressBarBookCourt)



        // Makes only dates from today forward selectable.
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())

        // Allows user to select date for the booking   x
        var datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("SELECT DATE OF BOOKING")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintsBuilder.build())
                .setTheme(R.style.ThemeOverlay_MaterialComponents_MaterialCalendar_Fullscreen)
                .build()

//        MaterialDatePicker.Builder.datePicker().setCalendarConstraints(constraintsBuilder.build())

        val calendaranimation = findViewById<LottieAnimationView>(R.id.calendaranimation)

        countDownTimer = object : CountDownTimer(3000, 3000){
            override fun onTick(p0: Long) {
                calendaranimation.visibility = VISIBLE
                calendaranimation.playAnimation()
            }

            override fun onFinish() {
                calendaranimation.visibility = GONE
                datePicker.show(supportFragmentManager, "DATE_PICKER")
            }

        }
        countDownTimer.start()

        datePicker.addOnPositiveButtonClickListener {
            // Respond to positive button click

            countDownTimer = object : CountDownTimer(3000, 2900) {

                override fun onTick(millisUntilFinished: Long) {

                    // show loader
                    animationView.visibility = VISIBLE
                    animationView.playAnimation()

                    val ref = FirebaseDatabase.getInstance().reference
                        .child("Booked Complexes")
                        .child(FirebaseAuth.getInstance().currentUser!!.uid)
                        .child(key.toString())
                        .child("bookedHours")

                    ref.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                Toast.makeText(baseContext, "Successs..", Toast.LENGTH_SHORT).show()
                                for (dss in snapshot.children) {
                                    val timeName = dss.getValue().toString()
                                    displayList.add(timeName.toInt())
                                }
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {}
                    })

                    for(i in displayList){
                        if(i == 0){
                            button1.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                            button1.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                            button1.isClickable = false
                        }
                        if(i == 1){
                            button2.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                            button2.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                            button2.isClickable = false
                        }
                        if(i == 2){
                            button3.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                            button3.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                            button3.isClickable = false
                        }
                        if(i == 3){
                            button4.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                            button4.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                            button4.isClickable = false
                        }
                        if(i == 4){
                            button5.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                            button5.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                            button5.isClickable = false
                        }
                        if(i == 5){
                            button6.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                            button6.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                            button6.isClickable = false
                        }
                        if(i == 6){
                            button7.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                            button7.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                            button7.isClickable = false
                        }
                        if(i == 7){
                            button8.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                            button8.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                            button8.isClickable = false
                        }
                        if(i == 8){
                            button9.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                            button9.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                            button9.isClickable = false
                        }
                        if(i == 9){
                            button10.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                            button10.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                            button10.isClickable = false
                        }
                        if(i == 10){
                            button11.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                            button11.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                            button11.isClickable = false
                        }
                        if(i == 11){
                            button12.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                            button12.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                            button12.isClickable = false
                        }
                        if(i == 12){
                            button13.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                            button13.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                            button13.isClickable = false
                        }
                        if(i == 13){
                            button14.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                            button14.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                            button14.isClickable = false
                        }
                        if(i == 14){
                            button15.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                            button15.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                            button15.isClickable = false
                        }
                        if(i == 15){
                            button16.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                            button16.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                            button16.isClickable = false
                        }
                        if(i == 16){
                            button17.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                            button17.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                            button17.isClickable = false
                        }
                        if(i == 17){
                            button18.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                            button18.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                            button18.isClickable = false
                        }
                        if(i == 18){
                            button19.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                            button19.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                            button19.isClickable = false
                        }
                        if(i == 19){
                            button20.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                            button20.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                            button20.isClickable = false
                        }
                        if(i == 20){
                            button21.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                            button21.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                            button21.isClickable = false
                        }
                        if(i == 21){
                            button22.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                            button22.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                            button22.isClickable = false
                        }
                        if(i == 22){
                            button23.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                            button23.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                            button23.isClickable = false
                        }
                        if(i == 23){
                            button24.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                            button24.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                            button24.isClickable = false
                        }
                    }
                }

                override fun onFinish() {
                    // hide loader
                    animationView.visibility = View.GONE
                    scrollView.visibility = VISIBLE
                }
            }
            countDownTimer.start()

            // store both-> headerText and selection
            bookingDate.text = datePicker.headerText
            totalPriceView.setText("Rs.0")


            // onClickListener on all the time buttons
            // if displayLists contains 0, then no onClicklistener on that button and the background color of that changes
            button1.setOnClickListener {
                if (button1.backgroundTintList === ColorStateList.valueOf(Color.parseColor("#FFFFFF"))) {
                    button1.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D1D5DB"))
                    button1.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(0);
                }
                else {
                    button1.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button1.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(0);
                }
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
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
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
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
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
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
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
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
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
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
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
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
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
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
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
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
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
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
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
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
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
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
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
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
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
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
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
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
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
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
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
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
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
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
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
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
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
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
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
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
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
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
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
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
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
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
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
                Toast.makeText(this, "There are ${storeHours.size} selections", Toast.LENGTH_LONG).show()
            }


            confirmBookingButton.setOnClickListener{

                var selectedCost = storeHours.size * price!!.toInt()

                if(selectedCost == 0){
                    Toast.makeText(this, "Please select a slot for booking.", Toast.LENGTH_SHORT).show()
                }
                else {
                    MaterialAlertDialogBuilder(this).also {
                        // set title for dailog box
                        it.setTitle("Total amount: Rs.${storeHours.size * price!!.toInt()}")
                        // set message for dialog box
                        it.setMessage("Are you sure you want to book the court?")
                        // set icon for dialog box
                        it.setIcon(R.drawable.ic_baseline_book_online_24)

                        // perform positive action which deletes details from the lender activity and player activity
                        it.setPositiveButton("YES") { dialogInterface, which ->

                            var storeHoursList: MutableList<Int> = mutableListOf()
                            var setStoreHoursList: MutableSet<Int> = mutableSetOf()
                            for(x in displayList){
                                storeHoursList.add(x);
                            }
                            for(x in storeHours){
                                storeHoursList.add(x);
                            }
                            for(x in storeHoursList){
                                setStoreHoursList.add(x)
                            }
                            storeHoursList.clear()
                            for(x in setStoreHoursList){
                                storeHoursList.add(x)
                            }

                            var BookedInfo = BookedComplexInfo(
                                name,
                                sport,
                                price,
                                courts,
                                location,
                                imageUri,
                                phone,
                                description,
                                FirebaseAuth.getInstance().currentUser!!.uid,
                                email,
                                storeHoursList
                            )

                            FirebaseDatabase.getInstance().reference
                                .child("Booked Complexes")
                                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                                .child(key.toString())
                                .setValue(BookedInfo).addOnSuccessListener {
                                    Toast.makeText(
                                        this,
                                        "Complex booking successful !!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }.addOnFailureListener {
                                    Toast.makeText(
                                        this,
                                        "Complex booking failed !!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        }

                        // performs neutral action
                        it.setNeutralButton("CANCEL") { dialogInterface, which ->
                        }

                        // performs negative/NO action
                        it.setNegativeButton("NO") { dialogInterface, which ->
                            Toast.makeText(this, "Booking cancelled !!", Toast.LENGTH_SHORT).show()
                        }

                        // create the AlertDialogBox
                        val alertDialog: androidx.appcompat.app.AlertDialog = it.create()
                        alertDialog.setCancelable(false)
                        alertDialog.show()

                    }
                }

            }


            getDetailsButton.setOnClickListener {

                var storeHoursSet: MutableSet<Int> = mutableSetOf()
                for(i in displayList){
                    storeHoursSet.add(i)
                }
                for(i in storeHours){
                    storeHoursSet.add(i)
                }

                var displaymsg = ""
                for(i in storeHoursSet){
                    displaymsg = displaymsg + i.toString() + " "
                }
                testView.setText(displaymsg)
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

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, PlayerHomeActivity::class.java))
    }

}

