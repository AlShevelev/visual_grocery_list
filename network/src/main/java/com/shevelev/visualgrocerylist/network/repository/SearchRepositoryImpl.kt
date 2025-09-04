package com.shevelev.visualgrocerylist.network.repository

import android.annotation.SuppressLint
import android.net.Uri
import com.shevelev.visualgrocerylist.network.api.SearchApi
import com.shevelev.visualgrocerylist.network.dto.ImageDto
import com.shevelev.visualgrocerylist.network.dto.ImageSizeDto
import com.shevelev.visualgrocerylist.network.dto.SearchResultDto
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPath
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory
import org.w3c.dom.Document
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import timber.log.Timber

internal class SearchRepositoryImpl(
    private val api: SearchApi,
) : SearchRepository {
    override suspend fun search(request: String): Result<SearchResultDto> {
        return runCatching {
            val resultXml = api.search(request).toString(Charsets.UTF_8)

            val xmlDoc = getXmlDoc(resultXml)

            val xPath = XPathFactory.newInstance().newXPath()

            val result = SearchResultDto(
                request = xmlDoc.getRequestValue(xPath),
                images = xmlDoc.getImageDocs(xPath)
            )

            result
        }.onFailure {
            Timber.e(it)
        }
    }

    private fun getXmlDoc(rawXml: String): Document {
        val dbFactory = DocumentBuilderFactory.newInstance()
        val dBuilder = dbFactory.newDocumentBuilder()

        return StringReader(rawXml).use { reader ->
            val xmlInput = InputSource(reader)
            dBuilder.parse(xmlInput)
        }
    }

    private fun Document.getRequestValue(xPath: XPath): String {
        val requestXpath = "/yandexsearch/request/query"
        return xPath.evaluate(requestXpath, this)
    }

    @SuppressLint("UseKtx")
    private fun Document.getImageDocs(xPath: XPath): List<ImageDto> {
        val result = mutableListOf<ImageDto>()

        val requestXpath = "/yandexsearch/response/results/grouping/group/doc/image-properties"
        val docs = xPath.evaluate(requestXpath, this, XPathConstants.NODESET) as NodeList

        for (i in 0 until docs.length) {
            val doc = docs.item(i)

            var id: String? = null

            var thumbnailLink: Uri? = null
            var thumbnailWidth: Int? = null
            var thumbnailHeight: Int? = null

            var originalWidth: Int? = null
            var originalHeight: Int? = null
            var imageLink: Uri?  = null

            var fileSize: Long? = null
            var mimeType: String? = null

            for (j in 0 until doc.childNodes.length) {
                val child = doc.childNodes.item(j)

                when (child.nodeName) {
                    "id" -> id = child.firstChild.nodeValue
                    "thumbnail-link" -> thumbnailLink = Uri
                        .parse(child.firstChild.nodeValue.replace("http://", "https://"))
                    "thumbnail-width" -> thumbnailWidth = child.firstChild.nodeValue.toInt()
                    "thumbnail-height" -> thumbnailHeight = child.firstChild.nodeValue.toInt()
                    "original-width" -> originalWidth = child.firstChild.nodeValue.toInt()
                    "original-height" -> originalHeight = child.firstChild.nodeValue.toInt()
                    "image-link" -> imageLink = Uri.parse(child.firstChild.nodeValue)
                    "file-size" -> fileSize = child.firstChild.nodeValue.toLong()
                    "mime-type" -> mimeType = child.firstChild.nodeValue
                }
            }

            result.add(
                ImageDto(
                    id = requireNotNull(id),

                    thumbnailLink = requireNotNull(thumbnailLink),
                    imageLink = requireNotNull(imageLink),
                    fileSize = requireNotNull(fileSize),
                    mimeType = requireNotNull(mimeType),

                    thumbnailSize = ImageSizeDto(
                        width = requireNotNull(thumbnailWidth),
                        height = requireNotNull(thumbnailHeight),
                    ),
                    originalSize = ImageSizeDto(
                        width = requireNotNull(originalWidth),
                        height = requireNotNull(originalHeight),
                    ),
                )
            )
        }

        return result
    }
}