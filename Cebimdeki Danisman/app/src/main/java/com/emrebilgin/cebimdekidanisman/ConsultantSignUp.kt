package com.emrebilgin.cebimdekidanisman

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.emrebilgin.cebimdekidanisman.databinding.ActivityConsultantSignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class ConsultantSignUp : AppCompatActivity() {
    private lateinit var binding: ActivityConsultantSignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsultantSignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth

        val spinner = binding.csuSpinner

        val cities = arrayOf("Seciniz",
            "Adana", "Adıyaman", "Afyonkarahisar", "Ağrı", "Aksaray", "Amasya", "Ankara",
            "Antalya", "Ardahan", "Artvin", "Aydın", "Balıkesir", "Bartın", "Batman",
            "Bayburt", "Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur", "Bursa", "Çanakkale",
            "Çankırı", "Çorum", "Denizli", "Diyarbakır", "Düzce", "Edirne", "Elazığ",
            "Erzincan", "Erzurum", "Eskişehir", "Gaziantep", "Giresun", "Gümüşhane", "Hakkâri",
            "Hatay", "Iğdır", "Isparta", "İstanbul", "İzmir", "Kahramanmaraş", "Karabük",
            "Karaman", "Kars", "Kastamonu", "Kayseri", "Kilis", "Kırıkkale", "Kırklareli",
            "Kırşehir", "Kocaeli", "Konya", "Kütahya", "Malatya", "Manisa", "Mardin", "Mersin",
            "Muğla", "Muş", "Nevşehir", "Niğde", "Ordu", "Osmaniye", "Rize", "Sakarya",
            "Samsun", "Şanlıurfa", "Siirt", "Sinop", "Sivas", "Şırnak", "Tekirdağ", "Tokat",
            "Trabzon", "Tunceli", "Uşak", "Van", "Yalova", "Yozgat", "Zonguldak"
        )

        if (spinner != null){
            val adapter = ArrayAdapter(this, R.layout.simple_spinner_item,cities)
            spinner.adapter = adapter
        }


    }

    fun back(view: View){
        val intent = Intent(this@ConsultantSignUp,ConsultantLogin::class.java)
        startActivity(intent)
        finish()
    }

    fun backToConsultantLogin(view: View){

        val alert  = AlertDialog.Builder(this@ConsultantSignUp)

        val editTextlist = listOf(
            binding.csuEditTextText,
            binding.csuEditTextText2,
            binding.csuEditTextText3,
            binding.csuEditTextText4,
            binding.csuEditTextText5,
            binding.csuEditTextText6,
            binding.csuEditTextText7
        )

        var bosAlanVar = false

        for (edittext in editTextlist)
        {
            val metin = edittext.text.toString()
            if  (metin.isEmpty()){
                bosAlanVar = true
                break
            }
        }

        val checbox = binding.csucheckBox
        var bosAlanVar1 = false

        if (!checbox.isChecked){
            bosAlanVar1 = true
        }

        val secilen_deger = binding.csuSpinner.selectedItem
        var bosAlanVar2 = false

        if (secilen_deger=="Seciniz")
        {
            bosAlanVar2 = true
        }


        if (bosAlanVar or bosAlanVar1 or bosAlanVar2){
            alert.setTitle("Hata")
            alert.setMessage("Hiçbir alan boş bırakılamaz")
        }

        else{

            auth.createUserWithEmailAndPassword(binding.csuEditTextText3.text.toString(),binding.csuEditTextText5.text.toString()).addOnSuccessListener{
                val intent = Intent(this@ConsultantSignUp,ConsultantLogin::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {exception->
                Toast.makeText(this,exception.localizedMessage, Toast.LENGTH_LONG).show()
            }

        }



        alert.show()

    }



}