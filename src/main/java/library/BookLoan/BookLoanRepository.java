package library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

@Repository
public class BookLoanRepository {

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	public Boolean getAvailability(String ISBN13) {
		List<String> dates = jdbcTemplate.query("SELECT date_in FROM book_loan WHERE ISBN13 = '" + ISBN13 + "'", availabilityMapper); 
		if(dates.isEmpty()){
			return true;
		}
		else {
			for(String date: dates) {
				if(date == null){
					return false;
				}
			}
			return true;
		}
	}

	public Integer checkBorrower(int cardId) {
		return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM borrower WHERE card_id = ?", Integer.class, cardId);
	}

	public Integer checkBorrowerLimit(int cardId) {
		return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM book_loan WHERE card_id = ?", Integer.class, cardId);
	}
	
	public int insertBookLoan(final BookLoan bookLoan) {
		try {
			return jdbcTemplate.update("INSERT INTO book_loan(ISBN13, card_id, date_out, due_date) VALUES(?, ?, ?, ?)", new Object[] {
				bookLoan.getISBN13(),
				bookLoan.getCardId(),
				bookLoan.getDateOut(),
				bookLoan.getDueDate()
			});
		}
		catch(Exception e) {
			return 0;
		}
	}

	public static final RowMapper<String> availabilityMapper = new RowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String date;
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			if(rs.getDate("date_in") == null) {
				date = null;
			}
			else {
				date = sdf.format(rs.getDate("date_in"));
			}
			return date;
		}
	};

}