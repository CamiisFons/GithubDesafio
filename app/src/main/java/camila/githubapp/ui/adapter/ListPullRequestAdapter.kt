package camila.githubapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import camila.githubapp.databinding.ItemPullRequestBinding
import camila.githubapp.model.PullRequest
import camila.githubapp.ui.fragment.listPullRequest.ListPullRequestFragmentDirections
import com.bumptech.glide.Glide

class ListPullRequestAdapter :
    RecyclerView.Adapter<ListPullRequestAdapter.ListPullRequestViewHolder>() {

    inner class ListPullRequestViewHolder(val itemPullRequestBinding: ItemPullRequestBinding) :
        RecyclerView.ViewHolder(itemPullRequestBinding.root)

    private val differCallback = object : DiffUtil.ItemCallback<PullRequest>() {
        override fun areItemsTheSame(oldItem: PullRequest, newItem: PullRequest): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: PullRequest, newItem: PullRequest): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    var pullRequest: List<PullRequest>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPullRequestViewHolder {
        return ListPullRequestViewHolder(
            ItemPullRequestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = pullRequest.size

    override fun onBindViewHolder(holder: ListPullRequestViewHolder, position: Int) {
        val pullRequest = pullRequest[position]
        holder.itemPullRequestBinding.apply {
            pullTitle.text = pullRequest.title
            pullDescription.text = pullRequest.body
            pullUsername.text = pullRequest.user.login
            pullName.text = pullRequest.user.login + pullRequest.title
            Glide.with(photoUser)
                .load(pullRequest.user.avatar_url)
                .circleCrop()
                .into(photoUser)
            cardPull.setOnClickListener { view ->
                val action =
                    ListPullRequestFragmentDirections.actionListPullRequestFragmentToWebViewFragment(
                        pullRequest.html_url,
                        pullRequest.title
                    )
                view.findNavController().navigate(action)
            }

        }
    }
}
