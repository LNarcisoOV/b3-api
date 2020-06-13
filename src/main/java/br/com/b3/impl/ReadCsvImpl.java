package br.com.b3.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReadCsvImpl {
	
	private static final long THIRTY_SECONDS = 30000l;
	private static final Logger log = LoggerFactory.getLogger(ReadCsvImpl.class);


	@Scheduled(fixedRate = THIRTY_SECONDS)
	public void readCsv() {
	}
}
