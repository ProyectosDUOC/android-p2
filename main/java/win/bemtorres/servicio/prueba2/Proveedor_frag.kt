package win.bemtorres.servicio.prueba2


import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import win.bemtorres.servicio.controldeinventario.Categoria
import win.bemtorres.servicio.controldeinventario.ConexionSQL
import win.bemtorres.servicio.controldeinventario.Proveedor


class Proveedor_frag : Fragment() {

    var miContexto : Context?= null
    var adaptador:AdapterProveedor? = null
    var list : RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var view = inflater.inflate(R.layout.fragment_proveedor_frag, container, false)

        val botonAgregar: Button = view.findViewById(R.id.btnAgregarP)
        val botonListar: Button = view.findViewById(R.id.btnListarP)

       list = view.findViewById(R.id.lv_Proveedor)


        botonAgregar.setOnClickListener {
            var intento: Intent = Intent(miContexto,AgregarProveedor::class.java)
            startActivity(intento)
        }

        botonListar.setOnClickListener {
            var conn = ConexionSQL(miContexto!!,null,1)
            val arrayProveedor = conn.listarProveedor()
            if(arrayProveedor.size>0){

                adaptador = AdapterProveedor(arrayProveedor!!)
                list?.layoutManager = LinearLayoutManager(miContexto,LinearLayout.VERTICAL,false)
                list?.adapter = adaptador

                //Metodo con lista
                // val adapter:AdapterProveedor = AdapterProveedor(miContexto!!, R.layout.item_proveedor,lista)
               // list.adapter= adapter
            }else{
                Toast.makeText(miContexto!!,"No hay datos",Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    override fun onResume() {
        var conn = ConexionSQL(miContexto!!,null,1)
        val arrayProveedor = conn.listarProveedor()
        if(arrayProveedor.size>0){

            adaptador = AdapterProveedor(arrayProveedor!!)
            list?.layoutManager = LinearLayoutManager(miContexto,LinearLayout.VERTICAL,false)
            list?.adapter = adaptador

            //Metodo con lista
            // val adapter:AdapterProveedor = AdapterProveedor(miContexto!!, R.layout.item_proveedor,lista)
            // list.adapter= adapter
        }else{
            Toast.makeText(miContexto!!,"No hay datos",Toast.LENGTH_SHORT).show()
        }
        super.onResume()
    }
}

/*

class  AdapterProveedor(var miContexto:Context,var miRecurso:Int,var miLista:ArrayList<Proveedor>):
    ArrayAdapter<Proveedor>(miContexto,miRecurso,miLista){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val item = LayoutInflater.from(miContexto).inflate(miRecurso,null)

        val nombrePro: TextView = item.findViewById(R.id.lblNombreP)
        val fecha: TextView = item.findViewById(R.id.lblFecha)
        val boton: Button = item.findViewById(R.id.btnDesactivarP)
        val boton2: Button = item.findViewById(R.id.btnActualizarP)

        val registro = miLista[position]
        nombrePro.text = registro.nombreProveedor

        fecha.text =registro.fechaInscripcion



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

            alerta.setMessage("¿Estás seguro que quieres $estado Proveedor?")
            alerta.setPositiveButton("Si", { dialog, which ->
                val db = ConexionSQL(miContexto, null, 1)
                registro.estado = valor
                db.actualizarProveedor(registro)
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
