package com.gdsc.solutionchallenge.app.dto.request;

import com.gdsc.solutionchallenge.ai.dto.InferPredictedResult;
import com.gdsc.solutionchallenge.file.dto.FileStoreInfo;
import com.gdsc.solutionchallenge.member.domain.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Getter
@Setter
public class UserImageRequest {

    private MultipartFile file;

    public UserImageServiceRequest toServiceRequest(InferPredictedResult prediction, FileStoreInfo fileInfo, Member member) {
        return new UserImageServiceRequest(file, prediction, fileInfo, member);
    }
}
