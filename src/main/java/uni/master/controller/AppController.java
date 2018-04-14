package uni.master.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import uni.master.executor.ScheduledTask;
import uni.master.service.CalculationService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/app")
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

    @RequestMapping(value = "/image", method = RequestMethod.GET)
    public void processImage(
            @RequestParam(required = false, defaultValue = "static/assets/100.jpg") String imageId,
            @RequestParam(required = false, defaultValue = "1") int loops,
            @RequestParam(required = false, defaultValue = "1") int nodes) throws Exception {
        logger.info("Calculate operation started: " + imageId + ", loops: " + loops + ", nodes: " + nodes);
        taskScheduler.scheduleAtFixedRate(scheduledTask, 1000);
        calculationService.calculate(imageId, loops);
    }

    @RequestMapping(value = "/images/{imageId}")
    public void getImage(@PathVariable String imageId, HttpServletResponse response) throws IOException, URISyntaxException {
        InputStream rpath = getClass().getClassLoader().getResourceAsStream("static/assets/" + imageId);
        StreamUtils.copy(rpath, response.getOutputStream());
    }

    @RequestMapping(value = "/images")
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
        System.out.println(System.getProperty("user.home") + File.pathSeparator + "costam");
        return Paths.get("").toAbsolutePath().toString();
    }

}


