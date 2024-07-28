package com.emrebilgin.cebimdekidanisman

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.emrebilgin.cebimdekidanisman.databinding.ActivityAppointmentListBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AppointmentList : AppCompatActivity() {

    private lateinit var binding: ActivityAppointmentListBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: AppointmentRecyclerAdapter
    private var appointments = arrayListOf<Appointment_>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firestore = Firebase.firestore
        auth = Firebase.auth

        adapter= AppointmentRecyclerAdapter()
        binding.appointmentrecyclerView.adapter=adapter
        binding.appointmentrecyclerView.layoutManager= LinearLayoutManager(this)

        fetchAppointments()
    }
    private fun fetchAppointments(){
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        firestore.collection("Appointments").orderBy("date", Query.Direction.ASCENDING).addSnapshotListener { value, error ->

            if (error !=null){


                Toast.makeText(this, error.localizedMessage,Toast.LENGTH_LONG).show()
            }
            else{
                if (value != null) {
                    if (value.isEmpty){
                        Toast.makeText(this,"Mesaj Yok",Toast.LENGTH_LONG).show()
                    }
                    else{
                        val documents =value.documents
                        appointments.clear()
                        for (document in documents)
                        {
                            val text = document.getString("text") ?: ""
                            val user = document.getString("user") ?: ""
                            val date: Date = try {
                                dateFormat.parse(text) ?: Date()
                            } catch (e: Exception) {
                                Date()
                            }
                            val appointment =Appointment_(user,text,date)
                            appointments.add(appointment)

                        }
                        appointments.sortBy { it.date }
                        adapter.appointments = appointments
                        adapter.notifyDataSetChanged()
                    }

                }
            }
        }
    }

    fun backToAppointment(view: View){
        val intent = Intent(this@AppointmentList,Appointment::class.java)
        startActivity(intent)
        finish()
    }
}