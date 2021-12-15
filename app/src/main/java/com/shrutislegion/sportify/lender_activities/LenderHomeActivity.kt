package com.shrutislegion.sportify.lender_activities

import android.app.Fragment
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.shrutislegion.sportify.R
import com.shrutislegion.sportify.lender_activities.fragments.ChatsFragment
import com.shrutislegion.sportify.lender_activities.fragments.HomeFragment
import com.shrutislegion.sportify.lender_activities.fragments.SearchFragment
import com.shrutislegion.sportify.lender_activities.fragments.UserFragment


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
                R.id.booked -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, SearchFragment()).commitAllowingStateLoss()
                R.id.chats -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, ChatsFragment()).commitAllowingStateLoss()
                R.id.user -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, UserFragment()).commitAllowingStateLoss()
            }

        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }


}