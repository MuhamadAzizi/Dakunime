package com.example.dakunime

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dakunime.databinding.ItemEpisodeBinding
import org.json.JSONArray

class ListEpisodeAdapter(val listEipsode: JSONArray, val title: String): RecyclerView.Adapter<ListEpisodeAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemEpisodeBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val episode = listEipsode.getJSONObject(position)
        val quality = episode.getJSONArray("videos")
        val q480p = quality.getJSONObject(0)
        val q720p = quality.getJSONObject(1)

        with(holder) {
            with(binding) {
                btnEpisode.text = episode.getString("eps")

                btnEpisode.setOnClickListener {
                    val playerIntent = Intent(root.context, PlayerActivity::class.java)
                    playerIntent.putExtra(PlayerActivity.EXTRA_EPISODE, episode.getString("eps"))
                    playerIntent.putExtra(PlayerActivity.EXTRA_TITLE, title)
                    playerIntent.putExtra(PlayerActivity.EXTRA_480, q480p.getString("stream"))
                    playerIntent.putExtra(PlayerActivity.EXTRA_720, q720p.getString("stream"))
                    root.context.startActivity(playerIntent)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listEipsode.length()
    }
}