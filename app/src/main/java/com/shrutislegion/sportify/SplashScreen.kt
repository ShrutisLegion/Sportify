// Splash Screen which is shown everytime when the app starts

package com.shrutislegion.sportify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val bottomanim = AnimationUtils.loadAnimation(this, R.anim.bottomanimation)
        val text = findViewById<TextView>(R.id.text)
        val topanim = AnimationUtils.loadAnimation(this, R.anim.topanim)
        val image = findViewById<ImageView>(R.id.logo)
        val user = FirebaseAuth.getInstance().currentUser

        // setting the animations
        image.setAnimation(topanim)
        getSupportActionBar()?.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            text.visibility = VISIBLE
            text.setAnimation(bottomanim)
        },500)

        var player = false
        var lender = false

        // Checks the user and update the boolean value accordingly
        if(user!=null){
            Toast.makeText(this,"User in not null!", Toast.LENGTH_LONG).show()
            val userID = user.uid
            val db = Firebase.firestore
            // search if the account is registered as Player
            db.collection("users")
                .get()
                .addOnSuccessListener {
                        result ->
                    for(document in result){
                        if(document.id == userID){
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
                            if (document.id == userID) {
                                lender = true
                                break
                            }
                        }
                    }
            }

        }

        Handler(Looper.getMainLooper()).postDelayed({
            // pass the value player to the next activity and then open the Home activity according to the boolean value
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(MainActivity.EXTRA_LOGINTYPE, "$player")
            startActivity(intent)
            finish()
        },2500)
    }
}