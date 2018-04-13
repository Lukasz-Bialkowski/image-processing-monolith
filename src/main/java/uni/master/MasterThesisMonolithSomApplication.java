package uni.master;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class MasterThesisMonolithSomApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(MasterThesisMonolithSomApplication.class);
	private ResourceService resourceService;
	private ImageRepository imageRepository;
	private HostResourceRepository hostResourceRepository;

	@Autowired
	public MasterThesisMonolithSomApplication(ResourceService resourceService,
			  ImageRepository imageRepository, HostResourceRepository hostResourceRepository) {
		this.resourceService = resourceService;
		this.imageRepository = imageRepository;
		this.hostResourceRepository = hostResourceRepository;
	}

//	@Scheduled(fixedRate = 1000)
	public void sendPcResource() {
		LOGGER.info("Saving host resources snapshot: " + LocalDateTime.now().toString());
		HostResources hostResources = resourceService.makeHostResources();
		hostResourceRepository.save(hostResources);
	}

	public static void main(String[] args) {
		SpringApplication.run(MasterThesisMonolithSomApplication.class, args);
	}
}
