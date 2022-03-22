package camila.githubapp.ui.fragment.webView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import camila.githubapp.databinding.FragmentListPullRequestBinding
import camila.githubapp.databinding.FragmentWebViewBinding
import camila.githubapp.model.PullRequest
import camila.githubapp.ui.fragment.listPullRequest.ListPullRequestFragmentArgs
import camila.githubapp.ui.fragment.listPullRequest.ListPullRequestViewModel

class WebViewFragment : Fragment() {

    private var _binding: FragmentWebViewBinding? = null
    private val binding: FragmentWebViewBinding get() = _binding ?: throw Exception("")

    private val args by navArgs<WebViewFragmentArgs>()


    private lateinit var pullRequest: PullRequest

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentWebViewBinding.inflate(inflater, container, false).apply {
        (activity as AppCompatActivity).supportActionBar?.title = args.pullName

        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.webView.apply {
            webViewClient = WebViewClient()
            args.urlPull.let { url ->
                loadUrl(url)
            }
        }
    }
}


