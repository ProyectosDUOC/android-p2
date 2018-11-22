package win.bemtorres.servicio.controldeinventario

class Categoria (var idCategoria:Int, var nombreCategoria: String, var estado: Int=1){
    override fun toString(): String {
        return  nombreCategoria
    }

}