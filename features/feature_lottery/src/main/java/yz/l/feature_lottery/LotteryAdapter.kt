package yz.l.feature_lottery

import android.view.LayoutInflater
import android.view.ViewGroup
import yz.l.common_paging.adapter.BasePagingAdapter
import yz.l.data.lottery.LotteryModel
import yz.l.feature_lottery.databinding.ItemLotteryLayBinding

/**
 * desc:
 * createed by liyuzheng on 2023/8/29 15:28
 */
class LotteryAdapter :
    BasePagingAdapter<LotteryModel, LotteryItemVH>(
        LotteryModel.diffCallback
    ) {
    override fun getItemViewType(position: Int, data: LotteryModel): Int {
        return 15
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LotteryItemVH {
        val binding = ItemLotteryLayBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LotteryItemVH(binding)
    }

}