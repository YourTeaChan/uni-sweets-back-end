package com.unisweets.unisweetsbackend.announcement.filter;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskFilter {
    private Boolean waiting;
    private Boolean inProgress;
    private Boolean done;
    private Boolean closed;
}
