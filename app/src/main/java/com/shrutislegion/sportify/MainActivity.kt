package com.shrutislegion.sportify

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.cuberto.liquid_swipe.LiquidPager
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.shrutislegion.sportify.adapters.Adapter
import com.shrutislegion.sportify.lenderactivities.LenderHomeActivity
import com.shrutislegion.sportify.playeractivities.PlayerHomeActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager = findViewById<ViewPager>(R.id.pager)

        val pager = findViewById<LiquidPager>(R.id.pager)
        pager.adapter = Adapter(supportFragmentManager)

//        Handler(Looper.getMainLooper()).postDelayed({
//            val intent = Intent(this, TypeRegActivity::class.java)
//            startActivity(intent)
//        }, 2000)
        getSupportActionBar()?.hide()

        val user = FirebaseAuth.getInstance().currentUser

        if(user!=null){
            Toast.makeText(this,"User in not null!",Toast.LENGTH_LONG).show()
            val userID = user.uid
            val db = Firebase.firestore
            var player = false
            var lender = false

            // search if the account is registered as Player
            db.collection("users")
                .get()
                .addOnSuccessListener {
                    result ->
                    for(document in result){
                        if(document.id == userID){
                            player = true
                            Toast.makeText(this,"User in users!",Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, PlayerHomeActivity::class.java))
                            finish()
                            break
                        }
                    }
                }

            // search if the account is registered as Lender
            if(!player) {
                db.collection("Landers")
                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result) {
                            if (document.id == userID) {
                                Toast.makeText(this,"User in Landers!",Toast.LENGTH_LONG).show()
                                lender = true
                                startActivity(Intent(this, LenderHomeActivity::class.java))
                                finish()
                                break
                            }
                        }
                    }
            }

        }

        FirebaseDynamicLinks.getInstance().getDynamicLink(getIntent()).addOnSuccessListener(OnSuccessListener<PendingDynamicLinkData>(){
            Log.i("MainActivity", "this is main")
            var deeplink: Uri? = null
            if(it != null){
                deeplink = it.link
            }
            if(deeplink!=null){
                Toast.makeText(this, deeplink.toString(), Toast.LENGTH_LONG).show()
                val currentPage: String? = deeplink.getQueryParameter("currPage")
                if(currentPage!=null) {
                    val curPage = Integer.parseInt(currentPage)
                    viewPager.currentItem = curPage
                }
            }
        }
        )
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                // Get deep link from result (may be null if no link is found)
                var deepLink: Uri? = null
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                }

                // Handle the deep link. For example, open the linked
                // content, or apply promotional credit to the user's
                // account.
                // ...

                // ...
            }
            .addOnFailureListener(this) { e -> Log.w(ContentValues.TAG, "getDynamicLink:onFailure", e) }


    }

    fun intentregtype(view: View) {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
        finish()
    }
}