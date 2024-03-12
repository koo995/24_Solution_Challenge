package com.gdsc.solutionchallenge.app.dto.request;

import com.gdsc.solutionchallenge.ai.dto.InferPredictedResult;
import com.gdsc.solutionchallenge.file.dto.FileStoreInfo;
import com.gdsc.solutionchallenge.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

// todo 레이어간에 의존성을 줄이기 위함이였는데... dto간에는 의존성이 생긴것 같다. 이거 관찮나
@Getter
@AllArgsConstructor
public class UserImageServiceRequest {

    private MultipartFile file;

    private InferPredictedResult prediction;

    private FileStoreInfo fileStoreInfo;

    private Member loginMember;
}
