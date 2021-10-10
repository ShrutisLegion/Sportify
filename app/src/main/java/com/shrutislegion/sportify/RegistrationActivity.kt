package com.shrutislegion.sportify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.royrodriguez.transitionbutton.TransitionButton
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_type_reg.*

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
    }

    fun onClick1(view: View) {
        PlayerReg.startAnimation();
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            val isSuccessful = true

            // Choose a stop animation if your call was succesful or not
            if (isSuccessful) {
                PlayerReg.stopAnimation(
                    TransitionButton.StopAnimationStyle.EXPAND,
                    TransitionButton.OnAnimationStopEndListener {
                        val intent = Intent(baseContext, LoginActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                        startActivity(intent)
                    })
            } else {
                transition_button.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null)
            }
        }, 2000)

    }
    fun onClick2(view: View) {
        LenderReg.startAnimation();
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            val isSuccessful = true

            // Choose a stop animation if your call was succesful or not
            if (isSuccessful) {
                registration_button.stopAnimation(
                    TransitionButton.StopAnimationStyle.EXPAND,
                    TransitionButton.OnAnimationStopEndListener {
                        val intent = Intent(baseContext, LanderRegistrationActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                        startActivity(intent)
                    })
            } else {
                registration_button.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null)
            }
        }, 2000)

    }
}