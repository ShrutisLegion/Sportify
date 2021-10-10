package com.shrutislegion.sportify

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.royrodriguez.transitionbutton.TransitionButton
import com.royrodriguez.transitionbutton.TransitionButton.OnAnimationStopEndListener
import kotlinx.android.synthetic.main.activity_log_or_reg.*


class logOrRegActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_or_reg)
    }

    fun onClick1(view: View) {
        transition_button.startAnimation();
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            val isSuccessful = true

            // Choose a stop animation if your call was succesful or not
            if (isSuccessful) {
                transition_button.stopAnimation(TransitionButton.StopAnimationStyle.EXPAND,
                    OnAnimationStopEndListener{
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
        registration_button.startAnimation();
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            val isSuccessful = true

            // Choose a stop animation if your call was succesful or not
            if (isSuccessful) {
                registration_button.stopAnimation(TransitionButton.StopAnimationStyle.EXPAND,
                    OnAnimationStopEndListener {
                        val intent = Intent(baseContext, RegistrationActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                        startActivity(intent)
                    })
            } else {
                registration_button.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null)
            }
        }, 2000)

    }


}