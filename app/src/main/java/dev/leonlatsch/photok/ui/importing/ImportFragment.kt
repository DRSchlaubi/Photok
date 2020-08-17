package dev.leonlatsch.photok.ui.importing

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.leonlatsch.photok.R
import dev.leonlatsch.photok.databinding.FragmentImportBinding
import dev.leonlatsch.photok.ui.gallery.GalleryFragment

@AndroidEntryPoint
class ImportFragment : Fragment() {

    private val viewModel: ImportViewModel by viewModels()
    private val SELECT_IMAGES = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentImportBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_import, container, false)
        binding.viewModel = viewModel
        binding.importClickListener = importClickListener
        binding.lifecycleOwner = this
        return binding.root
    }

    private val importClickListener = View.OnClickListener {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_IMAGES && resultCode == Activity.RESULT_OK) {
            val images = mutableListOf<Uri>()
            if (data != null) {
                if (data.clipData != null) {
                    val count = data.clipData!!.itemCount
                    for (i in 0 until count) {
                        val imageUri = data.clipData!!.getItemAt(i).uri
                        images.add(imageUri)
                    }
                } else if (data.data != null) {
                    val imageUri = data.data!!
                    images.add(imageUri)
                }
            }
            viewModel.importImages(images)
        }
    }
}