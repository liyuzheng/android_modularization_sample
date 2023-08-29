package yz.l.feature_lottery

import android.annotation.SuppressLint
import yz.l.common_paging.adapter.BasePagingViewHolder
import yz.l.data.lottery.LotteryModel
import yz.l.feature_lottery.databinding.ItemLotteryLayBinding

/**
 * desc:
 * createed by liyuzheng on 2023/8/29 15:22
 */
class LotteryItemVH(viewDataBinding: ItemLotteryLayBinding) :
    BasePagingViewHolder<LotteryModel, ItemLotteryLayBinding>(viewDataBinding) {
    @SuppressLint("SetTextI18n")
    override fun bind(data: LotteryModel) {
        mBinding.number.text = "第${data.number}期"

        val prizeNumbers = StringBuilder()
        data.numbers.forEach {
            prizeNumbers.append("$it ")
        }
        mBinding.numbers.text = "中奖号码：$prizeNumbers"
        mBinding.type.text = "彩票类型：${data.lotteryType}"
    }
}