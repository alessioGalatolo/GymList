package com.anexus.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.anexus.list.adapters.ListAdapter
import kotlinx.android.synthetic.main.activity_add_exercise.*
import kotlinx.android.synthetic.main.add_ex_dialog.view.*

class AddExercise : AppCompatActivity() {

    lateinit var listAdapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_exercise)

        //initialize toolbar
        //setSupportActionBar(addExTB)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.extras?.getString(PROGRAM_NAME_EXTRA)

        //initialize the list
        Data.currentSession!!.exercises.add(Exercise("name", 123,123,123))
        val layoutManager = LinearLayoutManager(this)
        listAdapter = ListAdapter(Data.currentSession!!.exercises)
        ex_rec_list.adapter = listAdapter
        ex_rec_list.layoutManager = layoutManager
        ex_rec_list.setHasFixedSize(true)

        //line between items
//        val dividerItemDecoration = DividerItemDecoration(ex_rec_list.context,
//                layoutManager.orientation)
//        ex_rec_list.addItemDecoration(dividerItemDecoration)

        //button to start Timer activity
        start_btn.setOnClickListener {
            if(Data.currentSession!!.exercises.isEmpty())
                Toast.makeText(this,
                        "Please enter at least one exercise before starting the timer",
                        Toast.LENGTH_LONG).show()
            else {
                startActivity(Intent(this, Timer::class.java))
            }
        }

        addExerciseFab.setOnClickListener {
            showDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun showDialog(){
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

        builder.setPositiveButton(R.string.ok) { _, _ ->

            if(viewInflated.nameInput.text.toString().isEmpty() || viewInflated.restInput.text.toString().isEmpty()) {
//                Snackbar.make(findViewById(R.id.coordinator),"boh", Snackbar.LENGTH_LONG).show()
//                Toast.makeText(this, getString(R.string.dialog_invalid_choice), Toast.LENGTH_SHORT).show()
                showDialog()
            } else{
                val s = viewInflated.restInput.text.toString()
                Data.currentSession!!.exercises.add(Exercise(viewInflated.nameInput.text.toString(),
                        viewInflated.set_spinner.selectedItem.toString().toInt(),
                        viewInflated.rep_spinner.selectedItem.toString().toInt(),
                        s.toInt()))
                Toast.makeText(this,
                        getString(R.string.dialog_valid_choice),
                        Toast.LENGTH_SHORT).show()
                listAdapter.notifyDataSetChanged() /*Data.currentSession!!.exercises.size - 1*/

            }
        }
        builder.setNegativeButton(R.string.cancel) { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
        viewInflated.nameInput.requestFocus()
    }


    class CustomDialogFragment : DialogFragment() {
        /** The system calls this to get the DialogFragment's layout, regardless
         * of whether it's being displayed as a dialog or an embedded fragment.  */
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            // Inflate the layout to use as dialog or embedded fragment
            return inflater.inflate(R.layout.add_ex_dialog, container, false)
        }
    }
}
