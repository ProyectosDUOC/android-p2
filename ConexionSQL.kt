package win.bemtorres.servicio.controldeinventario

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import java.lang.Exception
import java.sql.SQLException

class ConexionSQL(val miContexto:Context,
                  val nombre: String,
                  val factory: SQLiteDatabase.CursorFactory?,
                  val version: Int):SQLiteOpenHelper(miContexto,nombre,factory,version){

    override fun onCreate(db: SQLiteDatabase?) {
        val query1 = "CREATE TABLE categoria(idCategoria INTEGER PRIMARY KEY AUTOINCREMENT,  nombreCategoria TEXT,estado INTEGER )"
        db?.execSQL(query1)
        val query2 = "CREATE TABLE proveedor(idProveedor INTEGER PRIMARY KEY AUTOINCREMENT,  nombreProveedor TEXT, nombreContacto TEXT, rut TEXT, telefono TEXT, email TEXT, fechaInscripcion TEXT, estado INTEGER )"
        db?.execSQL(query2)
        val query3 = "CREATE TABLE producto(idProducto INTEGER PRIMARY KEY AUTOINCREMENT,  nombre TEXT, cantidadDisponible INTEGER, precioSinIva REAL, precioConIva REAL, precioDolar REAL, idProveedor INTEGER, idCategoria INTEGER, estado INTEGER )"
        db?.execSQL(query3)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val query1 = "DROP TABLE IF EXISTS categoria;"
        db?.execSQL(query1)
        val query2 = "DROP TABLE IF EXISTS proveedor;"
        db?.execSQL(query2)
        val query3 = "DROP TABLE IF EXISTS producto;"
        db?.execSQL(query3)
        onCreate(db)

    }

    fun insertarCategoria(categoria: Categoria){
        try {
            val db = this.writableDatabase
            var cv = ContentValues()
            cv.put("mensaje" , categoria.nombreCategoria)
            cv.put("estado" , categoria.estado)
            val result = db.insert("categoria", null, cv)
            db.close()
            if (result == -1L){
                Toast.makeText(miContexto, "Categoria no agregada", Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(miContexto, "Categoria agregada", Toast.LENGTH_SHORT).show()
            }
        }catch (e:Exception){
            Toast.makeText(miContexto,  "Error ${e.message}", Toast.LENGTH_SHORT).show()
        }

    }

    fun insertarProveedor(pro: Proveedor){
        try {
            val db = this.writableDatabase
            var cv = ContentValues()

            cv.put("nombreProveedor" , pro.nombreProveedor)
            cv.put("nombreContacto" , pro.nombreContacto)
            cv.put("rut" , pro.rut)
            cv.put("telefono" , pro.telefono)
            cv.put("email" , pro.email)
            cv.put("fechaInscripcion" , pro.fechaInscripcion)
            cv.put("estado" , pro.estado)

            val result = db.insert("proveedor", null, cv)
            db.close()
            if (result == -1L){
                Toast.makeText(miContexto, "Proveedor no agregado", Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(miContexto, "Proveedor agregado", Toast.LENGTH_SHORT).show()
            }
        }catch (e:Exception){
            Toast.makeText(miContexto,  "Error ${e.message}", Toast.LENGTH_SHORT).show()
        }

    }

    fun insertarProducto(pro: Producto){
        try {
            val db = this.writableDatabase
            var cv = ContentValues()

            cv.put("nombre" , pro.nombre)
            cv.put("cantidadDisponible" , pro.cantidadDisponible)
            cv.put("precioSinIva" , pro.precioSinIva)
            cv.put("precioConIva" , pro.precioConIva)
            cv.put("precioDolar" , pro.PrecioDolar)
            cv.put("idProveedor" , pro.idProveedor)
            cv.put("idCategoria" , pro.idCategoria)
            cv.put("estado" , pro.estado)

            val result = db.insert("producto", null, cv)
            db.close()
            if (result == -1L){
                Toast.makeText(miContexto, "Producto no agregado", Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(miContexto, "Producto aceptado", Toast.LENGTH_SHORT).show()
            }
        }catch (e:Exception){
            Toast.makeText(miContexto,  "Error ${e.message}", Toast.LENGTH_SHORT).show()
        }

    }

    fun listarCategoria() : ArrayList<Categoria> {
        var lista = ArrayList<Categoria>()
        try {
            val db = this.writableDatabase
            var cursor: Cursor? = null

            cursor = db.rawQuery("SELECT * FROM categoria", null)
            if (cursor?.moveToFirst() == true) {
                do {
                    val id = cursor.getInt(0)
                    val nombre = cursor.getString(1)
                    val estado = cursor.getInt(2)
                    val cate = Categoria(id,nombre,estado)
                    lista.add(cate)
                } while (cursor.moveToNext())
            }
            return lista
        } catch (ex: SQLException) {
            Toast.makeText(miContexto, "Error ${ex.message}", Toast.LENGTH_SHORT).show()
            Log.e("sqlListar", ex.message)
            return lista
        }
    }

    fun listarProveedor() : ArrayList<Proveedor> {
        var lista = ArrayList<Proveedor>()
        try {
            val db = this.writableDatabase
            var cursor: Cursor? = null

            cursor = db.rawQuery("SELECT * FROM proveedor", null)
            if (cursor?.moveToFirst() == true) {
                do {
                    val id = cursor.getInt(0)
                    val nombreProveedor = cursor.getString(1)
                    val nombreContacto = cursor.getString(2)
                    val rut = cursor.getString(3)
                    val telefono = cursor.getString(4)
                    val email = cursor.getString(5)
                    val fechaInscripcion = cursor.getString(6)
                    val estado = cursor.getInt(7)


                    val pro = Proveedor(id,nombreProveedor,nombreContacto,rut,telefono,email,fechaInscripcion,estado)
                    lista.add(pro)
                } while (cursor.moveToNext())
            }
            return lista
        } catch (ex: SQLException) {
            Toast.makeText(miContexto, "Error ${ex.message}", Toast.LENGTH_SHORT).show()
            Log.e("sqlListar", ex.message)
            return lista
        }
    }

    fun listarProducto() : ArrayList<Producto> {
        var lista = ArrayList<Producto>()
        try {
            val db = this.writableDatabase
            var cursor: Cursor? = null

            cursor = db.rawQuery("SELECT * FROM proveedor", null)
            if (cursor?.moveToFirst() == true) {
                do {

                    /**
                     *
                     *    val query3 = "CREATE TABLE producto
                     *    (idProducto INTEGER PRIMARY KEY AUTOINCREMENT,
                     *    nombre TEXT,
                     *    cantidadDisponible INTEGER,
                     *    precioSinIva REAL,
                     *    precioConIva REAL,
                     *    precioDolar REAL,
                     *    idProveedor INTEGER,
                     *    idCategoria INTEGER,
                     *    estado INTEGER )"

                     * **/

                    val id = cursor.getInt(0)
                    val nombre = cursor.getString(1)
                    val cantDisponible = cursor.getInt(2)
                    val precioS = cursor.getDouble(3)
                    val precioC = cursor.getDouble(4)
                    val precioD = cursor.getDouble(5)
                    val idPro = cursor.getInt(6)
                    val idCate = cursor.getInt(7)
                    val estado = cursor.getInt(8)

                    val pro = Producto(id,nombre,cantDisponible,precioS,precioC,precioD,idPro,idCate,estado)
                    lista.add(pro)
                } while (cursor.moveToNext())
            }
            return lista
        } catch (ex: SQLException) {
            Toast.makeText(miContexto, "Error ${ex.message}", Toast.LENGTH_SHORT).show()
            Log.e("sqlListar", ex.message)
            return lista
        }
    }
}
