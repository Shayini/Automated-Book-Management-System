package com.backend.server.Controller;

import com.backend.server.Model.Book;
import com.backend.server.Repository.Book_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:8081", "http://192.168.1.107"})   // The 1st address is for frontend(vue) running address, 2nd address is Wifi Modules IP address
                                                                            //Change the IP if necessary
@RestController
//Encapsulating @Controller + @ResponseBody annotations,@RestController means that this class is a Controller.
@RequestMapping(path="/Project/REST-API") //This means URL's start with /Project/REST-API (after Application path).

public class BookController {

    @Autowired
    // This means to get the bean called User_Repository which is auto-generated by Spring, we will use it to handle the data.
    private Book_repository bookRepository;


    @GetMapping(value="/name", params = { "bookName" }) //Map ONLY GET Requests and means URL's start with /login/{userName}(after Application path).
    public Optional findByBookName(@RequestParam("bookName") String bookname) {
        //@PathVariable is used to extract data right from the URI.It is used to retrieve values from URI itself.
        return bookRepository.findByBookName(bookname);
        //Java Optional can help in writing neat code without using too many null checks.
    }

//    @GetMapping(value="/admin") //Map ONLY GET Requests and means URL's start with /login/{userName}(after Application path).
//    public Optional findAdmin(@RequestParam("userName") String username) {
//        //@PathVariable is used to extract data right from the URI.It is used to retrieve values from URI itself.
//        return bookRepository.findByUserName(username);
//        //Java Optional can help in writing neat code without using too many null checks.
//    }

    @GetMapping(value="/bookdetails") //Map ONLY GET Requests and means URL's start with /login/{userName}(after Application path).
    public Iterable findBookDetails() {
        //@PathVariable is used to extract data right from the URI.It is used to retrieve values from URI itself.
        return bookRepository.findAll();
        //Java Optional can help in writing neat code without using too many null checks.
    }

    @PostMapping("/addbook") // Map ONLY POST Requests and means URL's start with /signin (after Application path).
    @ResponseBody // @ResponseBody means the returned Integer is the response, not a view Id.
    public Book addBook(@RequestBody Book book) { //@RequestBody annotation is used to bind the HTTP request/response body with a domain object in method parameter or return type.
        bookRepository.save(book); //Inherited save() method is used here.
        return book;

    }

//    @PostMapping("/addbookparam") // Map ONLY POST Requests and means URL's start with /add (after Application path).
//    @ResponseBody // @ResponseBody means the returned Integer is the response, not a view Id.
//    public String addURL(@RequestParam Integer bookID,@RequestParam String bookName,@RequestParam String author, @RequestParam String shelf, @RequestParam Integer copyNo, @RequestParam String status) {
//        //@RequestParam annotation is used to obtain a parameter from the URL.
//
//        Book book = new Book(); //Instantiating the Project class.
//        book.setBookID(bookID); //Invoking the setProjectID() method.
//        book.setBookName(bookName); //Invoking the setProjectAuthor() method.
//        book.setAuthor(author); //Invoking the setProjectName() method.
//        book.setShelf((shelf));
//        book.setCopyNo(copyNo);
//        book.setStatus(status);
//        bookRepository.save(book); //Inherited save() method is used here.
//        return "Response Is Successfully Added"; //Method addURL returning the String.
//
//    }

    @DeleteMapping(value="/deletebook/{id}")
    public String deleteBook(@PathVariable int id) {
        bookRepository.deleteByBookID(id);

        return "Book data successfully deleted!";
    }

    @PutMapping("/updatebook/{id}")
    public String updateBook(@PathVariable int id) {
        Optional<Book> bookOptional = bookRepository.findByBookID(id);

        if (!bookOptional.isPresent())
            return "Book Not Found";

        Book book = bookOptional.get();
        book.setBookID(id); //Invoking the setProjectID() method.
        book.setBookName(book.getBookName()); //Invoking the setProjectAuthor() method.
        book.setAuthor(book.getAuthor()); //Invoking the setProjectName() method.
        book.setShelf((book.getShelf()));
        book.setCopyNo(book.getCopyNo());
        book.setStatus("Available");
        bookRepository.save(book); //Inherited save() method is used here.
        //System.out.println(book.getStatus());

        return "Successfully Updated";
    }

    @PutMapping("/updatebook2/{id}")
    @ResponseBody // @ResponseBody means the returned Integer is the response, not a view Id.
    public String updateBook2(@PathVariable int id, @RequestBody Book book) {
        Optional<Book> bookOptional = bookRepository.findByBookID(id);

        if (!bookOptional.isPresent())
            return "Book Not Found";

        Book book1 = bookOptional.get();
        book1.setBookID(id); //Invoking the setProjectID() method.
        book1.setBookName(book.getBookName()); //Invoking the setProjectAuthor() method.
        book1.setAuthor(book.getAuthor()); //Invoking the setProjectName() method.
        book1.setShelf((book.getShelf()));
        book1.setCopyNo(book.getCopyNo());
        bookRepository.save(book1); //Inherited save() method is used here.
        //System.out.println(book.getStatus());

        return "Successfully Updated";
    }

}
