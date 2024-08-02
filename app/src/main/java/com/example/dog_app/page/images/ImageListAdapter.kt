package com.example.dog_app.page.images

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dog_app.R
import com.example.dog_app.databinding.AdapterImageBinding


class ImageListAdapter (private val context: Context, private val isFavoriteFlow: Boolean = false,
                        private val favList: ArrayList<String>) :
    RecyclerView.Adapter<ImageListAdapter.ImageViewHolder>() {

    private var list: MutableList<String> = ArrayList()

    private var onItemClickedListener: ((String, String, Int, View) -> Unit)? = null

    fun setOnItemClickedListener(listener: (String, String, Int, View) -> Unit) {
        onItemClickedListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            AdapterImageBinding.inflate(
                inflater,
                parent,
                /* attachToParent= */ false
            )
        return ImageViewHolder(binding)
    }

    fun setBreedList(breedList: ArrayList<String>) {
        this.list = breedList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(context, list[position], position, list.size, isFavoriteFlow, favList)
        holder.binding.ivShare.setOnClickListener {
            onItemClickedListener?.invoke(list[position], "SHARE", position, holder.binding.root)
        }
        holder.binding.ivFavourite.setOnClickListener {
            onItemClickedListener?.invoke(list[position], "FAVORITE", position, holder.binding.root)
        }
        holder.binding.ivDownload.setOnClickListener {
            onItemClickedListener?.invoke(list[position], "DOWNLOAD", position, holder.binding.root)
        }
    }

    class ImageViewHolder(val binding: AdapterImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            context: Context,
            breed: String,
            position: Int,
            size: Int,
            isFavoriteFlow: Boolean,
            favList: ArrayList<String>,
        ) {
            Glide.with(context).load(breed).into(binding.ivDog)

            if(isFavoriteFlow) {
                binding.ivFavourite.visibility = View.GONE
                binding.ivDownload.visibility = View.GONE
                binding.ivShare.visibility = View.GONE
            } else {
                binding.ivFavourite.visibility = View.VISIBLE
                binding.ivDownload.visibility = View.VISIBLE
                binding.ivShare.visibility = View.VISIBLE

                if(favList.contains(breed)) {
                    binding.ivFavourite.setImageDrawable(context.getDrawable(R.drawable.ic_favorite))
                } else {
                    binding.ivFavourite.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_border))
                }
            }
//            val url = URL(breed)
//            val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
//            binding.ivDog.setImageBitmap(bmp)
        }
    }
}