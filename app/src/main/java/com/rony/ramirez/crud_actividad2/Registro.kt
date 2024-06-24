package com.rony.ramirez.crud_actividad2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import Modelos.Conexion
import android.widget.Toast
import kotlinx.coroutines.withContext
import java.util.UUID

class Registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val txtNombre = findViewById<EditText>(R.id.txtNombreUsuario)
        val txtCorreo = findViewById<EditText>(R.id.txtEmailRegistro)
        val txtContraseña = findViewById<EditText>(R.id.txtContraseña)
        val btnRegistrarse = findViewById<Button>(R.id.btnRegistrarse1)
        val btnLogin = findViewById<Button>(R.id.btnInicio)

        btnRegistrarse.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val objConnection = Conexion().cadenaConexion()

                val createUser = objConnection?.prepareStatement("Insert into Usuario (UUID_usuario, Nombre, Correo, Contrasena) values (?, ?, ?, ?)")!!
                createUser.setString(1,UUID.randomUUID().toString())
                createUser.setString(2,txtNombre.text.toString())
                createUser.setString(3,txtCorreo.text.toString())
                createUser.setString(4,txtContraseña.text.toString())
                createUser.executeUpdate()
                withContext(Dispatchers.Main){
                    Toast.makeText(this@Registro,"El usuario ha sido Creado", Toast.LENGTH_LONG).show()
                    txtNombre.setText("")
                    txtContraseña.setText("")
                    txtCorreo.setText("")
                }
            }
        }

        btnLogin.setOnClickListener {
            val MainLogin = Intent(this, MainActivity::class.java)
            startActivity(MainLogin)
        }
    }
}