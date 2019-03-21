package com.anexus.list

import android.app.Fragment
import android.arch.persistence.room.Room
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.CoordinatorLayout
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.anexus.list.adapters.ProgramAdapter
import com.anexus.list.roomDatabase.DbWorkerThread
import com.anexus.list.roomDatabase.ProgramDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.program_name_dialog.view.*
import java.io.*


class MainActivity : AppCompatActivity() {

    private lateinit var mDbWorkerThread: DbWorkerThread
    private var mDatabase: ProgramDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setFragment(R.id.navigationViewProgram)

        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()
        mDatabase = ProgramDatabase.getInstance(this)

//        val mDatabase =
//                 Room.databaseBuilder(this, ProgramDatabase::class.java, "nome")
//                         .allowMainThreadQueries()
//                         .build()
////        this is very bad, TODO(use asynchronous thread)




        //checks for data written
//        if(File(filesDir.toString() + "/" + EX_DATA_FILENAME).exists()) {
//            val inputStream = openFileInput(EX_DATA_FILENAME)
//            val inputStreamReader =  InputStreamReader(inputStream)
//            val  bufferedReader =  BufferedReader(inputStreamReader)
//            val string: String? = bufferedReader.readLine()
//            copyDataLL(string)
//            inputStream.close()
//            inputStreamReader.close()
//            bufferedReader.close()
//        }

        setSupportActionBar(findViewById(R.id.mainActTB))
        supportActionBar?.title = getString(R.string.app_name)

//        val aba = bottomNavigationView.layoutParams as CoordinatorLayout.LayoutParams
//        aba.behavior = BottomNavigationViewBehavior()

        addProgramFab.setOnClickListener { _ ->
            val nameDialog = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.program_name_dialog, null)
            nameDialog.setView(view)

            view.programNameEt.requestFocus()
            view.programNameEt.selectAll()
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

            nameDialog.setPositiveButton(R.string.ok){ _: DialogInterface, _: Int ->
                imm.toggleSoftInputFromWindow(view.windowToken, 0,0)
                val intent = Intent(this, AddProgram::class.java)
                intent.putExtra(PROGRAM_NAME_EXTRA ,view.programNameEt.text.toString())
                insertDataInDb(Program(null, view.programNameEt.text.toString()))
//                mDatabase.programDao().insert(Program( view.programNameEt.text.toString()))
                startActivity(intent)
            }
            nameDialog.setNegativeButton(R.string.cancel){ dialogInterface: DialogInterface, _: Int ->
                imm.toggleSoftInputFromWindow(view.windowToken, 0,0)
                dialogInterface.cancel()
            }

            nameDialog.show()
        }

        bottomNavigationView.setOnNavigationItemSelectedListener{
            return@setOnNavigationItemSelectedListener setFragment(it.itemId)
        }

    }

    private fun setFragment(itemId: Int): Boolean{
        when(itemId) {
            R.id.navigationViewProgram -> {
                musicCardView?.visibility = View.GONE
                addProgramFab.show()
                supportFragmentManager.beginTransaction()
                        .replace(R.id.mainActFragment, GymFragment())
                        .commit()
                return true
            }

            R.id.navigationViewMusic -> {
                musicCardView?.visibility = View.VISIBLE
                addProgramFab.hide()
                supportFragmentManager.beginTransaction()
                        .replace(R.id.mainActFragment, MusicFragment())
                        .commit()
                return true
            }
            R.id.navigationViewStart -> {
                musicCardView?.visibility = View.GONE
                addProgramFab.hide()
                supportFragmentManager.beginTransaction()
                        .replace(R.id.mainActFragment, TimerFragment())
                        .commit()
                return true
            }
            else -> return false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            // User chose the "Settings" item, show the app settings UI...
            startActivity(Intent(this, SettingsActivity::class.java))
            true
        }

//        R.id.action_favorite -> {
//            // User chose the "Favorite" action, mark the current item
//            // as a favorite...
//            true
//        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    private fun insertDataInDb(program: Program) {
        val task = Runnable { mDatabase?.programDao()?.insert(program) }
        mDbWorkerThread.postTask(task)
    }

    private fun copyDataLL(dataString: String?) {
        var i = 0
        if(dataString == null)
            Log.e("idk", "è nullo, boh")
        else {
            Data.exList.removeAll(Data.exList)
            while (i < dataString.length - 1) {
                if ((dataString[i]) == '=') {
                    var x = dataString.indexOf(',', i)
                    val name = dataString.substring(i + 1, x)
                    i = x + 7
                    x = dataString.indexOf(',', i)
                    val sets = dataString.substring(i, x)
                    i = x + 7
                    x = dataString.indexOf(',', i)
                    val reps = dataString.substring(i, x)
                    i = x + 7
                    x = dataString.indexOf(')', i)
                    val rest = dataString.substring(i, x)
                    Data.exList.add(Ex(name, sets.toInt(), reps.toInt(), rest.toInt()))

                }
                i++
            }
        }
    }



    override fun onDestroy() {
        ProgramDatabase.destroyInstance()
        mDbWorkerThread.quit()
        this.openFileOutput(com.anexus.list.EX_DATA_FILENAME, Context.MODE_PRIVATE).use {
            it.write(Data.exList.toString().toByteArray())
        }
        super.onDestroy()
    }
}
