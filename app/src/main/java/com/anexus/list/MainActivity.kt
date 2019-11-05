package com.anexus.list

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.anexus.list.roomDatabase.DbWorkerThread
import com.anexus.list.roomDatabase.ProgramDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.program_name_dialog.view.*


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


        addProgramFab.setOnClickListener {

            val nameDialog = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.program_name_dialog, null)
            nameDialog.setView(view)

            view.programNameEt.requestFocus()
            view.programNameEt.selectAll()
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

            nameDialog.setPositiveButton(R.string.ok){ _: DialogInterface, _: Int ->
                imm.toggleSoftInputFromWindow(view.windowToken, 0,0)
                val intent = Intent(this, SessionManager::class.java)
                intent.putExtra(PROGRAM_NAME_EXTRA ,view.programNameEt.text.toString())

                val newProgram = Program(null, view.programNameEt.text.toString(), ArrayList())
                Data.currentProgram = newProgram

                insertDataInDb(newProgram)
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
            if(it.itemId == R.id.navigationViewMusic){
//                if(storagePermissionGranted()){
//                    return@setOnNavigationItemSelectedListener setFragment(it.itemId)
//                }else{
                val snackbar = Snackbar.make(mainActivityCoordinatorLayout, "This section requires storage access", Snackbar.LENGTH_SHORT)
                snackbar.anchorView = addProgramFab
                snackbar.show()
//                    Toast.makeText(this, "textttt", Toast.LENGTH_LONG).show()
//                    return@setOnNavigationItemSelectedListener true
//                }
            }
            return@setOnNavigationItemSelectedListener true
        }



//        if(storagePermissionGranted()){
//            //TODO("go on")
//        }




    }

    private fun storagePermissionGranted(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                val builder = AlertDialog.Builder(this)
                builder.setMessage(R.string.dialog_message)
                        .setTitle(R.string.dialog_title_storage)
                builder.setPositiveButton(R.string.ok) { _, _ ->
                    ActivityCompat.requestPermissions(this,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 0)}
                builder.create().show()

                return false
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        0)

                return false
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            return true
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

//    private fun copyDataLL(dataString: String?) {
//        var i = 0
//        if(dataString == null)
//            Log.e("idk", "è nullo, boh")
//        else {
//            Data.exList.removeAll(Data.exList)
//            while (i < dataString.length - 1) {
//                if ((dataString[i]) == '=') {
//                    var x = dataString.indexOf(',', i)
//                    val name = dataString.substring(i + 1, x)
//                    i = x + 7
//                    x = dataString.indexOf(',', i)
//                    val sets = dataString.substring(i, x)
//                    i = x + 7
//                    x = dataString.indexOf(',', i)
//                    val reps = dataString.substring(i, x)
//                    i = x + 7
//                    x = dataString.indexOf(')', i)
//                    val rest = dataString.substring(i, x)
//                    Data.exList.add(Ex(name, sets.toInt(), reps.toInt(), rest.toInt()))
//
//                }
//                i++
//            }
//        }
//    }



    override fun onDestroy() {
        ProgramDatabase.destroyInstance()
        mDbWorkerThread.quit()
        this.openFileOutput(EX_DATA_FILENAME, Context.MODE_PRIVATE).use {
            it.write(Data.exercises.toString().toByteArray())
        }
        super.onDestroy()
    }
}
