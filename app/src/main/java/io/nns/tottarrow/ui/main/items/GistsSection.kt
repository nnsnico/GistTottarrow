package io.nns.tottarrow.ui.main.items

import com.xwray.groupie.Section
import io.nns.tottarrow.domain.response.Gist

class GistsSection : Section() {
    fun updateGists(
        gists: List<Gist>
    ) {
        val gistsItem: List<GistItem> = gists.map { GistItem(it) }
        update(gistsItem)
    }
}
