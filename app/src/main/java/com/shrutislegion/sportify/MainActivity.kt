package com.shrutislegion.sportify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.cuberto.liquid_swipe.LiquidPager
import com.google.firebase.auth.FirebaseAuth
import com.shrutislegion.sportify.adapters.Adapter
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
            startActivity(Intent(this, LenderHomeActivity::class.java))
            finish()
        }

    }

    fun intentregtype(view: View) {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
        finish()
    }
}