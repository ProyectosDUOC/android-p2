package win.bemtorres.servicio.prueba2

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.text.TextUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_agregar_proveedor.*
import kotlinx.android.synthetic.main.fragment_proveedor_frag.*
import win.bemtorres.servicio.controldeinventario.ConexionSQL
import win.bemtorres.servicio.controldeinventario.Proveedor

class AgregarProveedor : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_proveedor)


        var nombreProveedor : String
        var nombreContacto : String
        var rut : String
        var tele : String
        var email : String
        var fecha : String

        val c = Calendar.getInstance()
        val anio = c.get(Calendar.YEAR)
        val mes = c.get(Calendar.MONTH)
        val dia = c.get(Calendar.DAY_OF_MONTH)
        var m= mes+1
        lblFecha.text = "$dia/$m/$anio"
        fecha =  "$dia/$m/$anio"

        btnFecha.setOnClickListener{
            val dp = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener{ view, year, monthOfYear, dayOfMonth ->
                    var m=monthOfYear+1
                    lblFecha.text = "$dayOfMonth/$m/$year"
                    fecha = "$dayOfMonth/$m/$year"
                    c.set(anio,mes,dia)
                }, anio,mes,dia)
            dp.show()
        }

        btnAgregarProvee.setOnClickListener {

            if(TextUtils.isEmpty(txtNombrePro.text.toString())){
                txtNombrePro.error = "Ingrese Proveedor"
                return@setOnClickListener
            }else{
                nombreProveedor = txtNombrePro.text.toString()
            }
            if(TextUtils.isEmpty(txtContacto.text.toString())){
                txtContacto.error = "Ingrese Contacto"
                return@setOnClickListener
            }else{
                nombreContacto = txtContacto.text.toString()
            }
            if(TextUtils.isEmpty(txtRut.text.toString())){
                txtRut.error = "Ingrese Rut"
                return@setOnClickListener
            }else{
                rut = txtRut.text.toString()
            }
            if(TextUtils.isEmpty(txtTelefono.text.toString())){
                txtTelefono.error = "Ingrese Telefono"
                return@setOnClickListener
            }else{
                tele = txtTelefono.text.toString()
            }
            if(TextUtils.isEmpty(txtEmail.text.toString())){
                txtEmail.error = "Ingrese Email"
                return@setOnClickListener
            }else{
                email = txtEmail.text.toString()
            }


            var proveedor = Proveedor(1, nombreProveedor,nombreContacto,rut,tele,email,fecha,1)

            var conn = ConexionSQL(this, null, 1)

            conn.insertarProveedor(proveedor)
        }



    }
}
