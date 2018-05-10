package uni.master.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
//import org.springframework.scheduling.TaskScheduler;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import uni.master.entity.ProcessTask;
import uni.master.entity.ResourceBenchmark;
//import uni.master.executor.ScheduledTask;
import uni.master.repository.ProcessTaskRepository;
import uni.master.repository.ResourceBenchmarkRepository;
import uni.master.service.CalculationService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

@RestController
@RequestMapping("/app")
public class AppController {

    private static final Logger logger = LoggerFactory.getLogger(AppController.class);
    private CalculationService calculationService;
//    private TaskScheduler taskScheduler;
//    private ScheduledTask scheduledTask;
    private ProcessTaskRepository processTaskRepository;
    private ResourceBenchmarkRepository resourceBenchmarkRepository;

    @Autowired
    public AppController(
            CalculationService calculationService,
//            TaskScheduler taskScheduler,
//            ScheduledTask scheduledTask,
            ProcessTaskRepository processTaskRepository,
            ResourceBenchmarkRepository resourceBenchmarkRepository) throws IOException {
        this.calculationService = calculationService;
//        this.taskScheduler = taskScheduler;
//        this.scheduledTask = scheduledTask;
        this.processTaskRepository = processTaskRepository;
        this.resourceBenchmarkRepository = resourceBenchmarkRepository;
    }

    @RequestMapping(value = "/process", method = RequestMethod.GET)
    public ProcessTask processImage(
            @RequestParam(required = false, defaultValue = "100.jpg") String imageId,
            @RequestParam(required = false, defaultValue = "1") int iterations,
            @RequestParam(required = false, defaultValue = "1") int nodes) throws Exception {

        logger.info("Calculate operation started: " + imageId + ", loops: " + iterations + ", nodes: " + nodes);
//        taskScheduler.scheduleAtFixedRate(scheduledTask, 1000);
        long startTimeNano = System.nanoTime();
        long startTime = System.currentTimeMillis();
        calculationService.calculate(imageId, iterations);
        long endTimeNano = System.nanoTime();
        long endTime = System.currentTimeMillis();

        return processTaskRepository.save(
                new ProcessTask(imageId, nodes, iterations, startTime, endTime, startTimeNano, endTimeNano));
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public List<ProcessTask> getHistory() {
        logger.info("History endpoint called");
        return processTaskRepository.findAll();
    }

    @RequestMapping(value = "/benchmark", method = RequestMethod.GET)
    public List<ResourceBenchmark> getHistoryBenchmark(@RequestParam int size) {
        logger.info("History endpoint called");
        Pageable page = PageRequest.of(0, size);
        return resourceBenchmarkRepository.findAll(page).getContent();
    }

    @RequestMapping(value = "/searchList", method = RequestMethod.GET)
    public List<ProcessTask> searchList(@RequestParam String userInput) {
        logger.info("Searching for userInput: " + userInput);
        return processTaskRepository.findtags(userInput);
    }

    @RequestMapping(value = "/image/{imageId}")
    public void getImage(@PathVariable String imageId, HttpServletResponse response) throws IOException, URISyntaxException {
        InputStream rpath = getClass().getClassLoader().getResourceAsStream("static/assets/" + imageId);
        StreamUtils.copy(rpath, response.getOutputStream());
    }

    @RequestMapping(value = "/image")
    public List<String> list() throws URISyntaxException, IOException {
        List<String> files = new ArrayList<>();
        URI uri = AppController.class.getClassLoader().getResource("static/assets").toURI();
        Path myPath;
        if (uri.getScheme().equals("jar")) {
            FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
            myPath = fileSystem.getPath("/resources");
        } else {
            myPath = Paths.get(uri);
        }
        Stream<Path> walk = Files.walk(myPath, 1);
        Iterator<Path> it = walk.iterator();
        it.next();
        while(it.hasNext()) {
            files.add(it.next().getFileName().toString());
        }
        return files;
    }

    @RequestMapping("/pwd")
    public String pwd() {
        System.out.println(new File(""));
        System.out.println(new File("."));
        System.out.println(Paths.get("/"));
        System.out.println(Paths.get(System.getProperty("user.home")));
        System.out.println(System.getProperty("user.home"));
        System.out.println(System.getProperty("user.home") + File.separator + "costam");
        return Paths.get("").toAbsolutePath().toString();
    }
}
