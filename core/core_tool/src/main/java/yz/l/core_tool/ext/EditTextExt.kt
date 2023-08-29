package yz.l.core_tool.ext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * desc:
 * createed by liyuzheng on 2023/8/29 20:11
 */
fun EditText.toFlow(): Flow<CharSequence> = callbackFlow {
    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            trySend(s ?: "")
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }
    addTextChangedListener(textWatcher)
    awaitClose { removeTextChangedListener(textWatcher) }
}

