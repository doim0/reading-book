package com.teamname.readinglog.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamname.readinglog.dto.BookDTO;
import com.teamname.readinglog.dto.PageUpdateDTO;
import com.teamname.readinglog.dto.ResponseBookDTO;
import com.teamname.readinglog.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(name = "Book API", description = "독서 기록 관리 API")
public class BookController {
    
    private final BookService bookService;

    @Operation(summary = "책 등록", description = "책을 등록합니다.")
    @PostMapping
    public ResponseEntity<ResponseBookDTO> registerBook(@RequestBody BookDTO bookDTO) {
        ResponseBookDTO response = bookService.registerBook(bookDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "책 조회", description = "고유 아이디로 책을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseBookDTO> getBook(@PathVariable int id) {
        ResponseBookDTO response = bookService.getBookById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "모든 책 조회", description = "모든 책을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<ResponseBookDTO>> getAllBooks() {
        List<ResponseBookDTO> response = bookService.getAllBooks();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "책 변경", description = "책의 정보를 조회합니다.(읽은 기록이 모두 초기화 됩니다.)")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseBookDTO> updateBook(@PathVariable int id, @RequestBody BookDTO bookDTO) {
        ResponseBookDTO response = bookService.updateBook(id, bookDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "책 삭제", description = "고유 아이디로 책을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "책 읽기", description = "고유아이디로 입력한 페이지만큼 책을 읽을 수 있습니다.(페이지 값은 현재의 currentPage보다 커야하고 totalPage를 넘을 수 없습니다.")
    @PatchMapping("/{id}/page")
    public ResponseEntity<ResponseBookDTO> updateCurrentPage(@PathVariable int id, @RequestBody PageUpdateDTO pageUpdate) {
        ResponseBookDTO response = bookService.updateCurrentPage(id, pageUpdate.getCurrentPage());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "읽은 책 조회", description = "전부 읽은 모든 책을 조회합니다.(currentPage == totalPage 인 책)")
    @GetMapping("/completed")
    public ResponseEntity<List<ResponseBookDTO>> getCompletedBooks() {
        List<ResponseBookDTO> response = bookService.getCompletedBooks();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "읽고 있는 책 조회", description = "읽고 있는 모든 책을 조회합니다.(current page > 0 이고 currentPage == totalPage 가 아닌 책)")
    @GetMapping("/reading")
    public ResponseEntity<List<ResponseBookDTO>> getReadingBooks() {
        List<ResponseBookDTO> response = bookService.getReadingBooks();
        return ResponseEntity.ok(response);
    }
}
