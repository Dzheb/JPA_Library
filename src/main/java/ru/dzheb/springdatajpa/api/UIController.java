package ru.dzheb.springdatajpa.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.dzheb.springdatajpa.service.BookService;
import ru.dzheb.springdatajpa.service.IssuerService;
import ru.dzheb.springdatajpa.service.ReaderService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ui")
public class UIController {
    private final BookService bookService;
    private final ReaderService readerService;
    private final IssuerService issuerService;

    @GetMapping("/books")
    public String allBooks(Model model) {
        model.addAttribute("books", bookService.allBooks());
        return "books";
    }

    @GetMapping("/reader")
    public String allReaders(Model model) {
        model.addAttribute("readers", readerService.allReaders());
        return "readers";
    }

    @GetMapping("/issues")
    public String allIssues(Model model) {
        model.addAttribute("issuesUI", issuerService.allIssues());
        return "issues";
    }

    @GetMapping("/reader/{id}")
    public String readerBooks(@PathVariable Long id, Model model) {
        model.addAttribute("reader", readerService.getReaderById(id));
        model.addAttribute("books", issuerService.getIssuesByReaderUI(id));
        return "reader_books";
    }

}
