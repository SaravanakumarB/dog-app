package com.example.dog_app.page.breed

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dog_app.R
import com.example.dog_app.databinding.AdapterBreedListBinding


class BreedListAdapter (private val context: Context, private val isFavoriteFlow: Boolean = false,
    private val favList: ArrayList<String>) :
    RecyclerView.Adapter<BreedListAdapter.TextViewHolder>() {

    private var list: MutableList<String> = ArrayList()

    private var onItemClickedListener: ((String, String, Int, View) -> Unit)? = null

    fun setOnItemClickedListener(listener: (String, String, Int, View) -> Unit) {
        onItemClickedListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TextViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            AdapterBreedListBinding.inflate(
                inflater,
                parent,
                /* attachToParent= */ false
            )
        return TextViewHolder(binding)
    }

    fun setBreedList(breedList: ArrayList<String>) {
        this.list = breedList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        holder.bind(context, list[position], position, list.size, isFavoriteFlow, favList)

        holder.binding.tvName.setOnClickListener {
            onItemClickedListener?.invoke(list[position], "NAME", position, holder.binding.root)
        }
        holder.binding.ivFavourite.setOnClickListener {
            onItemClickedListener?.invoke(list[position], "FAVORITE", position, holder.binding.root)
        }
    }

    class TextViewHolder(val binding: AdapterBreedListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            context: Context,
            breed: String,
            position: Int,
            size: Int,
            isFavoriteFlow: Boolean,
            favList: ArrayList<String>,
        ) {
            binding.tvName.text = "${position+1}. $breed"

            if(isFavoriteFlow) {
                binding.ivFavourite.visibility = View.GONE
            } else {
                binding.ivFavourite.visibility = View.VISIBLE

                if(favList.contains(breed)) {
                    binding.ivFavourite.setImageDrawable(context.getDrawable(R.drawable.ic_favorite))
                } else {
                    binding.ivFavourite.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_border))
                }
            }
        }
    }
}