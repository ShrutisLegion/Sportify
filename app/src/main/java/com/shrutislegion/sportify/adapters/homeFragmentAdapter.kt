package com.shrutislegion.sportify.adapters

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.shrutislegion.sportify.R
import com.shrutislegion.sportify.modules.ComplexInfo
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.shrutislegion.sportify.lenderactivities.LenderSharedActivity
import androidx.core.util.Pair
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.storage.FirebaseStorage
import androidx.core.content.ContextCompat.startActivity

import com.google.firebase.dynamiclinks.ShortDynamicLink

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task

import com.google.firebase.dynamiclinks.FirebaseDynamicLinks

import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.DynamicLink.AndroidParameters


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
        var phone = itemView.findViewById<TextView>(R.id.phoneNumber)
        var description = itemView.findViewById<TextView>(R.id.complexDescription)
        var card = itemView.findViewById<CardView>(R.id.card)
        var progressBarLCard = itemView.findViewById<ProgressBar>(R.id.progressBarLCard)
        var shareButton = itemView.findViewById<Button>(R.id.shareButton)
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
        holder.description.setText(model.description)
        holder.phone.setText(model.phone)

        // Glide used to load the image from the uri stored in firebase
        Glide.with(holder.image.context).load(model.imageUri).listener(object :
            RequestListener<Drawable> {
            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: com.bumptech.glide.load.DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                holder.progressBarLCard.visibility = View.GONE

                return false
            }

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }
        })
            .override(600,400)
            .placeholder(R.drawable.loading_image)
            .into(holder.image)

        //on click on card and start the Lender Share activity
        holder.card.setOnClickListener{

            val intent = Intent(holder.name.context, LenderSharedActivity::class.java)

            // Transfer all the required data to the Shared activity by putExtra
            intent.putExtra(LenderSharedActivity.EXTRA_IMAGEURI, model.imageUri.toString())
            intent.putExtra(LenderSharedActivity.EXTRA_NAME, holder.name.text.toString())
            intent.putExtra(LenderSharedActivity.EXTRA_PHONE, holder.phone.text.toString())
            intent.putExtra(LenderSharedActivity.EXTRA_SPORT, holder.type.text.toString())
            intent.putExtra(LenderSharedActivity.EXTRA_LOCATION, holder.location.text.toString())
            intent.putExtra(LenderSharedActivity.EXTRA_DESCRIPTION, holder.description.text.toString())
            intent.putExtra(LenderSharedActivity.EXTRA_PRICE, holder.price.text.toString())
            intent.putExtra(LenderSharedActivity.EXTRA_COURTS, holder.courts.text.toString())

            // create pairs of View and String for the transition effect to take place
            val p1 = Pair.create(holder.image as View, "image")
            val p2 = Pair.create<View, String>(holder.name, "complexName")
            val p3 = Pair.create<View, String>(holder.phone, "phoneNumber")
            val p4 = Pair.create<View, String>(holder.type, "sportType")
            val p5 = Pair.create<View, String>(holder.location, "location")
            val p6 = Pair.create<View, String>(holder.description, "description")
            val p7 = Pair.create<View, String>(holder.price, "price")
            val p8 = Pair.create<View, String>(holder.courts, "courts")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(holder.name.context as Activity, p1, p2, p3, p4, p5, p6, p7, p8)

            // Start the Shared activity with the transition
            holder.name.context.startActivity(intent, options.toBundle())
        }
        holder.shareButton.setOnClickListener {

            Toast.makeText(holder.name.context, "create link ", Toast.LENGTH_LONG).show()
            val dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://www.example.com/"))
                .setDynamicLinkDomain("shrutislegion.page.link") // Open links with this app on Android
                .setAndroidParameters(
                    AndroidParameters.Builder().build()
                ) // Open links with com.example.ios on iOS
                //.setIosParameters(new DynamicLink.IosParameters.Builder("com.example.ios").build())
                .buildDynamicLink()

            val dynamicLinkUri: Uri = dynamicLink.uri
            Toast.makeText(holder.name.context, "  Long refer " + dynamicLink.uri, Toast.LENGTH_LONG).show()
            //   https://referearnpro.page.link?apn=blueappsoftware.referearnpro&link=https%3A%2F%2Fwww.blueappsoftware.com%2F
            // apn  ibi link
            // manuall link
            //   https://referearnpro.page.link?apn=blueappsoftware.referearnpro&link=https%3A%2F%2Fwww.blueappsoftware.com%2F
            // apn  ibi link
            // manuall link
            val sharelinktext = "https://shrutislegion.page.link/?" +
                    "link=http://www.Sportify.com/mycomplex.php?comid="+getRef(position).key.toString() +
                    "&st="+"Let's get Sportified"+
                    "&sd="+"Join me!!!"+
                    "&si="+"model.imageUri.toString()";

            val shortLinkTask: Task<ShortDynamicLink> = FirebaseDynamicLinks.getInstance()
                .createDynamicLink() //.setLongLink(dynamicLink.getUri())
                .setLongLink(Uri.parse(sharelinktext)) // manually
                .buildShortDynamicLink()
                .addOnCompleteListener{ task ->
                        if (task.isSuccessful) {
                            // Short link created
                            val shortLink: Uri? = task.result.shortLink
                            val flowchartLink: Uri? = task.result.previewLink
                            Toast.makeText(holder.name.context, "short link " + shortLink.toString(), Toast.LENGTH_SHORT).show()
                            // share app dialog
                            val intent = Intent()
                            intent.action = Intent.ACTION_SEND
                            intent.putExtra(Intent.EXTRA_TEXT, "Let's get Sportified!!\nJoin me at this cool Sport Complex.\n"+shortLink.toString())
                            intent.type = "text/plain"
                            holder.name.context.startActivity(Intent.createChooser(intent, "Share to : "))
                        } else {
                            // Error
                            // ...
                            Toast.makeText(holder.name.context, " error " + task.exception, Toast.LENGTH_SHORT).show()
                        }
                    }
//            val intent = Intent()
//            intent.action = Intent.ACTION_SEND
//            intent.putExtra(Intent.EXTRA_TEXT, )
//            intent.type = "text/plain"
//            holder.name.context.startActivity(Intent.createChooser(intent, "Share to : "))
        }



        // putting OnClickListener on delete button and creating an alert dialogbox
        holder.delete.setOnClickListener {

            MaterialAlertDialogBuilder(holder.name.context).also {
                // set title for dailog box
                it.setTitle("Delete complex details")
                // set message for dialog box
                it.setMessage("Are you sure you want to delete the complex?")
                // set icon for dialog box
                it.setIcon(R.drawable.ic_baseline_warning_24)

                // perform positive action which deletes details from the lender activity and player activity
                it.setPositiveButton("YES"){
                    dialogInterface, which->
                    FirebaseDatabase.getInstance().reference
                        .child("Lenders")
                        .child(FirebaseAuth.getInstance().uid.toString())
                        .child("Complexes")
                        .child(getRef(position).key.toString())
                        .removeValue().addOnCompleteListener{
                            Toast.makeText(
                                holder.name.context,
                                "Complex details deleted successfully !!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    FirebaseDatabase.getInstance()
                        .getReference("All Complexes")
                        .child(getRef(position).key.toString())
                        .removeValue().addOnCompleteListener {
                            Toast.makeText(
                                holder.name.context,
                                "Complex details deleted from All complexes also !!",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    // deletes the image stored in Firebase Storage from the Url
                    FirebaseStorage.getInstance().getReferenceFromUrl(model.imageUri!!).delete()
                        .addOnSuccessListener {
                            Toast.makeText(holder.name.context,"Image deleted Successfully !!", Toast.LENGTH_LONG).show()
                        }
                        .addOnFailureListener{
                            Toast.makeText(holder.name.context, "Unsuccessful", Toast.LENGTH_LONG).show()
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
                val alertDialog: androidx.appcompat.app.AlertDialog = it.create()
                alertDialog.setCancelable(false)
                alertDialog.show()

            }

        }

    }

}
