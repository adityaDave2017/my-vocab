package com.android.vocab.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.android.vocab.R


@Suppress("unused")
class SplashActivity : AppCompatActivity() {

    private val LOG_TAG: String = SplashActivity::class.java.simpleName

    val SPLASH_TIMEOUT: Long = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val wordIntent = Intent(baseContext, WordsActivity::class.java)
            startActivity(wordIntent)
            finish()
        }, SPLASH_TIMEOUT)

    }
}
