package com.eztrip.dto.schedule;

import com.eztrip.entity.schedule.Schedule;
import lombok.Data;

@Data
public class ScheduleDto {
    private Long id;
    private String title;
    private String path;
    private String image;
    private int price;

    public ScheduleDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.path = schedule.getPath();
        this.image = schedule.getImage();
        this.price = schedule.getPrice();
    }
}