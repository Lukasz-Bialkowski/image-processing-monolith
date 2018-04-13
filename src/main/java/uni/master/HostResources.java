package uni.master;

import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
public class HostResources {

    public HostResources(long totalSwapSpaceSize, long totalPhysicalMemorySize, long freePhysicalMemorySize, long timestamp, long freeSwapSpaceSize, double systemCpuLoad, double processCpuLoad) {
        TotalSwapSpaceSize = totalSwapSpaceSize;
        TotalPhysicalMemorySize = totalPhysicalMemorySize;
        FreePhysicalMemorySize = freePhysicalMemorySize;
        Timestamp = timestamp;
        FreeSwapSpaceSize = freeSwapSpaceSize;
        SystemCpuLoad = systemCpuLoad;
        ProcessCpuLoad = processCpuLoad;
    }

    @Id
    @GeneratedValue
    private long id;
    private long TotalSwapSpaceSize;
    private long TotalPhysicalMemorySize;
    private long FreePhysicalMemorySize;
    private long Timestamp;
    private long FreeSwapSpaceSize;
    private double SystemCpuLoad;
    private double ProcessCpuLoad;
}

