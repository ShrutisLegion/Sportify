package com.shrutislegion.sportify.doas

import android.widget.GridLayout
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.shrutislegion.sportify.modules.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserDao {
    private val db = FirebaseFirestore.getInstance()
    private val userCollection = db.collection("users")

    fun addUser(user: User?){
        user?.let {
            GlobalScope.launch(Dispatchers.IO){
                userCollection.document(user.uid).set(it)
            }
        }
    }
    fun getUserById(uId: String): Task<DocumentSnapshot>{
        return userCollection.document(uId).get()
    }
}