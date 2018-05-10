package uni.master.service;

import com.sun.management.OperatingSystemMXBean;
import org.springframework.stereotype.Service;
import uni.master.entity.ResourceBenchmark;

import java.lang.management.ManagementFactory;

@Service
public class ResourceBenchmarkService {

    private OperatingSystemMXBean systemResources;
    private Runtime runtime = Runtime.getRuntime();

    public ResourceBenchmarkService(){
        this.systemResources = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    }

    public ResourceBenchmark makeResourceBenchmark() {
        return new ResourceBenchmark(
                systemResources.getTotalSwapSpaceSize(),
                runtime.totalMemory(),
                runtime.freeMemory(),
                runtime.totalMemory() - runtime.freeMemory(),
                System.currentTimeMillis(),
                systemResources.getFreeSwapSpaceSize(),
                systemResources.getSystemCpuLoad(),
                systemResources.getProcessCpuLoad(),
                systemResources.getSystemLoadAverage()
        );
    }
}
