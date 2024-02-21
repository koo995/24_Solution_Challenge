package com.gdsc.solutionchallenge.mission.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MissionCreateDto {

    private String title;

    private String description;

    private Long speciesId;

    private Long imageId;
}
    // 특정 이미지의 정보에서 다 가져오는 거니까... 이미지와 사람에 대한 정보를 받을 필요가 있을까?
    // 그리고 현재 보고있는 이미지의 사용자가 본인과 같아야 한다는 것도 체크해야한다.
    // 이미지 아이디만 넘기면 서버에서 이미지를 찾은 다음 만들어주는 것으로 하자.
