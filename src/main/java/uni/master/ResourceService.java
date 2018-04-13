package uni.master;

import com.sun.management.OperatingSystemMXBean;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
class ResourceService {
    private OperatingSystemMXBean systemResources;

    public ResourceService(){
        this.systemResources = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    }

    public HostResources makeHostResources() {
        return new HostResources(
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
