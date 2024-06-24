package com.rony.ramirez.crud_actividad2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.UUID

class detalles : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalles)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnExit = findViewById<Button>(R.id.btnExitDet)

        val UUIDreceived = intent.getStringExtra("UUID_Ticket")
        val txtNumberReceived = intent.getIntExtra("Numero de Ticket", -1)
        val txtTitleReceived = intent.getStringExtra("Titulo")
        val txtDescriptionReceived = intent.getStringExtra("Descripcion")
        val txtAuthorReceived = intent.getStringExtra("Autor")
        val txtEmailContactReceived = intent.getStringExtra("Email de Contacto")
        val txtStateReceived = intent.getStringExtra("Estado del Ticket")

        val txtUUID : TextView = findViewById(R.id.txtUUIDdetalle)
        val txtNumber : TextView = findViewById(R.id.txtNumDet)
        val txtTitle : TextView = findViewById(R.id.txtTitleDet)
        val txtDescription : TextView = findViewById(R.id.txtDescriptionDet)
        val txtAuthor : TextView = findViewById(R.id.txtAuthorDet)
        val txtEmailContact : TextView = findViewById(R.id.txtEmailContDet)
        val State : TextView = findViewById(R.id.txtStateDet)

        txtUUID.text = UUIDreceived
        txtNumber.text = txtNumberReceived.toString()
        txtTitle.text = txtTitleReceived
        txtDescription.text = txtDescriptionReceived
        txtAuthor.text = txtAuthorReceived
        txtEmailContact.text = txtEmailContactReceived
        State.text = txtStateReceived

        btnExit.setOnClickListener {
            val ScreenTickets = Intent(this,MainTickets::class.java)
            startActivity(ScreenTickets)
        }
    }
}