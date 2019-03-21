package com.anexus.list

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.inputmethod.InputMethodManager
import com.anexus.list.adapters.SessionListAdapter
import kotlinx.android.synthetic.main.activity_add_program.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_ex_dialog.view.*
import kotlinx.android.synthetic.main.program_name_dialog.view.*
import kotlinx.android.synthetic.main.session_list_item.view.*


class AddProgram : AppCompatActivity() {

    lateinit var adapter1: SessionListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_program)
        setSupportActionBar(findViewById(R.id.mainActTB))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        //TODO(Should be looking for existing programs)
        supportActionBar?.title = intent.extras?.getString(PROGRAM_NAME_EXTRA)



        adapter1 = SessionListAdapter(Data.sessionList) {
            val intent = Intent(this, AddExercise::class.java)
            intent.putExtra(SESSION_NAME_EXTRA, it.sessionName.text.toString())
            startActivity(intent)
        }
        val layManager = LinearLayoutManager(this)
        sessionList.apply {
            setHasFixedSize(true)
            layoutManager = layManager
            adapter = adapter1
        }

        addSessionFab.setOnClickListener {
            val nameDialog = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.program_name_dialog, null)
            nameDialog.setView(view)
            view.programNameEt.setText(getString(R.string.untitled_session))

            view.programNameEt.requestFocus()
            view.programNameEt.selectAll()
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

            nameDialog.setPositiveButton(R.string.ok){ dialogInterface: DialogInterface, i: Int ->
                imm.toggleSoftInputFromWindow(view.windowToken, 0,0)
//                TODO("add session")
            }
            nameDialog.setNegativeButton(R.string.cancel){ dialogInterface: DialogInterface, i: Int ->
                imm.toggleSoftInputFromWindow(view.windowToken, 0,0)
                dialogInterface.cancel()
            }

            nameDialog.show()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }



}
