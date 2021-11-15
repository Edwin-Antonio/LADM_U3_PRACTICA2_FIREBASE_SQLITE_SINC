package mx.tecnm.tepic.ladm_u3_practica2_fb_sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val sqliteNotas = Notas(this).getNotas() // Esta no va
        listaSQLite.adapter = ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, sqliteNotas)

        regresarAbrir.setOnClickListener {
            finish()
        }
    }

}