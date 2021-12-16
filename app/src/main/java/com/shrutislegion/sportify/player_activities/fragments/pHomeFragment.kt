package com.shrutislegion.sportify.player_activities.fragments

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.shrutislegion.sportify.R
import com.shrutislegion.sportify.adapters.pHomeFragmentAdapter
import com.shrutislegion.sportify.modules.ComplexInfo
import kotlinx.android.synthetic.main.fragment_p_home.view.*
import android.os.Parcelable
import android.view.View.VISIBLE
import android.widget.SearchView
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.database.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [pHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class pHomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var adapter: pHomeFragmentAdapter
    lateinit var countDownTimer: CountDownTimer
    private var scroll_state: Parcelable? = null
    var storeComplex: MutableList<ComplexInfo> = mutableListOf<ComplexInfo>()
    var i = 0

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
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_p_home, container, false)

        var linearLayoutManager = LinearLayoutManagerWrapper(context, LinearLayoutManager.VERTICAL, true)
        linearLayoutManager.stackFromEnd = true

        view.precView.layoutManager = linearLayoutManager

        // Progress bar's progress is updated
//        view.progressBarPHome.progress = i
        countDownTimer = object : CountDownTimer(2000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                view.precView.visibility = VISIBLE
                view.progressBarPHome.setVisibility(View.GONE)
//                view.progressBarPHome.setProgress(100)
            }
        }
        countDownTimer.start()

        var ref = FirebaseDatabase.getInstance().reference
            .child("All Complexes")

        if(ref != null){

            // Firebase recycler view is used here
            // options contains the collection of the data that has to be inserted in the recyclerVIew
            val options: FirebaseRecyclerOptions<ComplexInfo> = FirebaseRecyclerOptions.Builder<ComplexInfo>()
                .setQuery(FirebaseDatabase.getInstance().getReference("All Complexes"), ComplexInfo::class.java)
                .build()

            adapter = pHomeFragmentAdapter(options)
            view.precView.adapter = adapter
            adapter.startListening()

        }

        if(view.pHomeSearchBarLayout != null){

            view.pHomeSearchBarLayout.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {

                    complexSearch(p0)

                    return true
                }

            })

        }
        view.pHomeSearchBarLayout.setOnCloseListener {

            // Firebase recycler view is used here
            // options contains the collection of the data that has to be inserted in the recyclerVIew
            val options: FirebaseRecyclerOptions<ComplexInfo> =
                FirebaseRecyclerOptions.Builder<ComplexInfo>()
                    .setQuery(
                        FirebaseDatabase.getInstance().getReference("All Complexes"),
                        ComplexInfo::class.java
                    )
                    .build()

            adapter = pHomeFragmentAdapter(options)
            view.precView.adapter = adapter
            adapter.startListening()

            false
        }

        return view
    }

    private fun complexSearch(str: String?) {

        // Firebase recycler view is used here
        // options contains the collection of the data that has to be inserted in the recyclerVIew
        val options: FirebaseRecyclerOptions<ComplexInfo> = FirebaseRecyclerOptions.Builder<ComplexInfo>()
            .setQuery(FirebaseDatabase.getInstance().getReference("All Complexes")
                .orderByChild("complexName")
                .startAt(str).endAt(str+"\uf8ff"), ComplexInfo::class.java)
            .build()
        adapter = pHomeFragmentAdapter(options)
        requireView().precView.adapter = adapter
        adapter.startListening()

    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onPause() {
        super.onPause()
        scroll_state = LinearLayoutManagerWrapper(context,LinearLayoutManager.VERTICAL, true).onSaveInstanceState()
    }

    override fun onResume() {
        super.onResume()
        LinearLayoutManagerWrapper(context,LinearLayoutManager.VERTICAL, true).onRestoreInstanceState(scroll_state);
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment pHomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            pHomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}