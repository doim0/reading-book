package com.teamname.readinglog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "BOOK")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
