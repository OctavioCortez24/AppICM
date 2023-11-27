package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

class Login : AppCompatActivity() {
    private lateinit var textError:TextView;

    val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        anadirConecionConVista()

        var loginBtn:Button=findViewById(R.id.inicioSesion)
        var userBtn:EditText=findViewById(R.id.usuario_editText)
        var contraBtn:EditText=findViewById(R.id.contrasena_editText)

        loginBtn.setOnClickListener{

            val usuario:String =userBtn.text.toString()
            var contrasena:String=contraBtn.text.toString()
            var intent:Intent=Intent(this, MenuDeActivity::class.java)
            comprobarUsuario(usuario, contrasena, intent)

        }

    }
    fun comprobarUsuario(usuario:String,contrasena:String, intent: Intent) {
        val Driver: String = "oracle.jdbc.driver.OracleDriver"
        val url: String = "jdbc:oracle:thin:@192.168.100.87:1521:XE"
        val user: String = "SYSTEM"
        val contrasena: String = "1234"
        

        GlobalScope.launch(Dispatchers.IO) {

            try {
                Class.forName(Driver)
                val connection: Connection = DriverManager.getConnection(url, user, contrasena)

                var statement: Statement = connection.createStatement()
                var resultSet: ResultSet = statement.executeQuery("SELECT * FROM PERSONA_ICM WHERE DNI="+usuario)

                resultSet.next()


                connection.close()
                GlobalScope.launch(Dispatchers.Main) {
                    startActivity(intent)
                }

            } catch (e: Exception) {

                Log.e("Octavio", "Error: " + e)

                GlobalScope.launch(Dispatchers.Main) {
                    mensajeError()
                }

            }

        }
    }
    fun anadirConecionConVista(){
        textError=findViewById(R.id.textoError)
    }
    fun mensajeError(){
        try {
            textError.setText("Usuario o Contraseña incorrectos")
        }catch (e:Exception){
            Log.e("Octavio", "Error del Text View: " + e)
        }
    }
}