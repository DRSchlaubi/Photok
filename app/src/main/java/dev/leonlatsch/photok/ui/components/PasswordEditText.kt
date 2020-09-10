package dev.leonlatsch.photok.ui.components

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View.OnClickListener
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.databinding.*
import androidx.databinding.adapters.ListenerUtil
import androidx.databinding.adapters.TextViewBindingAdapter
import dev.leonlatsch.photok.R
import dev.leonlatsch.photok.databinding.PasswordEditTextBinding
import kotlinx.android.synthetic.main.password_edit_text.view.*

@InverseBindingMethods(
    value = [
        InverseBindingMethod(
            type = PasswordEditText::class,
            attribute = "textValue",
            event = "android:textAttrChanged",
            method = "getTextValue"
        )]
)
class PasswordEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private val onShowPasswordClickListener = OnClickListener {
        passwordEditTextValue.inputType = when(passwordEditTextValue.inputType) {
            INPUT_TYPE_PASSWORD -> {
                passwordEditTextIcon.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.eye_closed))
                INPUT_TYPE_TEXT
            }
            INPUT_TYPE_TEXT -> {
                passwordEditTextIcon.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.eye))
                INPUT_TYPE_PASSWORD
            }
            else -> {
                passwordEditTextIcon.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.eye))
                INPUT_TYPE_PASSWORD
            }
        }
    }

    init {
        if (isInEditMode) {
            LayoutInflater.from(context).inflate(R.layout.password_edit_text, this, true)
        } else {
            val binding: PasswordEditTextBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.password_edit_text,
                this,
                true
            )
            binding.onShowPasswordClickListener = onShowPasswordClickListener
            binding.root
        }

        attrs?.let {
            val styledAttrs = context.obtainStyledAttributes(it, R.styleable.PasswordEditText, 0, 0)
            val hint = resources.getText(
                styledAttrs.getResourceId(
                    R.styleable.PasswordEditText_password_edit_text_hint,
                    0
                )
            )

            setHint(hint as String)

            styledAttrs.recycle()
        }

        passwordEditTextValue.addTextChangedListener {
            val letterSpacing = if (it.isNullOrEmpty()) 0f else 0.4f
            passwordEditTextValue.letterSpacing = letterSpacing
        }
    }

    private fun setHint(hint: String) {
        passwordEditTextValue.hint = hint
    }

    fun setTextValue(value: String) {
        passwordEditTextValue.setText(value)
        passwordEditTextValue.setSelection(value.length)
    }

    val getTextValue: String
        get() {
            return passwordEditTextValue.text.toString()
        }

    companion object {
        const val INPUT_TYPE_PASSWORD = 129
        const val INPUT_TYPE_TEXT = 1

        /**
         * Binding Adapter for "textValue".
         *
         * @param passwordEditText The [PasswordEditText] to bind
         * @param value The new value. May come from LiveData
         */
        @BindingAdapter("textValue")
        fun setTextValueAdapter(passwordEditText: PasswordEditText, value: String?) {
            value?.let {
                if (value != passwordEditText.getTextValue) {
                    passwordEditText.setTextValue(value)
                }
            }
        }

        /**
         * "Copied" from Example code.
         * Sets TextWatcher to EditText.
         *
         * @see <a href="https://developer.android.com/reference/android/databinding/InverseBindingMethod">Generated binding classes | Android Developers</a!
         */
        @BindingAdapter(
            value = ["android:afterTextChanged", "android:textAttrChanged"],
            requireAll = false
        )
        fun setTextWatcherAdapter(
            passwordEditText: PasswordEditText,
            test: TextViewBindingAdapter.AfterTextChanged?,
            textAttrChanged: InverseBindingListener?
        ) {
            val newValue = object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    test?.let {
                        test.afterTextChanged(s)
                    }

                    textAttrChanged?.let {
                        textAttrChanged.onChange()
                    }
                }
            }
            val oldValue = ListenerUtil.trackListener(
                passwordEditText.passwordEditTextValue,
                newValue,
                R.id.textWatcher
            )
            if (oldValue != null) {
                passwordEditText.passwordEditTextValue.removeTextChangedListener(oldValue)
            }
            passwordEditText.passwordEditTextValue.addTextChangedListener(newValue)
        }
    }
}