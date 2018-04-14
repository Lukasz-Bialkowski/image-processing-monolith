package uni.master.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uni.master.ScheduledTask;
import uni.master.service.CalculationService;

@RestController
@RequestMapping("/process")
public class AppController {

    private static final Logger logger = LoggerFactory.getLogger(AppController.class);
    private CalculationService calculationService;
    private TaskScheduler taskScheduler;

    private ScheduledTask scheduledTask;

    @Autowired
    public AppController(
            CalculationService calculationService,
            TaskScheduler taskScheduler,
            ScheduledTask scheduledTask) {
        this.calculationService = calculationService;
        this.taskScheduler = taskScheduler;
        this.scheduledTask = scheduledTask;
    }

    @RequestMapping(method = RequestMethod.GET)
    public void processImage(
            @RequestParam(required = false, defaultValue = "inputImage.jpg") String imageId,
            @RequestParam(required = false, defaultValue = "1") int loops,
            @RequestParam(required = false, defaultValue = "1") int nodes) throws Exception {
        logger.info("Calculate operation started: " + imageId + ", loops: " + loops + ", nodes: " + nodes);
        taskScheduler.scheduleAtFixedRate(scheduledTask, 1000);
        calculationService.calculate(imageId, loops);
    }

//    @Scheduled(fixedRate = 1000)
//    public void sendPcResource() {
//        logger.info("Saving host resource benchmark snapshot: " + System.currentTimeMillis() / 1000);
//        ResourceBenchmark resourceBenchmark = benchmarkService.makeResourceBenchmark();
//        resourceBenchmarkRepository.save(resourceBenchmark);
//    }
}


