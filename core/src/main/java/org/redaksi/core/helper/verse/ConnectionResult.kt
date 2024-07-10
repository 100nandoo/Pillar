package org.redaksi.core.helper.verse

object ConnectionResult {
    val TAG: String = ConnectionResult::class.java.simpleName

    /** The connection was successful.  */
    const val SUCCESS: Int = 0

    /** Alkitab app is missing on this device.  */
    const val APP_MISSING: Int = 1

    /** The installed version of Alkitab app is out of date.  */
    const val APP_VERSION_UPDATE_REQUIRED: Int = 2

    /** The provider component of Alkitab has been disabled on this device.  */
    const val PROVIDER_DISABLED: Int = 3

    /** An internal error occurred. Retrying should resolve the problem.  */
    const val INTERNAL_ERROR: Int = 8
}