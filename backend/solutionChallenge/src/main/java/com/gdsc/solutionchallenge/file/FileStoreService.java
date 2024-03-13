package com.gdsc.solutionchallenge.file;

import com.gdsc.solutionchallenge.app.exception.NoLatLngException;
import com.gdsc.solutionchallenge.file.dto.FileStoreInfo;
import com.gdsc.solutionchallenge.file.exception.FileSaveException;
import com.gdsc.solutionchallenge.utils.ImgMetaDataExtractor;
import com.google.type.LatLng;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class FileStoreService {

    @Value("/Users/keonhongkoo/Desktop/solution_challenge/file/")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public String storeFile(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        String fullPath = getFullPath(storeFileName);
        try {
            multipartFile.transferTo(new File(fullPath));
        } catch (IOException e) {
            throw new FileSaveException();
        }
        return fullPath;
    }

    public FileStoreInfo extractFileInfoAndSave(MultipartFile file) {
        // 메타데이터 추출.
        LatLng latLng;
        try {
            latLng = ImgMetaDataExtractor.extractLatLng(file);
        } catch (Exception e) {
            throw new NoLatLngException();
        }
        String fullPath = storeFile(file);
        return new FileStoreInfo(latLng, fullPath);
    }


    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
