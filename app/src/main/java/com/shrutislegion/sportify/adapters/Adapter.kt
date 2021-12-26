// Adapter used in the Main activity to change the liquid swipe

@file:Suppress("DEPRECATION")

package com.shrutislegion.sportify.adapters

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.shrutislegion.sportify.PageFragment

class Adapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    private val total: Int = 4
    private val data : ArrayList<PageFragment> = ArrayList(total)

    init {
        for (i in 0 until total) {
            val fragment = PageFragment()
            val bundle = Bundle()
            if(i==1){
                Handler(Looper.getMainLooper()).postDelayed({
                    bundle.putInt("POSITION", i+1)
                    fragment.arguments = bundle
                    data.add(fragment)
                }, 3000)
            }
            bundle.putInt("POSITION", i+1)
            fragment.arguments = bundle
            data.add(fragment)
        }
    }


    override fun getItem(position: Int): Fragment {
        return data[position]
    }

    override fun getCount(): Int {
        return total
    }
}
