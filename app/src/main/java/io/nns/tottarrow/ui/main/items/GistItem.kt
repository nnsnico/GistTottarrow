package io.nns.tottarrow.ui.main.items

import com.xwray.groupie.databinding.BindableItem
import io.nns.tottarrow.R
import io.nns.tottarrow.databinding.ItemGistBinding
import io.nns.tottarrow.domain.response.Gist
import io.nns.tottarrow.domain.response.toFileList

data class GistItem(private val gist: Gist) : BindableItem<ItemGistBinding>() {

    override fun getLayout(): Int = R.layout.item_gist

    override fun bind(viewBinding: ItemGistBinding, position: Int) {
        viewBinding.gist = gist
        viewBinding.file = gist.files.toFileList().first()
    }
}