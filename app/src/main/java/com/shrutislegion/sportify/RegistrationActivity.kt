package com.shrutislegion.sportify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.royrodriguez.transitionbutton.TransitionButton
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
    }

//    fun onClick1(view: View) {
//        PlayerReg.startAnimation();
//        Handler(Looper.getMainLooper()).postDelayed(Runnable {
//            val isSuccessful = true
//
//            // Choose a stop animation if your call was succesful or not
//            if (isSuccessful) {
//                PlayerReg.stopAnimation(
//                    TransitionButton.StopAnimationStyle.EXPAND,
//                    TransitionButton.OnAnimationStopEndListener {
//                        val intent = Intent(this, LoginActivity::class.java)
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
//                        startActivity(intent)
//                    })
//            } else {
//                PlayerReg.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null)
//            }
//        }, 2000)
//
//    }
//    fun landerreg(view: View) {
//        LenderReg.startAnimation();
//        Handler(Looper.getMainLooper()).postDelayed(Runnable {
//            val isSuccessful = true
//
//            // Choose a stop animation if your call was succesful or not
//            if (isSuccessful) {
//                LenderReg.stopAnimation(
//                    TransitionButton.StopAnimationStyle.EXPAND,
//                    TransitionButton.OnAnimationStopEndListener {
//                        val intent = Intent(this, LanderRegistrationActivity::class.java)
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
//                        startActivity(intent)
//                    })
//            } else {
//                LenderReg.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null)
//            }
//        }, 2000)
//
//    }

    fun Click(view: View) {
        if(playerButton.isChecked){
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
                                val intent = Intent(this, LanderRegistrationActivity::class.java)
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