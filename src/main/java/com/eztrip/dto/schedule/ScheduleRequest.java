package com.eztrip.dto.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public class ScheduleRequest {
    private String title;       // 스케줄 제목
    private String path;        // 경로 (장소나 목적지)

    @JsonFormat(pattern = "yyyy-MM-dd")  // 날짜 포맷 지정
    private LocalDate date;     // 날짜

    private String image;       // 이미지 경로 (URL)
    private Long memberId;      // 사용자 ID (memberId)
    private int price;          // 총 금액
    private List<PathDetailRequest> pathDetails;  // 상세 경로 정보

    // getter와 setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<PathDetailRequest> getPathDetails() {
        return pathDetails;
    }

    public void setPathDetails(List<PathDetailRequest> pathDetails) {
        this.pathDetails = pathDetails;
    }

    // 내부 클래스 PathDetailRequest 정의
    public static class PathDetailRequest {
        private String segmentCode;  // 경로 세그먼트 코드 (A1, B2 등)
        private String placeName;    // 장소 이름
        private String address;      // 주소
        private int price;           // 가격
        private String segmentType;  // 세그먼트 유형 (예: 음식점, 숙박업소 등)

        // getter와 setter
        public String getSegmentCode() {
            return segmentCode;
        }

        public void setSegmentCode(String segmentCode) {
            this.segmentCode = segmentCode;
        }

        public String getPlaceName() {
            return placeName;
        }

        public void setPlaceName(String placeName) {
            this.placeName = placeName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getSegmentType() {
            return segmentType;
        }

        public void setSegmentType(String segmentType) {
            this.segmentType = segmentType;
        }
    }
}