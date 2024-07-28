package com.emrebilgin.cebimdekidanisman

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.emrebilgin.cebimdekidanisman.databinding.ActivityConsultantLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class ConsultantLogin : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityConsultantLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsultantLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val intentFromMain = intent

        auth = Firebase.auth


    }

    fun backToMain(view: View){
        val intent = Intent(this@ConsultantLogin,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun consultantSignUp(view: View){
        val intent = Intent(this@ConsultantLogin,ConsultantSignUp::class.java)
        startActivity(intent)
        finish()
    }





    fun mainpage(view: View){
        val kulllanici = binding.cEditTextText.getText().toString()
        val sifre = binding.cEditTextText2.getText().toString()

        val alert  = AlertDialog.Builder(this@ConsultantLogin)

        if(kulllanici.isEmpty() ||  sifre.isEmpty()){
            alert.setTitle("Hata")
            alert.setMessage("Kullanıcı adı veya şifre bölümü boş bırakılamaz")
            binding.cEditTextText.text.clear()
            binding.cEditTextText2.text.clear()
        }



        else{
            auth.signInWithEmailAndPassword(binding.cEditTextText.text.toString(),binding.cEditTextText2.text.toString()).addOnSuccessListener {
                val intent = Intent(this@ConsultantLogin,MainPage::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }
        alert.show()
    }
}