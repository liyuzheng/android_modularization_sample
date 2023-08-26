package yz.l.data_user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * desc:
 * createed by liyuzheng on 2023/8/26 14:52
 */
@Parcelize
data class TestParcelableModel(val n: String = "12345") : Parcelable