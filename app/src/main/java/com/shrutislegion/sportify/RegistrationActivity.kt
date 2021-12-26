// This Activity is used to commute to multiple registration and login options.

package com.shrutislegion.sportify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import com.airbnb.lottie.LottieAnimationView
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.shrutislegion.sportify.lender_activities.LenderLogActivity
import com.shrutislegion.sportify.player_activities.PlayerLogActivity
import kotlinx.android.synthetic.main.activity_registration.*

open class RegistrationActivity : AppCompatActivity() {

    lateinit var mGoogleSignInClient: GoogleSignInClient
    var playerLogged: Boolean = false
    var lenderLogged: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val leftanim = AnimationUtils.loadAnimation(this, R.anim.leftanim)
        val rightanim = AnimationUtils.loadAnimation(this, R.anim.rightanim)
        val anim = findViewById<LottieAnimationView>(R.id.anim)
        val registeranim = findViewById<LottieAnimationView>(R.id.registeranim)
        val playeranim = findViewById<LottieAnimationView>(R.id.playeranim)
        val useranim = findViewById<LottieAnimationView>(R.id.useranim)

        // Setting Animations
        registeranim.setAnimation(leftanim)
        lenderButton.setAnimation(rightanim)
        playeranim.setAnimation(rightanim)
        playerbutton.setAnimation(leftanim)
        useranim.setAnimation(leftanim)
        signinView.setAnimation(rightanim)
        regProceedButton.setAnimation(rightanim)

        regProceedButton.setOnClickListener {

            // animation of 4seconds and then startActivity
            startActivity(Intent(this, PLSignInActivity::class.java))

        }

    }

    fun lender(view: View){
        anim.visibility = VISIBLE
        anim.playAnimation()
        lenderButton.visibility = GONE
        playerbutton.visibility = GONE
        signinView.visibility = GONE
        registeranim.visibility = GONE
        playeranim.visibility = GONE
        useranim.visibility = GONE
        regProceedButton.visibility = GONE

        // Starting Lender Login Activity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LenderLogActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }, 4000)
    }

    fun player(view: View){
        anim.visibility = VISIBLE
        anim.playAnimation()
        lenderButton.visibility = GONE
        playerbutton.visibility = GONE
        signinView.visibility = GONE
        registeranim.visibility = GONE
        playeranim.visibility = GONE
        useranim.visibility = GONE
        regProceedButton.visibility = GONE

        // Starting Player Login Activity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, PlayerLogActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }, 4000)
    }

}