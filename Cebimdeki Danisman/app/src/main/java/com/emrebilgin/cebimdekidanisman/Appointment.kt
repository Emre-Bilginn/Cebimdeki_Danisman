package com.emrebilgin.cebimdekidanisman

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.emrebilgin.cebimdekidanisman.databinding.ActivityAppointmentBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Appointment : AppCompatActivity() {

    private lateinit var binding: ActivityAppointmentBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private var dateString: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firestore = Firebase.firestore
        auth = Firebase.auth


        val calendarView = binding.calendarView
        val textViewSelectedDate = binding.textViewSelectedDate

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            dateString = dateFormat.format(calendar.time)
            textViewSelectedDate.text = "Seçilen Tarih: $dateString"
        }

        binding.appointmentsendButton.setOnClickListener {

            auth.currentUser?.let {
                val user = it.email
                val appointmentText = dateString
                val date = FieldValue.serverTimestamp()

                val dataMap = HashMap<String, Any>()
                dataMap.put("text", appointmentText.toString())
                dataMap.put("user", user!!)
                dataMap.put("date", date)

                firestore.collection("Appointments").add(dataMap).addOnSuccessListener {
                    Toast.makeText(this,"Randevu Alındı",Toast.LENGTH_LONG).show()
                }.addOnFailureListener {
                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun backToMainPage(view: View){
        val intent = Intent(this@Appointment,MainPage::class.java)
        startActivity(intent)
        finish()
    }

    fun roadToAppointmenList(view: View){
        val intent = Intent(this@Appointment,AppointmentList::class.java)
        startActivity(intent)
        finish()
    }



}