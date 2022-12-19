package com.example.dakunime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.dakunime.databinding.ActivityAnimeDetailBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class AnimeDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityAnimeDetailBinding

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_POSTER = "extra_poster"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_DESCRIPTION = "extra_description"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = GridLayoutManager(this, 5)

        with(binding) {
            rvEpisodes.layoutManager = layoutManager

            Glide.with(this@AnimeDetailActivity)
                .load(intent.getStringExtra(EXTRA_POSTER))
                .into(imgPoster)

            tvTitle.text = intent.getStringExtra(EXTRA_TITLE)
            tvDescription.text = intent.getStringExtra(EXTRA_DESCRIPTION)
        }

        getAllEpisodes()
    }

    fun getAllEpisodes() {
        val client = AsyncHttpClient()
        val url = "https://dakunime.my.id/api/anime/${intent.getStringExtra(EXTRA_ID)}"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val response = responseBody?.let { String(it) }
                val jsonObject = JSONObject(response)
                val animeJsonObject = jsonObject.getJSONObject("anime")
                val episodeJsonArray = animeJsonObject.getJSONArray("episodes")

                val listEpisodeAdapter = intent.getStringExtra(EXTRA_TITLE)
                    ?.let { ListEpisodeAdapter(episodeJsonArray, it) }
                binding.rvEpisodes.adapter = listEpisodeAdapter
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