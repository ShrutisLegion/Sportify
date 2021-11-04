package com.shrutislegion.sportify.playeractivities

import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.os.CountDownTimer
import android.util.AttributeSet
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintSet.GONE
import androidx.databinding.adapters.SeekBarBindingAdapter.setProgress
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.common.collect.ComparisonChain.start
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.shrutislegion.sportify.R
import com.shrutislegion.sportify.adapters.pHomeFragmentAdapter
import com.shrutislegion.sportify.modules.ComplexInfo
import io.reactivex.rxjava3.internal.schedulers.SchedulerPoolFactory.start
import io.reactivex.rxjava3.schedulers.Schedulers.start
import kotlinx.android.synthetic.main.activity_lander_log.*
import kotlinx.android.synthetic.main.fragment_p_home.*
import kotlinx.android.synthetic.main.fragment_p_home.view.*
import kotlinx.android.synthetic.main.item_pcomplexdetails.view.*

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
        view.precView.layoutManager = LinearLayoutManagerWrapper(context,LinearLayoutManager.VERTICAL, false)


        view.progressBarPHome.progress = i
        countDownTimer = object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                i++
                view.progressBarPHome.setProgress(i * 100 / (2000 / 1000))
            }

            override fun onFinish() {
                //Do what you want
                i++
                view.progressBarPHome.setVisibility(View.GONE)
                view.progressBarPHome.setProgress(100)
            }
        }
        countDownTimer.start()

        // Firebase recycler view is used here
        // options contains the collection of the data that has to be inserted in the recyclerVIew
        val options: FirebaseRecyclerOptions<ComplexInfo> = FirebaseRecyclerOptions.Builder<ComplexInfo>()
            .setQuery(FirebaseDatabase.getInstance().getReference("All Complexes"), ComplexInfo::class.java)
            .build()

        adapter = pHomeFragmentAdapter(options)
        view.precView.adapter = adapter

        return view
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
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