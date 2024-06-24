package RecyclerViewHelpers

import Modelos.dataClassTickets
import androidx.recyclerview.widget.RecyclerView
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import Modelos.Conexion
import android.content.Intent
import android.view.View
import com.rony.ramirez.crud_actividad2.R
import com.rony.ramirez.crud_actividad2.detalles
import kotlinx.coroutines.withContext
import java.util.UUID

class Adapter(var Data: List<dataClassTickets>): RecyclerView.Adapter<ViewHolder>() {
    fun UpdateList(newList: List<dataClassTickets>){
        Data = newList
        notifyDataSetChanged()
    }
    fun UpdateScreen(uuid: String, newState: String){
        val index = Data.indexOfFirst{it.uuid == uuid}
        Data[index].StateTick = newState
        notifyDataSetChanged()
    }
    fun updateData(newState: String, uuid: String){
        GlobalScope.launch(Dispatchers.IO) {
            val objConnection = Conexion().cadenaConexion()

            val updateState = objConnection?.prepareStatement("update Tickets set Estado = ? where UUID_ticket = ?")!!
            updateState.setString(1,newState)
            updateState.setString(2,uuid.toString())
            updateState.executeUpdate()

            val commit = objConnection.prepareStatement("commit")
            commit.executeUpdate()

            withContext(Dispatchers.Main){
                UpdateScreen(uuid.toString(),newState.toString())
            }

        }
    }
    fun getTicketsFromDatabase(): List<dataClassTickets> {
        val tickets = mutableListOf<dataClassTickets>()
        val objConnection = Conexion().cadenaConexion()
        val statement = objConnection?.prepareStatement("SELECT * FROM Tickets")
        val resultSet = statement?.executeQuery()

        while (resultSet?.next() == true) {
            tickets.add(
                dataClassTickets(
                    resultSet.getString("UUID_ticket"),
                    resultSet.getInt("numTicket"),
                    resultSet.getString("titulo"),
                    resultSet.getString("descripcion"),
                    resultSet.getString("autor"),
                    resultSet.getString("emailContact"),
                    resultSet.getString("Estado")
                )
            )
        }
        return tickets
    }

    fun deletaData(Title:String, position: Int){
        val listData = Data.toMutableList()
        listData.removeAt(position)

        GlobalScope.launch (Dispatchers.IO){
            val objConnection = Conexion().cadenaConexion()

            val deleteTicket = objConnection?.prepareStatement("delete Tickets where titulo = ?")!!
            deleteTicket.setString(1,Title)
            deleteTicket.executeUpdate()

            val commit = objConnection.prepareStatement("commit")
            commit.executeUpdate()
        }
        Data = listData.toList()
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = Data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = Data[position]
        holder.txtName.text = item.titleTick
        holder.txtState.text = item.StateTick

        holder.imgDelete.setOnClickListener {
            val context = holder.itemView.context
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Confirmacion")
            builder.setMessage("¿Está seguro que quiere borrar?")

            builder.setPositiveButton("Si"){dialog,wich ->
                deletaData(item.titleTick,position)
            }
            builder.setNegativeButton("No"){ dialog, wich ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
        holder.imgEdit.setOnClickListener {
            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Actualizar Estado")
            builder.setMessage("Seguro quieres actualizar el Estado")

            val StateTicket = EditText(context)
            StateTicket.setHint(item.StateTick)
            builder.setView(StateTicket)

            builder.setPositiveButton("Actualizar"){
                    dialog, wich ->
                updateData(StateTicket.text.toString(),item.uuid)

            }
            builder.setNegativeButton("Cancelar"){
                    dialog, wich ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
        holder.itemView.setOnClickListener{
            val context = holder.itemView.context

            val ScreenDetails = Intent(context,detalles::class.java)

            ScreenDetails.putExtra("UUID_Ticket", item.uuid)
            ScreenDetails.putExtra("Numero de Ticket",item.numTicket)
            ScreenDetails.putExtra("Titulo", item.titleTick)
            ScreenDetails.putExtra("Descripcion", item.description)
            ScreenDetails.putExtra("Autor",item.AuthorTick)
            ScreenDetails.putExtra("Email de Contacto", item.EmailAuthor)
            ScreenDetails.putExtra("Estado del Ticket", item.StateTick)

            context.startActivity(ScreenDetails)
        }
    }

}