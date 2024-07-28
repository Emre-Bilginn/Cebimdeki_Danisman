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
import com.emrebilgin.cebimdekidanisman.databinding.ActivityGiveUsAdviceBinding
import com.emrebilgin.cebimdekidanisman.databinding.ActivityUserCommentsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import org.checkerframework.checker.units.qual.A

class GiveUsAdvice : AppCompatActivity() {

    private lateinit var binding : ActivityGiveUsAdviceBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: AdviceRecyclerAdapter
    private var advices = arrayListOf<Advice>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGiveUsAdviceBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firestore = Firebase.firestore
        auth = Firebase.auth

        adapter= AdviceRecyclerAdapter()
        binding.guaRecyclerView.adapter=adapter
        binding.guaRecyclerView.layoutManager= LinearLayoutManager(this)


        binding.sendButton.setOnClickListener {

            auth.currentUser?.let {
                val user = it.email
                val commentText = binding.guaEditText.text.toString()
                val date = FieldValue.serverTimestamp()

                val dataMap = HashMap<String,Any>()
                dataMap.put("text",commentText)
                dataMap.put("user",user!!)
                dataMap.put("date",date)

                firestore.collection("Advices").add(dataMap).addOnSuccessListener {
                    binding.guaEditText.setText("")
                }.addOnFailureListener {
                    Toast.makeText(this,it.localizedMessage, Toast.LENGTH_LONG).show()
                    binding.guaEditText.setText("")
                }

            }
        }

        firestore.collection("Advices").orderBy("date", Query.Direction.ASCENDING).addSnapshotListener { value, error ->

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
                        advices.clear()
                        for (document in documents)
                        {
                            val text =document.get("text") as String
                            val user =document.get("user") as String
                            val advice =Advice(user,text)
                            advices.add(advice)
                            adapter.advices = advices

                        }
                        adapter.notifyDataSetChanged()
                    }

                }
            }
        }

    }

    fun backToMainPage(view: View){
        val intent = Intent(this@GiveUsAdvice,MainPage::class.java)
        startActivity(intent)
        finish()
    }
}