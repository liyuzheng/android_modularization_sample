package yz.l.fm

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import yz.l.core_tool.common.BaseAct

/**
 * desc:
 * createed by liyuzheng on 2023/8/28 21:05
 */
class SplashAct : BaseAct() {
    override fun getLayoutId() = 0
    private val mViewModel by viewModels<SplashViewModel>()
    override fun setup() {
        if ((intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) || !isTaskRoot) {
            finish()
            return
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { true }
        setupSplashScreen(splashScreen)
        setupExitSplashScreenAnim(splashScreen)
        mViewModel.init()
    }

    private fun setupSplashScreen(splashScreen: SplashScreen) {
        val contentView: View = findViewById(android.R.id.content)
        contentView.viewTreeObserver.addOnPreDrawListener(object :
            ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                if (mViewModel.isReady) {
                    splashScreen.setKeepOnScreenCondition { false }
                    contentView.viewTreeObserver.removeOnPreDrawListener(this)
                }
                return mViewModel.isReady
            }
        })
    }

    private fun setupExitSplashScreenAnim(splashScreen: SplashScreen) {
        splashScreen.setOnExitAnimationListener { provider ->
            val splashScreenView = provider.view
            val springAnim = SpringAnimation(
                splashScreenView, SpringAnimation.TRANSLATION_X, splashScreenView.x
            )
            springAnim.spring.stiffness = SpringForce.STIFFNESS_MEDIUM
            springAnim.setStartVelocity(-2000f)
            springAnim.setStartValue(splashScreenView.x)
            springAnim.spring.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
            springAnim.start()
            springAnim.addEndListener { _, _, _, _ ->
                jumpToMain()
                provider.remove()
            }
        }
    }

    private fun jumpToMain() {
        startActivity(Intent(this@SplashAct, MainActivity::class.java))
        finish()
    }
}