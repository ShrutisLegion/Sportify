package com.shrutislegion.sportify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.cuberto.liquid_swipe.LiquidPager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.shrutislegion.sportify.adapters.Adapter
import com.shrutislegion.sportify.lenderactivities.LanderLogActivity
import com.shrutislegion.sportify.lenderactivities.LenderHomeActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pager = findViewById<LiquidPager>(R.id.pager)
        pager.adapter = Adapter(supportFragmentManager)

//        Handler(Looper.getMainLooper()).postDelayed({
//            val intent = Intent(this, TypeRegActivity::class.java)
//            startActivity(intent)
//        }, 2000)
        getSupportActionBar()?.hide()

        val user = FirebaseAuth.getInstance().currentUser

        if(user!=null){
            val userID = user.uid
            val db = Firebase.firestore
            var player = false
            var lender = false

            // search if the account is registered as Player
            db.collection("users")
                .get()
                .addOnSuccessListener {
                    result ->
                    for(document in result){
                        if(document.id === userID){
                            player = true
                            break
                        }
                    }
                }

            // search if the account is registered as Lender
            if(!player) {
                db.collection("Landers")
                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result) {
                            if (document.id === userID) {
                                lender = true
                                break
                            }
                        }
                    }
            }
            if(player){
                startActivity(Intent(this, PlayerHomeActivity::class.java))
                finish()
            }
            else{
                startActivity(Intent(this, LenderHomeActivity::class.java))
                finish()
            }

        }

    }

    fun intentregtype(view: View) {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
        finish()
    }
}