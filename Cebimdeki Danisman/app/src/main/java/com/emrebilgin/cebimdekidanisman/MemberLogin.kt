package com.emrebilgin.cebimdekidanisman

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.emrebilgin.cebimdekidanisman.databinding.ActivityMemberLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MemberLogin : AppCompatActivity() {

    private lateinit var binding: ActivityMemberLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemberLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val intentFromMain = intent


        auth = Firebase.auth


    }

    fun backToMain(view: View){
        val intent = Intent(this@MemberLogin,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun memberSignUp(view: View){
        val intent = Intent(this@MemberLogin,MemberSignUp::class.java)
        startActivity(intent)
        finish()
    }



    fun mainpage(view: View){

        val kulllanici = binding.meEditTextText.getText().toString()
        val sifre = binding.meEditTextText2.getText().toString()

        val alert  = AlertDialog.Builder(this@MemberLogin)

        if(kulllanici.isEmpty() ||  sifre.isEmpty()){
            alert.setTitle("Hata")
            alert.setMessage("Kullanıcı adı veya şifre bölümü boş bırakılamaz")
            binding.meEditTextText.text.clear()
            binding.meEditTextText2.text.clear()
        }


        else{
            auth.signInWithEmailAndPassword(binding.meEditTextText.text.toString(),binding.meEditTextText2.text.toString()).addOnSuccessListener {
                val intent = Intent(this@MemberLogin,MainPage::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this,it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
        alert.show()

    }


}