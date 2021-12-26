package com.shrutislegion.sportify.lender_activities

import android.animation.LayoutTransition
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.compose.animation.core.snap
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.shrutislegion.sportify.R
import com.shrutislegion.sportify.adapters.lRateReviewActivityAdapter
import com.shrutislegion.sportify.modules.ComplexRating
import kotlinx.android.synthetic.main.activity_lender_rate_review.*
import kotlinx.android.synthetic.main.item_l_rate_review.*

class LenderRateReviewActivity : AppCompatActivity() {

    var storeRR: ArrayList<ComplexRating> = ArrayList<ComplexRating>()
    lateinit var adapter: lRateReviewActivityAdapter

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
        setContentView(R.layout.activity_lender_rate_review)


        var linearLayoutManager = LinearLayoutManagerWrapper(this, LinearLayoutManager.VERTICAL, true)
        linearLayoutManager.stackFromEnd = true
        lRateReviewRV.layoutManager = linearLayoutManager

        var ref = FirebaseDatabase.getInstance().reference
            .child("Ratings")

        val postListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                val complex = snapshot.getValue()

                if(complex == null){
                }
                else{
                    for(i in snapshot.children){

                        for(j in i.children){

                            val ratingInfo:ComplexRating = j.getValue<ComplexRating>()!!
                            storeRR.add(ratingInfo)

                        }

                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        }
        ref.addValueEventListener(postListener)

        Handler(Looper.getMainLooper()).postDelayed({

            adapter = lRateReviewActivityAdapter(storeRR, this)

            lRateReviewRV.adapter = adapter
            adapter.notifyDataSetChanged()

            progressBarLRR.visibility = GONE
            lRateReviewRV.visibility = VISIBLE

        }, 2000)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, LenderHomeActivity::class.java))
    }

}
