package com.android.framework.base.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.net.ConnectivityManager
import android.nfc.NfcAdapter
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.format.Time
import android.util.TypedValue
import androidx.annotation.IntDef
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.lang.IllegalArgumentException
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.util.*
import javax.inject.Inject

class DeviceUtil @Inject constructor(resourceProvider: ResourceProvider){

    companion object {
        const val NFC_NOT_SUPPORTED = -1
        const val NFC_DISABLED = 0
        const val NFC_ENABLED = 1
        private const val TARGET_PLATFORM = "Android"
        private const val DEVICE_TYPE = "SMARTPHONE"
        private const val GOOGLE_SDK = "google_sdk"
        private const val DEVICE_NAME = "device_name"
    }

    private val context = resourceProvider.context

    fun getAppVersion(): String {
        return try {
            context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            ""
        }
    }

    fun getTargetPlatform(): String {
        return TARGET_PLATFORM
    }

    @SuppressLint("HardwareIds")
    fun getClientUID(): String {
        val androidId : String? = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)
        return if (androidId.isNullOrEmpty()) "" else androidId
    }

    fun getCarrier(): String? {
        val manager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return manager.networkOperatorName
    }

    fun getSimOperator(): String? {
        val manager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return manager.simOperator
    }

    fun getConnectionType(): String {
        var defaultType = "WIFI"
        val connectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            ?: return defaultType
        connectivityManager.activeNetworkInfo
        if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
            && connectivityManager.activeNetworkInfo!!.type == ConnectivityManager.TYPE_MOBILE
        ) {
            defaultType = "GSM"
        }
        return defaultType
    }

    fun isCracked(): String {
        val buildTags = Build.TAGS
        var commandFlag = false
        if (buildTags != null && buildTags.contains("test-keys")) {
            commandFlag = true
        }
        if (canExecuteCommand("/system/xbin/which su")
            || canExecuteCommand("/system/bin/which su")
            || canExecuteCommand("which su")
            || isFileExist()
        ) {
            commandFlag = true
        }
        return if (commandFlag) "1" else "0"
    }

    /**
     * @return commandFlag
     */
    fun checkCracked(): String? {
        return isCracked()
    }

    /**
     * Sistem komutu olarak çalıştırılabiliyor ise sistemde uygulama vardır.
     *
     * @param command command to be run
     * @return boolean flag
     */
    fun canExecuteCommand(command: String): Boolean {
        var isCommandExecuted = false
        try {
            val process = Runtime.getRuntime().exec(command)
            val inputReader = BufferedReader(
                InputStreamReader(
                    process
                        .inputStream
                )
            )
            val line = inputReader.readLine()
            if (line != null) {
                process.destroy()
                isCommandExecuted = true
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return isCommandExecuted
    }

    /**
     * Device üzerinde çalıştırılabilir su komutu var mı?
     *
     * @return boolean flag
     */
    fun isFileExist(): Boolean {
        val supaths = arrayOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su"
        )
        for (path in supaths) {
            if (getFile(path).exists()) {
                return true
            }
        }
        return false
    }

    /**
     * Verilen path'e bağlı file döner
     *
     * @param path expected file path
     * @return File
     */
    private fun getFile(path: String): File {
        return File(path)
    }

    /**
     * @param versionCode current version
     * @return boolean flag
     */
    fun isVersionGreaterThenOrEquals(versionCode: Int): Boolean {
        return Build.VERSION.SDK_INT >= versionCode
    }


    /**
     * Üretici bilgisini döner
     *
     * @return manufacturer
     */
    fun getManufacturer(): String {
        return Build.MANUFACTURER.toUpperCase(Locale.ENGLISH)
    }

    /**
     * İşletim sistemi versionunu döner
     *
     * @return osVersion
     */
    fun getOsVersion(): String {
        return Build.VERSION.RELEASE
    }

    /**
     * Device Tipi, Smart phone
     *
     * @return deviceType
     */
    fun getDeviceType(): String {
        return DEVICE_TYPE
    }

    /**
     * Cihaz markasını döner
     *
     * @return deviceModel
     */
    fun getDeviceModel(): String {
        return Build.MODEL
    }

    /**
     * Cihaz dilini döner
     *
     * @return deviceLanguage
     */
    fun getLanguage(): String {
        return Locale.getDefault().language.toUpperCase(Locale.ENGLISH)
    }

    /**
     * Cihaz ismini doner.
     * Orn; Bulent's Android
     *
     * @return device name
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun getDeviceName(): String? {
        return Settings.Global.getString(
            context.getContentResolver(),
            DEVICE_NAME
        )
    }

    /**
     * Connection available
     *
     * @return boolean flag
     */
    fun isConnectionAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (!checkNullOnMultipleObject(
                connectivityManager,
                connectivityManager.activeNetworkInfo!!
            )
        ) {
            (connectivityManager.activeNetworkInfo != null
                    && connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting)
        } else false
    }

    /**
     * Converts dp to pixel
     *
     * @param density input dp value
     * @return pixel size
     */
    fun pxToDp(density: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            density.toFloat(),
            context.getResources().getDisplayMetrics()
        )
            .toInt()
    }

    /**
     * Return Width of Screen
     *
     * @return screenWidth size
     */
    fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    /**
     * Return Height of Screen
     *
     * @return screenHeight size
     */
    fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

    /**
     * Checks if the device has nfc feature.
     * Throws an exception if has not.
     *
     * @return -1 if nfc not supported, 0 if nfc feature is disabled, 1 if nfc is enabled
     */
    @NfcStatus
    fun checkNfcStatus(): Int {
        var result: Int = NFC_NOT_SUPPORTED
        try {
            val nfcAdapter = NfcAdapter.getDefaultAdapter(context)
            if (nfcAdapter != null) {
                result =
                    if (nfcAdapter.isEnabled) NFC_ENABLED else NFC_DISABLED
            }
        } catch (ex: IllegalArgumentException) {
            ex.printStackTrace()
        }
        return result
    }

    fun isPossiblyEmulator(): Boolean {
        return (Build.MANUFACTURER.contains("Genymotion")
                || checkEmulatorHardware()
                || Build.DEVICE.contains("generic")
                || checkEmulatorModel())
    }

    private fun checkEmulatorHardware(): Boolean {
        return Build.HARDWARE.contains("goldfish") || Build.HARDWARE.contains("ranchu")
    }

    private fun checkEmulatorModel(): Boolean {
        return (Build.MODEL.contains("Emulator") || Build.MODEL.contains(GOOGLE_SDK)
                || Build.PRODUCT.contains(GOOGLE_SDK))
    }

    fun isMainIntent(intent: Intent): Boolean {
        return (!checkNullOnMultipleObject(intent, intent.action!!)
                && intent.action.equals(Intent.ACTION_MAIN, ignoreCase = true)
                && (intent.categories == null
                || intent.categories.toTypedArray()[0] == Intent.CATEGORY_LAUNCHER))
    }

    private fun checkNullOnMultipleObject(vararg objects: Any): Boolean {
        var anyNullObjectExist = false
        for (obj in objects) {
            if (obj == null) {
                anyNullObjectExist = true
                break
            }
        }
        return anyNullObjectExist
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(
        NFC_NOT_SUPPORTED,
        NFC_DISABLED,
        NFC_ENABLED
    )
    annotation class NfcStatus
}