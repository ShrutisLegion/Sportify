package com.shrutislegion.sportify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.style.ClickableSpan
import android.view.View
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.royrodriguez.transitionbutton.TransitionButton
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

//        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
//        mGoogleSignInClient= GoogleSignIn.getClient(context,gso)

    }

    fun Click(view: View) {
        if(playerButton.isChecked){
            landerButton.isEnabled = false
            Continue.startAnimation();
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                val isSuccessful = true

                // Choose a stop animation if your call was succesful or not
                if (isSuccessful) {
                    Continue.stopAnimation(
                            TransitionButton.StopAnimationStyle.EXPAND,
                            TransitionButton.OnAnimationStopEndListener {
                                val intent = Intent(this, LoginActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                                startActivity(intent)
                            })
                } else {
                    Continue.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null)
                }
            }, 2000)
        } else if(landerButton.isChecked){
            Continue.startAnimation();
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                val isSuccessful = true

                // Choose a stop animation if your call was succesful or not
                if (isSuccessful) {
                    Continue.stopAnimation(
                            TransitionButton.StopAnimationStyle.EXPAND,
                            TransitionButton.OnAnimationStopEndListener {
                                val intent = Intent(this, LanderLogActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                                startActivity(intent)
                            })
                } else {
                    Continue.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null)
                }
            }, 2000)

        }
    }
}