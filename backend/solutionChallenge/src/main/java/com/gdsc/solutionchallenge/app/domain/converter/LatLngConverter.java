package com.gdsc.solutionchallenge.app.domain.converter;

import com.google.type.LatLng;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class LatLngConverter implements AttributeConverter<LatLng, String> {

    @Override
    public String convertToDatabaseColumn(LatLng latLng) {
        if (latLng == null) {
            return null;
        }
        // LatLng 객체를 "위도,경도" 형태의 문자열로 변환
        return latLng.getLatitude() + "," + latLng.getLongitude();
    }

    @Override
    public LatLng convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        // 문자열을 파싱하여 LatLng 객체로 변환
        String[] parts = dbData.split(",");
        double latitude = Double.parseDouble(parts[0]);
        double longitude = Double.parseDouble(parts[1]);
        return LatLng.newBuilder()
                .setLatitude(latitude)
                .setLongitude(longitude)
                .build();
    }
}
