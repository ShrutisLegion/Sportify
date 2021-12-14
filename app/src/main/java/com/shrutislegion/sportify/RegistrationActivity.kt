package com.shrutislegion.sportify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.airbnb.lottie.LottieAnimationView
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.royrodriguez.transitionbutton.TransitionButton
import com.shrutislegion.sportify.lenderactivities.LanderLogActivity
import com.shrutislegion.sportify.playeractivities.PlayerLogActivity
import kotlinx.android.synthetic.main.activity_registration.*

open class RegistrationActivity : AppCompatActivity() {

    lateinit var mGoogleSignInClient: GoogleSignInClient
    var playerLogged: Boolean = false
    var lenderLogged: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val anim = findViewById<LottieAnimationView>(R.id.anim)
        val registeranim = findViewById<LottieAnimationView>(R.id.registeranim)
        val playeranim = findViewById<LottieAnimationView>(R.id.playeranim)
        val useranim = findViewById<LottieAnimationView>(R.id.useranim)

//        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
//        mGoogleSignInClient= GoogleSignIn.getClient(context,gso)

    }

    fun lander(view: View){
        anim.visibility = VISIBLE
        anim.playAnimation()
        landerButton.visibility = GONE
        playerbutton.visibility = GONE
        signin.visibility = GONE
        registeranim.visibility = GONE
        playeranim.visibility = GONE
        useranim.visibility = GONE

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LanderLogActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }, 4000)
    }

    fun player(view: View){
        anim.visibility = VISIBLE
        anim.playAnimation()
        landerButton.visibility = GONE
        playerbutton.visibility = GONE
        signin.visibility = GONE
        registeranim.visibility = GONE
        playeranim.visibility = GONE
        useranim.visibility = GONE

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, PlayerLogActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }, 4000)
    }

}