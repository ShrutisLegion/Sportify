package com.shrutislegion.sportify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.cuberto.liquid_swipe.LiquidPager
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.shrutislegion.sportify.adapters.Adapter
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.fragment_user.view.*

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

//        val user = FirebaseAuth.getInstance().currentUser
//        if(user!=null){
//            startActivity(Intent(this, LenderHomeActivity::class.java))
//        }

    }

    fun intentregtype(view: View) {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
    }
}