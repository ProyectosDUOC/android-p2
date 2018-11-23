package win.bemtorres.servicio.prueba2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_agregar_producto.*
import kotlinx.android.synthetic.main.activity_editar_producto.*
import win.bemtorres.servicio.controldeinventario.Categoria
import win.bemtorres.servicio.controldeinventario.ConexionSQL
import win.bemtorres.servicio.controldeinventario.Producto
import win.bemtorres.servicio.controldeinventario.Proveedor

class EditarProducto : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_producto)
        listarSpinner()
        var idProducto = intent.getIntExtra("idProducto",0)
        val db = ConexionSQL(this, null, 1)
        var producto : Producto? = db.buscarProducto(idProducto)

        var proveedor : Proveedor? = db.buscarProveedor(producto!!.idProveedor)
        var categoria : Categoria? = db.buscarCategoria(producto!!.idCategoria)

        var nombreProductoEdit :TextView = findViewById(R.id.txtNombreProductoEdit)
        var cantEdit : TextView = findViewById(R.id.txtCantidadEdit)
        var valorEdit :TextView  = findViewById(R.id.txtValorPEdit)


        nombreProductoEdit.text = producto.nombre
        cantEdit.text = producto.cantidadDisponible.toString()
        valorEdit.text = producto.precioSinIva.toString()
        lblValorDolarEdit.text = producto.PrecioDolar.toString()
        lblPrecioConIvaEdit.text = producto.precioConIva.toString()

        lblEstadoProductoEdit.text = if(producto.estado==1){"Activado"}else{"Desactivado"}

        //Toast.makeText(this,"${sp_categoriaEdit.getItemAtPosition(0).toString()}" , Toast.LENGTH_SHORT).show()

        sp_categoriaEdit.setSelection(numeroSpinner(sp_categoriaEdit,categoria!!.nombreCategoria))
        sp_proveedorEdit.setSelection(numeroSpinner(sp_proveedorEdit, proveedor!!.nombreProveedor!!))



        //Calcular
        valorEdit.keyListener
    }

    fun numeroSpinner(sp : Spinner, nombre : String): Int{
        var posicion = 0;

        for (i in 0 until sp.count){
            if (sp.getItemAtPosition(i).toString().equals(nombre)){
                posicion=i
            }
        }

        return  posicion
    }

    fun listarSpinner(){
        var proveedores :ArrayList<Proveedor>  = ArrayList()
        var proveedoresActivos :ArrayList<Proveedor>  = ArrayList()
        var categorias : ArrayList<Categoria> = ArrayList()
        var categoriasActivas : ArrayList<Categoria> = ArrayList()
        val db = ConexionSQL(this, null, 1)
        categorias = db.listarCategoria()
        proveedores = db.listarProveedor()


        for (ca in categorias){
            if (ca.estado==1){
                categoriasActivas.add(ca)
            }
        }

        for(pro in proveedores){
            if (pro.estado ==1){
                proveedoresActivos.add(pro)
            }
        }

        if (proveedoresActivos.size>0 && categoriasActivas.size>0){
            sp_proveedorEdit.adapter =  ArrayAdapter<Proveedor>(this, android.R.layout.simple_expandable_list_item_1,proveedoresActivos)
            sp_categoriaEdit.adapter = ArrayAdapter<Categoria>(this, android.R.layout.simple_expandable_list_item_1,categoriasActivas)
        }else{
            Toast.makeText(this,"No hay datos activos", Toast.LENGTH_SHORT).show()
            finish()
        }


    }
}
