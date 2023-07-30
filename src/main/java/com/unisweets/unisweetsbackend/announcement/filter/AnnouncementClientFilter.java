package com.unisweets.unisweetsbackend.announcement.filter;

import lombok.Data;

@Data
public class AnnouncementClientFilter {
    private Boolean waiting;
    private Boolean inProgress;
    private Boolean delivery;
    private Boolean closed;
}
