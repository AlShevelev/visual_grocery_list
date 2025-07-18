package com.shevelev.visualgrocerylist.network.repository

import com.shevelev.visualgrocerylist.network.dto.ImageDto
import com.shevelev.visualgrocerylist.network.dto.ImageSizeDto
import com.shevelev.visualgrocerylist.network.dto.SearchResultDto
import kotlin.String
import androidx.core.net.toUri

internal class DebugSearchRepositoryImpl: SearchRepository {
    override suspend fun search(request: String): Result<SearchResultDto> {
        val image = ImageDto(
            id = "1",
            thumbnailLink =
                "https://avatars.mds.yandex.net/i?id=9a4ceb6b4594326db6f6795312930977db9d4329-9065868-images-thumbs".toUri(),
            imageLink = "https://avatars.mds.yandex.net/i?id=9a4ceb6b4594326db6f6795312930977db9d4329-9065868-images-thumbs".toUri(),
            fileSize = 0,
            mimeType = "",
            thumbnailSize = ImageSizeDto(width = 0, height = 0),
            originalSize = ImageSizeDto(width = 0, height = 0),
        )
        
        return Result.success(
            SearchResultDto(
                request = "",
                images = listOf(
                    image.copy(id = "ljdflads"),
                    image.copy(id = "reopui4er"),
                    image.copy(id = "fl;l;dfds"),
                    image.copy(id = "38278eq87"),
                    image.copy(id = "[rtofsjiores"),
                    image.copy(id = "oewdkklraew"),
                    image.copy(id = "reclkdflkasl"),
                    image.copy(id = "reodiack.jfa"),
                )
            )
        )
    }
}