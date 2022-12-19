package com.example.dakunime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.dakunime.databinding.ActivityMainBinding
import com.loopj.android.http.*
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvListAnime.layoutManager = layoutManager

        getListAnime()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_about -> {
                val aboutIntent = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(aboutIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun getListAnime() {
        val client = AsyncHttpClient()
        val url = "https://dakunime.my.id/api/anime"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val response = responseBody?.let { String(it) }
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONArray("animes")

                val listAnimeAdapter = ListAnimeAdapter(jsonArray)
                binding.rvListAnime.adapter = listAnimeAdapter
                binding.tvCountAnime.text = jsonArray.length().toString()
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                TODO("Not yet implemented")
            }

        })
    }
}