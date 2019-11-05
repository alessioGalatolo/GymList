package com.anexus.list


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anexus.list.adapters.ProgramAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class GymFragment : Fragment() {

    private var mainView: View? = null
    private lateinit var ladapter: ProgramAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val programData: ArrayList<Program> = ArrayList()

        ladapter = ProgramAdapter(programData){
            val intent = Intent(context, SessionManager::class.java)
            intent.putExtra(PROGRAM_NAME_EXTRA , it.name)

            Data.currentProgram = it
            startActivity(intent)
        }

        fetchWeatherDataFromDb(programData)



    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_gym, container, false)
        mainView?.findViewById<RecyclerView>(R.id.programList)?.apply {
            adapter = ladapter
            layoutManager = LinearLayoutManager(this.context)
        }
        return mainView
    }

    private fun fetchWeatherDataFromDb(programData: ArrayList<Program>){
        GlobalScope.launch {
            val newDataPrograms = Data.programDb.getAll()
            if (newDataPrograms.isNotEmpty()) {
                programData.addAll(newDataPrograms)
                Data.programs.addAll(programData)

                ladapter.notifyDataSetChanged()
            }else{
                            //TODO("add some suggestion messages")
            }
        }
    }
}
