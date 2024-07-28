package com.emrebilgin.cebimdekidanisman

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.emrebilgin.cebimdekidanisman.databinding.ActivityChatPageBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore

class ChatPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityChatPageBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var adapter: ChatRecyclerAdapter
    private var chats = arrayListOf<Chat>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firestore = Firebase.firestore
        auth = Firebase.auth



        adapter= ChatRecyclerAdapter()
        binding.chatRecycler.adapter=adapter
        binding.chatRecycler.layoutManager=LinearLayoutManager(this)

        binding.sendbutton.setOnClickListener {

            auth.currentUser?.let {
                val user = it.email
                val chatText = binding.chatText.text.toString()
                val date =FieldValue.serverTimestamp()

                val dataMap = HashMap<String,Any>()
                dataMap.put("text",chatText)
                dataMap.put("user",user!!)
                dataMap.put("date",date)

                firestore.collection("Chats").add(dataMap).addOnSuccessListener {
                    binding.chatText.setText("")
                }.addOnFailureListener {
                    Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
                    binding.chatText.setText("")
                }

            }
        }

        firestore.collection("Chats").orderBy("date",Query.Direction.ASCENDING).addSnapshotListener { value, error ->

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
                        chats.clear()
                        for (document in documents)
                        {
                            val text =document.get("text") as String
                            val user =document.get("user") as String
                            val chat =Chat(user,text)
                            chats.add(chat)
                            adapter.chats = chats

                        }
                        adapter.notifyDataSetChanged()
                    }

                }
            }
        }

    }

    fun backToMainPage(view: View){
        val intent = Intent(this@ChatPage,MainPage::class.java)
        startActivity(intent)
        finish()
    }


}