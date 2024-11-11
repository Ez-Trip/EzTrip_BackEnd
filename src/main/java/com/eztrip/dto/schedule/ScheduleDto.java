package com.eztrip.dto.schedule;

import com.eztrip.entity.schedule.PathDetail;
import com.eztrip.entity.schedule.Schedule;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ScheduleDto {
    private Long id;
    private String title;
    private String path;
    private String image;
    private int price;
    private List<PathDetailDto> pathDetails;  // PathDetail을 포함한 리스트

    public ScheduleDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.path = schedule.getPath();
        this.image = schedule.getImage();
        this.price = schedule.getPrice();
        // PathDetail을 PathDetailDto로 변환하여 리스트로 담기
        this.pathDetails = schedule.getPathDetails().stream()
                .map(PathDetailDto::new)  // PathDetailDto 객체로 변환
                .collect(Collectors.toList());
    }

    // PathDetail DTO
    @Data
    public static class PathDetailDto {
        private String segmentCode;
        private String placeName;
        private String address;
        private int price;
        private String segmentType;

        public PathDetailDto(PathDetail pathDetail) {
            this.segmentCode = pathDetail.getSegmentCode();
            this.placeName = pathDetail.getPlaceName();
            this.address = pathDetail.getAddress();
            this.price = pathDetail.getPrice();
            this.segmentType = pathDetail.getSegmentType();
        }
    }
}