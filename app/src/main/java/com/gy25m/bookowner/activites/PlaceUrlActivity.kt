package com.gy25m.bookowner.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gy25m.bookowner.R
import com.gy25m.bookowner.databinding.ActivityPlaceUrlBinding

class PlaceUrlActivity : AppCompatActivity() {
    val binding by lazy { ActivityPlaceUrlBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.wv.webViewClient=android.webkit.WebViewClient()
        binding.wv.webChromeClient=android.webkit.WebChromeClient()
        binding.wv.settings.javaScriptEnabled=true

        var place_url=intent.getStringExtra("place_url") ?: ""
        binding.wv.loadUrl(place_url)
    }

    override fun onBackPressed() {
        if (binding.wv.canGoBack())binding.wv.goBack()
        super.onBackPressed()
    }
}