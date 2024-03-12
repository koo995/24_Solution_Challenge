package com.gdsc.solutionchallenge.file.dto;

import com.google.type.LatLng;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileStoreInfo {

    private LatLng latLng;

    private String fullPath;
}
