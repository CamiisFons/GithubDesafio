package camila.githubapp.ui.fragment.listPullRequest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import camila.githubapp.databinding.FragmentListPullRequestBinding
import camila.githubapp.ui.adapter.ListPullRequestAdapter
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import camila.githubapp.R
import com.google.android.material.snackbar.Snackbar

@AndroidEntryPoint
class ListPullRequestFragment : Fragment() {


    private var _binding: FragmentListPullRequestBinding? = null
    private val binding: FragmentListPullRequestBinding get() = _binding ?: throw Exception("")

    private val viewModel: ListPullRequestViewModel by viewModels()
    private val pullAdapter: ListPullRequestAdapter = ListPullRequestAdapter()

    private val args by navArgs<ListPullRequestFragmentArgs>()

    private val snackbar: Snackbar by lazy {
        Snackbar.make(
            binding.root,
            R.string.error,
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("ok") { requireActivity().finish() }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentListPullRequestBinding.inflate(inflater, container, false).apply {
        (activity as AppCompatActivity).supportActionBar?.title = args.repository
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPullRequestResponse()
        initRecyclerView()
        observePullRequestList()
    }

    private fun getPullRequestResponse() {
        viewModel.getPullRequests(args.userName, args.repository)
    }


    private fun initRecyclerView() {

        binding.pullRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = pullAdapter
        }
    }

    private fun observePullRequestList() {
        viewModel.pullRequestList.observe(this, Observer {
            pullAdapter.pullRequest = it
            pullAdapter.notifyItemChanged(it.size)

            if (pullAdapter.itemCount == 0) {
                _binding?.pbLoading?.visibility = View.INVISIBLE

                _binding?.errorMessage?.visibility = View.VISIBLE
                Toast.makeText(context, "No pull request found", Toast.LENGTH_LONG).show()

            } else {
                _binding?.pbLoading?.visibility = View.INVISIBLE

            }

        })

        viewModel.error.observe(this, {
            snackbar.show()
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
