# ECO SPOT - 24_Solution_Challenge

2024 GDSC Solution Challenge

## What is problem?
- Conserving ecological diversity is curcial, in what ways can we actively contribute to biodiversity conservation and ensure its preservation?
- we created ecological map using AI to increase accuracy and provide missions to attract people's participation
## 2. Feature
- Ecological Map
  - Develop a comprehensive scological map to visualize and understand the distribution and habitas of various of species.
- AI
  - Utilize advanced AI technology to accurately identify and classify flora and fauna, enhancing the precision of species data.
- Challenge
  - Introduce interactive challenges and missions to captivate user interest, encouraging active participation and contribution to biodiversity conservation.

## Stack: Spring Boot, Spring Data JPA, QueryDSL, MySQL, GCP(Google Cloud Platform), Firebase

## 2024.01 ~ 2024.02
본 프로젝트는 4명의 팀원이 함께 진행했습니다. 저는 백엔드를 전담했으며, 클라이언트는 안드로이드 앱으로 구현되었습니다.

## 프로젝트 소개
AI 서비스 Gemini를 활용해 생태 지도를 만들어가는 애플리케이션 서비스입니다.

사용자가 특정 생물을 촬영하면 Gemini가 해당 생물을 식별하여 명칭을 알려줍니다. 사용자는 촬영한 생물들로 개인 도감을 만들고, 다른 사용자들이 저장한 생물들의 위치를 지도에서 추적할 수 있습니다. 또한 특정 생물 촬영 미션을 만들고 서로의 미션을 달성하면서 즐겁게 생태지도를 더욱 풍성하게 만들어갈 수 있습니다.

## 성과
프로젝트를 진행하며 여러 문제를 해결하는 과정에서 특히 기억에 남는 것은 스프링의 트랜잭션 처리 방법과 DB의 트랜잭션 처리 방법에 대한 이해가 깊어진 점입니다. 프로젝트 초기에는 기본기 부족으로 제대로 이해하지 못하고 잘못된 방식으로 문제를 해결했습니다. 그러나 몇 개월 후 다시 문제를 해결하는 과정에서 더 근본적인 문제점들을 발견하게 되었고, 이를 통해 실질적인 성장을 경험했습니다.

## Architecture
<img width="500" alt="arch" src="https://github.com/user-attachments/assets/57a740c1-78c3-4492-b314-ab67c9c0063f">

## About
매주 오프라인 미팅을 통해 4명의 팀원이 프로젝트 진행 상황과 의견을 공유했습니다. 초기에는 온라인으로 미팅을 진행했으나 의사소통에 어려움이 많아 오프라인 미팅으로 전환했습니다. 그러나 팀원들 간 구현 역량과 프로젝트 목표에 차이가 있어 각자의 현재 상황을 파악하고 공유할 필요성을 느꼈습니다. 이에 팀원들과 솔직한 대화를 나누어 각자의 강점과 약점을 공유했고, 이를 바탕으로 팀원들의 역할을 효과적으로 분담했습니다. 저는 이 과정에서 백엔드를 전담하게 되었습니다.

GCP를 활용해 간단한 인프라를 구성했습니다. 팀원들마다 선호하는 기술 스택과 아키텍처가 달랐지만, 시간적 제약으로 인해 제가 인프라 구성을 전담하게 되었습니다. 요구사항을 효율적으로 충족할 수 있는 심플한 아키텍처를 설계했고, 이를 통해 의견 충돌을 최소화하며 프로젝트를 신속하게 진행할 수 있었습니다.

## ERD
<img width=500 src="https://github.com/koo995/algorithm_note/assets/107671886/3ccd1b51-6222-47c4-8e02-e77c20b95112">

## YOUTUBE VIDEO
[click](https://www.youtube.com/watch?v=OBB-oj9h3Ew)
