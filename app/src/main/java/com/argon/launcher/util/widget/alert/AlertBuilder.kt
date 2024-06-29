package com.argon.launcher.util.widget.alert

import android.content.Context
import android.view.Gravity
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

@DslMarker
annotation class DslPopup

@DslPopup
class AlertBuilder(
    val context: Context,
    val setup: AlertBuilder.() -> Unit = {}
) {

    var title: String = ""
    var btnPosText: String = ""
    var btnNegText: String = ""
    var btnNeuText: String = ""
    var message: String = ""
    var btnPositiveAction: (() -> Unit)? = null
    var btnNegativeAction: (() -> Unit)? = null
    var btnNeutralAction: (() -> Unit)? = null
    var cancelable: Boolean = false

    fun build(): AlertDialog {
        setup()
        val options = AlertOptions(
            title = title,
            btnPosText = btnPosText,
            btnNegText = btnNegText,
            btnNeuText = btnNeuText,
            message = message,
            btnPositiveAction = btnPositiveAction,
            btnNegativeAction = btnNegativeAction,
            btnNeutralAction = btnNeutralAction,
            cancelable = cancelable
        )
        return setupCustomAlertDialog(options)
    }

    private fun setupCustomAlertDialog(options: AlertOptions): AlertDialog {
        val alertDialog = MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(btnPosText) { dialog, _ ->
                options.btnPositiveAction?.invoke()
                dialog.dismiss()
            }
            .setNegativeButton(btnNegText) { dialog, _ ->
                options.btnNegativeAction?.invoke()
                dialog.dismiss()
            }
            .setNeutralButton(btnNeuText) { dialog, _ ->
                options.btnNeutralAction?.invoke()
                dialog.dismiss()
            }
            .setCancelable(options.cancelable)
            .create()
        alertDialog.window?.setGravity(Gravity.CENTER)
        return alertDialog
    }

}

data class AlertOptions(
    val title: String,
    val btnPosText: String,
    val btnNegText: String,
    val btnNeuText: String,
    val message: String,
    val btnPositiveAction: (() -> Unit)? = null,
    val btnNegativeAction: (() -> Unit)? = null,
    val btnNeutralAction: (() -> Unit)? = null,
    var cancelable: Boolean = false
)

fun Fragment.alert(setup: AlertBuilder.() -> Unit) {
    val builder = AlertBuilder(requireContext(), setup = setup)
    builder.build().show()
}

fun AppCompatActivity.alert(setup: AlertBuilder.() -> Unit) {
    val builder = AlertBuilder(this, setup = setup)
    builder.build().show()
}