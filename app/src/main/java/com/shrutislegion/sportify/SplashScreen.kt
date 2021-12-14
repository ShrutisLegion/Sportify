package com.shrutislegion.sportify

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val bottomanim = AnimationUtils.loadAnimation(this, R.anim.bottomanimation)
        val text = findViewById<TextView>(R.id.text)
        val topanim = AnimationUtils.loadAnimation(this, R.anim.topanim)
        val image = findViewById<ImageView>(R.id.logo)
        image.setAnimation(topanim)
        getSupportActionBar()?.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            text.visibility = VISIBLE
            text.setAnimation(bottomanim)
        },500)

        Handler(Looper.getMainLooper()).postDelayed(
            {
            val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
        },2500)
    }
}