package lt.techin.booksBackend.controller;

import lt.techin.booksBackend.model.Book;
import lt.techin.booksBackend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class BookController {

  private final BookService bookService;

  @Autowired
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping("/books")
  public ResponseEntity<List<Book>> getBooks() {
    return ResponseEntity.ok(bookService.findAllBooks());
  }

  @GetMapping("/books/{id}")
  public ResponseEntity<?> getBook(@PathVariable long id) {

    Optional<Book> foundMovie = bookService.findBookById(id);

    if (foundMovie.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(foundMovie.get());

  }

  @PostMapping("/books")
  public ResponseEntity<?> saveBook(@RequestBody Book book) {

    if (book.getTitle() == null || book.getTitle().isEmpty()) {
      return ResponseEntity.badRequest().body("Book title field can't be empty");
    }

    if (book.getTitle().length() < 3 || book.getTitle().length() > 100) {
      return ResponseEntity.badRequest().body("Book title length should be 3-100 characters long");
    }

    if (book.getAuthor() == null || book.getAuthor().isEmpty()) {
      return ResponseEntity.badRequest().body("Book author field can't be empty");
    }

    if (book.getCategory() == null || book.getCategory().isEmpty()) {
      return ResponseEntity.badRequest().body("Book category field can't be empty");
    }

    if (book.getPrice() == null) {
      return ResponseEntity.badRequest().body("Book price field can't be empty");
    }

    if (book.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
      return ResponseEntity.badRequest().body("Book price should be larger than 0");
    }

    if (book.getCover() == null || book.getCover().isEmpty()) {
      return ResponseEntity.badRequest().body("Book cover field can't be empty");
    }

    if (bookService.existsBookByTitle(book.getTitle())
            && bookService.existsBookByAuthor(book.getAuthor())) {
      return ResponseEntity.badRequest().body("A book with such title and author already exists");
    }

    Book savedBook = bookService.saveBook(book);

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedBook.getId())
                    .toUri())
            .body(savedBook);
  }

  @PutMapping("/books/{id}")
  public ResponseEntity<?> updateBook(@PathVariable long id, @RequestBody Book book) {

    if (book.getTitle() == null || book.getTitle().isEmpty()) {
      return ResponseEntity.badRequest().body("Book title field can't be empty");
    }

    if (book.getTitle().length() < 3 || book.getTitle().length() > 100) {
      return ResponseEntity.badRequest().body("Book title length should be 3-100 characters long");
    }

    if (book.getAuthor() == null || book.getAuthor().isEmpty()) {
      return ResponseEntity.badRequest().body("Book author field can't be empty");
    }

    if (book.getCategory() == null || book.getCategory().isEmpty()) {
      return ResponseEntity.badRequest().body("Book category field can't be empty");
    }

    if (book.getPrice() == null) {
      return ResponseEntity.badRequest().body("Book price field can't be empty");
    }

    if (book.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
      return ResponseEntity.badRequest().body("Book price should be larger than 0");
    }

    if (book.getCover() == null || book.getCover().isEmpty()) {
      return ResponseEntity.badRequest().body("Book cover field can't be empty");
    }

    if (bookService.existsBookByTitle(book.getTitle())
            && bookService.existsBookByAuthor(book.getAuthor())) {
      return ResponseEntity.badRequest().body("A book with such title and author already exists");
    }

    if (bookService.existsBookById(id)) {
      Book bookToUpdate = bookService.findBookById(id).get();
      bookToUpdate.setTitle(book.getTitle());
      bookToUpdate.setAuthor(book.getAuthor());
      bookToUpdate.setCategory(book.getCategory());
      bookToUpdate.setPrice(book.getPrice());
      bookToUpdate.setCover(book.getCover());
      return ResponseEntity.ok(bookService.saveBook(bookToUpdate));
    }

    Book savedBook = bookService.saveBook(book);

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                    .replacePath("/api/books/{id}")
                    .buildAndExpand(savedBook.getId())
                    .toUri())
            .body(savedBook);
  }

  @DeleteMapping("/books/{id}")
  public ResponseEntity<Void> deleteBook(@PathVariable long id) {

    if (!bookService.existsBookById(id)) {
      return ResponseEntity.notFound().build();
    }

    bookService.deleteBookById(id);
    return ResponseEntity.noContent().build();
  }
}
