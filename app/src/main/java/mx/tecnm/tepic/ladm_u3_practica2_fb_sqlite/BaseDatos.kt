package mx.tecnm.tepic.ladm_u3_practica2_fb_sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatos(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE NOTA( IDNOTA INTEGER PRIMARY KEY AUTOINCREMENT, TITULO VARCHAR(200), CONTENIDO VARCHAR(500), HORAFECHA DATETIME)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}