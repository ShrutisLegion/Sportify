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
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.shrutislegion.sportify.adapters.Adapter
import com.shrutislegion.sportify.lender_activities.LenderHomeActivity
import com.shrutislegion.sportify.modules.LoggedInUserInfo
import com.shrutislegion.sportify.player_activities.PlayerHomeActivity

class MainActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_LOGINTYPE= "logintype_extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var regObject = RegistrationActivity()

        val viewPager = findViewById<ViewPager>(R.id.pager)

        val pager = findViewById<LiquidPager>(R.id.pager)
        pager.adapter = Adapter(supportFragmentManager)
        viewPager.visibility = View.GONE

//        Handler(Looper.getMainLooper()).postDelayed({
//            val intent = Intent(this, TypeRegActivity::class.java)
//            startActivity(intent)
//        }, 2000)
        getSupportActionBar()?.hide()

        val user = FirebaseAuth.getInstance().currentUser

        val loginType = intent.getStringExtra(MainActivity.EXTRA_LOGINTYPE)

        if(user != null) {
            if (loginType == "true") {

                val model: LoggedInUserInfo = LoggedInUserInfo(user.displayName, user.email,
                    user.uid, user.photoUrl!!.toString(), ""
                )

                FirebaseDatabase.getInstance().reference
                    .child("Logged in users")
                    .child("players")
                    .child(user.uid)
                    .setValue(model)

                Toast.makeText(this, "User in users!", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, PlayerHomeActivity::class.java))
            } else {

                val model: LoggedInUserInfo = LoggedInUserInfo(user.displayName, user.email,
                    user.uid, user.photoUrl!!.toString(), ""
                )

                FirebaseDatabase.getInstance().reference
                    .child("Logged in users")
                    .child("lenders")
                    .child(user.uid)
                    .setValue(model)

                Toast.makeText(this, "User in Landers!", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, LenderHomeActivity::class.java))
            }
        }
        else{
            viewPager.visibility = View.VISIBLE
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

//    override fun onSaveInstanceState(savedInstanceState: Bundle) {
//        super.onSaveInstanceState(savedInstanceState)
//        // Save UI state changes to the savedInstanceState.
//        // This bundle will be passed to onCreate if the process is
//        // killed and restarted.
//        savedInstanceState.putBoolean("playerLogged", true)
//        savedInstanceState.putBoolean("lenderLogged", false)
//        // etc.
//    }
//
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        // Restore UI state from the savedInstanceState.
//        // This bundle has also been passed to onCreate.
//        val playerLogged = savedInstanceState.getBoolean("playerLogged")
//        val lenderLogged = savedInstanceState.getBoolean("lenderLogged")
//    }
}