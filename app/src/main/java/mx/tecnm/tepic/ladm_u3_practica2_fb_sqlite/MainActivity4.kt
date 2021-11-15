package mx.tecnm.tepic.ladm_u3_practica2_fb_sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_main4.*

class MainActivity4 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        //DB SQLite
        mostrarSQlite()
        regresarEliminadas.setOnClickListener { finish() }
    }

    private fun mostrarSQlite() {
        val notasSQL = Notas(this).getNotas()
        notasEliminadas.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,notasSQL)
    }

}