package org.idnp.datastoresamplegra

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.idnp.datastoresamplegra.models.User

class MainActivity2 : AppCompatActivity() {

    private val Context.dataStore by preferencesDataStore(NotePrefs.PREFS_NAME)
    lateinit var notePrefs: NotePrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        notePrefs = NotePrefs(dataStore)

        var inputName: EditText = findViewById(R.id.input_name)
        var inputAge: EditText = findViewById(R.id.input_age)
        var inputPhone: EditText = findViewById(R.id.input_phone)
        var inputEmail: EditText = findViewById(R.id.input_email)
        var btnSave: Button = findViewById(R.id.btn_save)
        btnSave.setOnClickListener {
            lifecycleScope.launch {
                var user = User(
                    inputName.text.toString(),
                    inputAge.text.toString().toInt(),
                    inputPhone.text.toString(),
                    inputEmail.text.toString()
                )
                notePrefs.saveNoteUser(user)
                inputName.setText("")
                inputAge.setText("")
                inputPhone.setText("")
                inputEmail.setText("")
            }
        }
        var btnLoad: Button = findViewById(R.id.btn_load)
        btnLoad.setOnClickListener {
            lifecycleScope.launch {
                notePrefs.getUser.collect { user ->
                    inputName.setText(user.name)
                    inputAge.setText(user.age.toString())
                    inputPhone.setText(user.phone)
                    inputEmail.setText(user.email)
                }
            }
        }


        /* lateinit var floatingActionButton: FloatingActionButton
        lateinit var layoutConst: LinearLayout */

        /* layoutConst = findViewById(R.id.LayoutConst)
        floatingActionButton = findViewById(R.id.floatingActionButton)

        layoutConst.setBackgroundColor(Color.RED)


        floatingActionButton.setOnClickListener {
            lifecycleScope.launch {
                notePrefs.backgroundColor.collect { mycolor ->
                    layoutConst.setBackgroundColor(Integer.parseInt(mycolor.toString()))
                }
           }
        } */
    }
}