package br.com.b3.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.b3.model.User;
import br.com.b3.service.UserRepository;

@Component
public class ReadCsvImpl {

	private static final long THIRTY_SECONDS = 30000l;
	private static final Logger LOG = LoggerFactory.getLogger(ReadCsvImpl.class);
	private static final String DIRECTORY_OF_CSV_FILES = new File("").getAbsolutePath().concat("\\app\\data\\");
	private static final String LINE_OUT_OF_PATTERN = "Line out of pattern.";
	private static final String LINE_WITHOUT_SOME_FIELD = "Line without some field.";

	@Autowired
	private UserRepository userRepositoy;

	@Scheduled(fixedRate = THIRTY_SECONDS)
	public void readCsv() throws IOException {
		File root = new File(DIRECTORY_OF_CSV_FILES);

		for (File f : root.listFiles()) {
			if (isCsvFile(f)) {
				readCsvFile(f);
			}
		}
	}

	private void readCsvFile(File f) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(f));

		while (br.ready()) {
			String line = br.readLine();
			try {
				String[] splitedString = splitLine(line);
				applyRulesToCreateAndSaveUser(splitedString);
			} catch (Exception e) {
				LOG.info(e.getMessage());
			}
		}
		br.close();
	}

	private void applyRulesToCreateAndSaveUser(String[] splitedString) {
		if (splitedString.length < 2) {
			if (isALineWithAllFields(splitedString)) {
				createAndSaveUser(splitedString);
			} else {
				LOG.info(LINE_WITHOUT_SOME_FIELD);
			}
		} else {
			LOG.info(LINE_OUT_OF_PATTERN);
		}
	}

	private void createAndSaveUser(String[] splitedString) {
		try {
			Long companyId = Long.parseLong(splitedString[0]);
			String email = splitedString[1];
			Date birthdate = new SimpleDateFormat("dd/MM/yyyy").parse(splitedString[2]);
			
			User user = new User();
			user.setCompanyId(companyId);
			user.setEmail(email);
			user.setBirthdate(birthdate);
			
			save(user);
		} catch(Exception e) {
			LOG.info(e.getMessage());
		}
	}

	private void save(User user) {
		userRepositoy.save(user);
	}

	private boolean isALineWithAllFields(String[] splitedString) {
		return splitedString[0] != null && !splitedString[0].isEmpty() &&
		splitedString[1] != null && !splitedString[1].isEmpty() &&
		splitedString[2] != null && !splitedString[2].isEmpty();
	}

	private String[] splitLine(String line) {
		if (isAValidLine(line)) {
			return line.split(";");
		}
		return null;
	}

	private boolean isAValidLine(String line) {
		return line != null && !line.isEmpty();
	}

	private boolean isCsvFile(File f) {
		return f.isFile() && f.getName().endsWith(".csv") && f.getTotalSpace() > 0l;
	}
}
