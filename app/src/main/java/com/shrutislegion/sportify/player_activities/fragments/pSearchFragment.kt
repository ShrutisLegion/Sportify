package com.shrutislegion.sportify.player_activities.fragments

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Parcelable
import android.util.AttributeSet
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.shrutislegion.sportify.R
import com.shrutislegion.sportify.adapters.pSearchFragmentAdapter
import com.shrutislegion.sportify.modules.BookedComplexInfo
import kotlinx.android.synthetic.main.fragment_p_home.view.progressBarPHome
import kotlinx.android.synthetic.main.fragment_p_search.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [pSearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class pSearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var adapter: pSearchFragmentAdapter
    lateinit var countDownTimer: CountDownTimer
    private var scroll_state: Parcelable? = null
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
        val view: View = inflater.inflate(R.layout.fragment_p_search, container, false)
        view.pBRecView.layoutManager = LinearLayoutManagerWrapper(context,LinearLayoutManager.VERTICAL, false)

        view.pBRecView.isNestedScrollingEnabled = false

        // Progress bar's progress is updated
//        view.progressBarPHome.progress = i
        countDownTimer = object : CountDownTimer(2000, 1900) {

            override fun onTick(millisUntilFinished: Long) {

                // Firebase recycler view is used here
                // options contains the collection of the data that has to be inserted in the recyclerVIew
                val options: FirebaseRecyclerOptions<BookedComplexInfo> = FirebaseRecyclerOptions.Builder<BookedComplexInfo>()
                    .setQuery(FirebaseDatabase.getInstance().getReference("Booked Complexes").child(FirebaseAuth.getInstance().currentUser!!.uid), BookedComplexInfo::class.java)
                    .build()

                adapter = pSearchFragmentAdapter(options)
                view.pBRecView.adapter = adapter
                adapter.startListening()
            }

            override fun onFinish() {
                view.pBRecView.visibility = View.VISIBLE
                view.progressBarPHome.setVisibility(View.GONE)
//                view.progressBarPHome.setProgress(100)
            }
        }
        countDownTimer.start()

        return view
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onPause() {
        super.onPause()
        scroll_state = LinearLayoutManagerWrapper(context,LinearLayoutManager.VERTICAL, false).onSaveInstanceState()
    }

    override fun onResume() {
        super.onResume()
        LinearLayoutManagerWrapper(context,LinearLayoutManager.VERTICAL, false).onRestoreInstanceState(scroll_state);
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment pSearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            pSearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}