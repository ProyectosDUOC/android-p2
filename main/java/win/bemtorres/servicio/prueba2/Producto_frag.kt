package win.bemtorres.servicio.prueba2


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import win.bemtorres.servicio.controldeinventario.ConexionSQL
import win.bemtorres.servicio.controldeinventario.Producto
import win.bemtorres.servicio.controldeinventario.Proveedor


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class Producto_frag : Fragment() {

    var miContexto : Context?= null
    var adaptador:AdapterProducto? = null
    var lista : RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_producto_frag, container, false)

        var botonAgregar : Button = view.findViewById(R.id.btnAddProducto)
        var botonListar : Button = view.findViewById(R.id.btnListarProducto)
        lista = view.findViewById(R.id.lv_Producto)

        botonAgregar.setOnClickListener {
            var intento: Intent = Intent(miContexto,AgregarProducto::class.java)
            startActivity(intento)
        }

        botonListar.setOnClickListener {
            var conn = ConexionSQL(miContexto!!,null,1)
            val arrayProducto = conn.listarProducto()
            if(arrayProducto.size>0){

                adaptador = AdapterProducto(arrayProducto!!)
                lista?.layoutManager = LinearLayoutManager(miContexto,LinearLayout.VERTICAL,false)
                lista?.adapter = adaptador

                //val adapter:AdapterProducto = AdapterProducto(miContexto!!, R.layout.item_producto,lista)
                //list.adapter= adapter
            }else{
                Toast.makeText(miContexto!!,"No hay datos",Toast.LENGTH_SHORT).show()
            }

        }
        return view
    }

    override fun onResume() {
        var conn = ConexionSQL(miContexto!!,null,1)
        val arrayProducto = conn.listarProducto()
        if(arrayProducto.size>0){
            adaptador = AdapterProducto(arrayProducto!!)
            lista?.layoutManager = LinearLayoutManager(miContexto,LinearLayout.VERTICAL,false)
            lista?.adapter = adaptador
        }else{
            Toast.makeText(miContexto!!,"No hay datos",Toast.LENGTH_SHORT).show()
        }
        super.onResume()
    }


}


/*

class  AdapterProducto(var miContexto:Context,var miRecurso:Int,var miLista:ArrayList<Producto>):
    ArrayAdapter<Producto>(miContexto,miRecurso,miLista){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val item = LayoutInflater.from(miContexto).inflate(miRecurso,null)

        val nombreProducto: TextView = item.findViewById(R.id.lblNombreProducto)
        val nombreProveedor: TextView = item.findViewById(R.id.lblCliente)

        val boton: Button = item.findViewById(R.id.btnDesactivarProducto)
        val boton2: Button = item.findViewById(R.id.btnActualizarProducto)

        val db = ConexionSQL(miContexto, null, 1)

        val registro = miLista[position]
        nombreProducto.text = registro.nombre
        var nombrePro =  db.buscarProveedor(registro.idProveedor)!!.nombreProveedor.toString()
        nombreProveedor.text  = nombrePro

        if(registro.estado==1){
            boton.setText("Activado")
        }else{
            boton.setText("Desactivado")
        }

        boton.setOnClickListener{
            val alerta = AlertDialog.Builder(miContexto)
            alerta.setTitle("Eliminar")
            var estado = "Activar"
            var valor = 0
            if(registro.estado==1){
                estado="Desactivar"
                valor = 0
            }else{
                estado="Activar"
                valor = 1
            }

            alerta.setMessage("¿Estás seguro que quieres $estado Producto?")
            alerta.setPositiveButton("Si", { dialog, which ->
                val db = ConexionSQL(miContexto, null, 1)
                registro.estado = valor
                db.actualizarProducto(registro)
            })
            alerta.setNegativeButton("No", { dialog, which ->
                dialog.cancel()
            })

            alerta.show()
        }

        boton2.setOnClickListener{
            Toast.makeText(miContexto!!,"Actualizar", Toast.LENGTH_SHORT).show()
        }

        return  item

    }
}
*/