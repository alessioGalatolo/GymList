package com.anexus.list

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.anexus.list.adapters.SessionListAdapter
import com.anexus.list.roomDatabase.DatabaseManager
import kotlinx.android.synthetic.main.activity_session_manager.*
import kotlinx.android.synthetic.main.program_name_dialog.view.*
import kotlinx.android.synthetic.main.session_list_item.view.*


class SessionManager : AppCompatActivity() {

    private lateinit var adapter1: SessionListAdapter
    private val databaseManager = DatabaseManager(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_manager)
        setSupportActionBar(findViewById(R.id.mainActTB))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //gets program name
        //TODO(Should be looking for existing programs)
        supportActionBar?.title = intent.extras?.getString(PROGRAM_NAME_EXTRA)



        adapter1 = SessionListAdapter(Data.currentProgram!!.sessions) {
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
            //add sesssion button

            //create alert dialog to add a name
            val nameDialog = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.program_name_dialog, null)
            nameDialog.setView(view)
            view.programNameEt.setText(getString(R.string.untitled_session))
            view.programNameEt.requestFocus()
            view.programNameEt.selectAll()
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

            //set the responses
            nameDialog.setPositiveButton(R.string.ok){ _: DialogInterface, _: Int ->
                imm.toggleSoftInputFromWindow(view.windowToken, 0,0)

                if(Data.currentProgram == null){
                    TODO("generate error")
                }else{
                    val newSession = Session(view.programNameEt.text.toString(), null, ArrayList())
                    Data.currentProgram!!.sessions.add(newSession)
                    Data.currentSession = newSession

                    adapter1.notifyDataSetChanged()
                }
            }
            nameDialog.setNegativeButton(R.string.cancel){ dialogInterface: DialogInterface, _: Int ->
                imm.toggleSoftInputFromWindow(view.windowToken, 0,0)
                dialogInterface.cancel()
            }

            nameDialog.show()
        }


    }

    private fun updateDataFromDb(program: Program) {
        //old access to db
//        val task = Runnable {
//            val programData2 =
//                    mDatabase?.programDao()?.getAll()
//            mUiHandler.post {
//                if (programData2 == null || programData2.isEmpty()) {
//                    Toast.makeText(this, "No data in cache", Toast.LENGTH_SHORT).show()
//                }else{
//
//                    programData.addAll(programData2)
//                    ladapter.notifyDataSetChanged()
//                }
//            }
//        }
//        mDbWorkerThread.postTask(task)


        //new version
//        databaseManager.getAll()
        //ladapter.notify
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }



}
