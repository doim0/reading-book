package com.teamname.readinglog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamname.readinglog.dto.BookDTO;
import com.teamname.readinglog.dto.ResponseBookDTO;
import com.teamname.readinglog.entity.BookEntity;
import com.teamname.readinglog.exception.BookNotFoundException;
import com.teamname.readinglog.exception.InvalidPageException;
import com.teamname.readinglog.mapper.BookMapper;
import com.teamname.readinglog.repository.BookRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {
    
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Transactional
    public ResponseBookDTO registerBook(BookDTO bookDTO) {
        try {
            BookEntity bookEntity = bookMapper.toEntity(bookDTO);
            BookEntity saved = bookRepository.save(bookEntity);
            log.info("Book registered successfully with ID: {}", saved.getId());
            return bookMapper.toResponseDTO(saved);
        } catch (Exception e) {
            log.error("Failed to register book: {}", e.getMessage());
            throw new RuntimeException("Failed to register book", e);
        }
    }

    @Transactional(readOnly = true)
    public ResponseBookDTO getBookById(int id) {
        return bookRepository.findById(id)
                .map(bookMapper::toResponseDTO)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<ResponseBookDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ResponseBookDTO updateBook(int id, BookDTO bookDTO) {
        BookEntity entity = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
        
        bookMapper.updateEntityFromDTO(entity, bookDTO);
        BookEntity saved = bookRepository.save(entity);
        log.info("Book updated successfully with ID: {}", id);
        return bookMapper.toResponseDTO(saved);
    }
    
    @Transactional
    public void deleteBook(int id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
        log.info("Book deleted successfully with ID: {}", id);
    }
    
    @Transactional
    public ResponseBookDTO updateCurrentPage(int id, int currentPage) {
        BookEntity entity = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
        
        // 페이지 유효성 검증
        validatePageNumber(entity, currentPage);
        
        entity.setCurrentPage(currentPage);
        
        // 읽기 완료 처리
        if (currentPage >= entity.getTotalPages() && entity.getEndTime() == null) {
            entity.setEndTime(java.time.LocalDateTime.now()
                    .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            log.info("Book reading completed for ID: {}", id);
        }
        
        BookEntity saved = bookRepository.save(entity);
        log.info("Current page updated to {} for book ID: {}", currentPage, id);
        return bookMapper.toResponseDTO(saved);
    }
    
    private void validatePageNumber(BookEntity entity, int newPage) {
        // 페이지가 음수인 경우
        if (newPage < 0) {
            throw new InvalidPageException("Page number cannot be negative");
        }
        
        // 총 페이지를 초과하는 경우
        if (newPage > entity.getTotalPages()) {
            throw new InvalidPageException(
                String.format("Page number %d exceeds total pages %d", newPage, entity.getTotalPages())
            );
        }
        
        // 현재 페이지보다 적은 경우 (뒤로 갈 수 없음)
        if (newPage < entity.getCurrentPage()) {
            throw new InvalidPageException(
                String.format("Cannot go back from page %d to page %d", entity.getCurrentPage(), newPage)
            );
        }
    }
    
    @Transactional(readOnly = true)
    public List<ResponseBookDTO> getCompletedBooks() {
        return bookRepository.findAll()
                .stream()
                .filter(entity -> entity.getEndTime() != null)
                .map(bookMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ResponseBookDTO> getReadingBooks() {
        return bookRepository.findAll()
                .stream()
                .filter(entity -> entity.getEndTime() == null && entity.getCurrentPage() > 0)
                .map(bookMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
