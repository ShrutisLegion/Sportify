package com.shrutislegion.sportify.player_activities

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.shrutislegion.sportify.adapters.pBookedHoursAdapter
import com.shrutislegion.sportify.modules.BookedComplexInfo
import kotlinx.android.synthetic.main.activity_player_book_court.*


class PlayerBookDateActivity: AppCompatActivity(){

    lateinit var countDownTimer: CountDownTimer
    lateinit var adapter: pBookedHoursAdapter

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
        const val EXTRA_UID = "uid_extra"
    }

    // To override LinearLayoutManager by Wrapper, as it crashes the application sometimes
    inner class LinearLayoutManagerWrapper : LinearLayoutManager {
        constructor(context: Context?) : super(context) {}
        constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(
            context,
            orientation,
            reverseLayout
        ) {
        }

        constructor(
            context: Context?,
            attrs: AttributeSet?,
            defStyleAttr: Int,
            defStyleRes: Int
        ) : super(context, attrs, defStyleAttr, defStyleRes) {
        }

        override fun supportsPredictiveItemAnimations(): Boolean {
            return false
        }
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
        var complexUserId = intent.getStringExtra(PlayerBookDateActivity.EXTRA_UID)
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

            var linearLayoutManager = LinearLayoutManagerWrapper(this, LinearLayoutManager.HORIZONTAL, true)
            linearLayoutManager.stackFromEnd = true
            selectedHoursRecView.layoutManager = linearLayoutManager


            // onClickListener on all the time buttons
            // if displayLists contains 0, then no onClicklistener on that button and the background color of that changes

            var cBookedRef = FirebaseDatabase.getInstance().reference
                .child("Currently Selected Hours")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)

            var cBookedHoursList: MutableList<Int> = mutableListOf<Int>()

            button1.setOnClickListener {
                if (!storeHours.contains(0)) {
                    button1.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D81B60"))
                    button1.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(0);
                    cBookedHoursList.add(0)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                else {
                    button1.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button1.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(0);
                    cBookedHoursList.remove(0)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
            }
            button2.setOnClickListener {
                if (!storeHours.contains(1)) {
                    button2.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D81B60"))
                    button2.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(1);
                    cBookedHoursList.add(1)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                else{
                    button2.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button2.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(1);
                    cBookedHoursList.remove(1)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
            }
            button3.setOnClickListener {
                if (!storeHours.contains(2)) {
                    button3.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D81B60"))
                    button3.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(2);
                    cBookedHoursList.add(2)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                else{
                    button3.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button3.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(2);
                    cBookedHoursList.remove(2)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
            }
            button4.setOnClickListener {
                if (!storeHours.contains(3)) {
                    button4.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D81B60"))
                    button4.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(3);
                    cBookedHoursList.add(3)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                else{
                    button4.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button4.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(3);
                    cBookedHoursList.remove(3)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
            }
            button5.setOnClickListener {
                if (!storeHours.contains(4)) {
                    button5.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D81B60"))
                    button5.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(4);
                    cBookedHoursList.add(4)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                else{
                    button5.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button5.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(4);
                    cBookedHoursList.remove(4)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
            }
            button6.setOnClickListener {
                if (!storeHours.contains(5)) {
                    button6.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D81B60"))
                    button6.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(5);
                    cBookedHoursList.add(5)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                else{
                    button6.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button6.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(5)
                    cBookedHoursList.remove(5)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
            }
            button7.setOnClickListener {
                if (!storeHours.contains(6)) {
                    button7.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D81B60"))
                    button7.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(6);
                    cBookedHoursList.add(6)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                else{
                    button7.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button7.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(6);
                    cBookedHoursList.remove(6)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
            }
            button8.setOnClickListener {
                if (!storeHours.contains(7)) {
                    button8.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D81B60"))
                    button8.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(7);
                    cBookedHoursList.add(7)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                else{
                    button8.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button8.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(7);
                    cBookedHoursList.remove(7)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
            }
            button9.setOnClickListener {
                if (!storeHours.contains(8)) {
                    button9.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D81B60"))
                    button9.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(8);
                    cBookedHoursList.add(8)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                else{
                    button9.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button9.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(8);
                    cBookedHoursList.remove(8)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
            }
            button10.setOnClickListener {
                if (!storeHours.contains(9)) {
                    button10.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D81B60"))
                    button10.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(9);
                    cBookedHoursList.add(9)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                else{
                    button10.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button10.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(9);
                    cBookedHoursList.remove(9)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
            }
            button11.setOnClickListener {
                if (!storeHours.contains(10)) {
                    button11.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D81B60"))
                    button11.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(10);
                    cBookedHoursList.add(10)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                else{
                    button11.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button11.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(10);
                    cBookedHoursList.remove(10)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
            }
            button12.setOnClickListener {
                if (!storeHours.contains(11)) {
                    button12.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D81B60"))
                    button12.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(11);
                    cBookedHoursList.add(11)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                else{
                    button12.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button12.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(11);
                    cBookedHoursList.remove(11)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
            }
            button13.setOnClickListener {
                if (!storeHours.contains(12)) {
                    button13.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D81B60"))
                    button13.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(12);
                    cBookedHoursList.add(12)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                else{
                    button13.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button13.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(12);
                    cBookedHoursList.remove(12)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
            }
            button14.setOnClickListener {
                if (!storeHours.contains(13)) {
                    button14.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D81B60"))
                    button14.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(13);
                    cBookedHoursList.add(13)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                else{
                    button14.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button14.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(13);
                    cBookedHoursList.remove(13)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
            }
            button15.setOnClickListener {
                if (!storeHours.contains(14)) {
                    button15.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D81B60"))
                    button15.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(14);
                    cBookedHoursList.add(14)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                else{
                    button15.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button15.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(14);
                    cBookedHoursList.remove(14)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
            }
            button16.setOnClickListener {
                if (!storeHours.contains(15)) {
                    button16.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D81B60"))
                    button16.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(15);
                    cBookedHoursList.add(15)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                else{
                    button16.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button16.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(15);
                    cBookedHoursList.remove(15)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
            }
            button17.setOnClickListener {
                if (!storeHours.contains(16)) {
                    button17.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D81B60"))
                    button17.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(16);
                    cBookedHoursList.add(16)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                else{
                    button17.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button17.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(16);
                    cBookedHoursList.remove(16)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
            }
            button18.setOnClickListener {
                if (!storeHours.contains(17)) {
                    button18.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D81B60"))
                    button18.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(17);
                    cBookedHoursList.add(17)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                else{
                    button18.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button18.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(17);
                    cBookedHoursList.remove(17)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
            }
            button19.setOnClickListener {
                if (!storeHours.contains(18)) {
                    button19.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D81B60"))
                    button19.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(18);
                    cBookedHoursList.add(18)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                else{
                    button19.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button19.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(18);
                    cBookedHoursList.remove(18)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
            }
            button20.setOnClickListener {
                if (!storeHours.contains(19)) {
                    button20.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D81B60"))
                    button20.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(19);
                    cBookedHoursList.add(19)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                else{
                    button20.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button20.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(19);
                    cBookedHoursList.remove(19)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
            }
            button21.setOnClickListener {
                if (!storeHours.contains(20)) {
                    button21.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D81B60"))
                    button21.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(20);
                    cBookedHoursList.add(20)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                else{
                    button21.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button21.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(20);
                    cBookedHoursList.remove(20)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
            }
            button22.setOnClickListener {
                if (!storeHours.contains(21)) {
                    button22.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D81B60"))
                    button22.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(21);
                    cBookedHoursList.add(21)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                else{
                    button22.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button22.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(21);
                    cBookedHoursList.remove(21)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
            }
            button23.setOnClickListener {
                if (!storeHours.contains(22)) {
                    button23.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D81B60"))
                    button23.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(22);
                    cBookedHoursList.add(22)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                else{
                    button23.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button23.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(22);
                    cBookedHoursList.remove(22)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
            }
            button24.setOnClickListener {
                if (!storeHours.contains(23)) {
                    button24.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D81B60"))
                    button24.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                    storeHours.add(23);
                    cBookedHoursList.add(23)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                else{
                    button24.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    button24.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
                    storeHours.remove(23);
                    cBookedHoursList.remove(23)
                    cBookedRef.setValue(cBookedHoursList)

                    adapter = pBookedHoursAdapter(cBookedHoursList, this)
                    selectedHoursRecView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                totalPriceView.setText("Rs.${storeHours.size * price!!.toInt()}")
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

                            // To get the boked complexes by a user in the Player Activity, this node is created

                            var BookedInfo = BookedComplexInfo(
                                name,
                                sport,
                                price,
                                courts,
                                location,
                                imageUri,
                                phone,
                                description,
                                complexUserId.toString(),
                                email,
                                storeHoursList
                            )

                            FirebaseDatabase.getInstance().reference
                                .child("Booked Complexes")
                                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                                .child(key.toString())
                                .setValue(BookedInfo).addOnSuccessListener {

                                    // Removes the currently stored booking hours from the Firebase Realtime Database
                                    cBookedRef.removeValue()

                                    Toast.makeText(
                                        this,
                                        "Complex Pbooking successful !!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }.addOnFailureListener {
                                    Toast.makeText(
                                        this,
                                        "Complex booking failed !!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }


                            // To get the booked complexes in the Lender Activity this node is created

                            var BookedInfoForLender = BookedComplexInfo(
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
                                .child("Booked Complexes Lender")
                                .child(complexUserId.toString())
                                .child(key.toString())
                                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                                .setValue(BookedInfoForLender).addOnSuccessListener {
                                    Toast.makeText(
                                        this,
                                        "Complex Lbooking successful !!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }.addOnFailureListener{
                                    Toast.makeText(
                                        this,
                                        "Complex Lbooking failed !!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                            startActivity(Intent(this, PlayerHomeActivity::class.java))

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

