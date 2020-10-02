package com.example.todolistapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.todolistapp.R

class SplashScreenActivity : AppCompatActivity() {
    private val delayedTime: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        var handler = Handler()
        handler.postDelayed({
            startActivity(Intent(this,RegisterActivity::class.java ))
//            checkLogin()
        }, delayedTime)

    }

//    private fun checkLogin() {
//        TODO("Not yet implemented")
//    }
}