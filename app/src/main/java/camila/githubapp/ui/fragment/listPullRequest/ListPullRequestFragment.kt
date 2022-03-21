package camila.githubapp.ui.fragment.listPullRequest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import camila.githubapp.databinding.FragmentListPullRequestBinding
import camila.githubapp.ui.adapter.ListPullRequestAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListPullRequestFragment : Fragment() {

    private var _binding: FragmentListPullRequestBinding? = null
    private val binding: FragmentListPullRequestBinding get() = _binding ?: throw Exception("")

    private val viewModel: ListPullRequestViewModel by viewModels()
    private val listPullRequestAdapter: ListPullRequestAdapter = ListPullRequestAdapter(ArrayList())

    private val args by navArgs<ListPullRequestFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentListPullRequestBinding.inflate(inflater, container, false).apply {
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


    private fun initRecyclerView() {

//        val pullAdapter = ListPullRequestAdapter {
//            val action = ListRepositoryFragmentDirections.actionListRepositoryFragmentToListPullRequestFragment(it.name, it.owner.login)
//            findNavController().navigate(action)
//        }

        binding.pullRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listPullRequestAdapter
        }

        viewModel.pullRequestList.observe(viewLifecycleOwner) {
            listPullRequestAdapter.pullRequestList.addAll(it)

            if (listPullRequestAdapter.itemCount == 0) {
                _binding?.pbLoading?.visibility = View.INVISIBLE

                _binding?.errorMessage?.visibility = View.VISIBLE
                Toast.makeText(context, "No items", Toast.LENGTH_LONG).show()

                Log.e("Pull Request", "No pulls found for this Repository")

            } else {
                _binding?.pbLoading?.visibility = View.INVISIBLE

                Log.e("Pull Request", "cheio")
            }
            listPullRequestAdapter.notifyDataSetChanged()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
