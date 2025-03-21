package yz.l.fm.lrcview

import android.text.TextUtils
import android.text.format.DateUtils
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.Locale
import java.util.regex.Pattern

/**
 * @author 李宇征
 * @since 2025/3/20 11:05
 * @desc:
 */
object LrcUtils {
    private val PATTERN_LINE = Pattern.compile("((\\[\\d\\d:\\d\\d\\.\\d{2,3}])+)(.+)")
    private val PATTERN_TIME = Pattern.compile("\\[(\\d\\d):(\\d\\d)\\.(\\d{2,3})]")

    /**
     * 从文件解析歌词
     */
    private fun parseLrc(lrcFile: File?): List<LrcEntry>? {
        if (lrcFile == null || !lrcFile.exists()) {
            return null
        }

        val entryList = mutableListOf<LrcEntry>()
        try {
            BufferedReader(InputStreamReader(FileInputStream(lrcFile), "utf-8")).use { br ->
                var line: String?
                while (br.readLine().also { line = it } != null) {
                    val list = parseLine(line)
                    if (list != null && list.isNotEmpty()) {
                        entryList.addAll(list)
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        entryList.sort()
        return entryList
    }

    /**
     * 从文本解析歌词
     */
    fun parseLrc(lrcText: String?): List<LrcEntry>? {
        if (TextUtils.isEmpty(lrcText)) {
            return null
        }
        var text = lrcText
        if (text?.startsWith("\uFEFF") == true) {
            text = text.replace("\uFEFF", "")
        }
        val entryList = mutableListOf<LrcEntry>()
        val array =
            text?.split("\n".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray() ?: arrayOf()
        for (line in array) {
            val list = parseLine(line)
            if (!list.isNullOrEmpty()) {
                entryList.addAll(list)
            }
        }
        entryList.sort()
        return entryList
    }
    /**
     * 解析一行歌词
     */
    private fun parseLine(line: String?): List<LrcEntry>? {
        if (TextUtils.isEmpty(line)) {
            return null
        }

        val trimmedLine = line?.trim()
        val lineMatcher = trimmedLine?.let { PATTERN_LINE.matcher(it) }
        if (lineMatcher?.matches() != true) {
            return null
        }

        val times = lineMatcher.group(1)
        val text = lineMatcher.group(3)?.toString().toString()
        val entryList = mutableListOf<LrcEntry>()

        val timeMatcher = times?.let { PATTERN_TIME.matcher(it) }
        while (timeMatcher?.find() == true) {
            val min = timeMatcher.group(1)?.toLong() ?: 0L
            val sec = timeMatcher.group(2)?.toLong() ?: 0L
            val milString = timeMatcher.group(3)
            var mil = milString?.toLong() ?: 0L
            if (milString != null) {
                if (milString.length == 2) {
                    mil *= 10
                }
            }
            val time = min * DateUtils.MINUTE_IN_MILLIS + sec * DateUtils.SECOND_IN_MILLIS + mil
            entryList.add(LrcEntry(time, text))
        }
        return entryList
    }

    /**
     * 转为[分:秒]
     */
    fun formatTime(milli: Long): String {
        val m = (milli / DateUtils.MINUTE_IN_MILLIS).toInt()
        val s = ((milli / DateUtils.SECOND_IN_MILLIS) % 60).toInt()
        val mm = String.format(Locale.getDefault(), "%02d", m)
        val ss = String.format(Locale.getDefault(), "%02d", s)
        return "$mm:$ss"
    }
}