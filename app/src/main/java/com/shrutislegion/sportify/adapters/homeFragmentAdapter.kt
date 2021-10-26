package com.shrutislegion.sportify.adapters

import android.app.AlertDialog
import android.content.ContentResolver.ANY_CURSOR_ITEM_TYPE
import android.provider.Settings.System.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.shrutislegion.sportify.LenderHomeActivity
import com.shrutislegion.sportify.R
import com.shrutislegion.sportify.modules.ComplexInfo
import kotlin.coroutines.coroutineContext
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class homeFragmentAdapter(options: FirebaseRecyclerOptions<ComplexInfo>) :
    FirebaseRecyclerAdapter<ComplexInfo, homeFragmentAdapter.myViewHolder>(options) {

    lateinit var databaseReference: DatabaseReference

    inner class myViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        // creating viewHolder and getting all the required views by their Ids
        val name = itemView.findViewById<TextView>(R.id.nameOfComplex)
        val type = itemView.findViewById<TextView>(R.id.sportType)
        val courts = itemView.findViewById<TextView>(R.id.courtsCount)
        val price = itemView.findViewById<TextView>(R.id.hourPrice)
        val location = itemView.findViewById<TextView>(R.id.complexLocation)
        var image = itemView.findViewById<ImageView>(R.id.complexImage)
        var delete = itemView.findViewById<ImageView>(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.item_complexdetails, parent, false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int, model: ComplexInfo) {

        // setting the text fields with the values obtained from the firebase in the recyclerView
        holder.name.setText(model.complexName)
        holder.type.setText(model.typeOfSport)
        holder.courts.setText(model.numberOfCourts)
        holder.price.setText(model.pricePerHour)
        holder.location.setText(model.location)

        // Glide used to load the image from the uri stored in firebase
        Glide.with(holder.image.context).load(model.imageUri).into(holder.image)

        // putting OnClickListener on delete button and creating an alert dialogbox
        holder.delete.setOnClickListener {

            AlertDialog.Builder(holder.name.context).also {
                // set title for dailog box
                it.setTitle("Delete complex details")
                // set message for dialog box
                it.setMessage("Are you sure you want to delete the complex?")
                // set icon for dialog box
                it.setIcon(R.drawable.ic_baseline_warning_24)

                // perform positive action
                it.setPositiveButton("YES"){
                    dialogInterface, which->
                    FirebaseDatabase.getInstance().reference
                        .child("Lenders")
                        .child(FirebaseAuth.getInstance().uid.toString())
                        .child("Complexes")
                        .child(getRef(position).key.toString())
                        .removeValue().addOnCompleteListener{
                            Toast.makeText(holder.name.context, "Complex details deleted successfully !!", Toast.LENGTH_SHORT).show()
                        }
                }

                // performs neutral action
                it.setNeutralButton("CANCEL"){
                    dialogInterface, which->
                }

                // performs negative/NO action
                it.setNegativeButton("NO"){
                    dialogInterface, which->
                        Toast.makeText(holder.name.context, "Operation cancelled!", Toast.LENGTH_SHORT).show()
                }

                // create the AlertDialogBox
                val alertDialog:AlertDialog = it.create()
                alertDialog.setCancelable(false)
                alertDialog.show()

            }
        }

    }

}
