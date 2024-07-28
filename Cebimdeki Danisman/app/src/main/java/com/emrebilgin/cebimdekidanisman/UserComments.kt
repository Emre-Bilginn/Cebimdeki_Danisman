package com.emrebilgin.cebimdekidanisman

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.emrebilgin.cebimdekidanisman.databinding.ActivityFrequentlyAskedQuestionsBinding
import com.emrebilgin.cebimdekidanisman.databinding.ActivityUserCommentsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore

class UserComments : AppCompatActivity() {

    private lateinit var binding : ActivityUserCommentsBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: CommentRecyclerAdapter
    private var comments = arrayListOf<Comment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserCommentsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firestore = Firebase.firestore
        auth = Firebase.auth

        adapter= CommentRecyclerAdapter()
        binding.commentrecyclerView.adapter=adapter
        binding.commentrecyclerView.layoutManager= LinearLayoutManager(this)


        binding.sendButton.setOnClickListener {

            auth.currentUser?.let {
                val user = it.email
                val commentText = binding.commentEditText.text.toString()
                val date = FieldValue.serverTimestamp()

                val dataMap = HashMap<String,Any>()
                dataMap.put("text",commentText)
                dataMap.put("user",user!!)
                dataMap.put("date",date)

                firestore.collection("Comments").add(dataMap).addOnSuccessListener {
                    binding.commentEditText.setText("")
                }.addOnFailureListener {
                    Toast.makeText(this,it.localizedMessage, Toast.LENGTH_LONG).show()
                    binding.commentEditText.setText("")
                }

            }
        }

        firestore.collection("Comments").orderBy("date", Query.Direction.ASCENDING).addSnapshotListener { value, error ->

            if (error!=null){
                Toast.makeText(this,error.localizedMessage,Toast.LENGTH_LONG).show()
            }
            else{
                if (value != null) {
                    if (value.isEmpty){
                        Toast.makeText(this,"Mesaj Yok",Toast.LENGTH_LONG).show()
                    }
                    else{
                        val documents =value.documents
                        comments.clear()
                        for (document in documents)
                        {
                            val text =document.get("text") as String
                            val user =document.get("user") as String
                            val comment =Comment(user,text)
                            comments.add(comment)
                            adapter.comments = comments

                        }
                        adapter.notifyDataSetChanged()
                    }

                }
            }
        }

    }

    fun backToMainPage(view: View){
        val intent = Intent(this@UserComments,MainPage::class.java)
        startActivity(intent)
        finish()
    }



}