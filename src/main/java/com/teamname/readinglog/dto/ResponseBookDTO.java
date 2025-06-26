package com.teamname.readinglog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBookDTO {
    private int id;
    private String title;
    private String author;
    private String publisher;
    private String language;
    private int currentPage;
    private int totalPages;
    private String startTime;
    private String endTime;
}
