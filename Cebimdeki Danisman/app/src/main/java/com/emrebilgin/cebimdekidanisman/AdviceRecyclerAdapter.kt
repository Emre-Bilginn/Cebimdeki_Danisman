package com.emrebilgin.cebimdekidanisman

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class AdviceRecyclerAdapter : RecyclerView.Adapter<AdviceRecyclerAdapter.AdviceHolder>() {

    private val ADVICE1 = 1
    private val ADVICE2 = 2

    class AdviceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    private val diffUtil = object : DiffUtil.ItemCallback<Advice>() {
        override fun areItemsTheSame(oldItem: Advice, newItem: Advice): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Advice, newItem: Advice): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var advices : List<Advice>
        get() =recyclerListDiffer.currentList
        set(value) =recyclerListDiffer.submitList(value)

    override fun getItemViewType(position: Int): Int {
        val chat = advices.get(position)

        if (chat.user == FirebaseAuth.getInstance().currentUser?.email.toString()){
            return ADVICE1
        }
        else{
            return ADVICE2
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdviceHolder {
        if (viewType == ADVICE1){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.advices_row, parent, false)
            return AdviceHolder(view)
        }
        else{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.advices_row, parent, false)
            return AdviceHolder(view)
        }
    }

    override fun onBindViewHolder(holder: AdviceHolder, position: Int) {
        val textView = holder.itemView.findViewById<TextView>(R.id.advicetextView)
        textView.text = "${advices.get(position).user} : ${advices.get(position).text}"
    }

    override fun getItemCount(): Int {
        return advices.size
    }
}