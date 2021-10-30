package com.shrutislegion.sportify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.item_complexdetails.*

class LenderSharedActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_CONTACT = "name_extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lender_shared)

        val name = intent.getStringExtra(EXTRA_CONTACT) // key is used to identify the value that is to be used
        nameOfComplex.setText("$name")
    }
}