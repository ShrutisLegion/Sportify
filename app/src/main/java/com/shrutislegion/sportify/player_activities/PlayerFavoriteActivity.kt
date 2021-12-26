package com.shrutislegion.sportify.player_activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Parcelable
import android.util.AttributeSet
import android.view.Menu
import android.view.View
import android.view.View.VISIBLE
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.shrutislegion.sportify.R
import com.shrutislegion.sportify.adapters.pFavoriteActivityAdapter
import com.shrutislegion.sportify.modules.ComplexInfo
import kotlinx.android.synthetic.main.activity_player_favorite.*

class PlayerFavoriteActivity : AppCompatActivity() {

    lateinit var adapter: pFavoriteActivityAdapter
    lateinit var countDownTimer: CountDownTimer
    private var scroll_state: Parcelable? = null

    // To override LinearLayoutManager by Wrapper, as it crashes the application sometimes
    inner class LinearLayoutManagerWrapper : LinearLayoutManager {
        constructor(context: Context?) : super(context) {}
        constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(
            context,
            orientation,
            reverseLayout
        ) {
        }

        constructor(
            context: Context?,
            attrs: AttributeSet?,
            defStyleAttr: Int,
            defStyleRes: Int
        ) : super(context, attrs, defStyleAttr, defStyleRes) {
        }

        override fun supportsPredictiveItemAnimations(): Boolean {
            return false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_favorite)

        pFavRecView.layoutManager = LinearLayoutManagerWrapper(this,LinearLayoutManager.VERTICAL, false)

        countDownTimer = object : CountDownTimer(2000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                pFavRecView.visibility = View.VISIBLE
                progressBarPHome.visibility = View.GONE
            }
        }
        countDownTimer.start()

        // Firebase recycler view is used here
        // options contains the collection of the data that has to be inserted in the recyclerVIew
        val options: FirebaseRecyclerOptions<ComplexInfo> = FirebaseRecyclerOptions.Builder<ComplexInfo>()
            .setQuery(FirebaseDatabase.getInstance().getReference("Favorite Complexes").child(FirebaseAuth.getInstance().currentUser!!.uid), ComplexInfo::class.java)
            .build()

        adapter = pFavoriteActivityAdapter(options)
        pFavRecView.adapter = adapter
        adapter.startListening()

    }

        override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.pfav_menu, menu)

        val menuitem = menu!!.findItem(R.id.action_pfav_search)
        val searchView: SearchView = menuitem.actionView as SearchView
        searchView.queryHint = "Search by Complex Name"

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                // Firebase recycler view is used here
                // options contains the collection of the data that has to be inserted in the recyclerVIew
                val options: FirebaseRecyclerOptions<ComplexInfo> = FirebaseRecyclerOptions.Builder<ComplexInfo>()
                    .setQuery(
                        FirebaseDatabase.getInstance().getReference("Favorite Complexes")
                            .child(FirebaseAuth.getInstance().currentUser!!.uid)
                        .orderByChild("complexName")
                        .startAt(newText).endAt(newText+"\uf8ff"), ComplexInfo::class.java)
                    .build()

                adapter = pFavoriteActivityAdapter(options)
                pFavRecView.adapter = adapter
                adapter.startListening()


                return true
            }

        })

        searchView.setOnCloseListener {

            false
        }

        return super.onCreateOptionsMenu(menu)
    }


    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
    override fun onPause() {
        super.onPause()
        scroll_state = LinearLayoutManagerWrapper(this,LinearLayoutManager.VERTICAL, false).onSaveInstanceState()
    }

    override fun onResume() {
        super.onResume()
        LinearLayoutManagerWrapper(this,LinearLayoutManager.VERTICAL, false).onRestoreInstanceState(scroll_state);
    }

    override fun onBackPressed() {
        super.onBackPressed()
        adapter.stopListening()
        val intent: Intent = Intent(this, PlayerHomeActivity::class.java)

        intent.putExtra(PlayerHomeActivity.EXTRA_FRAGMENT, "3")

        startActivity(intent)
    }

}