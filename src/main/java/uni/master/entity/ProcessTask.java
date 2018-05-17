package uni.master.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class ProcessTask {

    @Id @GeneratedValue @Getter @Setter private Long id;
    @Getter @Setter private String host;
    @Getter @Setter private String ipAddress;
    @Getter @Setter private String imageId;
    @Getter @Setter private int nodes;
    @Getter @Setter private int iterations;
    @Getter @Setter private Date startTime;
    @Getter @Setter private Date endTime;
    @Getter @Setter private Long totalTime;
    private static final int NANO_PREFIX = 1_000_000;

    public ProcessTask(String imageId, int nodes, int iterations, long startTime, long endTime, long startTimeNano, long endTimeNano){
        try {
            ipAddress = ProcessTask.getLocalHostLANAddress().getHostAddress();
            host = ProcessTask.getLocalHostLANAddress().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.imageId = imageId;
        this.nodes = nodes;
        this.iterations = iterations;
        this.startTime = new Date(startTime);
        this.endTime = new Date(endTime);
        this.totalTime = (endTimeNano - startTimeNano) / NANO_PREFIX;
    }

    @Override
    public String toString() {
        return "ProcessTask{" +
                "id=" + id +
                ", host='" + host + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", imageId='" + imageId + '\'' +
                ", nodes=" + nodes +
                ", iterations=" + iterations +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", totalTime=" + totalTime +
                '}';
    }

    private static InetAddress getLocalHostLANAddress() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            boolean isSecond = false;
            // Iterate all NICs (network interface cards)...
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // Iterate all IP addresses assigned to each card...
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {

                        if (inetAddr.isSiteLocalAddress()) {
                            // Found non-loopback site-local address. Return it immediately...
                            if(isSecond)
                                return inetAddr;
                            isSecond = true;
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
