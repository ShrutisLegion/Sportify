package com.shrutislegion.sportify.doas

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.shrutislegion.sportify.modules.Lender
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LenderDaos {

    private val db = FirebaseFirestore.getInstance()
    private val landerCollection = db.collection("Landers")

    fun addUser(lander: Lender?){
        lander?.let {
            GlobalScope.launch(Dispatchers.IO){
                landerCollection.document(lander.lid).set(it)
            }
        }
    }
    fun getUserById(lId: String): Task<DocumentSnapshot> {
        return landerCollection.document(lId).get()
    }

}