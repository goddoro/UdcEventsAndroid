package com.goddoro.udc.views.upload

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.goddoro.common.common.AutoClearedValue
import com.goddoro.common.common.widget.setOnDebounceClickListener
import com.goddoro.common.util.ScreenUtil
import com.goddoro.udc.databinding.DialogUploadCompleteBinding
import org.koin.android.ext.android.inject
import kotlin.math.roundToInt


fun AppCompatActivity.showUploadCompleteDialog(
    username: String

) {
    UploadCompleteDialog.show(
        supportFragmentManager, username
    )
}

class UploadCompleteDialog(

    private val username : String

) : DialogFragment() {

    companion object {
        fun show(
            fm: FragmentManager,
            username : String
        ) {
            val dialog = UploadCompleteDialog(username)
            dialog.show(fm, dialog.tag)
        }
    }


    private val screenUtil: ScreenUtil by inject()

    private val TAG = UploadCompleteDialog::class.java.simpleName

    /**
     * Binding Instance
     */
    private var mBinding: DialogUploadCompleteBinding by AutoClearedValue()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DialogUploadCompleteBinding.inflate(inflater, container, false)

        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }


        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBinding.lifecycleOwner = viewLifecycleOwner

        initView()
    }

    private fun initView() {
        mBinding.apply {

            txtTitle.text = "${username}님 행사를 업로드하였습니다! \nUDC EVENTS에서 검수 후에 등록하고 알려드리도록 하겠습니다."

            btnConfirm.setOnDebounceClickListener {
                dismiss()
            }

        }
    }

    /**
     * For Size
     */
    override fun onResume() {
        super.onResume()

        val wm = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        wm.defaultDisplay.getSize(point)
        val width = (point.x * 0.9f).roundToInt()
        val height = screenUtil.toPixel(160).roundToInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }


}