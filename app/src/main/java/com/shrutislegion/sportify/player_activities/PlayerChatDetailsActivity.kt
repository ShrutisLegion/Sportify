package com.shrutislegion.sportify.player_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_chat_details.*
import com.shrutislegion.sportify.R
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.shrutislegion.sportify.adapters.chatDetailsAdapter
import com.shrutislegion.sportify.modules.ChatMessageInfo
import java.util.*
import kotlin.collections.ArrayList


class ChatDetailsActivity : AppCompatActivity() {

//    lateinit var binding: ActivityChatDetailsBinding
    var receiverId: String? = null
    var storeMessage: ArrayList<ChatMessageInfo> = ArrayList<ChatMessageInfo>()
    lateinit var adapter: chatDetailsAdapter

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

    companion object{
        const val EXTRA_USERNAME= "name_extra"
        const val EXTRA_USEREMAIL= "email_extra"
        const val EXTRA_USERLASTMSG= "lastMsg_extra"
        const val EXTRA_USERIMGURL= "imgUrl_extra"
        const val EXTRA_RECEIVERID= "id_extra"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
//        binding = ActivityChatDetailsBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        setContentView(R.layout.activity_chat_details)

        // To get the shared intent data
        var userName = intent.getStringExtra(EXTRA_USERNAME)
        var userEmail = intent.getStringExtra(EXTRA_USEREMAIL)
        var userLastMsg = intent.getStringExtra(EXTRA_USERLASTMSG)
        var userImgUrl = intent.getStringExtra(EXTRA_USERIMGURL)
        receiverId = intent.getStringExtra(EXTRA_RECEIVERID)

        Glide.with(this)
            .load(userImgUrl)
            .override(600, 400)
            .placeholder(R.drawable.ic_account)
            .into(chatUserProfileImage)

        chatUserName.text = userName.toString()

        backArrowButton.setOnClickListener {

            val intent: Intent = Intent(this, PlayerHomeActivity::class.java)

            intent.putExtra(PlayerHomeActivity.EXTRA_FRAGMENT, "2")

            startActivity(intent)

        }

        adapter = chatDetailsAdapter(storeMessage, this)
        chatDetailsRV.adapter = adapter

        val linearLayoutManager = LinearLayoutManagerWrapper(this, LinearLayoutManager.VERTICAL, false)
        chatDetailsRV.layoutManager = linearLayoutManager

        val senderId = FirebaseAuth.getInstance().currentUser!!.uid

        val ref = FirebaseDatabase.getInstance().reference
            .child("Chats")
            .child(senderId+receiverId)

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){
                    storeMessage.clear()

                    for(dss in snapshot.children){
                        val messageModel: ChatMessageInfo = dss.getValue<ChatMessageInfo>()!!
                        storeMessage.add(messageModel)
                    }

                    adapter.notifyDataSetChanged()

                }

            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        ref.addValueEventListener(postListener)


        chatDetailsSendButton.setOnClickListener {

            var msg = chatDetailsMessage.text.toString()
            val msgData = ChatMessageInfo(senderId+"", receiverId+"", msg+"", Date().time)

            chatDetailsMessage.setText("")

            FirebaseDatabase.getInstance().reference
                .child("Chats")
                .child(senderId+receiverId)
                .push()
                .setValue(msgData).addOnSuccessListener {

                    FirebaseDatabase.getInstance().reference
                        .child("Chats")
                        .child(receiverId+senderId)
                        .push()
                        .setValue(msgData).addOnSuccessListener {

                        }

                }

        }

        chatUserPhoneCall.setOnClickListener {

            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:0123456789")
            startActivity(callIntent)

        }

    }

    override fun onBackPressed() {
        super.onBackPressed()

        val intent: Intent = Intent(this, PlayerHomeActivity::class.java)

        intent.putExtra(PlayerHomeActivity.EXTRA_FRAGMENT, "2")

        startActivity(intent)
    }
}



//            val fragment = pChatFragment()
//
//            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
//
//            transaction.replace(R.id.fragment_containerChat, fragment).commit()
//
//            chatDetailsRV.visibility = GONE
//            chatDetailsLL.visibility = GONE
//            chatDetailsToolbar.visibility = GONE
//            supportActionBar?.show()

//            val fm: FragmentManager = supportFragmentManager
//            val fragment = pChatFragment()
//            fm.beginTransaction().add(R.id.fragment_containerChat, fragment).commit()
//            finish()