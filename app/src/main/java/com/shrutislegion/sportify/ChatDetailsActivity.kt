package com.shrutislegion.sportify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.shrutislegion.sportify.databinding.ActivityChatDetailsBinding
import kotlinx.android.synthetic.main.activity_chat_details.*
import kotlinx.android.synthetic.main.fragment_p_home.*
import com.shrutislegion.sportify.R
import androidx.fragment.app.FragmentManager
import com.shrutislegion.sportify.player_activities.fragments.pChatFragment
import android.app.Fragment
import android.view.View.GONE
import androidx.fragment.app.FragmentTransaction


class ChatDetailsActivity : AppCompatActivity() {

//    lateinit var binding: ActivityChatDetailsBinding

    companion object{
        const val EXTRA_USERNAME= "name_extra"
        const val EXTRA_USEREMAIL= "email_extra"
        const val EXTRA_USERLASTMSG= "lastMsg_extra"
        const val EXTRA_USERIMGURL= "imgUrl_extra"
        const val EXTRA_USERID= "id_extra"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
//        binding = ActivityChatDetailsBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        setContentView(R.layout.activity_chat_details)

        // To get the shared intent data
        var userName = intent.getStringExtra(ChatDetailsActivity.EXTRA_USERNAME)
        var userEmail = intent.getStringExtra(ChatDetailsActivity.EXTRA_USEREMAIL)
        var userLastMsg = intent.getStringExtra(ChatDetailsActivity.EXTRA_USERLASTMSG)
        var userImgUrl = intent.getStringExtra(ChatDetailsActivity.EXTRA_USERIMGURL)
        var userId = intent.getStringExtra(ChatDetailsActivity.EXTRA_USERID)

        Glide.with(this)
            .load(userImgUrl)
            .override(600, 400)
            .placeholder(R.drawable.ic_account)
            .into(chatUserProfileImage)

        chatUserName.text = userName.toString()

        backArrowButton.setOnClickListener {

            val fragment = pChatFragment()

            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

            transaction.replace(R.id.fragment_containerChat, fragment).commit()

            chatDetailsRV.visibility = GONE
            chatDetailsLL.visibility = GONE
            chatDetailsToolbar.visibility = GONE
            supportActionBar?.show()

//            val fm: FragmentManager = supportFragmentManager
//            val fragment = pChatFragment()
//            fm.beginTransaction().add(R.id.fragment_containerChat, fragment).commit()
//            finish()

        }

    }
}