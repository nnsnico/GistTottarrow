package io.nns.tottarrow.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import arrow.core.Either
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.nns.tottarrow.R
import io.nns.tottarrow.databinding.MainFragmentBinding
import io.nns.tottarrow.ui.main.items.GistsSection
import io.nns.tottarrow.util.ext.hideKeyboard
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import reactivecircus.flowbinding.appcompat.QueryTextEvent
import reactivecircus.flowbinding.appcompat.queryTextEvents
import reactivecircus.flowbinding.core.scrollChangeEvents
import kotlin.math.abs

@UseExperimental(ExperimentalCoroutinesApi::class, FlowPreview::class)
class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding
    private val gistSection = GistsSection()
    private val viewModel: MainViewModel by viewModel()

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.gistsList.apply {
            val groupAdapter = GroupAdapter<GroupieViewHolder>().apply {
                add(gistSection)
            }
            adapter = groupAdapter
        }

        viewModel.gists.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Either.Right -> {
                    Log.d(
                        MainFragment::class.java.simpleName,
                        it.b.toString()
                    )
                    gistSection.updateGists(it.b)
                }
                is Either.Left -> {
                    Log.d(
                        MainFragment::class.java.simpleName,
                        it.a.toString()
                    )
                    gistSection.updateGists(emptyList())
                    Snackbar.make(this@MainFragment.view!!, it.a.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        GlobalScope.launch(Dispatchers.Main) {
            binding.main
                .scrollChangeEvents()
                .onEach { event ->
                    if (abs(event.scrollY) >= 100) {
                        hideKeyboard()
                    }
                }
                .launchIn(this)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)
        val searchView: SearchView = menu.findItem(R.id.search_menu).let {
            it.actionView as SearchView
        }.also {
            it.queryHint = "Search in github user"
        }

        GlobalScope.launch(Dispatchers.Main) {
            searchView
                .queryTextEvents(true)
                .debounce(500)
                .distinctUntilChanged()
                .collect { event ->
                    when (event) {
                        is QueryTextEvent.QueryChanged -> {
                            Log.d(
                                "${MainFragment::class.java}#onQueryTextChanged",
                                event.queryText.toString()
                            )
                        }
                        is QueryTextEvent.QuerySubmitted -> {
                            hideKeyboard()
                            searchView.clearFocus()
                            viewModel.searchInUser(event.queryText.toString())
                        }
                    }
                }
        }

        return super.onCreateOptionsMenu(menu, inflater)
    }
}
