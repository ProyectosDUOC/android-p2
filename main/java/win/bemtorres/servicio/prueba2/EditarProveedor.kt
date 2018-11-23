package win.bemtorres.servicio.prueba2

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_editar_proveedor.*
import win.bemtorres.servicio.controldeinventario.Categoria
import win.bemtorres.servicio.controldeinventario.ConexionSQL
import win.bemtorres.servicio.controldeinventario.Proveedor

class EditarProveedor : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_proveedor)

        var idProveedor = intent.getIntExtra("idProveedor",0)
        val db = ConexionSQL(this, null, 1)
        var proveedor : Proveedor? = db.buscarProveedor(idProveedor)

        var nombreProEdit :TextView = findViewById(R.id.txtNombreProEdit)
        var contactoEdit : TextView = findViewById(R.id.txtContactoEdit)
        var rutEdit :TextView  = findViewById(R.id.txtRutEdit)
        var telEdit : TextView = findViewById(R.id.txtTelefonoEdit)
        var emailEdit :TextView = findViewById(R.id.txtEmailEdit)
        var  lblFechaEdit : TextView = findViewById(R.id.lblFechaEdit)
        var activoEdit : TextView = findViewById(R.id.lblEstado)

        nombreProEdit.text = proveedor!!.nombreProveedor
        contactoEdit.text = proveedor!!.nombreContacto
        rutEdit.text = proveedor!!.rut
        telEdit.text = proveedor!!.telefono
        emailEdit.text = proveedor!!.email
        lblFechaEdit.text = proveedor!!.fechaInscripcion
        activoEdit.text = if (proveedor.estado==1){ "Activo" } else{"Desactivado"}


        var fecha= proveedor.fechaInscripcion

        val c = Calendar.getInstance()
        val anio = c.get(Calendar.YEAR)
        val mes = c.get(Calendar.MONTH)
        val dia = c.get(Calendar.DAY_OF_MONTH)

        btnFechaEdit.setOnClickListener {
            val dp = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener{ view, year, monthOfYear, dayOfMonth ->
                    var m=monthOfYear+1
                    lblFechaEdit.text = "$dayOfMonth/$m/$year"
                    fecha = "$dayOfMonth/$m/$year"
                    c.set(anio,mes,dia)
                }, anio,mes,dia)
            dp.show()
        }

        btnActualizarProvee.setOnClickListener {

            var nombreProveedor : String
            var nombreContacto : String
            var rut : String
            var tele : String
            var email : String


            if(TextUtils.isEmpty(nombreProEdit.text.toString())){
                nombreProEdit.error = "Ingrese Proveedor"
                return@setOnClickListener
            }else{
                nombreProveedor = nombreProEdit.text.toString()
            }
            if(TextUtils.isEmpty(contactoEdit.text.toString())){
                contactoEdit.error = "Ingrese Contacto"
                return@setOnClickListener
            }else{
                nombreContacto = contactoEdit.text.toString()
            }
            if(TextUtils.isEmpty(rutEdit.text.toString())){
                txtRutEdit.error = "Ingrese Rut"
                return@setOnClickListener
            }else{
                rut = txtRutEdit.text.toString()
            }
            if(TextUtils.isEmpty(txtTelefonoEdit.text.toString())){
                txtTelefonoEdit.error = "Ingrese Telefono"
                return@setOnClickListener
            }else{
                tele = txtTelefonoEdit.text.toString()
            }
            if(TextUtils.isEmpty(txtEmailEdit.text.toString())){
                txtEmailEdit.error = "Ingrese Email"
                return@setOnClickListener
            }else{
                email = txtEmailEdit.text.toString()
            }


            var proveedor = Proveedor(idProveedor, nombreProveedor,nombreContacto,rut,tele,email,fecha,1)
            var conn = ConexionSQL(this, null, 1)
            conn.actualizarProveedor(proveedor)

        }

    }
}
