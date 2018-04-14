package uni.master;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

@SpringBootApplication
@EnableScheduling
public class MasterThesisMonolithSomApplication {
	private static Logger logger = LoggerFactory.getLogger(MasterThesisMonolithSomApplication.class);

	@Bean
	public TaskScheduler taskScheduler() {
		return new ConcurrentTaskScheduler(); //single threaded by default
	}

	public static void main(String[] args) {
		logger.info("Application started");
		logger.info("$PWD output: " + MasterThesisMonolithSomApplication.class.getClassLoader().getResource(""));
		SpringApplication.run(MasterThesisMonolithSomApplication.class, args);
	}
}
