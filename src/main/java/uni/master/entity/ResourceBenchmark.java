package uni.master.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ResourceBenchmark {

    @Id @GeneratedValue Long id;
    private long TotalSwapSpaceSize;
    private long TotalPhysicalMemorySize;
    private long FreePhysicalMemorySize;
    private long Timestamp;
    private long FreeSwapSpaceSize;
    private double SystemCpuLoad;
    private double ProcessCpuLoad;

    public ResourceBenchmark(long totalSwapSpaceSize, long totalPhysicalMemorySize, long freePhysicalMemorySize, long timestamp, long freeSwapSpaceSize, double systemCpuLoad, double processCpuLoad) {
        TotalSwapSpaceSize = totalSwapSpaceSize;
        TotalPhysicalMemorySize = totalPhysicalMemorySize;
        FreePhysicalMemorySize = freePhysicalMemorySize;
        Timestamp = timestamp;
        FreeSwapSpaceSize = freeSwapSpaceSize;
        SystemCpuLoad = systemCpuLoad;
        ProcessCpuLoad = processCpuLoad;
    }
}

