package org.redaksi.core.helper.verse

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PackageInfoFlags
import android.os.Build
import android.util.Log

object AlkitabIntegrationUtil {
    val TAG: String = AlkitabIntegrationUtil::class.java.simpleName

    const val providerAuthority: String = "yuku.alkitab.provider"

    /**
     * Minimum Alkitab app package version (declared in AndroidManifest.xml android:versionCode)
     * in order to be compatible with this client version.
     */
    const val ALKITAB_APP_VERSION_CODE: Int = 101

    /** Verifies that Alkitab app is installed and enabled on this device,
     * and that the version installed on this device is no older than the one required by this client.
     * @return status code indicating whether there was an error. Can be one of following: [ConnectionResult.SUCCESS], [ConnectionResult.APP_MISSING], [ConnectionResult.APP_VERSION_UPDATE_REQUIRED], [ConnectionResult.PROVIDER_DISABLED], [ConnectionResult.INTERNAL_ERROR]
     */
    fun isIntegrationAvailable(context: Context): Int {
        val pm = context.packageManager
        val providerInfo = if (Build.VERSION.SDK_INT >= 33) {
            pm.resolveContentProvider(providerAuthority, PackageManager.ComponentInfoFlags.of(0))
        } else {
            pm.resolveContentProvider(providerAuthority, 0)
        }
        if (providerInfo == null) {
            return ConnectionResult.APP_MISSING
        }

        if (!providerInfo.enabled) {
            return ConnectionResult.PROVIDER_DISABLED
        }

        try {
            val packageInfo = if (Build.VERSION.SDK_INT >= 33) {
                pm.getPackageInfo(providerInfo.packageName, PackageInfoFlags.of(0))
            } else {
                pm.getPackageInfo(providerInfo.packageName, 0)
            }
            if (packageInfo.versionCode < ALKITAB_APP_VERSION_CODE) {
                return ConnectionResult.APP_VERSION_UPDATE_REQUIRED
            }

            return ConnectionResult.SUCCESS
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "Having a provider should have a correct package also", e)
            return ConnectionResult.INTERNAL_ERROR
        }
    }
}
