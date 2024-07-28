package com.emrebilgin.cebimdekidanisman

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.emrebilgin.cebimdekidanisman.databinding.ActivityChatPageBinding
import com.emrebilgin.cebimdekidanisman.databinding.ActivityFrequentlyAskedQuestionsBinding

class FrequentlyAskedQuestions : AppCompatActivity() {

    private lateinit var binding : ActivityFrequentlyAskedQuestionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFrequentlyAskedQuestionsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.faqTextView1.text="Soru : Seanslar kaç dk sürüyor?"
        binding.faqTextView2.text="Cevap : Seanslar danışmana göre değişiklik gösterebilir ama en az 45 dakikadır"
        binding.faqTextView3.text="Soru : Seans esnasında tek kişi olmamız gerekiyor?"
        binding.faqTextView4.text="Cevap : Evet. Tek başınıza olmanız beklenmektedir. Ekstra bir durum olursa yanınızda bir kişi daha bulunabilir tabiki danışmanın bilgisi dahilinde olabilir"
        binding.faqTextView5.text="Soru : Seans esnasında kamera ve mikrofon açık mı olmalı?"
        binding.faqTextView6.text="Cevap : Herhangi bir sorun olmadığı takdirde hem kameranın hem de mikrofonun açık olması gerekir "
        binding.faqTextView7.text="Soru : Haftanın hangi günleri seans için randevu alabiliriz?"
        binding.faqTextView8.text="Cevap : Seanslar haftaiçi yapılmaktadır"
        binding.faqTextView9.text="Soru : Gün içinde hangi saatler arası seans yapılmaktadır?"
        binding.faqTextView10.text="Cevap : Seanslar 08.00 - 18.00 saatleri arasında yapılmaktadır yapılmaktadır"
        binding.faqTextView11.text="Soru : Haftada kaç kere seans yapabiliriz?"
        binding.faqTextView12.text="Cevap : Haftada normalde 1 kere seans yapılmakta fakat danışanın isteği doğrultusunda bu sayı arttırılabilir"

    }

    fun backToMainPage(view: View){
        val intent = Intent(this@FrequentlyAskedQuestions,MainPage::class.java)
        startActivity(intent)
        finish()
    }
}