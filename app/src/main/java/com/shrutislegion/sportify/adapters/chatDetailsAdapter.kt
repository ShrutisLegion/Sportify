package com.shrutislegion.sportify.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.shrutislegion.sportify.R
import com.shrutislegion.sportify.modules.ChatMessageInfo
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class chatDetailsAdapter(var storeMessage: ArrayList<ChatMessageInfo>, val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var SENDER_VIEW_TYPE: Int = 1
    var RECEIVER_VIEW_TYPE:Int = 2


    class SenderViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        var senderMsgDetailText = itemView.findViewById<TextView>(R.id.senderMsgDetailText)
        var senderTimetext = itemView.findViewById<TextView>(R.id.senderTimeText)

    }

    class ReceiverViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        var receiverMsgDetailText = itemView.findViewById<TextView>(R.id.receiverMsgDetailText)
        var receiverTimeText = itemView.findViewById<TextView>(R.id.receiverTimeText)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType == SENDER_VIEW_TYPE){

            val view = LayoutInflater.from(context)
                .inflate(R.layout.item_sender_msg, parent, false)
            return SenderViewHolder(view)

        }
        else{

            val view = LayoutInflater.from(context)
                .inflate(R.layout.item_receiver_msg, parent, false)
            return ReceiverViewHolder(view)

        }

    }

    override fun getItemViewType(position: Int): Int {

        if(storeMessage[position].senderId.equals(FirebaseAuth.getInstance().currentUser!!.uid)){

            return SENDER_VIEW_TYPE

        }
        else{

            return  RECEIVER_VIEW_TYPE

        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var messageInfo:ChatMessageInfo = storeMessage[position]

        if(holder.javaClass == SenderViewHolder::class.java){

            (holder as SenderViewHolder).senderMsgDetailText.text =  messageInfo.message
            val dateFormat: SimpleDateFormat = SimpleDateFormat("hh:mm a")
            (holder as SenderViewHolder).senderTimetext.text = dateFormat.format(Date(messageInfo.messageTime))

        }
        else{

            (holder as ReceiverViewHolder).receiverMsgDetailText.text = messageInfo.message
            val dateFormat: SimpleDateFormat = SimpleDateFormat("hh:mm a")
            (holder as ReceiverViewHolder).receiverTimeText.text = dateFormat.format(Date(messageInfo.messageTime))

        }

    }

    override fun getItemCount(): Int {
        return  storeMessage.size
    }

}