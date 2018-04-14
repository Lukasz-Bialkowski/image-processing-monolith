package uni.master.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.bind.annotation.*;
import uni.master.executor.ScheduledTask;
import uni.master.service.CalculationService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

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
            ScheduledTask scheduledTask) throws IOException {

        this.calculationService = calculationService;
        this.taskScheduler = taskScheduler;
        this.scheduledTask = scheduledTask;
    }

    @RequestMapping(method = RequestMethod.GET)
    public void processImage(
            @RequestParam(required = false, defaultValue = "static/assets/100.jpg") String imageId,
            @RequestParam(required = false, defaultValue = "1") int loops,
            @RequestParam(required = false, defaultValue = "1") int nodes) throws Exception {
        logger.info("Calculate operation started: " + imageId + ", loops: " + loops + ", nodes: " + nodes);
        taskScheduler.scheduleAtFixedRate(scheduledTask, 1000);
        calculationService.calculate(imageId, loops);
    }

    @RequestMapping(value = "/getImage/{imageId}")
    public byte[] getImage(@PathVariable String imageId, HttpServletRequest request) throws IOException, URISyntaxException {
        URL rpath = getClass().getClassLoader().getResource("static/assets/100.jpg");
        Path path = new File(rpath.toURI()).toPath();
        return new byte[]{1};
    }

}


