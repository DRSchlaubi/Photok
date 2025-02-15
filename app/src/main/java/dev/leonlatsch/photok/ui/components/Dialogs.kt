/*
 *   Copyright 2020 Leon Latsch
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package dev.leonlatsch.photok.ui.components

import android.content.Context
import android.content.DialogInterface
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import dev.leonlatsch.photok.R
import dev.leonlatsch.photok.other.runOnMain

/**
 * Holds Dialogs and Toast presets.
 *
 * @since 1.0.0
 * @author Leon Latsch
 */
object Dialogs {

    fun showConfirmDialog(
        context: Context,
        title: String,
        onPositiveButtonClicked: DialogInterface.OnClickListener
    ) {
        runOnMain {
            AlertDialog.Builder(context)
                .setMessage(HtmlCompat.fromHtml(title, HtmlCompat.FROM_HTML_MODE_LEGACY))
                .setPositiveButton(R.string.common_yes, onPositiveButtonClicked)
                .setNegativeButton(R.string.common_no, null)
                .show()
        }
    }

    fun showLongToast(context: Context, message: String) {
        runOnMain {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    fun showShortToast(context: Context, message: String) {
        runOnMain {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun showLicensesDialog(context: Context) {
        runOnMain {
            val webView = WebView(context)
            webView.loadUrl(LICENSE_REPORT_URL)

            AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.about_third_party))
                .setView(webView)
                .setNeutralButton(context.getString(R.string.common_ok)) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    private const val LICENSE_REPORT_URL = "file:///android_asset/open_source_licenses.html"
}