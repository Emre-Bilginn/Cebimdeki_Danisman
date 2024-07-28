package com.emrebilgin.cebimdekidanisman

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.emrebilgin.cebimdekidanisman.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



    }

    fun memberLogin(view: View){
        val intent = Intent(this@MainActivity, MemberLogin::class.java)
        startActivity(intent)
        finish()
    }

    fun consultantLogin(view: View){
        val intent = Intent(this@MainActivity, ConsultantLogin::class.java)
        startActivity(intent)
        finish()
    }

    fun mainpage(view: View){
        val intent = Intent(this@MainActivity,MainPage::class.java)
        startActivity(intent)
        finish()
    }



}