package com.shrutislegion.sportify.lender_activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.shrutislegion.sportify.PLSignInActivity
import com.shrutislegion.sportify.R
import com.shrutislegion.sportify.RegistrationActivity
import com.shrutislegion.sportify.doas.lenderDaos
import com.shrutislegion.sportify.modules.lander
import kotlinx.android.synthetic.main.activity_lender_log.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@Suppress("DEPRECATION")
class LenderLogActivity : AppCompatActivity() {

    private val RC_SIGN_IN: Int = 123
    private val TAG = "RegActivity Tag"
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    lateinit var googleApiClient: GoogleApiClient
    lateinit var gso: GoogleSignInOptions

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lender_log)

        val leftanim = AnimationUtils.loadAnimation(this, R.anim.leftanim)
        val rightanim = AnimationUtils.loadAnimation(this, R.anim.rightanim)
        val bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottomanimation)
        val inverstoranim = findViewById<LottieAnimationView>(R.id.investoranim)
        val chat = findViewById<LottieAnimationView>(R.id.chat)
        val feedback = findViewById<LottieAnimationView>(R.id.feedback)
        val grow = findViewById<TextView>(R.id.grow)
        val chattext = findViewById<TextView>(R.id.chattext)
        val feedbacktext = findViewById<TextView>(R.id.feedbacktext)
        val text = findViewById<TextView>(R.id.text)

        grow.setAnimation(rightanim)
        inverstoranim.setAnimation(leftanim)
        chat.setAnimation(rightanim)
        feedback.setAnimation(leftanim)
        chattext.setAnimation(leftanim)
        feedbacktext.setAnimation(rightanim)
        text.setAnimation(leftanim)
        signInButton.setAnimation(bottomAnim)

        //google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth

        signInButton.setOnClickListener{
            signIn()
        }
    }
    override fun onStart(){
        super.onStart()
        val currentlander = auth.currentUser
        updateUI(currentlander)
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            // Google Sign In was successful, authenticate with Firebase
            val account = completedTask.getResult(ApiException::class.java)!!
            Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            // Google Sign In failed, update UI appropriately
            Log.w(TAG, "Google sign in failed", e)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        signInButton.visibility = View.GONE
        progressBarSignIn.visibility = View.VISIBLE

        GlobalScope.launch(Dispatchers.IO) {
            val auth = auth.signInWithCredential(credential).await()
            val firebaseUser = auth.user
            withContext(Dispatchers.Main){
                updateUI(firebaseUser)
            }
        }
    }

    private fun updateUI(firebaseUser: FirebaseUser?) {

        if (firebaseUser!=null){

            val checkId = firebaseUser.uid
            var check: Boolean = false
            var check1: Boolean = false
            var check2: Boolean = false

            Firebase.firestore.collection("Landers")
                .get().addOnSuccessListener { result->
                    for(document in result){
                        check1 = true
                        if(document.id == checkId){
                            check = true

                            MaterialAlertDialogBuilder(this).also {
                                // set title for dailog box
                                it.setTitle("Account already exists")
                                // set message for dialog box
                                it.setMessage("Proceed to Login?")
                                // set icon for dialog box
                                it.setIcon(R.drawable.ic_baseline_account_box_24)

                                // perform positive action which deletes details from the lender activity and player activity
                                it.setPositiveButton("CONTINUE") { dialogInterface, which ->

                                    googleSignInClient.signOut().addOnCompleteListener{
                                        Firebase.auth.signOut()
                                    }
                                    startActivity(Intent(this, PLSignInActivity::class.java))
                                    finish()

                                }

                                // performs neutral action
                                it.setNeutralButton("CANCEL"){
                                        dialogInterface, which->

                                    googleSignInClient.signOut().addOnCompleteListener{
                                        Firebase.auth.signOut()
                                    }
                                    signInButton.visibility = VISIBLE
                                    progressBarSignIn.visibility = GONE
                                }

                                // performs negative/NO action
                                it.setNegativeButton("NO"){
                                        dialogInterface, which->

                                    googleSignInClient.signOut().addOnCompleteListener{
                                        Firebase.auth.signOut()
                                    }
                                    Toast.makeText(this, "Please use different account to register", Toast.LENGTH_SHORT).show()
                                    signInButton.visibility = VISIBLE
                                    progressBarSignIn.visibility = GONE
                                }

                                // create the AlertDialogBox
                                val alertDialog: androidx.appcompat.app.AlertDialog = it.create()
                                alertDialog.setCancelable(false)
                                alertDialog.show()

                            }

                        }
                    }
                }

            Handler(Looper.getMainLooper()).postDelayed({
                if(!check) {
                    Firebase.firestore.collection("users")
                        .get().addOnSuccessListener { result ->
                            for (document in result) {
                                check2 = true
                                if (document.id == checkId) {
                                    check = true

                                    MaterialAlertDialogBuilder(this).also {
                                        // set title for dailog box
                                        it.setTitle("Account already exists")
                                        // set message for dialog box
                                        it.setMessage("Proceed to Login?")
                                        // set icon for dialog box
                                        it.setIcon(R.drawable.ic_baseline_account_box_24)

                                        // perform positive action which deletes details from the lender activity and player activity
                                        it.setPositiveButton("CONTINUE") { dialogInterface, which ->

                                            googleSignInClient.signOut().addOnCompleteListener{
                                                Firebase.auth.signOut()
                                            }
                                            startActivity(Intent(this, PLSignInActivity::class.java))
                                            finish()

                                        }

                                        // performs neutral action
                                        it.setNeutralButton("CANCEL"){
                                                dialogInterface, which->

                                            googleSignInClient.signOut().addOnCompleteListener{
                                                Firebase.auth.signOut()
                                            }
                                            signInButton.visibility = VISIBLE
                                            progressBarSignIn.visibility = GONE
                                        }

                                        // performs negative/NO action
                                        it.setNegativeButton("NO"){
                                                dialogInterface, which->

                                            googleSignInClient.signOut().addOnCompleteListener{
                                                Firebase.auth.signOut()
                                            }
                                            Toast.makeText(this, "Please use different account to register", Toast.LENGTH_SHORT).show()
                                            signInButton.visibility = VISIBLE
                                            progressBarSignIn.visibility = GONE
                                        }

                                        // create the AlertDialogBox
                                        val alertDialog: androidx.appcompat.app.AlertDialog = it.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()

                                    }

                                }
                            }
//                            Toast.makeText(this,"$check + $check1 + $check2", Toast.LENGTH_LONG).show()
                            if(check1 && check2 && !check){
                                val lander = lander(firebaseUser.uid, firebaseUser.displayName, firebaseUser.photoUrl.toString())
                                val landersDao = lenderDaos()
                                landersDao.addUser(lander)
                                progressBarSignIn.visibility = View.GONE
                                val mainActivityIntent = Intent(this, LenderHomeActivity::class.java)
                                startActivity(mainActivityIntent)
                                finish()
                            }
                        }
                }
            },1000)


        } else {
            signInButton.visibility = View.VISIBLE
            progressBarSignIn.visibility = View.GONE
//            Toast.makeText(this, "Try Again!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, RegistrationActivity::class.java))
    }
}
