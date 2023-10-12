package com.rulyadhika.tanamanio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.Timer
import kotlin.concurrent.schedule
import kotlin.system.exitProcess

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Timer().schedule(2000) {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)

            exitProcess(1)
        }
    }
}