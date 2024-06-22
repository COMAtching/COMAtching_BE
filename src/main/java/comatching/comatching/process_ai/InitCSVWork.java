package comatching.comatching.process_ai;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitCSVWork implements ApplicationRunner {
	private CSVHandler csvHandler;

	InitCSVWork(CSVHandler csvHandler) {
		this.csvHandler = csvHandler;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("[Init] - Generate CSV");
		csvHandler.syncCsv();
	}
}
