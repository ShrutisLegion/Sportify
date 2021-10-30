package com.shrutislegion.sportify

import android.app.ActivityOptions
import android.app.Fragment
import android.content.Intent
import android.content.IntentFilter.create
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.shrutislegion.sportify.adapters.homeFragmentAdapter
import kotlinx.android.synthetic.main.activity_add_complex.*
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.item_complexdetails.*
import java.net.URI.create
import androidx.core.util.Pair
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.item_complexdetails.view.*


@Suppress("DEPRECATION")
class LenderHomeActivity : AppCompatActivity() {

    lateinit var bottomNav: ChipNavigationBar
    lateinit var gso: GoogleSignInOptions
    lateinit var mGoogleSignInClient: GoogleSignInClient
//    val homeFragment: HomeFragment = HomeFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lender_home)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)
        bottomNav = findViewById(R.id.bottom_nav)

        // By default the home page should be selected on opening the app
        if(savedInstanceState==null){
            bottomNav.setItemSelected(R.id.home,true)
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commitAllowingStateLoss()
        }

        // Listener on the bottomNav, and selecting the fragment according to their ids
        bottomNav.setOnItemSelectedListener {
            var fragment: Fragment? = null
            when(it){
                R.id.home -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commitAllowingStateLoss()
                R.id.booked-> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, SearchFragment()).commitAllowingStateLoss()
                R.id.chats -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, ChatsFragment()).commitAllowingStateLoss()
                R.id.user -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, UserFragment()).commitAllowingStateLoss()
            }

        }


    }


}