package uni.master.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Enumeration;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ResourceBenchmark {

    @Id @GeneratedValue
    @Getter Long id;
    @Getter private String host;
    @Getter private String ipAddress;
    @Getter private long TotalSwapSpaceSize;
    @Getter private long FreeSwapSpaceSize;
    @Getter private long TotalPhysicalMemorySize;
    @Getter private long FreePhysicalMemorySize;
    @Getter private Date Timestamp;
    @Getter private double SystemCpuLoad;
    @Getter private double ProcessCpuLoad;
    @Getter private double SystemLoadAverage;

    public ResourceBenchmark(long totalSwapSpaceSize, long totalPhysicalMemorySize, long freePhysicalMemorySize, long timestamp, long freeSwapSpaceSize, double systemCpuLoad, double processCpuLoad, double systemLoadAverage) {
        try {
            ipAddress = ResourceBenchmark.getLocalHostLANAddress().getHostAddress();
            host = ResourceBenchmark.getLocalHostLANAddress().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        TotalSwapSpaceSize = totalSwapSpaceSize / 1048576;
        TotalPhysicalMemorySize = totalPhysicalMemorySize / 1048576;
        FreePhysicalMemorySize = freePhysicalMemorySize / 1048576;
        Timestamp = new Date(timestamp);
        FreeSwapSpaceSize = freeSwapSpaceSize / 1048576;
        SystemCpuLoad = ((double)((int)(systemCpuLoad * 10000)))/10000;
        ProcessCpuLoad = ((double)((int)(processCpuLoad * 10000)))/10000;
        SystemLoadAverage = systemLoadAverage;
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
                ", SystemLoadAverage=" + SystemLoadAverage +
                '}';
    }

    private static InetAddress getLocalHostLANAddress() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            // Iterate all NICs (network interface cards)...
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // Iterate all IP addresses assigned to each card...
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {

                        if (inetAddr.isSiteLocalAddress()) {
                            // Found non-loopback site-local address. Return it immediately...
                            return inetAddr;
                        }
                        else if (candidateAddress == null) {
                            // Found non-loopback address, but not necessarily site-local.
                            // Store it as a candidate to be returned if site-local address is not subsequently found...
                            candidateAddress = inetAddr;
                            // Note that we don't repeatedly assign non-loopback non-site-local addresses as candidates,
                            // only the first. For subsequent iterations, candidate will be non-null.
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                // We did not find a site-local address, but we found some other non-loopback address.
                // Server might have a non-site-local address assigned to its NIC (or it might be running
                // IPv6 which deprecates the "site-local" concept).
                // Return this non-loopback candidate address...
                return candidateAddress;
            }
            // At this point, we did not find a non-loopback address.
            // Fall back to returning whatever InetAddress.getLocalHost() returns...
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress;
        }
        catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException("Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }
}

