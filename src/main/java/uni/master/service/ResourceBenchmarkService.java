package uni.master.service;

import com.sun.management.OperatingSystemMXBean;
import org.springframework.stereotype.Service;
import uni.master.entity.ResourceBenchmark;

import java.lang.management.ManagementFactory;

@Service
public class ResourceBenchmarkService {
    private OperatingSystemMXBean systemResources;

    public ResourceBenchmarkService(){
        this.systemResources = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    }

    public ResourceBenchmark makeResourceBenchmark() {
        return new ResourceBenchmark(
                systemResources.getTotalSwapSpaceSize(),
                systemResources.getTotalPhysicalMemorySize(),
                systemResources.getFreePhysicalMemorySize(),
                System.currentTimeMillis(),
                systemResources.getFreeSwapSpaceSize(),
                systemResources.getSystemCpuLoad(),
                systemResources.getProcessCpuLoad()
        );
    }
}
