package yz.l.feature_lottery

import android.util.SparseArray
import android.view.ViewGroup.LayoutParams
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import yz.l.common_paging.adapter.EmptyAdapter
import yz.l.common_paging.adapter.VerticalFooterAdapter
import yz.l.common_paging.ext.concat
import yz.l.common_paging.ext.setup
import yz.l.core.mvvm.BaseBindingAct
import yz.l.core.mvvm.exts.binding
import yz.l.core_tool.ext.sparse
import yz.l.core_tool.ext.toFlow
import yz.l.feature_lottery.databinding.LotteriedActBinding

/**
 * desc:
 * createed by liyuzheng on 2023/8/29 14:07
 */
class LotteriesAct : BaseBindingAct<LotteriedActBinding>() {
    override val mBinding by binding<LotteriedActBinding>(R.layout.lotteried_act)
    private val mViewModel by viewModels<LotteriesViewModel>()
    override fun setupView() {
        super.setupView()
        setupRv()
        setupSearch()
    }

    override fun variables(): SparseArray<ViewModel> {
        return sparse(BR.lotteryVM to mViewModel)
    }

    override fun setupData() {
        super.setupData()
        fetchData()
    }

    private val adapter by lazy { LotteryAdapter() }

    @OptIn(FlowPreview::class)
    private fun setupSearch() {
        mBinding.search
            .toFlow()
            .debounce(1000)
            .distinctUntilChangedBy {
                mViewModel.searchObs.value = it.toString()
            }.launchIn(lifecycleScope).start()
    }

    private fun setupRv() {
        mBinding.swipeRl.setOnRefreshListener {
            adapter.refresh()
        }
        val concatAdapter = adapter.concat(
            VerticalFooterAdapter(adapter),
            EmptyAdapter(adapter, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        )
        mBinding.rv.adapter = concatAdapter
        adapter.setup(lifecycleScope, mBinding.rv) {
            mViewModel.loadingObs.value = it.mediator?.refresh == LoadState.Loading
        }
    }

    private fun fetchData() {
        lifecycleScope.launch {
            mViewModel.posts.collectLatest {
                adapter.submitData(it)
            }
        }
    }
}