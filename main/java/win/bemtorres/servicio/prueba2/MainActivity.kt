package win.bemtorres.servicio.prueba2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var adapter : ArrayAdapter<CharSequence> =  ArrayAdapter.createFromResource(this, android
            .R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        sDe!!.setAdapter(adapter)
        sA!!.setAdapter(adapter)


        sA.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var valor1 = sDe.selectedItem.toString()
                var valor2 = sA.selectedItem.toString()
                var mensaje = ""

                if (txtNumber.text.toString().length>0){
                    if (txtNumber.text.toString().toInt()>=0){
                        var numero = txtNumber.text.toString().toInt()
                        if (valor1.equals("CLP")){
                            when(valor2){
                                "USD" -> mensaje= (numero/657.8f).toString()
                                "CLP" ->mensaje= (1*numero).toString()
                                "Euro" -> mensaje= (numero/763.89f).toString()
                            }
                        }
                        if (valor1.equals("USD")){
                            when(valor2){
                                "USD" -> mensaje= (numero).toString()
                                "CLP" ->mensaje= (numero*657.8f).toString()
                                "Euro" -> mensaje= (numero*1.16f).toString()
                            }
                        }
                        if (valor1.equals("Euro")){
                            when(valor2){
                                "USD" -> mensaje= (numero/1.16f).toString()
                                "CLP" ->mensaje= (763.89f*numero).toString()
                                "Euro" -> mensaje= (numero).toString()

                            }
                        }

                        mensaje ="$valor1 $numero -> $valor2 $mensaje"
                        Toast.makeText(this@MainActivity, mensaje, Toast.LENGTH_SHORT).show()
                    }else{
                        txtNumber.error = "Ingrese valores positivos"
                    }
                }else{
                    txtNumber.error = "Ingrese numero"
                }


            }

        }


    }
}
