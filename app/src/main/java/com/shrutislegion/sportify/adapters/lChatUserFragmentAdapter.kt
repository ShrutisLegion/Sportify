// Adapter used in the Lender Chat Fragment
// This is used to show all the players and their last message with the current owner

package com.shrutislegion.sportify.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.shrutislegion.sportify.R
import com.shrutislegion.sportify.lender_activities.LenderChatDetailsActivity
import com.shrutislegion.sportify.modules.LoggedInUserInfo

class lChatUserFragmentAdapter(var storeUsers: ArrayList<LoggedInUserInfo>, val context: Context):
    RecyclerView.Adapter<lChatUserFragmentAdapter.viewHolder>(){

    // ViewHolder to hold the fields
    class viewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        var chatUserProfileImage = itemView.findViewById<ImageView>(R.id.chatUserProfileImage)
        var chatUserName = itemView.findViewById<TextView>(R.id.chatUserName)
        var chatUserLastMessage = itemView.findViewById<TextView>(R.id.chatUserLastMessage)
        var pChatCardView = itemView.findViewById<CardView>(R.id.pChatCardView)

    }

    // Inflates the layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_chatdetails_home, parent, false)

        return viewHolder(view)

    }

    // Binds the fields with the values taken from the FirebaseDatabase
    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        var model = storeUsers[position]

        Glide.with(holder.chatUserLastMessage.context)
            .load(model.photoUrl)
            .override(600, 400)
            .placeholder(R.drawable.ic_account)
            .into(holder.chatUserProfileImage)

        holder.chatUserName.text = model.userName.toString()

        // Getting the last message from the Database using the orderByChild() and limitToLast()
        FirebaseDatabase.getInstance().reference
            .child("Chats")
            .child(FirebaseAuth.getInstance().currentUser!!.uid + model.userId)
            .orderByChild("messageTime")
            .limitToLast(1)
            .addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.hasChildren()){

                        for(i in snapshot.children){
                            holder.chatUserLastMessage.text = i.child("message").getValue().toString()
                        }

                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        // Go to the LenderChatDetailsActivity and passing the strings to the next intent
        holder.pChatCardView.setOnClickListener {

            val intent = Intent(holder.pChatCardView.context, LenderChatDetailsActivity::class.java)

            intent.putExtra(LenderChatDetailsActivity.EXTRA_USERNAME, model.userName.toString())
            intent.putExtra(LenderChatDetailsActivity.EXTRA_USEREMAIL, model.userEmail.toString())
            intent.putExtra(LenderChatDetailsActivity.EXTRA_USERLASTMSG, model.lastMessage.toString())
            intent.putExtra(LenderChatDetailsActivity.EXTRA_USERIMGURL, model.photoUrl.toString())
            intent.putExtra(LenderChatDetailsActivity.EXTRA_RECEIVERID, model.userId.toString())


            holder.pChatCardView.context.startActivity(intent)

        }
    }
    // returns the size
    override fun getItemCount(): Int {
        return storeUsers.size
    }

}