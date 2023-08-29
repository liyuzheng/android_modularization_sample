package yz.l.core_tool.ext

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * desc:
 * createed by liyuzheng on 2023/8/29 0:35
 */
inline fun LifecycleOwner.launchWhenResumed(
    retryTime: Int = 1,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    lifecycleScope.launch {
        var retryCount = 0
        repeatOnLifecycle(Lifecycle.State.RESUMED) {
            try {
                block()
                this@launch.cancel()
            } finally {
                if (retryTime != -1) {
                    retryCount += 1
                    if (retryCount >= retryTime) {
                        this@launch.cancel()
                    }
                }
            }
        }
    }
}

inline fun LifecycleOwner.launchWhenCreated(
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            block()
            this@launch.cancel()
        }
    }
}