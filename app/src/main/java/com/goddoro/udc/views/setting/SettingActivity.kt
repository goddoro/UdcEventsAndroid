package com.goddoro.udc.views.setting

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.goddoro.common.Broadcast.profileImageUpdateBroadcast
import com.goddoro.common.common.debugE
import com.goddoro.common.common.observeOnce
import com.goddoro.common.data.repository.AuthRepository
import com.goddoro.common.dialog.showTextDialog
import com.goddoro.common.util.Navigator
import com.goddoro.common.util.ToastUtil
import com.goddoro.udc.R
import com.goddoro.udc.databinding.ActivitySettingBinding
import com.goddoro.udc.util.startActivity
import com.goddoro.udc.views.upload.danceClass.UploadClassActivity
import dagger.android.AndroidInjection.inject
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.type.MediaType
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingActivity : AppCompatActivity() {
    private val TAG = SettingActivity::class.java.simpleName

    private lateinit var mBinding : ActivitySettingBinding
    private val mViewModel : SettingViewModel by viewModel()

    private val authRepository : AuthRepository by inject()
    private val navigator : Navigator by inject()

    private val toastUtil : ToastUtil by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySettingBinding.inflate(LayoutInflater.from(this))

        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        setContentView(mBinding.root)

        initView()
        observeViewModel()

    }

    private fun initView() {


        val info = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
        mBinding.txtVersion.text = info.versionName

    }

    private fun observeViewModel() {
        mViewModel.apply {


            clickLogOut.observeOnce(this@SettingActivity){
                authRepository.signOut()
                navigator.startMainActivity(this@SettingActivity, true)
            }

            clickTagDetailDialog.observeOnce(this@SettingActivity){
                navigator.startTagDetailActivity(this@SettingActivity,it)
            }

            clickRegisterClass.observeOnce(this@SettingActivity) {
                startActivity(UploadClassActivity::class, enterAnim = R.anim.slide_in_from_right,R.anim.slide_out_to_left)
            }

            clickEditProfile.observeOnce(this@SettingActivity){
                TedImagePicker.with(this@SettingActivity)
                    .title(resources.getString(R.string.txt_pick_profile))
                    .showCameraTile(false)
                    .mediaType(
                        MediaType.IMAGE
                    )
                    .start {

                        mViewModel.updateProfile(it)
                    }
            }

            onProfileChangeCompleted.observeOnce(this@SettingActivity){
                toastUtil.createToast("프로필 사진을 변경하였습니다").show()
                profileImage.value = it.toString()
                profileImageUpdateBroadcast.onNext(Unit)
            }

            errorInvoked.observe(this@SettingActivity){
                debugE(TAG, it.message)
                showTextDialog(
                    resources.getString(R.string.dialog_error_unknown),
                    it.message ?: "알 수 없는 에러 발생")
            }
        }

    }


}