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

    companion object {
        const val EXTRA_FRAGMENT = "name_extra"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lender_home)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)
        bottomNav = findViewById(R.id.bottom_nav)

        var frag = intent.getStringExtra(LenderHomeActivity.EXTRA_FRAGMENT)

        if(frag == "0"){
            bottomNav.setItemSelected(R.id.home, true)
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commitAllowingStateLoss()
            supportActionBar!!.title = "Your Complexes"
        }
        else if(frag == "1"){
            bottomNav.setItemSelected(R.id.booked, true)
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, SearchFragment()).commitAllowingStateLoss()
            supportActionBar!!.title = "Booked Complexes"
        }
        else if(frag == "2") {
            bottomNav.setItemSelected(R.id.chats, true)
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, ChatsFragment()).commitAllowingStateLoss()
            supportActionBar!!.title = "Chats"
        }
        else if(frag == "3"){
            bottomNav.setItemSelected(R.id.user, true)
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, UserFragment()).commitAllowingStateLoss()
            supportActionBar!!.title = "Profile"
        }

        // By default the home page should be selected on opening the app
        else if(savedInstanceState==null){
            bottomNav.setItemSelected(R.id.home,true)
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commitAllowingStateLoss()
            supportActionBar!!.title = "Your Complexes"
        }

        // Listener on the bottomNav, and selecting the fragment according to their ids
        bottomNav.setOnItemSelectedListener {
            var fragment: Fragment? = null
            when(it){
                R.id.home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commitAllowingStateLoss()
                    supportActionBar!!.title = "Your Complexes"
                }
                R.id.booked -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, SearchFragment()).commitAllowingStateLoss()
                    supportActionBar!!.title = "Booked Complexes"
                }
                R.id.chats -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, ChatsFragment()).commitAllowingStateLoss()
                    supportActionBar!!.title = "Chats"
                }
                R.id.user -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, UserFragment()).commitAllowingStateLoss()
                    supportActionBar!!.title = "Profile"
                }
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