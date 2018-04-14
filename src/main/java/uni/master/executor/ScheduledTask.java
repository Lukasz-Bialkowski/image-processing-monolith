package uni.master.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uni.master.entity.ResourceBenchmark;
import uni.master.repository.ResourceBenchmarkRepository;
import uni.master.service.ResourceBenchmarkService;

@Component
public class ScheduledTask implements Runnable {
    static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);
    @Autowired
    ResourceBenchmarkService benchmarkService;
    @Autowired
    ResourceBenchmarkRepository resourceBenchmarkRepository;

    @Override
    public void run() {
        logger.info("Saving host resource benchmark snapshot: " + System.currentTimeMillis() / 1000);
        ResourceBenchmark resourceBenchmark = benchmarkService.makeResourceBenchmark();
        resourceBenchmarkRepository.save(resourceBenchmark);
    }
}