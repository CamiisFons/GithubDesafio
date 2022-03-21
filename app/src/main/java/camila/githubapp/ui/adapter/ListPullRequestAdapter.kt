package camila.githubapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import camila.githubapp.databinding.ItemPullRequestBinding
import camila.githubapp.model.PullRequest
import com.bumptech.glide.Glide

class ListPullRequestAdapter(val pullRequestList: MutableList<PullRequest>) :
    RecyclerView.Adapter<ListPullRequestAdapter.ListPullRequestViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPullRequestViewHolder {
        return ListPullRequestViewHolder(
            ItemPullRequestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListPullRequestViewHolder, position: Int) {
        holder.bindingPull(this.pullRequestList[position])
    }

    inner class ListPullRequestViewHolder(itemPullRequestBinding: ItemPullRequestBinding) :
        RecyclerView.ViewHolder(itemPullRequestBinding.root) {

        private val pullTitle: TextView = itemPullRequestBinding.pullTitle
        private val pullDescription: TextView = itemPullRequestBinding.pullDescription
        private val pullUsername: TextView = itemPullRequestBinding.pullUsername
        private val pullName: TextView = itemPullRequestBinding.pullName
        private val cardView: CardView = itemPullRequestBinding.cardPull
        private val ivAvatar: ImageView = itemPullRequestBinding.photoUser

        fun bindingPull(pullRequest: PullRequest) {
            pullTitle.text = pullRequest.title
            pullDescription.text = pullRequest.body
            pullUsername.text = pullRequest.user.login
            pullName.text = pullRequest.user.login + pullRequest.title
            Glide.with(ivAvatar)
                .load(pullRequest.user.avatar_url)
                .circleCrop()
                .into(ivAvatar)
            cardView.setOnClickListener {
                //onClick(pullRequest)
            }

        }

    }

    override fun getItemCount() = pullRequestList.size

}

