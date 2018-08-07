package com.example.alessio.list

//import android.app.*
import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewManager
import android.widget.Toast
import android.view.ViewGroup
import android.widget.Toolbar
import kotlinx.android.synthetic.main.add_ex_dialog.*
import kotlinx.android.synthetic.main.add_ex_dialog.view.*
import java.io.*


class MainActivity : AppCompatActivity() {

    lateinit var listAdapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(File(filesDir.toString() + "/" + EX_DATA_FILENAME).exists()) {
            val file = openFileInput(EX_DATA_FILENAME)
//            var bytes: ByteArray = ByteArray(file)
//            file.read(bytes)

            copyDataLL(file)
//            Toast.makeText(this, "${Data.exList} akaka", Toast.LENGTH_LONG).show()

        }


        val layoutManager = LinearLayoutManager(this)
        listAdapter = ListAdapter(Data.exList)
        ex_rec_list.adapter = listAdapter
        ex_rec_list.layoutManager = layoutManager
        ex_rec_list.setHasFixedSize(true)

        start_btn.setOnClickListener {
            val intent = Intent(this, Timer::class.java)
            startActivity(intent)

        }

        add_ex_btn.setOnClickListener {
            showDialog()
        }
    }

    private fun copyDataLL(inputStream: InputStream) {
        val inputStreamReader =  InputStreamReader(inputStream);
        val  bufferedReader =  BufferedReader(inputStreamReader);
        val dataString: String? = bufferedReader.readLine()
        var i = 0
        if(dataString == null)
            Log.e("idk", "è nullo, boh")
        else
            while(i < dataString.length - 1){
                if((dataString[i]) == '=') {
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
                    Log.i("idk", name + sets + reps + rest)
                    Data.exList.add(Ex(name, sets.toInt(), reps.toInt(), rest.toInt() ))

                }
                i++
            }
        inputStream.close()
        inputStreamReader.close()
        bufferedReader.close()
    //        Log.i("idk", "fuoir dal ciclo")
        }

    fun showDialog(){
        //this is the dialog trying to put it fullscreen

//        val dialog = Dialog(this, android.R.style.Theme_Material_Light_DialogWhenLarge_DarkActionBar)
//        val view: View = View.inflate(this, R.layout.custom_action_bar, null)
////        dialog.setTitle("title")
////        val view = View.inflate(this, R.layout.add_ex_dialog, null)
//        dialog.setContentView(View.inflate(this, R.layout.add_ex_dialog, null))
//        dialog.show()



//         this is the dialog using dialogfragment

//        val fragmentManager = supportFragmentManager
//        val newFragment = CustomDialogFragment()
//        // The device is smaller, so show the fragment fullscreen
//        val transaction = fragmentManager.beginTransaction();
//        // For a little polish, specify a transition animation
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        // To make it fullscreen, use the 'content' root view as the container
//        // for the fragment, which is always the root view for the activity
//        transaction.add(android.R.id.content, newFragment)
//                .addToBackStack(null).commit();




            val builder = AlertDialog.Builder(this)
            val viewInflated = this.layoutInflater.inflate(R.layout.add_ex_dialog, null)
            builder.setView(viewInflated)
            builder.setTitle(R.string.dialog_title)

            builder.setPositiveButton(R.string.ok) { dialog, id ->

                if(viewInflated.nameInput.text.toString().isEmpty() || viewInflated.restInput.text.toString().isEmpty()) {
                    Toast.makeText(this, "Please complete every field", Toast.LENGTH_SHORT).show()
                } else{
                    val s = viewInflated.restInput.text.toString()
//                    val restTmp = s.substring(s.length - 2).toInt() + s.substring(0, s.length - 3).toInt() * 60
                    Data.exList.add(Ex(viewInflated.nameInput.text.toString(),
                            viewInflated.set_spinner.selectedItem.toString().toInt(),
                            viewInflated.rep_spinner.selectedItem.toString().toInt(),
                            s.toInt()))
                    Toast.makeText(this,
                            "Exercise Added",
                            Toast.LENGTH_SHORT).show()
                    listAdapter.notifyItemInserted(Data.exList.size - 1)

                }
            }
            builder.setNegativeButton(R.string.cancel) { dialog, id ->
                dialog.dismiss()
            }
            builder.create().show()
    }


    override fun onDestroy() {
        this.openFileOutput(com.example.alessio.list.EX_DATA_FILENAME, Context.MODE_PRIVATE).use {
            it.write(Data.exList.toString().toByteArray())
        }
        super.onDestroy()
    }
}

class CustomDialogFragment : DialogFragment() {
    /** The system calls this to get the DialogFragment's layout, regardless
     * of whether it's being displayed as a dialog or an embedded fragment.  */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout to use as dialog or embedded fragment
        return inflater.inflate(R.layout.add_ex_dialog, container, false)
    }
}