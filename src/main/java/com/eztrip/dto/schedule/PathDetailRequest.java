package com.eztrip.dto.schedule;

public class PathDetailRequest {

    private String segmentCode;  // 경로 세그먼트 (A1, B2 등)
    private String placeName;    // 장소 이름
    private String address;      // 장소 주소
    private int price;           // 금액
    private String segmentType;  // 세그먼트 타입 (음식, 숙박 등)

    // 기본 생성자 및 생성자
    public PathDetailRequest() {}

    public PathDetailRequest(String segmentCode, String placeName, String address, int price, String segmentType) {
        this.segmentCode = segmentCode;
        this.placeName = placeName;
        this.address = address;
        this.price = price;
        this.segmentType = segmentType;
    }

    // Getter와 Setter
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