package com.shrutislegion.sportify.player_activities

import android.app.Fragment
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.database.FirebaseDatabase
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.shrutislegion.sportify.R
import com.shrutislegion.sportify.RegistrationActivity
import com.shrutislegion.sportify.adapters.pHomeFragmentAdapter
import com.shrutislegion.sportify.interfaces.pHomeFragmentInterface
import com.shrutislegion.sportify.modules.ComplexInfo
import com.shrutislegion.sportify.player_activities.fragments.pChatFragment
import com.shrutislegion.sportify.player_activities.fragments.pHomeFragment
import com.shrutislegion.sportify.player_activities.fragments.pSearchFragment
import com.shrutislegion.sportify.player_activities.fragments.pUserFragment

@Suppress("DEPRECATION")
class PlayerHomeActivity : AppCompatActivity() {

    lateinit var bottomNav: ChipNavigationBar
    lateinit var gso: GoogleSignInOptions
    lateinit var mGoogleSignInClient: GoogleSignInClient
//    lateinit var adapter: pHomeFragmentAdapter
    lateinit var listener: pHomeFragmentInterface

    companion object {
        const val EXTRA_FRAGMENT = "name_extra"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_home)

//        supportActionBar?.hide()

        var regObj = RegistrationActivity()
        regObj.playerLogged = true
        regObj.lenderLogged = false

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)
        bottomNav = findViewById(R.id.bottom_nav)

        var frag = intent.getStringExtra(PlayerHomeActivity.EXTRA_FRAGMENT)

        if(frag == "1"){
            bottomNav.setItemSelected(R.id.booked, true)
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, pSearchFragment()).commitAllowingStateLoss()
            supportActionBar!!.title = "Your Bookings"
        }
        else if(frag == "2"){
            bottomNav.setItemSelected(R.id.chats,true)
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, pChatFragment()).commitAllowingStateLoss()
            supportActionBar!!.title = "Chats"
        }
        else if(frag == "3"){
            bottomNav.setItemSelected(R.id.user, true)
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, pUserFragment()).commitAllowingStateLoss()
            supportActionBar!!.title = "Profile"
        }
        // By default the home page should be selected on opening the app
        else if(savedInstanceState==null){
            bottomNav.setItemSelected(R.id.home,true)
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, pHomeFragment()).commitAllowingStateLoss()
            supportActionBar!!.title = "All Complexes"
        }

        // Listener on the bottomNav, and selecting the fragment according to their ids
        bottomNav.setOnItemSelectedListener {
            var fragment: Fragment? = null
            when(it){
                R.id.home ->{
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, pHomeFragment()).commitAllowingStateLoss()
                    supportActionBar!!.title = "All Complexes"
                }
                R.id.booked -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, pSearchFragment()).commitAllowingStateLoss()
                    supportActionBar!!.title = "Your Bookings"
                }
                R.id.chats -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, pChatFragment()).commitAllowingStateLoss()
                    supportActionBar!!.title = "Chats"
                }
                R.id.user -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, pUserFragment()).commitAllowingStateLoss()
                    supportActionBar!!.title = "Profile"
                }
            }

        }


    }

//    @JvmName("setListener1")
//    fun setListener(listener: pHomeFragmentInterface){
//        this.listener = listener
//    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//
//        menuInflater.inflate(R.menu.phome_menu, menu)
//
//        val menuitem = menu!!.findItem(R.id.action_phome_search)
//        val searchView: SearchView = menuitem.actionView as SearchView
//        searchView.queryHint = "Search by Complex Name"
//
//        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//
//                // Firebase recycler view is used here
//                // options contains the collection of the data that has to be inserted in the recyclerVIew
//                val options: FirebaseRecyclerOptions<ComplexInfo> = FirebaseRecyclerOptions.Builder<ComplexInfo>()
//                    .setQuery(
//                        FirebaseDatabase.getInstance().getReference("All Complexes")
//                        .orderByChild("complexName")
//                        .startAt(newText).endAt(newText+"\uf8ff"), ComplexInfo::class.java)
//                    .build()
//
//                (supportFragmentManager.findFragmentById(R.id.home) as? pHomeFragment)?.complexSearch(newText)
//                (supportFragmentManager.findFragmentById(R.id.home) as? pHomeFragment)?.adapter = pHomeFragmentAdapter(options)
////                (supportFragmentManager.findFragmentById(R.id.home) as? pHomeFragment)?.adapter.startListening()
//
//
//                return true
//            }
//
//        })
//
//        searchView.setOnCloseListener {
//
//            false
//        }
//
//        return super.onCreateOptionsMenu(menu)
//    }


    override fun onBackPressed() {
        super.onBackPressed()
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }
}