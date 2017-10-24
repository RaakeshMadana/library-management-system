package library;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookLoanService {

	@Autowired
	BookLoanRepository bookLoanRepo;

	@RequestMapping("/checkout")
	public int checkOut(@RequestParam(value="borrower") int cardId, @RequestParam(value="isbn13") String ISBN13) {
		if(bookLoanRepo.checkBorrower(cardId) == 1) {
			java.util.Date today = new java.util.Date();
			java.util.Date due = new java.util.Date(today.getTime() + (1000 * 60 * 60 * 24 * 14));
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			String dateOut = sdf.format(today);
			String dueDate = sdf.format(due);
			BookLoan bookLoan = new BookLoan(0, ISBN13, cardId, dateOut, dueDate, null);
			return bookLoanRepo.insertBookLoan(bookLoan);
		}
		else {
			return 0;
		}
	}

}
