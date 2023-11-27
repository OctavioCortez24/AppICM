package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ErrorLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_error_login)

        var btnAceptar:Button=findViewById(R.id.aceptar)

        btnAceptar.setOnClickListener{
            var intent:Intent=Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}