package com.ramilkapev.curiosityimagestesttask

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.flexbox.FlexboxLayoutManager
import com.stfalcon.imageviewer.StfalconImageViewer

class ImagesAdapter(private val context: Context,
                    private val imagesList: MutableList<String>):
    RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.images_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImagesAdapter.ViewHolder, position: Int) {
        holder.bindImages(imagesList[position], position + 1)
        Log.d("asdada1", imagesList.size.toString())
        Log.d("asdada", imagesList[position])
    }

    override fun getItemCount() = imagesList.size

    inner class ViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.iv_image_item)
        fun bindImages(imageUrl: String, position: Int) {
            Glide.with(itemView).load(imageUrl).placeholder(R.drawable.placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
            val lp = imageView.layoutParams
            if (lp is FlexboxLayoutManager.LayoutParams) {
                lp.flexGrow = 1f
            }

            imageView.setOnClickListener {
                StfalconImageViewer.Builder(context, imagesList) { imageView, imageUrl ->
                    Glide.with(itemView)
                        .load(imageUrl)
                        .fitCenter()
                        .into(imageView)
                }.withTransitionFrom(imageView)
                    .withStartPosition(adapterPosition)
                    .show()
            }
            imageView.setOnLongClickListener {
                DBHelper(context, null).deleteImage(position)
                deleteItem(adapterPosition)
                Log.d("asd2", imagesList.size.toString())
                Log.d("asd21", imagesList.toString())
                return@setOnLongClickListener true
            }
        }
    }

    fun deleteItem(index: Int) {
        imagesList.removeAt(index)
        notifyItemRemoved(index)
    }


}