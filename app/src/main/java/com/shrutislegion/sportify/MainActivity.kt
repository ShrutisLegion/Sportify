package com.shrutislegion.sportify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.cuberto.liquid_swipe.LiquidPager

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
    }

    fun intentregtype(view: View) {
        val intent = Intent(this, TypeRegActivity::class.java)
        startActivity(intent)
    }
}