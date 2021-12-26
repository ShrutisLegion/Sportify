// Activity used to add complexes
// It contains fields which are necessary to be filled, with an image that needs to be uploaded.

package com.shrutislegion.sportify.lender_activities

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.View.VISIBLE
import android.widget.Toast
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.shrutislegion.sportify.R
import com.shrutislegion.sportify.modules.ComplexInfo
import kotlinx.android.synthetic.main.activity_add_complex.*
import java.util.*


@Suppress("DEPRECATION")
class AddComplexActivity : AppCompatActivity() {

    lateinit var database: FirebaseDatabase
    lateinit var storage: FirebaseStorage
    lateinit var auth: FirebaseAuth
    lateinit var dialog: ProgressDialog
    var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_complex)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        database = FirebaseDatabase.getInstance()
        dialog = ProgressDialog(this)

        // Creating a dialog while the complex is being added to the Firebase storage
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        dialog.setTitle("Adding your complex")
        dialog.setMessage("Please Wait")
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)

        // Opening the imagePicker and selecting an complexiomage from the gallery
        floatingActionButton.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .start()
        }

    }

    fun submitComplexDetails(view: android.view.View) {


        // If any of the field is empty then Toast to fill all the details
        if(TextUtils.isEmpty(complexName.text.toString()) ||
            (typeOfSport.text!!.isEmpty()) ||
            (totalCourts.text!!.isEmpty()) ||
            (pricePerHour.text!!.isEmpty()) ||
            (complexLocation.text!!.isEmpty()) ||
            (phoneNumber.text!!.isEmpty()) ||
            (complexDescription.text!!.isEmpty())
        ){
            Toast.makeText(this,"Plase enter all the required details!", Toast.LENGTH_LONG).show()
        }
        // Uploading an image is also compulsory
        else if (uri == null){
            Toast.makeText(this,"Please upload your complex image!", Toast.LENGTH_LONG).show()
        }
        else {
            dialog.show()
            val name = complexName.getText().toString()
            val type = typeOfSport.getText().toString()
            val courts = totalCourts.getText().toString().toInt()
            val price = pricePerHour.getText().toString().toInt()
            val location = complexLocation.getText().toString()
            var uriString: String = ""
            val phone = phoneNumber.getText().toString()
            val description = complexDescription.text.toString()

            // getting the reference of the Firebase Realtime Database
            val reference: StorageReference = storage.getReference().child("complexes_image")
                .child(FirebaseAuth.getInstance().getUid().toString())
                .child(Date().time.toString() + "")


            // Uploading the image and getting a firebase url
            if (uri != null) {
                reference.putFile(uri!!).addOnSuccessListener(OnSuccessListener {
                    Toast.makeText(this, "Image Uploaded Successfully!", Toast.LENGTH_LONG).show()

                    reference.getDownloadUrl().addOnSuccessListener(OnSuccessListener<Uri>() {
                        uriString = it.toString()
                        Toast.makeText(this,"Image downloaded successfully", Toast.LENGTH_LONG).show()

                        //Creating a model and setting it with the suitable fields
                        val User =
                            ComplexInfo(
                                name+"",
                                type+"",
                                price,
                                courts.toString()+"",
                                location+"",
                                uriString+"",
                                phone+"",
                                description+"",
                                FirebaseAuth.getInstance().uid.toString()+"",
                                FirebaseAuth.getInstance().currentUser!!.email.toString()+"",
                                0F,
                                Date().time.toString() + ""
                            )

                        User.imageUri = uriString

                        val complexID = FirebaseDatabase.getInstance()
                            .getReference("Lenders")
                            .child(FirebaseAuth.getInstance().uid.toString())
                            .child("Complexes").push().key

                        // Uploading the data to the firebase
                        FirebaseDatabase.getInstance().
                            getReference("Lenders").
                            child(FirebaseAuth.getInstance().uid.toString())
                            .child("Complexes")
                            .child(complexID!!).setValue(User).addOnSuccessListener {
                                dialog.dismiss()
                                Toast.makeText(this, "Complex added successfully!!", Toast.LENGTH_LONG).show()
                                startActivity(Intent(this, LenderHomeActivity::class.java))
                                finish()
                            }

                        FirebaseDatabase.getInstance().getReference("All Complexes").child(complexID!!).setValue(User)


                    })
                })
            } else {
                Toast.makeText(this, "Please upload the complex image !!", Toast.LENGTH_SHORT).show()
            }


        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        uri = data?.getData()
        if(uri == null){
            Toast.makeText(this, "Please upload image of your complex!", Toast.LENGTH_SHORT).show()
        }
        else {
            uploadedImage.setImageURI(uri)
            uploadedImage.visibility = VISIBLE
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, LenderHomeActivity::class.java))
    }

    // getting the  location from the Google API
    fun loc(view: View) {
        val intent = Intent(this, LocationActivity::class.java)
        startActivity(intent)
    }


}