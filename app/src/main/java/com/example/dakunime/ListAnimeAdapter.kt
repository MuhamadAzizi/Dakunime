package com.example.dakunime

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dakunime.databinding.ItemAnimeBinding
import org.json.JSONArray

class ListAnimeAdapter(val listAnime: JSONArray) : RecyclerView.Adapter<ListAnimeAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemAnimeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val anime = listAnime.getJSONObject(position)

        with(holder) {
            with(binding) {
                Glide.with(root.context)
                    .load(anime.getString("poster"))
                    .into(imgPoster)
                tvTitle.text = anime.getString("title_en")
                tvDescription.text = anime.getString("description")

                root.setOnClickListener {
                    val animeDetailIntent = Intent(root.context, AnimeDetailActivity::class.java)
                    animeDetailIntent.putExtra(AnimeDetailActivity.EXTRA_ID, anime.getString("id"))
                    animeDetailIntent.putExtra(AnimeDetailActivity.EXTRA_POSTER, anime.getString("poster"))
                    animeDetailIntent.putExtra(AnimeDetailActivity.EXTRA_TITLE, anime.getString("title_en"))
                    animeDetailIntent.putExtra(AnimeDetailActivity.EXTRA_DESCRIPTION, anime.getString("description"))
                    root.context.startActivity(animeDetailIntent)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listAnime.length()
    }
}