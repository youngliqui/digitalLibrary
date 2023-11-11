package by.youngliqui.digitalLibrary.controllers;

import by.youngliqui.digitalLibrary.models.Book;
import by.youngliqui.digitalLibrary.models.Person;
import by.youngliqui.digitalLibrary.services.BookService;
import by.youngliqui.digitalLibrary.services.PeopleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books/index";
    }

    @GetMapping(params = {"page", "books_per_page"})
    public String index(@RequestParam("page") int page, @RequestParam("books_per_page") int booksPerPage, Model model){
        if (booksPerPage == 0) {
            model.addAttribute("books", bookService.findAll());
        } else {
            model.addAttribute("books", bookService.findAll(page, booksPerPage));
        }
        return "books/index";
    }

    @GetMapping(params = "sort_by_year")
    public String index(@RequestParam("sort_by_year") boolean sort, Model model) {
        if (sort) {
            model.addAttribute("books", bookService.findAll("yearOfProduction"));
        }
        else {
            model.addAttribute("books", bookService.findAll());
        }

        return "books/index";
    }

    @GetMapping(params = {"page", "books_per_page", "sort_by_year"})
    public String index(@RequestParam(value = "page", required = false) int page,
                        @RequestParam(value = "books_per_page", required = false) int booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) boolean sort,
                        Model model) {
        if (booksPerPage == 0 && !sort) {
            model.addAttribute("books", bookService.findAll());
        }
        else if (booksPerPage == 0) {
            model.addAttribute("books", bookService.findAll("yearOfProduction"));
        }
        else if (!sort) {
            model.addAttribute("books", bookService.findAll(page, booksPerPage));
        } else {
            model.addAttribute("books", bookService.findAll(page, booksPerPage, "yearOfProduction"));
        }

        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") Book book, @PathVariable("id") int id) {
        bookService.update(id, book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,
                       @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.findOne(id));

        Optional<Person> bookOwner = bookService.findBookOwnerByBookId(id);

        if (bookOwner.isPresent()) {
            model.addAttribute("owner", bookOwner.get());
        }
        else {
            model.addAttribute("people", peopleService.findAll());
        }

        return "books/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/appoint")
    public String appoint(@PathVariable("id") int id,
                          @ModelAttribute("person") Person selectedPerson) {
        bookService.appoint(id, selectedPerson);

        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookService.release(id);

        return "redirect:/books/{id}";
    }

    @GetMapping("/search")
    public String search() {
        return "books/search";
    }

    @GetMapping(value = "/search", params = "request")
    public String search(@RequestParam("request") String request, Model model) {
        if (request == null) {
            model.addAttribute("books", bookService.findAll());
        }
        else {
            model.addAttribute("books", bookService.findBooksByNameStartingWith(request));
        }

        return "books/search";
    }
}
