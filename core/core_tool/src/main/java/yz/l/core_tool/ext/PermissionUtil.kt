package yz.l.core_tool.ext

import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * desc:权限请求
 * @param p{n} 代表 n个权限
 * @param block {
 *        @param granted:是否都允许了
 *        @param deniedList:未允许的权限
 *        @param alwaysDeniedList:点击了不在询问的权限
 *        }
 * createed by liyuzheng on 2023/3/7 11:33
 */

fun LifecycleOwner.requestPermission(
    p1: String,
    block: (granted: Boolean, deniedList: Array<String>, alwaysDeniedList: Array<String>) -> Unit
) {
    requestPermission(block, arrayOf(p1))
}

fun LifecycleOwner.requestPermission(
    p1: String,
    p2: String,
    block: (granted: Boolean, deniedList: Array<String>, alwaysDeniedList: Array<String>) -> Unit
) {
    requestPermission(block, arrayOf(p1, p2))
}


fun LifecycleOwner.requestPermission(
    p1: String,
    p2: String,
    p3: String,
    block: (granted: Boolean, deniedList: Array<String>, alwaysDeniedList: Array<String>) -> Unit
) {
    requestPermission(block, arrayOf(p1, p2, p3))
}

fun LifecycleOwner.requestPermission(
    p1: String,
    p2: String,
    p3: String,
    p4: String,
    block: (granted: Boolean, deniedList: Array<String>, alwaysDeniedList: Array<String>) -> Unit
) {
    requestPermission(block, arrayOf(p1, p2, p3, p4))
}

fun LifecycleOwner.requestPermission(
    block: (granted: Boolean, deniedList: Array<String>, alwaysDeniedList: Array<String>) -> Unit,
    permissions: Array<String>
) {
    runCatching {
        when (this@requestPermission) {
            is FragmentActivity -> {
                PermissionFragment.getPermissionFragment(this@requestPermission, block)
                    .requestPermission(permissions)
            }

            is Fragment -> {
                PermissionFragment.getPermissionFragment(
                    this@requestPermission.requireActivity(),
                    block
                )
                    .requestPermission(permissions)
            }

            else -> {
                throw RuntimeException("requestPermission LifecycleOwner必须是activity或fragment")
            }
        }
    }.onFailure {
        block(false, permissions, arrayOf())
    }
}

/**
 * 权限申请代理fragment
 * 由于launcher不能在activity使用时随时创建，因此需要代理fragment，以便于回调能随时接收、修改
 */
class PermissionFragment : Fragment() {
    companion object {
        private const val TAG = "PermissionFragment"
        fun getPermissionFragment(
            activity: FragmentActivity,
            block: (granted: Boolean, deniedList: Array<String>, alwaysDeniedList: Array<String>) -> Unit
        ): PermissionFragment {
            var permissionFragment = findPermissionsFragment(activity)
            if (permissionFragment == null) {
                permissionFragment = PermissionFragment()
                activity.supportFragmentManager.commit(true) {
                    add(permissionFragment, TAG)
                }
            }
            permissionFragment.block = block
            return permissionFragment
        }

        private fun findPermissionsFragment(activity: FragmentActivity): PermissionFragment? {
            return activity.supportFragmentManager.findFragmentByTag(TAG) as? PermissionFragment
        }
    }

    private var block: (granted: Boolean, deniedList: Array<String>, alwaysDeniedList: Array<String>) -> Unit =
        { _, _, _ -> }

    private val launcher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        lifecycleScope.launchWhenResumed {
            dealResult(requireActivity(), result, block)
            requireActivity().supportFragmentManager.commit(true) {
                remove(this@PermissionFragment)
            }
        }
    }

    fun requestPermission(
        permissions: Array<String>
    ) {
        lifecycleScope.launchWhenResumed {
            launcher.launch(permissions)
        }
    }

    /**
     * 处理请求权限结果
     */
    private suspend fun dealResult(
        activity: FragmentActivity,
        result: Map<String, Boolean>,
        block: (granted: Boolean, deniedList: Array<String>, alwaysDeniedList: Array<String>) -> Unit,
    ) = withContext(Dispatchers.IO) {
        val allDeniedList = result.filterValues { !it }.mapNotNull { it.key }
        val alwaysDeniedList =
            allDeniedList.filter {
                ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    it
                )
            }
        val deniedList = allDeniedList - alwaysDeniedList.toSet()
        var granted = true
        if (allDeniedList.isNotEmpty()) {
            granted = false
        }
        withContext(Dispatchers.Main) {
            block(granted, deniedList.toTypedArray(), alwaysDeniedList.toTypedArray())
        }
    }

}


