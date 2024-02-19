package com.gdsc.solutionchallenge.file;

import com.gdsc.solutionchallenge.file.exception.FileSaveException;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class FileStore {

    private final Storage storage;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    @Value("https://storage.googleapis.com/")
    private String fileDir;

    public String storeFile(MultipartFile multipartFile) {
        try {
            // 이미지 업로드
            String uuid = UUID.randomUUID().toString();
            BlobInfo blob = storage.create(
                    BlobInfo.newBuilder(bucketName, uuid)
                            .setContentType(multipartFile.getContentType()).build(),
                    multipartFile.getInputStream()
            );
            log.info("blob={}", blob);
            return fileDir + blob.getBucket() + "/" + blob.getName();
        } catch (IOException e) {
            throw new FileSaveException();
        }
    }
}
