package camila.githubapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import camila.githubapp.databinding.ItemRepositoryBinding
import camila.githubapp.model.Repository
import com.bumptech.glide.Glide

class RepositoryListAdapter(private val onClick: (Repository) -> Unit) :
    PagingDataAdapter<Repository, RepositoryListAdapter.RepositoryListViewHolder>(
        RepositoryItemDiffCallback()
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepositoryListViewHolder {
        return RepositoryListViewHolder(
            ItemRepositoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RepositoryListViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }


    inner class RepositoryListViewHolder(itemRepositoryBinding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(itemRepositoryBinding.root) {

        private val username: TextView = itemRepositoryBinding.username
        private val starts: TextView = itemRepositoryBinding.starQnty
        private val forks: TextView = itemRepositoryBinding.forkQnty
        private val repositoryName: TextView = itemRepositoryBinding.repositoryName
        private val fullName: TextView = itemRepositoryBinding.fullName
        private val description: TextView = itemRepositoryBinding.descriptionRepository
        private val ivAvatar: ImageView = itemRepositoryBinding.photoUser
        private val cardView: CardView = itemRepositoryBinding.cardRepository


        fun bind(item: Repository) {
            username.text = item.owner.login
            starts.text = item.stargazers_count.toString()
            forks.text = item.forks.toString()
            repositoryName.text = item.name
            fullName.text = item.full_name
            description.text = item.description
            Glide.with(ivAvatar)
                .load(item.owner.avatar_url)
                .circleCrop()
                .into(ivAvatar)
            cardView.setOnClickListener {
                onClick(item)
            }
        }
    }
}


class RepositoryItemDiffCallback :
    DiffUtil.ItemCallback<Repository>() {
    override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem == newItem
    }

}

