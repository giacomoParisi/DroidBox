package com.github.giacomoparisi.droidbox.architecture.model.ui

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import android.widget.Toast
import com.github.giacomoparisi.droidbox.R
import com.github.giacomoparisi.droidbox.architecture.model.exception.ManagedException
import javax.inject.Inject

/**
 * Created by Giacomo Parisi on 30/06/2017.
 * https://github.com/giacomoParisi
 */
open class DroidUIManager @Inject constructor(private val application: Application) {

    /* ============= FIELDS ============= */

    // True if error view is needed
    var error = ObservableBoolean()

    // Class type of the last error produced
    var lastError: Class<Throwable>? = null

    // Id of the last error produced
    var lastErrorCode: Int = 0

    // Error message text
    var errorMessage = ObservableField<String>()

    // True if loading view is needed
    var loading = ObservableBoolean()

    // Title text
    var title = ObservableInt()

    var defaultErrorMessage = R.string.ERROR_DefaultMessage

    /* ============= ERROR / LOADING  ============= */

    fun showError(throwable: Throwable, errorCode: Int = 0) {
        hideLoading()
        errorMessage.set(getErrorMessage(throwable))
        lastError = throwable.javaClass
        lastErrorCode = errorCode
        error.set(true)
    }

    fun showLoading() {
        loading.set(true)
    }

    fun hideLoading() {
        loading.set(false)
    }

    fun hideError() {
        error.set(false)
    }

    fun getErrorMessage(throwable: Throwable): String {
        return if (throwable is ManagedException) {
            if (throwable.errorMessageRes != 0) {
                application.getString(throwable.errorMessageRes)
            } else {
                throwable.errorMessage ?: application.getString(defaultErrorMessage)
            }
        } else {
            application.getString(defaultErrorMessage)
        }
    }

    /* ============= ANDROID NATIVE UI ============= */

    /**
     * Show toast message in the activities that observe the current droidViewModel
     *
     * @param message String resource id of the toast message
     * @param toastDuration Duration id of toast, it can be Toast.LENGTH_LONG or Toast.LENGTH_SHORT
     */
    fun showToast(@StringRes message: Int, toastDuration: Int, droidUIActions: DroidUIActions<androidx.fragment.app.FragmentActivity>) {
        droidUIActions {
            Toast.makeText(it, message, toastDuration).show()
        }
    }

    /**
     * Show toast message in the activities that observe the current droidViewModel
     *
     * @param message String message
     * @param toastDuration Duration id of toast, it can be Toast.LENGTH_LONG or Toast.LENGTH_SHORT
     */
    fun showToast(message: String, toastDuration: Int, droidUIActions: DroidUIActions<androidx.fragment.app.FragmentActivity>) {
        droidUIActions {
            Toast.makeText(it, message, toastDuration).show()
        }
    }
}
