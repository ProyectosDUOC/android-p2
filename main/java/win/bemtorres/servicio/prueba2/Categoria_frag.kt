package win.bemtorres.servicio.prueba2


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.*
import kotlinx.android.synthetic.main.fragment_categoria_frag.*
import win.bemtorres.servicio.controldeinventario.Categoria
import win.bemtorres.servicio.controldeinventario.ConexionSQL


class Categoria_frag : Fragment() {

    var miContexto : Context?= null
    var adaptador:AdapterCategoria? = null
    var list : RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_categoria_frag, container, false)

        val boton: Button = view.findViewById(R.id.btnAgregar)
        val boton2: Button = view.findViewById(R.id.btnListar)
        val boton3: Button = view.findViewById(R.id.btnBuscar)
        list  = view.findViewById(R.id.lvlCategoria)
        var txtNombre : EditText = view.findViewById(R.id.txtCategoria)

        boton3.setOnClickListener{
            var nombre : String
            if(TextUtils.isEmpty(txtNombre.text.toString())){
                txtNombre.error = "Ingrese nombre Categoría"
                return@setOnClickListener
            }else{
                nombre = txtNombre.text.toString()
            }
            var conn = ConexionSQL(miContexto!!, null,1)
            var cate: Categoria? = conn.buscarCategoriaNombre(nombre)

            if (cate==null){
                Toast.makeText(miContexto!!,"No Encontrado", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(miContexto!!,"${cate!!.estado}", Toast.LENGTH_SHORT).show()
            }
        }

       boton.setOnClickListener{
            var nombre : String

            if(TextUtils.isEmpty(txtNombre.text.toString())){
                txtNombre.error = "Ingrese nombre Categoría"
                return@setOnClickListener
            }else{
                nombre = txtNombre.text.toString()
            }


            var conn = ConexionSQL(miContexto!!,null,1)
            var encontrado = 0
            //Buscar si existe con el mismo nombre

           var listaC : ArrayList<Categoria> = conn.listarCategoria()

            for (ca in listaC){
                if (ca.nombreCategoria.toUpperCase().equals(nombre.toUpperCase())){
                    encontrado = 1
                }
            }

           if (encontrado==0){
               var categoria = Categoria(1,nombre,1);
               conn.insertarCategoria(categoria)
           }else{
               Toast.makeText(miContexto!!, "Ya Existe", Toast.LENGTH_SHORT).show()
           }
       }

       boton2.setOnClickListener {
            var conn = ConexionSQL(miContexto!!,null,1)
            val arrayCategoria = conn.listarCategoria()

           if (arrayCategoria.size>0){
               adaptador = AdapterCategoria(arrayCategoria!!)
               list?.layoutManager = LinearLayoutManager(miContexto,LinearLayout.VERTICAL,false)
               list?.adapter = adaptador
           }else{
               Toast.makeText(miContexto!!,"No hay Categorias", Toast.LENGTH_SHORT).show()
           }

           //Metodo Lista

           //val adapter:AdapterCategoria = AdapterCategoria(miContexto!!, R.layout.item_categoria,lista)
            //list.adapter= adapter
       }
       return  view
    }

    override fun onResume() {
        var conn = ConexionSQL(miContexto!!,null,1)
        val arrayCategoria = conn.listarCategoria()

        if (arrayCategoria.size>0){
            adaptador = AdapterCategoria(arrayCategoria!!)
            list?.layoutManager = LinearLayoutManager(miContexto,LinearLayout.VERTICAL,false)
            list?.adapter = adaptador
        }else{
            Toast.makeText(miContexto!!,"No hay Categorias", Toast.LENGTH_SHORT).show()
        }
        super.onResume()
    }
}
/*
class  AdapterCategoria(var miContexto:Context,var miRecurso:Int,var miLista:ArrayList<Categoria>):
    ArrayAdapter<Categoria>(miContexto,miRecurso,miLista){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val item = LayoutInflater.from(miContexto).inflate(miRecurso,null)

        val nombreCate: TextView = item.findViewById(R.id.lblNombreCategoria)
        val boton: Button = item.findViewById(R.id.btnDesactivar)
        val boton2: Button = item.findViewById(R.id.btnActualizar)

        val registro = miLista[position]
        nombreCate.text = registro.nombreCategoria

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

            alerta.setMessage("¿Estás seguro que quieres $estado Categoria?")
            alerta.setPositiveButton("Si", { dialog, which ->
                val db = ConexionSQL(miContexto, null, 1)
                registro.estado = valor
                db.actualizarCategoria(registro)
            })
            alerta.setNegativeButton("No", { dialog, which ->
                dialog.cancel()
            })

            alerta.show()
        }

        boton2.setOnClickListener{
           Toast.makeText(miContexto!!,"Actualizar",Toast.LENGTH_SHORT).show()
        }

        return  item

    }
} */