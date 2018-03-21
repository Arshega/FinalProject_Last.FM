package com.example.pc.finalproject

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.example.pc.finalproject.Adapter.AlbumAdapter
import com.example.pc.finalproject.Model.Album
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL

class MainActivity : AppCompatActivity() {
    var c = 0
    var test : TextView ?= null
    var prog : ProgressBar ?= null
    var adapter :AlbumAdapter ?= null
    lateinit var butt: Button
    var searchvalue = ""
    var process1 : fetchData?=null
    var urlal = ""
    var bool =1
    var datas: ArrayList<Album> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        initializer()
        butt.setOnClickListener(View.OnClickListener {
            var process : fetchData = fetchData()
            searchvalue = editText!!.text.toString()
            if(searchvalue != "" && adapter ==null && bool ==1) {
                urlal = StringUrl(searchvalue)
                process.execute()
                process1 = process
                bool =0
            }
            else{
                if(process1 != null) {
                    process1!!.cancel(true)
                    prog!!.visibility = View.GONE
                    button.isEnabled = true
                }
                process1 = null
                adapter = null
                bool = 1
            }
        })
    }


    inner class fetchData : AsyncTask<Void, Void, Void>(){
        override fun onPreExecute() {
            super.onPreExecute()
            datas.clear()
            prog!!.visibility = View.VISIBLE
            button.isEnabled = false
        }


        override fun doInBackground(vararg p0: Void?): Void? {
            try {
                var data = StringUrl(searchvalue)
                var limi = JSONObject(data).getJSONObject("results").getJSONObject("albummatches").getJSONArray("album")
                var count = 0
                while(count !=  limi.length() && count<= 50) {
                    var data = StringUrl(searchvalue)
                    var JO = JSONObject(data).getJSONObject("results").getJSONObject("albummatches").getJSONArray("album")
                            .getJSONObject(count).getJSONArray("image").getJSONObject(3)
                    var JA = JSONObject(data).getJSONObject("results").getJSONObject("albummatches").getJSONArray("album").getJSONObject(count)
                    var image = JO.getString("#text")
                    var name = JA.getString("name")
                    var artist = JA.getString("artist")
                    datas.add(Album(image,name,artist))
                    adapter = AlbumAdapter(datas, applicationContext)
                    publishProgress()
                    count++
                }
            }
            catch (e:InterruptedException){
                e.printStackTrace()
                prog!!.visibility = View.GONE
                button.isEnabled = true
                adapter = null
            }
            return null
        }


        override fun onProgressUpdate(vararg values: Void?) {
            var layout_manager = LinearLayoutManager(applicationContext)
            recylerView.layoutManager = layout_manager
            recylerView.setHasFixedSize(true)
            recylerView.adapter = adapter
            textView!!.visibility = View.GONE
            super.onProgressUpdate(*values)
        }


        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            prog!!.visibility = View.GONE

        }
    }

    fun StringUrl (stringer:String): String {

        var url = URL("http://ws.audioscrobbler.com/2.0/?method=album.search&album="+stringer.capitalize()
                +
                "&api_key=6672f4014a94fc7befe86d6ba8ca1057&format=json")
        var httpURLConnection = url.openConnection()
        var inputStream = httpURLConnection.getInputStream()
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        var line: String? = ""
        var data :String =""
        while (line != null) {
            line = bufferedReader.readLine()
            data += line
        }
        return  data
    }

    fun initializer(){
        test = findViewById(R.id.editText)
        prog = findViewById(R.id.progressBar)
        butt = findViewById(R.id.button)
        prog!!.visibility = View.GONE

}








}

