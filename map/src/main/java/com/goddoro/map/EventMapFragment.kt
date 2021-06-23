package com.goddoro.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.goddoro.common.common.debugE
import com.goddoro.common.common.observeOnce
import com.goddoro.common.data.model.NaverItem
import com.goddoro.map.databinding.FragmentEventMapBinding
import com.goddoro.map.dialog.MapDetailDialog
import com.naver.maps.map.*
import dagger.android.support.DaggerFragment
import ted.gun0912.clustering.naver.TedNaverClustering
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.MarkerIcons
import de.hdodenhof.circleimageview.CircleImageView
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * created By DORO 2020/09/12
 */

class EventMapFragment : Fragment(), OnMapReadyCallback {

    private val TAG = EventMapFragment::class.java.simpleName
    /**
     * Binding Instance
     */
    private lateinit var  mBinding: FragmentEventMapBinding


    /**
     * ViewModel Instance
     */

    private val mViewModel: EventMapViewModel by viewModel()

    lateinit var naverMap: NaverMap

    lateinit var tedNaverClustering: TedNaverClustering<NaverItem>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentEventMapBinding.inflate(inflater, container, false).also { mBinding = it}.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner

        initSetting()
        observeViewModel()

    }

    private fun initSetting() {

        mBinding.mapView.getMapAsync(this)

    }

    private fun observeViewModel() {

        mViewModel.apply {

            events.observe(viewLifecycleOwner,Observer {

                if (it.isNotEmpty()) {

                    val mapItems : List<NaverItem> = it.map{ event ->
                        NaverItem(lat = event.latitude ?: 0.0, lng = event.longitude ?: 0.0)
                    }

                    debugE(TAG, mapItems)

                    TedNaverClustering.with<NaverItem>(requireContext(), naverMap)
                        .items(mapItems)
                        .markerClickListener {
                            MapDetailDialog.show(requireActivity().supportFragmentManager, it)
                        }
                        .make()


                }
            })


            errorInvoked.observe(viewLifecycleOwner, Observer {
                debugE(TAG,it)
            })
        }
    }


    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.moveCamera(
            CameraUpdate.toCameraPosition(
                CameraPosition(
                    NaverMap.DEFAULT_CAMERA_POSITION.target,
                    NaverMap.DEFAULT_CAMERA_POSITION.zoom
                )
            )
        )

        mViewModel.listEvents()

    }


    companion object {

        fun newInstance() = EventMapFragment()
    }

}