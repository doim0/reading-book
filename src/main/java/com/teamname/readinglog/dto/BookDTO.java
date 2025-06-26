package com.teamname.readinglog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private String title;
    private String author;
    private String publisher;
    private String language;
    private int totalPages;
}
