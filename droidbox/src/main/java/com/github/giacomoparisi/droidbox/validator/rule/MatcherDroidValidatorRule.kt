package com.github.giacomoparisi.droidbox.validator.rule

import android.widget.TextView
import com.github.giacomoparisi.droidbox.validator.helper.DroidEditTextHelper
import com.github.giacomoparisi.droidbox.validator.rule.core.DroidValidatorRule

/**
 * Created by Giacomo Parisi on 15/02/18.
 * https://github.com/giacomoParisi
 */
class MatcherDroidValidatorRule(
        view: TextView,
        value: TextView?,
        errorMessage: String,
        private val match: Boolean)
    : DroidValidatorRule<TextView, TextView?>(view, value, errorMessage) {

    public override fun isValid(view: TextView): Boolean {

        if (value == null || view.text.isNullOrEmpty() || view.text.isNullOrBlank()) {
            return true
        }

        return if (match) {
            view.text.toString() == value!!.text.toString()
        } else {
            view.text.toString() != value!!.text.toString()
        }
    }

    public override fun onValidationSucceeded(view: TextView) {
        DroidEditTextHelper.removeError(view)
    }

    public override fun onValidationFailed(view: TextView) {
        DroidEditTextHelper.setError(view, errorMessage)
    }
}