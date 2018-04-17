package uni.master.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ResourceBenchmark {

    @Id @GeneratedValue
    @Getter Long id;
    @Getter private String host;
    @Getter private String ipAddress;
    @Getter private long TotalSwapSpaceSize;
    @Getter private long TotalPhysicalMemorySize;
    @Getter private long FreePhysicalMemorySize;
    @Getter private long Timestamp;
    @Getter private long FreeSwapSpaceSize;
    @Getter private double SystemCpuLoad;
    @Getter private double ProcessCpuLoad;

    public ResourceBenchmark(long totalSwapSpaceSize, long totalPhysicalMemorySize, long freePhysicalMemorySize, long timestamp, long freeSwapSpaceSize, double systemCpuLoad, double processCpuLoad) {
        try {
            ipAddress = InetAddress.getLocalHost().getHostAddress();
            host = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        TotalSwapSpaceSize = totalSwapSpaceSize;
        TotalPhysicalMemorySize = totalPhysicalMemorySize;
        FreePhysicalMemorySize = freePhysicalMemorySize;
        Timestamp = timestamp;
        FreeSwapSpaceSize = freeSwapSpaceSize;
        SystemCpuLoad = systemCpuLoad;
        ProcessCpuLoad = processCpuLoad;
    }

    @Override
    public String toString() {
        return "ResourceBenchmark{" +
                "id=" + id +
                ", host='" + host + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", TotalSwapSpaceSize=" + TotalSwapSpaceSize +
                ", TotalPhysicalMemorySize=" + TotalPhysicalMemorySize +
                ", FreePhysicalMemorySize=" + FreePhysicalMemorySize +
                ", Timestamp=" + Timestamp +
                ", FreeSwapSpaceSize=" + FreeSwapSpaceSize +
                ", SystemCpuLoad=" + SystemCpuLoad +
                ", ProcessCpuLoad=" + ProcessCpuLoad +
                '}';
    }
}

