package com.devsplan.ketchup.rsc.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class RscDTO {
    private int rscNo;                  // 자원 번호
    private String rscCategory;         // 자원 카테고리(회의실 / 차량)
    private String rscName;             // 자원 이름(회의실 이름 / 차종)
    private String rscInfo;             // 자원 정보(회의실 위치 / 차량 번호)
    private int rscCap;                 // 인원(수용 가능 인원 / 탑승 가능 인원)
    private boolean rscIsAvailable;     // 사용 가능 여부
    private String rscDescr;            // 비고
}