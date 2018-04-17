package uni.master.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

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
            this.host = InetAddress.getLocalHost().getHostName();
            this.ipAddress = InetAddress.getLocalHost().getHostAddress();
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
}
