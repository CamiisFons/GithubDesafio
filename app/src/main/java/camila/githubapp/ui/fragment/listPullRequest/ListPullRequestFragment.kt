package camila.githubapp.ui.fragment.listPullRequest

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
import androidx.navigation.fragment.findNavController
import camila.githubapp.ui.adapter.RepositoryListAdapter
import camila.githubapp.ui.fragment.listRepository.ListRepositoryFragmentDirections


@AndroidEntryPoint
class ListPullRequestFragment : Fragment() {


    private var _binding: FragmentListPullRequestBinding? = null
    private val binding: FragmentListPullRequestBinding get() = _binding ?: throw Exception("")

    private val viewModel: ListPullRequestViewModel by viewModels()

    private val args by navArgs<ListPullRequestFragmentArgs>()

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
    }

    private fun getPullRequestResponse() {
        viewModel.getPullRequests(args.userName, args.repository)
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun initRecyclerView() {

        val adapterPull = ListPullRequestAdapter {
            val action = ListPullRequestFragmentDirections.actionListPullRequestFragmentToWebViewFragment(it.html_url, it.title)
            findNavController().navigate(action)
        }

        binding.pullRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterPull
        }

        viewModel.pullRequestList.observe(viewLifecycleOwner) {
            adapterPull.pullList.addAll(it)

            if (adapterPull.itemCount == 0) {
                _binding?.pbLoading?.visibility = View.INVISIBLE

                _binding?.errorMessage?.visibility = View.VISIBLE
                Toast.makeText(context, "No items", Toast.LENGTH_LONG).show()

                Log.e("Pull Request", "No pulls found for this Repository")

            } else {
                _binding?.pbLoading?.visibility = View.INVISIBLE

                Log.e("Pull Request", "cheio")
            }

            adapterPull.notifyDataSetChanged()

        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
