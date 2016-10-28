package ccnu.computer.model;

public class PosterInfo {
    private Integer id;

    private String user;

    private String content;

    private String agreement;

    private String comment;

    private String transmit;

    private String time;

    private String topic;

    private String islabel;

    private String isrelated;

    private String title;

    private String labelname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user == null ? null : user.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement == null ? null : agreement.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public String getTransmit() {
        return transmit;
    }

    public void setTransmit(String transmit) {
        this.transmit = transmit == null ? null : transmit.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic == null ? null : topic.trim();
    }

    public String getIslabel() {
        return islabel;
    }

    public void setIslabel(String islabel) {
        this.islabel = islabel == null ? null : islabel.trim();
    }

    public String getIsrelated() {
        return isrelated;
    }

    public void setIsrelated(String isrelated) {
        this.isrelated = isrelated == null ? null : isrelated.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getLabelname() {
        return labelname;
    }

    public void setLabelname(String labelname) {
        this.labelname = labelname == null ? null : labelname.trim();
    }
}