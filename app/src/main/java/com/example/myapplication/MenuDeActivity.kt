package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

class MenuDeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_de)


        val botonICM:Button=findViewById(R.id.ICM);
        val botonBD:Button=findViewById(R.id.botonBaseDatos)


        botonICM.setOnClickListener{
            var intent=Intent(this, ImcCalculatorActivity::class.java)
            startActivity(intent)
        }

        botonBD.setOnClickListener{
            var intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }

    fun comprobarUsuario(usuario:String,contrasena:String){
        val Driver:String ="oracle.jdbc.driver.OracleDriver"
        val url:String= "jdbc:oracle:thin:@172.15.2.199:1521:XE"
        val user:String="SYSTEM"
        val contrasena:String="1234"
        GlobalScope.launch(Dispatchers.IO) {
            try {
                Class.forName(Driver)
                val connection: Connection = DriverManager.getConnection(url,user,contrasena)

                var statement: Statement =connection.createStatement()
                var stringBuffer:StringBuffer= StringBuffer()
                var resultSet: ResultSet =statement.executeQuery("SELECT * FROM PERSONA_ICM")

                var json: JSONObject = JSONObject()


                while (resultSet.next()){
                    json.put("dni",resultSet.getInt(1))
                }

                connection.close()
                GlobalScope.launch(Dispatchers.Main) {
                    Log.i("OctavioInfo","DNI: "+json.get("dni"))

                }

            }catch (e:Exception){
                Log.e("Octavio","Error: "+e)
            }
        }
    }
}