package yz.l.fm

import android.graphics.Color
import android.util.Log
import android.util.SparseArray
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import yz.l.core.mvvm.BaseBindingAct
import yz.l.core.mvvm.exts.binding
import yz.l.core_tool.ext.getPlatformProxy
import yz.l.core_tool.ext.sparse
import yz.l.fm.databinding.MainActBinding
import yz.l.fm.glide.TTransformation
import yz.l.fm.glide.TTransformation.Companion.P_FULL
import yz.l.fm.glide.TTransformation.Companion.P_LEFT
import yz.l.fm.glide.TTransformation.Companion.P_RIGHT
import yz.l.fm.lrcview.LrcView.OnPlayClickListener
import yz.l.fm.platformstrategy.ILogPlatformAction

/**
 * desc:
 * createed by liyuzheng on 2023/8/24 17:59
 */
class MainActivity : BaseBindingAct<MainActBinding>() {
    override val mBinding by binding<MainActBinding>(R.layout.main_act)
    override var onBackPressedCallbackEnable = true //允许截获backPress
    private val mViewModel by viewModels<MainActViewModel>()

    override fun variables(): SparseArray<ViewModel> {
        return sparse(BR.mainVM to mViewModel)
    }

    private val logProxy by lazy {
        getPlatformProxy<ILogPlatformAction>()
    }

    override fun setup() {
        super.setup()
//        LotteryProxy.launchLotteriesActAuto(this)
//        val s = LrcUtils.parseLrc(lyricTxt)
//        s?.forEach {
//            Log.d("MainActivity", "[${it.getTime()}] ${it.getText()}")
//        }
        mBinding.lrc.loadLrc(lyricTxt)
        mBinding.lrc.setDraggable(true, object : OnPlayClickListener {
            override fun onPlayClick(view: yz.l.fm.lrcview.LrcView, time: Long): Boolean {
                Log.d("LrcView", "onPlayClick")
                return true
            }
        })
        var t = 0L
//        lifecycleScope.launch {
//            repeat(10000) {
//                delay(100)
//                t += 100
//                mBinding.lrc.updateTime(t)
//            }
//        }
        Glide.get(this).clearMemory()
        val url =
            "http://p1.music.126.net/9A9o8KnCftKoJLmfk7jE-A==/1252343744099790.jpg?imageView=1&type=webp&quality=80"
        val colorFilter = Color.argb(128, 0, 0, 0)

        Glide.with(mBinding.img1)
            .load(url)
            .override(50,50)
            .transform(TTransformation(P_LEFT, colorFilter))
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(mBinding.img1)

        Glide.with(mBinding.img2)
            .load(url)
            .transform(TTransformation(P_RIGHT, colorFilter))
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(mBinding.img2)

        Glide.with(mBinding.img3)
            .load(url)
            .transform(TTransformation(P_FULL, colorFilter))
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(mBinding.img3)
    }
}