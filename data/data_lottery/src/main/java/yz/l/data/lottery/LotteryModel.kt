package yz.l.data.lottery

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName
import yz.l.network.BaseResponse

/**
 * desc:
 * createed by liyuzheng on 2023/8/29 14:13
 */

data class LotteryList(
    @SerializedName("data")
    val data: MutableList<LotteryModel> = mutableListOf(),
    @SerializedName("next")
    var next: String
) : BaseResponse()

data class LotteryModel(
    @SerializedName("lottery_type")
    val lotteryType: String,
    @SerializedName("numbers")
    val numbers: MutableList<String>,
    @SerializedName("date_time")
    val dateTime: String,
    @SerializedName("number")
    val number: String
) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<LotteryModel>() {
            override fun areItemsTheSame(
                oldItem: LotteryModel,
                newItem: LotteryModel
            ): Boolean =
                oldItem.lotteryType == newItem.lotteryType
                        && oldItem.number == newItem.number

            override fun areContentsTheSame(
                oldItem: LotteryModel,
                newItem: LotteryModel
            ): Boolean =
                oldItem == newItem

        }
    }
}

