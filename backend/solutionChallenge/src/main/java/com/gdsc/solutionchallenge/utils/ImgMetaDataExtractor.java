package com.gdsc.solutionchallenge.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;
import com.google.type.LatLng;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ImgMetaDataExtractor {

    public static LatLng extractLatLng(MultipartFile imageFile) throws Exception {
        Metadata metadata = ImageMetadataReader.readMetadata(imageFile.getInputStream());
        GpsDirectory gpsDir = metadata.getFirstDirectoryOfType(GpsDirectory.class);

        if (gpsDir != null) {
            Double latitude = gpsDir.getGeoLocation().getLatitude();
            Double longitude = gpsDir.getGeoLocation().getLongitude();
            return LatLng.newBuilder()
                    .setLatitude(latitude)
                    .setLongitude(longitude).build();
        }
        return null; // todo 여기도 예외처리를 해줘야 할듯.
    }

}
