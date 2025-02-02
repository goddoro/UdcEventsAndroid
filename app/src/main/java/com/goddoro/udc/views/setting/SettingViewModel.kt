package com.goddoro.udc.views.setting

import android.net.Uri
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.common.toUri
import com.goddoro.common.data.model.Tag
import com.goddoro.common.data.model.User
import com.goddoro.common.data.repository.AuthRepository
import com.goddoro.common.data.repository.UserRepository
import kotlinx.coroutines.launch
import net.bytebuddy.implementation.bytecode.Throw


/**
 * created By DORO 2020/10/10
 */

class SettingViewModel (
    private val authRepository: AuthRepository,
    private val userRepository : UserRepository
): ViewModel() {

    // region DATA

    private val curUser : MutableLiveData<User?> = MutableLiveData(authRepository.curUser.value)

    val profileImage = MediatorLiveData<String>().apply {
        addSource(curUser){
            this.value = it?.avatarUrl
        }
    }

    val email = MediatorLiveData<String>().apply {
        addSource(curUser){
            this.value = it?.email
        }
    }

    val username = MediatorLiveData<String>().apply {
        addSource(curUser){
            this.value = it?.username
        }
    }

    val nickName = MediatorLiveData<String>().apply {
        addSource(curUser){
            this.value = it?.username
        }
    }

    val location = MediatorLiveData<String>().apply {
        addSource(curUser){
            this.value = it?.location
        }
    }

    val followArtist = MediatorLiveData<List<Tag>>().apply {
        addSource(curUser){
            this.value = it?.followArtists
        }
    }

    val genres = MediatorLiveData<List<String>>().apply {
        addSource(curUser){
            this.value = it?.genres
        }
    }

    // endregion

    // region STATE

    // endregion


    // region EVENT

    val clickTagDetailDialog : MutableLiveData<Once<Int>> = MutableLiveData()

    val clickRegisterClass : MutableLiveData<Once<Unit>> = MutableLiveData()

    val clickEditProfile : MutableLiveData<Once<Unit>> = MutableLiveData()

    val onProfileChangeCompleted : MutableLiveData<Once<Uri>> = MutableLiveData()

    val errorInvoked : MutableLiveData<Throwable> = MutableLiveData()

    // endregion


    val clickLogOut : MutableLiveData<Once<Unit>> = MutableLiveData()


    
    fun updateProfile( uri : Uri) {

        viewModelScope.launch {
            kotlin.runCatching {
                userRepository.updateProfile(uri)
            }.onSuccess {
                onProfileChangeCompleted.value = Once(uri)
            }.onFailure {
                errorInvoked.value = it
            }

        }

        
    }


    fun onClickLogOut() {

        clickLogOut.value = Once(Unit)
    }

    fun onClickTagDetail( position : Int) {
        clickTagDetailDialog.value = Once(position)
    }

    fun onClickEditProfile() {
        clickEditProfile.value = Once(Unit)
    }

    fun onClickRegisterClass() {
        clickRegisterClass.value = Once(Unit)
    }

}