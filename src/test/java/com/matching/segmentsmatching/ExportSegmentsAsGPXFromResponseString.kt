package com.matching.segmentsmatching

import com.matching.segmentsmatching.services.XMLNomiResponseParser
import org.junit.Test
import java.io.File
import java.lang.StringBuilder
import java.nio.charset.StandardCharsets

class ReadSegmentsFromResponseStringSaveThemAsGPX{

    val response = "/home/radim/IdeaProjects/segments-matching/samples/sampleSegmentsString.xml"
    val saveGPX = "/home/radim/IdeaProjects/segments-matching/samples/sampleSegmentsAsGPX/"
    val pre = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>\n" +
            "<gpx version=\"1.1\" creator=\"Locus Map, Android\"\n" +
            " xmlns=\"http://www.topografix.com/GPX/1/1\"\n" +
            " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
            " xsi:schemaLocation=\"http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd\"\n" +
            " xmlns:gpx_style=\"http://www.topografix.com/GPX/gpx_style/0/2\"\n" +
            " xmlns:gpxx=\"http://www.garmin.com/xmlschemas/GpxExtensions/v3\"\n" +
            " xmlns:gpxtrkx=\"http://www.garmin.com/xmlschemas/TrackStatsExtension/v1\"\n" +
            " xmlns:gpxtpx=\"http://www.garmin.com/xmlschemas/TrackPointExtension/v2\"\n" +
            " xmlns:locus=\"http://www.locusmap.eu\">\n" +
            "\t<metadata>\n" +
            "\t\t<desc>File with points/tracks from Locus Map/3.39.4</desc>\n" +
            "\t</metadata>\n" +
            "<trk>\n"
    val post = "</trk>\n" +
            "</gpx>"

    @Test
    fun readSegmentsSaveAsGPX(){
        var count = 1
        XMLNomiResponseParser()
                .parseXMLNomiResponse(File(response).readText(StandardCharsets.UTF_8))
                .forEach { segment ->
                    val builder = StringBuilder()
                    with(builder) {
                        append(pre)
                        append("<name>${segment.name}</name>\n")
                        append("<trkseg>\n")
                        segment.locations.forEach {location ->
                            append("<trkpt lat=\"${location.lat}\" lon=\"${location.lon}\">\n" +
                                    "</trkpt>\n")
                        }
                        append("</trkseg>\n")
                        append(post)
                    }
                    File(saveGPX + "segment$count.gpx").writeText(builder.toString(),StandardCharsets.UTF_8)
                    count++
                }
    }
}