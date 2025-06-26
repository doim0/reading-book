package com.teamname.readinglog.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.teamname.readinglog.dto.BookDTO;
import com.teamname.readinglog.dto.ResponseBookDTO;
import com.teamname.readinglog.entity.BookEntity;

@Component
public class BookMapper {
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public BookEntity toEntity(BookDTO bookDTO) {
        BookEntity entity = new BookEntity();
        entity.setTitle(bookDTO.getTitle());
        entity.setAuthor(bookDTO.getAuthor());
        entity.setPublisher(bookDTO.getPublisher());
        entity.setLanguage(bookDTO.getLanguage());
        entity.setTotalPages(bookDTO.getTotalPages());
        entity.setCurrentPage(0);
        entity.setStartTime(LocalDateTime.now().format(FORMATTER));
        return entity;
    }
    
    public ResponseBookDTO toResponseDTO(BookEntity entity) {
        ResponseBookDTO dto = new ResponseBookDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setAuthor(entity.getAuthor());
        dto.setPublisher(entity.getPublisher());
        dto.setLanguage(entity.getLanguage());
        dto.setTotalPages(entity.getTotalPages());
        dto.setCurrentPage(entity.getCurrentPage());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        return dto;
    }
    
    public void updateEntityFromDTO(BookEntity entity, BookDTO bookDTO) {
        entity.setTitle(bookDTO.getTitle());
        entity.setAuthor(bookDTO.getAuthor());
        entity.setPublisher(bookDTO.getPublisher());
        entity.setLanguage(bookDTO.getLanguage());
        entity.setTotalPages(bookDTO.getTotalPages());
        entity.setCurrentPage(0);
        entity.setStartTime(LocalDateTime.now().format(FORMATTER));
        entity.setEndTime(null);
    }
}
