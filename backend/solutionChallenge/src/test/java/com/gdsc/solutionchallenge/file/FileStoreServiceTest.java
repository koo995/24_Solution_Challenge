package com.gdsc.solutionchallenge.file;

import com.gdsc.solutionchallenge.IntegrationTestSupport;
import com.gdsc.solutionchallenge.app.exception.NoLatLngException;
import com.gdsc.solutionchallenge.file.dto.FileStoreInfo;
import com.google.type.LatLng;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

import static org.assertj.core.api.Assertions.assertThatObject;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FileStoreServiceTest extends IntegrationTestSupport {

    @Autowired
    private FileStoreService fileStoreService;

    @DisplayName("파일에서 위치 경도를 추출하고 저장한다.")
    @Test
    void extractFileInfoAndSave() throws Exception {
        // given
        String fileName = "lion.jpeg";
        MultipartFile file = new MockMultipartFile("file", fileName, "image/jpeg", new FileInputStream("/Users/keonhongkoo/Downloads/"+fileName));

        // when
        FileStoreInfo fileStoreInfo = fileStoreService.extractFileInfoAndSave(file);

        //then
        assertThatObject(fileStoreInfo.getLatLng()).isInstanceOf(LatLng.class);
        assertThatObject(fileStoreInfo.getFullPath()).isInstanceOf(String.class);
    }

    @DisplayName("위치정보는 반드시 있어야 한다.")
    @Test
    void latlng() throws Exception {
        // given
        String fileName = "panda.jpg";
        MultipartFile file = new MockMultipartFile("file", fileName, "image/jpeg", new FileInputStream("/Users/keonhongkoo/Downloads/"+fileName));

        // then
        assertThatThrownBy(() -> {
            fileStoreService.extractFileInfoAndSave(file);
        }).isInstanceOf(NoLatLngException.class);
    }
}