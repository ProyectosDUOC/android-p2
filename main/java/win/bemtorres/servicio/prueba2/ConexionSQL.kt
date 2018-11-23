package win.bemtorres.servicio.controldeinventario

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.icu.util.Calendar
import android.util.Log
import android.widget.Toast
import java.lang.Exception
import java.sql.SQLException

class ConexionSQL(val miContexto:Context,
            val factory: SQLiteDatabase.CursorFactory?,
            val version: Int):SQLiteOpenHelper(miContexto,"miDB",factory,version){


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

    //agregar

    fun insertarCategoria(categoria: Categoria){
        try {
            val db = this.writableDatabase
            var cv = ContentValues()
            cv.put("nombreCategoria" , categoria.nombreCategoria)
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

    fun insertarProveedor(prov: Proveedor){
        try {
            val db = this.writableDatabase
            var cv = ContentValues()

            cv.put("nombreProveedor" , prov.nombreProveedor)
            cv.put("nombreContacto" , prov.nombreContacto)
            cv.put("rut" , prov.rut)
            cv.put("telefono" , prov.telefono)
            cv.put("email" , prov.email)
            cv.put("fechaInscripcion" , prov.fechaInscripcion)
            cv.put("estado" , prov.estado)

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

    //Listar
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

            cursor = db.rawQuery("SELECT * FROM producto", null)
            if (cursor?.moveToFirst() == true) {
                do {
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

    //Eliminar

    fun eliminarCategoria(id:Int){
        try {
            val db = this.writableDatabase
            val args = arrayOf(id.toString())
            val result = db.delete("categoria","idCategoria=?",args)
            if (result ==0){
                Toast.makeText(miContexto,"Categoria no eliminada - $args",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(miContexto,"Categoria eliminado",Toast.LENGTH_SHORT).show()
            }
        }catch (ex: SQLException){
            Toast.makeText(miContexto,"error ${ex.message}",Toast.LENGTH_SHORT).show()
            Log.e("sqlEliminar", ex.message)
        }
    }

    fun eliminarProveedor(id:Int){
        try {
            val db = this.writableDatabase
            val args = arrayOf(id.toString())
            val result = db.delete("proveedor","idProveedor=?",args)
            if (result ==0){
                Toast.makeText(miContexto,"Proveedor no eliminado- $args",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(miContexto,"Proveedor eliminado",Toast.LENGTH_SHORT).show()
            }
        }catch (ex: SQLException){
            Toast.makeText(miContexto,"error ${ex.message}",Toast.LENGTH_SHORT).show()
            Log.e("sqlEliminar", ex.message)
        }
    }

    fun eliminarProducto(id:Int){
        try {
            val db = this.writableDatabase
            val args = arrayOf(id.toString())
            val result = db.delete("producto","idProducto=?",args)
            if (result ==0){
                Toast.makeText(miContexto,"Producto no eliminado- $args",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(miContexto,"Producto eliminado",Toast.LENGTH_SHORT).show()
            }
        }catch (ex: SQLException){
            Toast.makeText(miContexto,"error ${ex.message}",Toast.LENGTH_SHORT).show()
            Log.e("sqlEliminar", ex.message)
        }
    }

    //Actualizar

    fun actualizarCategoria(cate: Categoria){
        try{
            val db = this.writableDatabase
            var cv = ContentValues()

            val args = arrayOf(cate.idCategoria.toString())
            cv.put("nombreCategoria" , cate.nombreCategoria)
            cv.put("estado" , cate.estado)

            val result = db.update("categoria",cv,"idCategoria=?",args)
            db.close()
            if (result==0){
                Toast.makeText(miContexto,"Categoria no actualizada", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(miContexto,"Categoria actualizada", Toast.LENGTH_SHORT).show()
            }
        }catch (ex: Exception){
            Toast.makeText(miContexto,"error ${ex.message}",Toast.LENGTH_SHORT).show()
            Log.e("sqlActualizar",ex.message)
        }
    }

    fun actualizarProveedor(prov: Proveedor){
        try{
            val db = this.writableDatabase
            var cv = ContentValues()

            val args = arrayOf(prov.idProveedor.toString())

            cv.put("nombreProveedor" , prov.nombreProveedor)
            cv.put("nombreContacto" , prov.nombreContacto)
            cv.put("rut" , prov.rut)
            cv.put("telefono" , prov.telefono)
            cv.put("email" , prov.email)
            cv.put("fechaInscripcion" , prov.fechaInscripcion)
            cv.put("estado" , prov.estado)

            val result = db.update("proveedor",cv,"idProveedor=?",args)
            db.close()
            if (result==0){
                Toast.makeText(miContexto,"Proveedor no actualizado", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(miContexto,"Proveedor actualizado", Toast.LENGTH_SHORT).show()
            }
        }catch (ex: Exception){
            Toast.makeText(miContexto,"error ${ex.message}",Toast.LENGTH_SHORT).show()
            Log.e("sqlActualizar",ex.message)
        }
    }

    fun actualizarProducto(pro: Producto){
        try{
            val db = this.writableDatabase
            var cv = ContentValues()

            val args = arrayOf(pro.idProducto.toString())

            cv.put("nombre" , pro.nombre)
            cv.put("cantidadDisponible" , pro.cantidadDisponible)
            cv.put("precioSinIva" , pro.precioSinIva)
            cv.put("precioConIva" , pro.precioConIva)
            cv.put("precioDolar" , pro.PrecioDolar)
            cv.put("idProveedor" , pro.idProveedor)
            cv.put("idCategoria" , pro.idCategoria)
            cv.put("estado" , pro.estado)

            val result = db.update("producto",cv,"idProducto=?",args)
            db.close()
            if (result==0){
                Toast.makeText(miContexto,"Producto no actualizado", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(miContexto,"Producto actualizado", Toast.LENGTH_SHORT).show()
            }
        }catch (ex: Exception){
            Toast.makeText(miContexto,"error ${ex.message}",Toast.LENGTH_SHORT).show()
            Log.e("sqlActualizar",ex.message)
        }
    }

    //Buscar
    fun buscarCategoria(idCate: Int) : Categoria?{
        var categoria : Categoria? = null
        try{
            val db = this.writableDatabase
            var cursor: Cursor? = null

            cursor = db.rawQuery("SELECT * FROM categoria", null)
            if (cursor?.moveToFirst() == true) {
                do {
                    if(idCate == cursor.getInt(0)){
                        val id = cursor.getInt(0)
                        val nombre = cursor.getString(1)
                        val estado = cursor.getInt(2)
                        categoria = Categoria(id,nombre,estado)
                        break
                    }
                } while (cursor.moveToNext())
            }
            return categoria
        }catch (ex: Exception){
            Toast.makeText(miContexto,"error ${ex.message}",Toast.LENGTH_SHORT).show()
            Log.e("sql Buscarr",ex.message)
            return  null
        }
    }

    fun buscarCategoriaNombre(nombre: String) : Categoria?{
        var categoria : Categoria? = null
        try{
            val db = this.writableDatabase
            var cursor: Cursor? = null

            cursor = db.rawQuery("SELECT * FROM categoria", null)
            if (cursor?.moveToFirst() == true) {
                do {
                    if(nombre.equals(cursor.getString(1).toUpperCase())){
                        val id = cursor.getInt(0)
                        val nombre = cursor.getString(1)
                        val estado = cursor.getInt(2)
                        categoria = Categoria(id,nombre,estado)
                        break
                    }
                } while (cursor.moveToNext())
            }
            return categoria
        }catch (ex: Exception){
            Toast.makeText(miContexto,"error ${ex.message}",Toast.LENGTH_SHORT).show()
            Log.e("sql Buscarr",ex.message)
            return  null
        }
    }

    fun buscarProveedor(idProve: Int) : Proveedor?{
        var prove : Proveedor? = null
        try{
            val db = this.writableDatabase
            var cursor: Cursor? = null

            cursor = db.rawQuery("SELECT * FROM proveedor", null)
            if (cursor?.moveToFirst() == true) {
                do {
                    if(idProve == cursor.getInt(0)){

                        val id = cursor.getInt(0)
                        val nombreProveedor = cursor.getString(1)
                        val nombreContacto = cursor.getString(2)
                        val rut = cursor.getString(3)
                        val telefono = cursor.getString(4)
                        val email = cursor.getString(5)
                        val fechaInscripcion = cursor.getString(6)
                        val estado = cursor.getInt(7)

                        prove = Proveedor(id,nombreProveedor,nombreContacto,rut,telefono,email,fechaInscripcion,estado)

                    }
                } while (cursor.moveToNext())
            }
            return prove
        }catch (ex: Exception){
            Toast.makeText(miContexto,"error ${ex.message}",Toast.LENGTH_SHORT).show()
            Log.e("sql Buscarr",ex.message)
            return  null
        }
    }

    fun buscarProducto(idProducto: Int) : Producto?{
        var pro : Producto? = null
        try{
            val db = this.writableDatabase
            var cursor: Cursor? = null

            cursor = db.rawQuery("SELECT * FROM producto", null)
            if (cursor?.moveToFirst() == true) {
                do {
                    if(idProducto == cursor.getInt(0)){

                        val id = cursor.getInt(0)
                        val nombre = cursor.getString(1)
                        val cantDisponible = cursor.getInt(2)
                        val precioS = cursor.getDouble(3)
                        val precioC = cursor.getDouble(4)
                        val precioD = cursor.getDouble(5)
                        val idPro = cursor.getInt(6)
                        val idCate = cursor.getInt(7)
                        val estado = cursor.getInt(8)

                        pro = Producto(id,nombre,cantDisponible,precioS,precioC,precioD,idPro,idCate,estado)
                        break

                    }
                } while (cursor.moveToNext())
            }
            return pro
        }catch (ex: Exception){
            Toast.makeText(miContexto,"error ${ex.message}",Toast.LENGTH_SHORT).show()
            Log.e("sql Buscar Producto",ex.message)
            return  null
        }
    }
}
