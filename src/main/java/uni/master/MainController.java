package uni.master;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class MainController {

    private CalculationService calculationService;

    private ServletContext servletContext;

    @Autowired
    public MainController(CalculationService calculationService,
            ServletContext servletContext) {
        this.calculationService = calculationService;
        this.servletContext = servletContext;
    }

    @RequestMapping(value = "/calculate")
    public void processImage(int imageId, int loops, int nodes) throws Exception {
        calculationService.calculate();
    }

    @RequestMapping(value = "/image", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage() throws IOException {
        InputStream in = servletContext.getResourceAsStream("/WEB-INF/images/image-example.jpg");
        return org.apache.commons.io.IOUtils.toByteArray(in);
    }

}
