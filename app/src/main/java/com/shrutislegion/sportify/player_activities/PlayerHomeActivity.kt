package com.shrutislegion.sportify.player_activities

import android.app.Fragment
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.shrutislegion.sportify.R
import com.shrutislegion.sportify.RegistrationActivity
import com.shrutislegion.sportify.player_activities.fragments.pChatFragment
import com.shrutislegion.sportify.player_activities.fragments.pHomeFragment
import com.shrutislegion.sportify.player_activities.fragments.pSearchFragment
import com.shrutislegion.sportify.player_activities.fragments.pUserFragment

@Suppress("DEPRECATION")
class PlayerHomeActivity : AppCompatActivity() {

    lateinit var bottomNav: ChipNavigationBar
    lateinit var gso: GoogleSignInOptions
    lateinit var mGoogleSignInClient: GoogleSignInClient
//    val homeFragment: HomeFragment = HomeFragment.newInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_home)

        var regObj = RegistrationActivity()
        regObj.playerLogged = true
        regObj.lenderLogged = false

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)
        bottomNav = findViewById(R.id.bottom_nav)

        // By default the home page should be selected on opening the app
        if(savedInstanceState==null){
            bottomNav.setItemSelected(R.id.home,true)
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, pHomeFragment()).commitAllowingStateLoss()
        }

        // Listener on the bottomNav, and selecting the fragment according to their ids
        bottomNav.setOnItemSelectedListener {
            var fragment: Fragment? = null
            when(it){
                R.id.home -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, pHomeFragment()).commitAllowingStateLoss()
                R.id.booked -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, pSearchFragment()).commitAllowingStateLoss()
                R.id.chats -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, pChatFragment()).commitAllowingStateLoss()
                R.id.user -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, pUserFragment()).commitAllowingStateLoss()
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