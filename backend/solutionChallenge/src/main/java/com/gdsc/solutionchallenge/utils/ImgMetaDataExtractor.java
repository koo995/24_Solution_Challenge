package com.gdsc.solutionchallenge.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;
import com.gdsc.solutionchallenge.app.exception.NoLatLngException;
import com.google.type.LatLng;
import org.springframework.web.multipart.MultipartFile;

public class ImgMetaDataExtractor {

    public static LatLng extractLatLng(MultipartFile imageFile) throws Exception {
        Metadata metadata = ImageMetadataReader.readMetadata(imageFile.getInputStream());
        GpsDirectory gpsDir = metadata.getFirstDirectoryOfType(GpsDirectory.class);

        if (gpsDir == null) {
            throw new NoLatLngException();
        }
        Double latitude = gpsDir.getGeoLocation().getLatitude();
        Double longitude = gpsDir.getGeoLocation().getLongitude();
        if (latitude == null || longitude == null) {
            throw new NoLatLngException();
        }
        return LatLng.newBuilder()
                .setLatitude(latitude)
                .setLongitude(longitude).build();
    }

}
