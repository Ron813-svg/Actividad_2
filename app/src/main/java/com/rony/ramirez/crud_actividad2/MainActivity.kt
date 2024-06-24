package com.rony.ramirez.crud_actividad2

import android.os.Bundle

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import Modelos.Conexion
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val txtCorreo = findViewById<EditText>(R.id.txtEmail)
        val txtContraseña = findViewById<EditText>(R.id.txtContrasenaInicio)
        val btnInicio = findViewById<Button>(R.id.btnLogin)
        val btnRegistro = findViewById<Button>(R.id.btnRegistro)

        btnInicio.setOnClickListener {

            val ScreenTick = Intent(this, MainTickets::class.java)

            GlobalScope.launch(Dispatchers.IO) {
                val objConnection = Conexion().cadenaConexion()

                val Verification =
                    objConnection?.prepareStatement("Select * from Usuario Where Correo = ? and Contrasena = ?")!!
                Verification.setString(1, txtCorreo.text.toString())
                Verification.setString(2, txtContraseña.text.toString())
                val Result = Verification.executeQuery()

                if (Result.next()) {
                    startActivity(ScreenTick)
                } else {
                    withContext(Dispatchers.Main) {
                        ShowToast()
                    }
                }
            }

        }
        btnRegistro.setOnClickListener {
            val pantallaRegistro = Intent(this, Registro::class.java)
            startActivity(pantallaRegistro)
        }
    }

   private fun ShowToast(){
       val parentLayout = findViewById<View>(android.R.id.content)
       val snackbar = Snackbar.make(parentLayout, "", Snackbar.LENGTH_LONG)


       val inflater = layoutInflater
       val customSnackbarView = inflater.inflate(R.layout.toastgif, null)


       val text = customSnackbarView.findViewById<TextView>(R.id.Text)
       text.text = "El Usuario o la contraseña son Incorrectos"


       val snackbarView = snackbar.view as ViewGroup
       snackbarView.setPadding(0, 0, 0, 0)
       snackbarView.addView(customSnackbarView,0)


       snackbar.show()
    }
}