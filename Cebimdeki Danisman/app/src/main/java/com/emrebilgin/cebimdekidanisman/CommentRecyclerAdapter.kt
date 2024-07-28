package com.emrebilgin.cebimdekidanisman

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class CommentRecyclerAdapter: RecyclerView.Adapter<CommentRecyclerAdapter.CommentHolder>() {

    private val COMMENT1 = 1
    private val COMMENT2 = 2

    class CommentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    private val diffUtil = object : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var comments : List<Comment>
        get() =recyclerListDiffer.currentList
        set(value) =recyclerListDiffer.submitList(value)

    override fun getItemViewType(position: Int): Int {

        val chat = comments.get(position)

        if (chat.user == FirebaseAuth.getInstance().currentUser?.email.toString()){
            return COMMENT1
        }
        else{
            return COMMENT2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        if (viewType == COMMENT1){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_row, parent, false)
            return CommentHolder(view)
        }
        else{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_row, parent, false)
            return CommentHolder(view)
        }
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        val textView = holder.itemView.findViewById<TextView>(R.id.commenttextView)
        textView.text = "${comments.get(position).user} : ${comments.get(position).text}"
    }
    override fun getItemCount(): Int {
        return comments.size
    }



}