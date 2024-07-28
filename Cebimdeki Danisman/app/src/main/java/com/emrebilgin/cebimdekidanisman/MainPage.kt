package com.emrebilgin.cebimdekidanisman

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.Firebase
import androidx.appcompat.app.AppCompatActivity
import com.emrebilgin.cebimdekidanisman.databinding.ActivityMainPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore


class MainPage : AppCompatActivity() {
    private lateinit var binding: ActivityMainPageBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()

        binding.textView2.text="--Sağlıklı beslenme, kendinize olan sevginizi göstermenin en güzel yoludur. Vücudunuz size yıllarca hizmet etti, şimdi sıra sizin ona iyi bakmanızda--"
        binding.textView5.text="--> Seanslar kaç dk sürüyor"
        binding.textView6.text="--> Tüm görüşmeler çok verimli herkese tavsiye ediyorum"
        binding.textView8.text="--> Kalori açıkları oluşturma da uygulama içi bir program geliştirebilirsiniz"

        checkUserRoleAndSetButtonVisibility()

    }

    private fun checkUserRoleAndSetButtonVisibility() {
        val currentUser = auth.currentUser
        if (currentUser != null && !currentUser.isAnonymous) {
            if (!currentUser.email.isNullOrEmpty()) {
                // Kullanıcının e-posta adresi varsa ve chat butonu MainPage içindeyse,
                // chat butonunu göstermek için görünürlüğünü ayarlayabilirsiniz.
                binding.button4.visibility = View.VISIBLE
            }
        } else {
            // Kullanıcı giriş yapmamışsa veya anonim kullanıcı ise butonu gizle
            binding.button4.visibility = View.GONE
            // veya diğer durumları ele almak için gerekli işlemleri yapabilirsiniz.
        }
    }

    fun appointment(view: View){
        val intent = Intent(this@MainPage,Appointment::class.java)
        startActivity(intent)
        finish()
    }

    fun chat(view: View) {
        val user = auth.currentUser
        if (user != null) {
            val intent = Intent(this@MainPage, ChatPage::class.java)
            startActivity(intent)
        }
    }
    fun frequentlyAskedQuestions(view: View){
        val intent = Intent(this@MainPage,FrequentlyAskedQuestions::class.java)
        startActivity(intent)
        finish()
    }

    fun userComments(view: View){
        val intent = Intent(this@MainPage,UserComments::class.java)
        startActivity(intent)
        finish()
    }

    fun giveUsAdvice(view: View){
        val intent = Intent(this@MainPage,GiveUsAdvice::class.java)
        startActivity(intent)
        finish()
    }




}