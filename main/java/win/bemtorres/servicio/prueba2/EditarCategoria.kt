package win.bemtorres.servicio.prueba2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_editar_categoria.*
import kotlinx.android.synthetic.main.item_categoria.*
import win.bemtorres.servicio.controldeinventario.Categoria
import win.bemtorres.servicio.controldeinventario.ConexionSQL

class EditarCategoria : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_categoria)

        var idCategoria = intent.getIntExtra("idCategoria",0)
        val db = ConexionSQL(this, null, 1)
        var categoria : Categoria? = db.buscarCategoria(idCategoria)

        var nombreCategoria: TextView = findViewById(R.id.txtCateg)

        nombreCategoria.text = categoria!!.nombreCategoria
        lblID.text = categoria!!.idCategoria.toString()
        lblEstado.text = if(categoria.estado==1){"Activado"}else {"Descartivado"}

        btnActualizarCateg.setOnClickListener {
            var nombreCategoria2: TextView = findViewById(R.id.txtCateg)
            var nuevoNombre:String = ""
            if(TextUtils.isEmpty(txtCateg.text.toString())){
                txtCateg.error = "Ingrese nombre Categoría"
                return@setOnClickListener
            }else{
                nuevoNombre = nombreCategoria2.text.toString()
            }

            var conn = ConexionSQL(this,null,1)
            var encontrado = 0
            //Buscar si existe con el mismo nombre

            var listaC : ArrayList<Categoria> = conn.listarCategoria()

            for (ca in listaC){
                if (ca.nombreCategoria.toUpperCase().equals(nuevoNombre.toUpperCase())){
                    encontrado = 1
                }
            }

            if (encontrado==0){
                categoria.nombreCategoria = nuevoNombre
                conn.actualizarCategoria(categoria)
            }else{
                Toast.makeText(this, "Ya Existe, intente con otro", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
