package mx.tecnm.tepic.ladm_u3_practica2_fb_sqlite

import android.content.ContentValues
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*
import kotlin.collections.ArrayList

class Notas(p: Context) {
    var titulo = ""
    var contenido = ""
    var horafecha = ""
    var pnt = p

    @RequiresApi(Build.VERSION_CODES.N)
    fun insertarNote() : Boolean {
        val tablaNotes = BaseDatos(pnt,"Notas1",null,1).writableDatabase
        val datos = ContentValues()

        datos.put("titulo",titulo)
        datos.put("contenido",contenido)
        datos.put("horafecha",horafecha)

        val resultado = tablaNotes.insert("NOTA",null,datos)
        if (resultado == -1L){
            return false
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getDateTime(): String? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    fun getNotas() : ArrayList<String> {
        val tablaNotes = BaseDatos(pnt,"Notas1",null,1).readableDatabase
        val resultado = ArrayList<String>()
        val cursor = tablaNotes.query("NOTA", arrayOf("*"),null,null,null,null,null)
        if (cursor.moveToFirst()) {
            do {
                val datos = cursor.getString(3)+"\n"+cursor.getString(1)+"\n"+cursor.getString(2)
                resultado.add(datos)
            } while (cursor.moveToNext())
        }
        return resultado
    }

    fun mandarIDsNotaBorrar() : ArrayList<Int> {
        val tablaNotes = BaseDatos(pnt,"Notas1",null,1).readableDatabase
        val resultado = ArrayList<Int>()
        val cursor = tablaNotes.query("NOTA", arrayOf("*"),null,null,null,null,null)
        if (cursor.moveToFirst()) {
            do {
                resultado.add(cursor.getInt(0))
            } while (cursor.moveToNext())
        }
        return resultado
    }

    fun eliminarNotasHF(hf: String) : Boolean {
        val tablaNotes = BaseDatos(pnt,"Notas1",null,1).writableDatabase
        val resultado = tablaNotes.delete("NOTA", "HORAFECHA=?", arrayOf(hf))
        if (resultado == 0) return false
        return true
    }
}