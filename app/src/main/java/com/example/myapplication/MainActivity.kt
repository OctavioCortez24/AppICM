package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.Conexion;
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import oracle.jdbc.driver.OracleSQLException
import org.json.JSONArray
import org.json.JSONObject
import java.sql.Connection
import java.sql.Driver
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var botonMostrar: Button
    private lateinit var mostrarEdad: TextView
    val Driver:String ="oracle.jdbc.driver.OracleDriver"
    val url:String= "jdbc:oracle:thin:@172.15.2.199:1521:XE"
    //192.168.100.87
    val user:String="SYSTEM"
    val contrasena:String="1234"
    private lateinit var tabLayout:TableLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        botonMostrar=findViewById(R.id.botonMostrar)
        mostrarEdad=findViewById(R.id.mostrarResultado)

        tabLayout=findViewById(R.id.tablaPersona)


        botonMostrar.setOnClickListener{
            Log.i("OctavioInfo","Hasta aca llega")
            var b=this
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    Class.forName(Driver)
                    val connection:Connection=DriverManager.getConnection(url,user,contrasena)

                    var statement:Statement=connection.createStatement()
                    var resultSet:ResultSet=statement.executeQuery("SELECT * FROM ICM")

                    var json:JSONObject= JSONObject()


                    while (resultSet.next()){
                        json.put("id",resultSet.getString(1))
                        json.put("dni",resultSet.getInt(2))
                        json.put("icm",resultSet.getFloat(3))
                        json.put("altura",resultSet.getFloat(4))
                        json.put("peso",resultSet.getFloat(4))
                        json.put("edad",resultSet.getInt(6))
                    }

                    Log.i("OctavioInfo",json.toString())
                    connection.close()

                    var dniTextView:TextView=findViewById(R.id.mostrarDNI)
                    var icmTextView:TextView=findViewById(R.id.mostrarICM)
                    var alturaTextView:TextView=findViewById(R.id.mostrarAltura)
                    var pesoTextView:TextView=findViewById(R.id.mostrarPeso)

                    GlobalScope.launch(Dispatchers.Main) {
                       // Log.i("OctavioInfo","Exito")
                        for(i in 1..4){
                            var tableRow:TableRow= TableRow(b)
                            var textViewICM:TextView= TextView(b)
                            textViewICM.text=json.get("icm").toString()
                            tableRow.addView(textViewICM)
                            var textViewEdad:TextView= TextView(b)
                            textViewEdad.text=json.get("edad").toString()
                            tableRow.addView(textViewEdad)
                            var textViewAltura:TextView= TextView(b)
                            textViewAltura.text=json.get("altura").toString()
                            tableRow.addView(textViewAltura)
                            var textViewPeso:TextView= TextView(b)
                            textViewPeso.text=json.get("peso").toString()
                            tableRow.addView(textViewPeso)
                            tabLayout.addView(tableRow)

                        }
                        dniTextView.text="DNI:"+json.get("dni").toString()
                        icmTextView.text="ICM: "+json.get("icm").toString()
                        alturaTextView.text="Altura: "+json.get("altura").toString()
                        pesoTextView.text="Peso: "+json.get("peso").toString()
                        mostrarEdad.text="Edad: "+json.get("edad").toString()

                    }

                }catch (e:Exception){
                    Log.e("Octavio","Error: "+e)
                }
            }



        }


    }
}