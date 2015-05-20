package test.freelancer.com.fltest.handlers;

/**
 * Created by Android 17 on 5/20/2015.
 */
public class TvProgramHandler {

    private int id;
    private String name;
    private String startTime;
    private String endTime;
    private String channel;
    private String rating;


    public TvProgramHandler() {
    }

    public TvProgramHandler(int id, String name, String startTime, String endTime, String channel, String rating) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.channel = channel;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
