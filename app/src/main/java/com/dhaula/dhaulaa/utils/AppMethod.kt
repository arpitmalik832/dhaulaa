import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


object AppMethod {
    fun formatPriceText(value: String): String? {
        return "₹ $value"
    }

    fun mailTo(
        ctx: Context,
        subject: String?,
        mailBody: String?,
        vararg emailID: String?
    ) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "plain/text"
        intent.putExtra(Intent.EXTRA_EMAIL, emailID)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, mailBody)
        ctx.startActivity(Intent.createChooser(intent, "Select Mail"))
    }

    fun isAppIsInBackground(context: Context): Boolean {
        var isInBackground = true
        val am =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningProcesses =
            Objects.requireNonNull(am)
                .runningAppProcesses
        for (processInfo in runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (activeProcess in processInfo.pkgList) {
                    if (activeProcess == context.packageName) {
                        isInBackground = false
                    }
                }
            }
        }
        return isInBackground
    }

}