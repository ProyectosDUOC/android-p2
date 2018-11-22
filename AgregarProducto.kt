package win.bemtorres.servicio.prueba2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_agregar_producto.*
import kotlinx.android.synthetic.main.activity_agregar_proveedor.*
import win.bemtorres.servicio.controldeinventario.Categoria
import win.bemtorres.servicio.controldeinventario.ConexionSQL
import win.bemtorres.servicio.controldeinventario.Producto
import win.bemtorres.servicio.controldeinventario.Proveedor

class AgregarProducto : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_producto)

        listarSpinner()

        val Iva = 0.19
        var dolar = 720


        var valorDolar:Double = 0.0
        var valorConIva :Double = 0.0
        var precio: Double =0.0
        var nombreP: String =""
        var cant: Int = 0


        btnCalcular.setOnClickListener {
            precio = 0.0

            if(TextUtils.isEmpty(txtValorP.text.toString())){
                txtValorP.error = "Ingrese Valor"
                return@setOnClickListener
            }else{
                precio = txtValorP.text.toString().toDouble()
            }

            valorDolar = (precio/dolar)
            valorConIva = (precio*Iva + precio)
            lblPrecioConIva.text = "$$valorConIva"
            lblValorDolar.text = "$valorDolar USD"
        }

        btnAgregarProducto.setOnClickListener {
            var proveedor = sp_proveedor.selectedItem as Proveedor
            var categoria = sp_categoria.selectedItem as Categoria

            precio = 0.0

            if(TextUtils.isEmpty(txtValorP.text.toString())){
                txtValorP.error = "Ingrese Valor"
                return@setOnClickListener
            }else{
                precio = txtValorP.text.toString().toDouble()
            }

            valorDolar = (precio/dolar)
            valorConIva = (precio*Iva + precio)

            if(TextUtils.isEmpty(txtNombreProducto.text.toString())){
                txtNombreProducto.error = "Ingrese nombre producto"
                return@setOnClickListener
            }else{
                nombreP = txtNombreProducto.text.toString()
            }
            if(TextUtils.isEmpty(txtCantidad.text.toString())){
                txtCantidad.error = "Ingrese cantidad del producto"
                return@setOnClickListener
            }else{
                cant = txtCantidad.text.toString().toInt()
            }

            var producto = Producto(1,nombreP,cant,precio,valorConIva,valorDolar,proveedor.idProveedor,categoria.idCategoria,1)

            var conn = ConexionSQL(this, null, 1)

            conn.insertarProducto(producto)


           // Toast.makeText(this,"${proveedor.nombreProveedor} +  ${categoria.nombreCategoria}", Toast.LENGTH_SHORT).show()
        }


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
            sp_proveedor.adapter =  ArrayAdapter<Proveedor>(this, android.R.layout.simple_expandable_list_item_1,proveedoresActivos)
            sp_categoria.adapter = ArrayAdapter<Categoria>(this, android.R.layout.simple_expandable_list_item_1,categoriasActivas)
        }else{
            Toast.makeText(this,"Faltan Datos", Toast.LENGTH_SHORT).show()


            startActivity(Intent(this,MainActivity::class.java))
            finish()


        }


         }
}
