package com.cts.iiht.main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.cts.iiht.entity.Book;
import com.cts.iiht.entity.Subject;
import com.cts.iiht.service.BookService;
import com.cts.iiht.service.SubjectService;
import com.cts.iiht.util.JPAUtil;


public class MainClass {
	static Scanner in = null;

	public static void main(String[] args) {
		String choice;
		BookService bookService = new BookService();
		SubjectService subjectService = new SubjectService();
		Book book;
		Subject subject;
		List<Subject> subjects;
		try {
			in = new Scanner(System.in);
			do {
				long id = 0;
				System.out.println();
				System.out.println("a.  Add a Book");
				System.out.println("b.  Delete a Subject");
				System.out.println("c.  Delete a book");
				System.out.println("d.  Search for a book");
				System.out.println("e.  Search for a subject");
				System.out.println("f.  Exit");
				System.out.println("--------------------------");
				System.out.println("Enter your choice");
				choice = in.next();    

				switch(choice) {
				case "a":
					subjects = subjectService.getAllSubjects();
					book = createBook(subjects, subjectService);
					bookService.addBook(book);
					System.out.println("Book with id: "+book.getBookId() + " has been added successfully");
					break;
				case "b":
					System.out.println("Enter subject id to delete");
					id = in.nextLong();
					subjectService.deleteSubject(id);
					break;
				case "c":
					System.out.println("Enter book id to delete");
					id = in.nextLong();
					bookService.deleteBook(id);
					break;
				case "d":
					System.out.println("Enter book id to search");
					id = in.nextLong();
					book = bookService.searchBook(id);
					if(book != null) {
						System.out.format("\n%10s %15s %15s %15s %15s", "Id", "Price", "Title", "Volume", "Publish Date");
						System.out.format("\n%10d %15.2f %15s %15d %15s \n", book.getBookId(), book.getPrice(), book.getTitle(), book.getVolume(), book.getPublishDate());
						subject = book.getSubject();
						System.out.println("Mapped Subject Details are");
						System.out.format("\n%10s %20s %15s ", "Id", "SubTitle", "DurationInHours");
						System.out.format("\n%10d %20s %15d \n", subject.getSubjectId(), subject.getSubtitle(), subject.getDurationInHours());
					}
					else {
						System.out.println("Unable to find Book with id "+ id);
					}
					break;
				case "e":
					System.out.println("Enter subject id to search");
					id = in.nextLong();
					subject = subjectService.searchSubject(id);
					if(subject != null) {
						System.out.format("\n%10s %20s %15s ", "Id", "SubTitle", "DurationInHours");
						System.out.format("\n%10d %20s %15d \n", subject.getSubjectId(), subject.getSubtitle(), subject.getDurationInHours());
					}
					else {
						System.out.println("Unable to find Subject with id "+ id);
					}
					break;	
				case "f":
					System.out.println("Thank You");
					JPAUtil.shutdown();
					System.exit(0);
					break;
				default:
					System.out.println("Invalid Option.");
				}
			}while (!"i".equalsIgnoreCase(choice));
		}

		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if (in != null) {
				in.close();
			}
		}
	}


	private static Book createBook(List<Subject> subjects, SubjectService subjectService) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Book book = new Book();
		System.out.println("Adding a Book");
		System.out.println("Please enter Price");
		book.setPrice(in.nextDouble());
		System.out.println("Please enter Title");
		book.setTitle(in.next());
		System.out.println("Please enter Volume");
		book.setVolume(in.nextInt());
		System.out.println("Please enter PublishDate(dd/MM/yyyy)");
		book.setPublishDate(LocalDate.parse(in.next(), formatter));
		Map<Long, Subject> subjectMap = new HashMap<>();
		if(subjects != null && !subjects.isEmpty()) {
			System.out.println("All available subject details");
			System.out.format("\n%10s %20s %15s ", "Id", "SubTitle", "DurationInHours");
			for(Subject subject : subjects) {
				System.out.format("\n%10d %20s %15d \n", subject.getSubjectId(), subject.getSubtitle(), subject.getDurationInHours());
				subjectMap.put(subject.getSubjectId(), subject);
			}
		}
		else {
			System.out.println("NO subjects available. Please select 0 to create subject");
		}
		System.out.println("Please enter subject id to enter as a reference (enter 0 if you want to create a subject)");
		long id = in.nextLong();
		if(id > 0) {
			book.setSubject(subjectService.searchSubject(id));
		}
		else {
			book.setSubject(createSubject(subjectService));
		}
		return book;
	}

	private static Subject createSubject(SubjectService subjectService) {
		Subject subject = new Subject();
		System.out.println("Adding a Subject");
		System.out.println("Please enter subtitle");
		subject.setSubtitle(in.next());
		System.out.println("Please enter durationInHours");
		subject.setDurationInHours(in.nextInt());
		subjectService.addSubject(subject);
		return subject;
	}
}

