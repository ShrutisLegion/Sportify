package com.shrutislegion.sportify.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.shrutislegion.sportify.R

class pBookedHoursAdapter(var cBookedHoursList: MutableList<Int>, val context: Context) : RecyclerView.Adapter<pBookedHoursAdapter.viewHolder>() {

    class viewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var showHourButton = itemView.findViewById<Button>(R.id.showHourButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.item_pbookedhours_view, parent, false)
        return viewHolder(view)

    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        var model = cBookedHoursList[position]

        if(model == 0){
            holder.showHourButton.text = "00:00"
        }
        if(model == 1){
            holder.showHourButton.text = "01:00"
        }
        if(model == 2){
            holder.showHourButton.text = "02:00"
        }
        if(model == 3){
            holder.showHourButton.text = "03:00"
        }
        if(model == 4){
            holder.showHourButton.text = "04:00"
        }
        if(model == 5){
            holder.showHourButton.text = "05:00"
        }
        if(model == 6){
            holder.showHourButton.text = "06:00"
        }
        if(model == 7){
            holder.showHourButton.text = "07:00"
        }
        if(model == 8){
            holder.showHourButton.text = "08:00"
        }
        if(model == 9){
            holder.showHourButton.text = "09:00"
        }
        if(model == 10){
            holder.showHourButton.text = "10:00"
        }
        if(model == 11){
            holder.showHourButton.text = "11:00"
        }
        if(model == 12){
            holder.showHourButton.text = "12:00"
        }
        if(model == 13){
            holder.showHourButton.text = "13:00"
        }
        if(model == 14){
            holder.showHourButton.text = "14:00"
        }
        if(model == 15){
            holder.showHourButton.text = "15:00"
        }
        if(model == 16){
            holder.showHourButton.text = "16:00"
        }
        if(model == 17){
            holder.showHourButton.text = "17:00"
        }
        if(model == 18){
            holder.showHourButton.text = "18:00"
        }
        if(model == 19){
            holder.showHourButton.text = "19:00"
        }
        if(model == 20){
            holder.showHourButton.text = "20:00"
        }
        if(model == 21){
            holder.showHourButton.text = "21:00"
        }
        if(model == 22){
            holder.showHourButton.text = "22:00"
        }
        if(model == 23){
            holder.showHourButton.text = "23:00"
        }
    }

    override fun getItemCount(): Int {
        return cBookedHoursList.size
    }


}