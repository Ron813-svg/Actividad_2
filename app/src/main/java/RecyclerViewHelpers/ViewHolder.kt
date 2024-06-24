package RecyclerViewHelpers

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.rony.ramirez.crud_actividad2.R
import android.widget.ImageView
import android.widget.TextView

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val txtName:TextView = view.findViewById(R.id.txtTituloCard)
    val imgDelete:ImageView = view.findViewById(R.id.imgBorrar)
    val imgEdit:ImageView = view.findViewById(R.id.imgEditar)
    val txtState:TextView = view.findViewById(R.id.txtState)
}
