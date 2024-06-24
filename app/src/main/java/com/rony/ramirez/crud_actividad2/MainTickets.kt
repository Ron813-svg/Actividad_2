package com.rony.ramirez.crud_actividad2

import android.os.Bundle
import RecyclerViewHelpers.Adapter
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import Modelos.Conexion
import Modelos.dataClassTickets
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.rony.ramirez.crud_actividad2.databinding.ActivityMainBinding
import kotlinx.coroutines.withContext
import java.util.UUID


class MainTickets : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_tickets)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        val btnSalirActivity = findViewById<Button>(R.id.btnSalirActivity)


        val txtNumTicket = findViewById<EditText>(R.id.txtnumTicket)
        val txtTitle = findViewById<EditText>(R.id.txtTitle)
        val txtDescription = findViewById<EditText>(R.id.txtDescripcion)
        val txtAuthor = findViewById<EditText>(R.id.txtAutor)
        val txtEmail = findViewById<EditText>(R.id.txtEmailTick)
        val txtState = findViewById<EditText>(R.id.txtEstado)
        val btnAdd = findViewById<Button>(R.id.btnIngresarTick)
        val rcvTickets = findViewById<RecyclerView>(R.id.rcvTickets)

        rcvTickets.layoutManager = LinearLayoutManager(this)

        fun showData(): List<dataClassTickets> {
            val objConnection = Conexion().cadenaConexion()
            val statement = objConnection?.createStatement()
            val result = statement?.executeQuery("Select * from Tickets")!!

            val Tickets = mutableListOf<dataClassTickets>()

            while (result.next()){
                val uuid = result.getString("UUID_ticket")
                val numTicket = result.getInt("numTicket")
                val titleTick = result.getString("titulo")
                val description = result.getString("descripcion")
                val authorTick = result.getString("autor")
                val emailAuthor = result.getString("emailContact")
                val stateTick = result.getString("Estado")

                val ticket = dataClassTickets(uuid, numTicket, titleTick, description, authorTick, emailAuthor, stateTick)
                Tickets.add(ticket)
            }
            return Tickets
        }

        CoroutineScope(Dispatchers.IO).launch {
            val Tickets = showData()
            withContext(Dispatchers.Main){
                val myAdapter = Adapter(Tickets)
                rcvTickets.adapter = myAdapter
            }
        }
        btnSalirActivity.setOnClickListener {
            val pantallaInicio = Intent(this, MainActivity::class.java)
            startActivity(pantallaInicio)
        }

        btnAdd.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val objConnection = Conexion().cadenaConexion()

                val addTickets = objConnection?.prepareStatement("INSERT INTO Tickets (UUID_ticket, numTicket, titulo, descripcion, autor, emailContact, Estado) values( ?, ?, ?, ?, ?, ?, ?)")!!

                addTickets.setString(1,UUID.randomUUID().toString())
                addTickets.setInt(2,txtNumTicket.text.toString().toInt())
                addTickets.setString(3,txtTitle.text.toString())
                addTickets.setString(4,txtDescription.text.toString())
                addTickets.setString(5,txtAuthor.text.toString())
                addTickets.setString(6,txtEmail.text.toString())
                addTickets.setString(7,txtState.text.toString())
                addTickets.executeUpdate()

                val newTicket = showData()

                withContext(Dispatchers.Main){
                    (rcvTickets.adapter as Adapter)?.UpdateList(newTicket)
                    Toast.makeText(this@MainTickets,"El ticket ha sido ingresado correctamente", Toast.LENGTH_LONG).show()
                    txtNumTicket.setText("")
                    txtTitle.setText("")
                    txtDescription.setText("")
                    txtAuthor.setText("")
                    txtEmail.setText("")
                    txtState.setText("")
                }

        }






    }
    fun snackbarShow() {
        val parentLayout = findViewById<View>(android.R.id.content)
        val snackbar = Snackbar.make(parentLayout, "", Snackbar.LENGTH_LONG)


        val inflater = layoutInflater
        val customSnackbarView = inflater.inflate(R.layout.snackbar, null)


        val text = customSnackbarView.findViewById<TextView>(R.id.textIn)
        text.text = "Se ha iniciado correctamente"


        val snackbarView = snackbar.view as ViewGroup
        snackbarView.setPadding(0, 0, 0, 0)
        snackbarView.addView(customSnackbarView, 0)


        snackbar.show()
    }
        snackbarShow()
    }
}