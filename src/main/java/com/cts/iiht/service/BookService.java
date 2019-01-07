package com.cts.iiht.service;

import java.util.List;

import com.cts.iiht.dao.BookDao;
import com.cts.iiht.entity.Book;

public class BookService {
	
	BookDao bookDao = new BookDao();
	
	
	public void addBook(Book book ) {
		bookDao.addBook(book);
	}
	
	public void deleteBook(long id) {
		bookDao.deleteBook(id);
	}

	public Book searchBook(long id) {
		return bookDao.searchBook(id);
	}

	public List<Book> getAllBooks(){
		return bookDao.getAllBooks();
	}
	
	public List<Book> getAllBooksSortByTitle(){
		return bookDao.getAllBooksSortByTitle();
	}
	
	public List<Book> getAllBooksSortByPublishDate(){
		return bookDao.getAllBooksSortByPublishDate();
	}
}
