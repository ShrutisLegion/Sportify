package com.shrutislegion.sportify.lenderactivities
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.OptionalPendingResult
import com.google.android.gms.common.api.ResultCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shrutislegion.sportify.R
import com.shrutislegion.sportify.RegistrationActivity
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.fragment_user.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@Suppress("DEPRECATION")
class UserFragment : Fragment(), GoogleApiClient.OnConnectionFailedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var googleApiClient: GoogleApiClient
    lateinit var gso: GoogleSignInOptions
    lateinit var mGoogleSignInClient:GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_user, container, false)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        googleApiClient = GoogleApiClient.Builder(requireContext()).enableAutoManage(
            LenderHomeActivity(), this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build()
        mGoogleSignInClient= GoogleSignIn.getClient(context,gso)

        val user = FirebaseAuth.getInstance().currentUser
        // check if the user is already signed in
        if (user != null) {
            view.UserName.setText(user.displayName)
            view.UserEmailId.setText(user.email)
            Glide.with(this).load(user.photoUrl).into(view.UserProfilePicture)
        }


        view.SignOutButton.setOnClickListener(View.OnClickListener() {
            mGoogleSignInClient.signOut().addOnCompleteListener{
                Firebase.auth.signOut()
                startActivity(Intent(context, RegistrationActivity::class.java))
            }
        })

        return view
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Toast.makeText(context,"Connection Failed!", Toast.LENGTH_LONG).show()
    }

    private fun handleSignInResult(result: GoogleSignInResult){
        if(result.isSuccess){
            val account: GoogleSignInAccount = result.signInAccount!!
            UserName.setText(account.displayName)
            UserEmailId.setText(account.email)

            Glide.with(this).load(account.photoUrl).into(UserProfilePicture)
        }
        else{
            startActivity(Intent(context, RegistrationActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()

        val opr: OptionalPendingResult<GoogleSignInResult> = Auth.GoogleSignInApi.silentSignIn(googleApiClient)

        if(opr.isDone) run {
            val result: GoogleSignInResult = opr.get()
            handleSignInResult(result)
        }
        else{
            opr.setResultCallback(ResultCallback<GoogleSignInResult>() {
                handleSignInResult(it)
            })
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
                UserFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

}
//            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(ResultCallback<Status>() {
//                if(it.isSuccess){
//                    startActivity(Intent(context, RegistrationActivity::class.java))
//                }
//                else Toast.makeText(context, "Log Out Failed !!", Toast.LENGTH_LONG).show()
//            })
