package au.com.deanpike.uitestshared.util

import android.content.Context
import java.io.InputStreamReader

object FileUtil {

    fun readFile(context: Context, fileName: String): String {
        return context.classLoader.getResourceAsStream(fileName)?.let { InputStreamReader(it, "UTF-8").readText() }!!
    }
}