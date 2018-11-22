package win.bemtorres.servicio.prueba2

import android.icu.util.Calendar
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import win.bemtorres.servicio.controldeinventario.Categoria
import win.bemtorres.servicio.controldeinventario.ConexionSQL
import win.bemtorres.servicio.controldeinventario.Proveedor

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            var cate1 = Categoria(1,"Bebida",1)
            var cate2 = Categoria(1,"Torta",1)
            var cate3 = Categoria(1,"Pasta",1)
            var conn = ConexionSQL(this,null,1)
            conn.insertarCategoria(cate1)
            conn.insertarCategoria(cate2)
            conn.insertarCategoria(cate3)

            val c = Calendar.getInstance()
            val anio = c.get(Calendar.YEAR)
            val mes = c.get(Calendar.MONTH)
            val dia = c.get(Calendar.DAY_OF_MONTH)
            var m= mes+1
            var fecha: String = "$dia/$m/$anio"


            var prove = Proveedor(1,"Spagetti","Benjamin", "1001-1","98882992","benja@spagetti.cl",fecha,1)
            conn.insertarProveedor(prove)
            fab.visibility = View.GONE

        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_categoria -> {
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                val frag = Categoria_frag()
                frag.miContexto = this
                ft.replace(R.id.ly_content,frag)
                ft.commit()
            }
            R.id.nav_proveedores -> {
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                val frag = Proveedor_frag()
                frag.miContexto = this
                ft.replace(R.id.ly_content,frag)
                ft.commit()
            }
            R.id.nav_producto -> {
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                val frag = Producto_frag()
                frag.miContexto = this
                ft.replace(R.id.ly_content,frag)
                ft.commit()
            }
            R.id.nav_estadis -> {

            }
            R.id.nav_salir -> {
                finish()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
