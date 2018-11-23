package win.bemtorres.servicio.prueba2

import android.content.Intent
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import win.bemtorres.servicio.controldeinventario.ConexionSQL
import win.bemtorres.servicio.controldeinventario.Producto

class AdapterEstadistica(val miLista:ArrayList<Producto>?) : RecyclerView.Adapter<AdapterEstadistica.ViewHolder>() , View.OnClickListener{


    override fun onClick(p0: View?) {
        Toast.makeText(p0!!.context,"clic", Toast.LENGTH_LONG).show()
    }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterEstadistica.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_estadistica, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: AdapterEstadistica.ViewHolder, position: Int) {
        holder.bindItems(miLista!![position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return miLista!!.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(prod: Producto) {
            var vista = itemView
            var nombreProducto: TextView = vista.findViewById(R.id.lblNombreProductoE)
            var cant : TextView = vista.findViewById(R.id.lblCantidadE)
            var linearLayout : LinearLayout = vista.findViewById(R.id.linearEstadistica)


            nombreProducto.text = prod.nombre
            cant.text = prod.cantidadDisponible.toString()

            if (prod.cantidadDisponible<5){
                linearLayout.setBackgroundColor(Color.RED)
            }

            if (prod.cantidadDisponible>=6 && prod.cantidadDisponible<20) {
                linearLayout.setBackgroundColor(Color.YELLOW)
            }
            if (prod.cantidadDisponible>=20){
                linearLayout.setBackgroundColor(Color.GREEN)
            }
        }
    }

}