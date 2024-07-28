package com.emrebilgin.cebimdekidanisman

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class AppointmentRecyclerAdapter : RecyclerView.Adapter<AppointmentRecyclerAdapter.AppointmentHolder>(){

    private val APPOINTMENT1 = 1
    private val APPOINTMENT2 = 2

    class AppointmentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    private val diffUtil = object : DiffUtil.ItemCallback<Appointment_>() {
        override fun areItemsTheSame(oldItem: Appointment_, newItem: Appointment_): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Appointment_, newItem: Appointment_): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var appointments : List<Appointment_>
        get() =recyclerListDiffer.currentList
        set(value) =recyclerListDiffer.submitList(value)

    override fun getItemViewType(position: Int): Int {

        val chat = appointments.get(position)

        if (chat.user == FirebaseAuth.getInstance().currentUser?.email.toString()){
            return APPOINTMENT1
        }
        else{
            return APPOINTMENT2
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.appointment_row, parent, false)
        return AppointmentHolder(view)

    }

    override fun onBindViewHolder(holder: AppointmentHolder, position: Int) {
        val textView = holder.itemView.findViewById<TextView>(R.id.appointmenttextView)
        textView.text = "${appointments.get(position).user} : ${appointments.get(position).text}"
    }

    override fun getItemCount(): Int {
        return appointments.size
    }

}