package com.anexus.list


import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anexus.list.adapters.SongAdapter
import kotlinx.android.synthetic.main.activity_main.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MusicFragment : Fragment() {

    private val songsList: ArrayList<Song> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission(this.requireContext())


        myQuickSort(songsList, 0, songsList.size - 1, "title")
//        adapter = SongsAdapter(this, songs) {
//            song ->
//            songTitleFront.text = song.title
//            songArtistFront.text = song.artist
//        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_music, container, false)
        val mAdapter = SongAdapter(songsList)
        view.findViewById<RecyclerView>(R.id.songsListView).apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }


        return view
    }



private fun requestPermission(context: Context){



//    // 1. Instantiate an AlertDialog.Builder with its constructor
//    val builder = AlertDialog.Builder(context)
////         2. Chain together various setter methods to set the dialog characteristics
//    builder.setMessage(R.string.dialog_message)
//            .setTitle(R.string.dialog_title_storage)
//    builder.setPositiveButton(R.string.ok) { dialog, id ->
//        ActivityCompat.requestPermissions(MainActivity(),
//                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//                0)
//        requestPermission(context)
//    }
//    if (ContextCompat.checkSelfPermission(context,
//                    Manifest.permission.READ_EXTERNAL_STORAGE)
//            != PackageManager.PERMISSION_GRANTED) {
//        val dialog = builder.create()
//        dialog.show()
//        //requestPermission(context)
//    }else


//    if(ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
//        Snackbar.make(mainActivityCoordinatorLayout, "This section requires storage access", Snackbar.LENGTH_SHORT)
//                .setAnchorView(addProgramFab)
//                .show()

    retrieveMusic()


}

    private fun retrieveMusic(){
        val mContentResolver: ContentResolver? = context?.contentResolver
        val uri= android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor = mContentResolver?.query(uri, null, null, null, null);
        if (cursor == null) {
            // query failed, handle error.
        } else if (!cursor.moveToFirst()) {
            // no media on the device

        } else {
            val titleColumn: Int = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE)
            val idColumn: Int = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID)
            do {
                val thisId = cursor.getLong(idColumn);
                val thisTitle = cursor.getString(titleColumn);
                songsList.add(Song(thisTitle, "", null, 0, thisId))
                // ...process entry...
            } while (cursor.moveToNext())
        }
    }

private fun inverti(a: ArrayList<Song>, i: Int, j: Int): Int{
    val tmp = a[i + 1]
    a[i + 1] = a[j]
    a[j] = tmp
    return i + 1
}

private fun distribution(a: ArrayList<Song>, sx: Int, dx: Int, orderBy: String): Int {
    val px= dx
    var i: Int = sx - 1
//        var j: Int = sx
    for (j in sx..dx){
        when(orderBy){
            "title" -> if(a[j].title < a[px].title) i = inverti(a, i, j)
            "artist" ->if(a[j].artist < a[px].artist) i = inverti(a, i, j)
            "length" ->if(a[j].length < a[px].length) i = inverti(a, i, j)
        }
    }
    val tmp = a[i + 1]
    a[i + 1] = a[px]
    a[px] = tmp
    return i + 1
}

private fun myQuickSort(a: ArrayList<Song>, sx: Int, dx: Int, orderBy: String): Unit{
    if(sx < dx){
        val px = distribution(a, sx, dx, orderBy)
        myQuickSort(a, sx, px - 1, orderBy)
        myQuickSort(a, px + 1, dx, orderBy)
    }
}



}
