package com.shrutislegion.sportify.player_activities.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.shrutislegion.sportify.ChatDetailsActivity
import com.shrutislegion.sportify.R
import com.shrutislegion.sportify.adapters.pChatUserFragmentAdapter
import com.shrutislegion.sportify.modules.LoggedInUserInfo
import kotlinx.android.synthetic.main.fragment_p_chat.*
import kotlinx.android.synthetic.main.fragment_p_chat.view.*
import kotlinx.android.synthetic.main.fragment_p_home.view.*
import kotlinx.android.synthetic.main.item_chatdetails_home.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [pChatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class pChatFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var storeUsers: ArrayList<LoggedInUserInfo> = ArrayList<LoggedInUserInfo>()
    lateinit var adapter: pChatUserFragmentAdapter

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
        val view:View = inflater.inflate(R.layout.fragment_p_chat, container, false)

        var linearLayoutManager = LinearLayoutManagerWrapper(context, LinearLayoutManager.VERTICAL, false)

        view.pChatRV.layoutManager = linearLayoutManager
        view.pChatRV.isNestedScrollingEnabled = false

        var ref = FirebaseDatabase.getInstance().reference
            .child("Logged in users")
            .child("lenders")

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.getValue() == null){

                }
                else{
                    for(i in snapshot.children){
                        val user:LoggedInUserInfo = i.getValue<LoggedInUserInfo>()!!
                        if(!user.userId.equals(FirebaseAuth.getInstance().currentUser!!.uid)) {
                            storeUsers.add(user)
                        }
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        ref.addValueEventListener(postListener)

        Handler(Looper.getMainLooper()).postDelayed({

            adapter = pChatUserFragmentAdapter(storeUsers, context!!)
            view.pChatRV.adapter = adapter

            adapter.notifyDataSetChanged()

            view.progressBarPChat.visibility = GONE
            view.pChatRV.visibility = VISIBLE

        }, 2000)

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment pChatFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            pChatFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}