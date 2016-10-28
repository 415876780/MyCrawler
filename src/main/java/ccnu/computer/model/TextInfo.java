package ccnu.computer.model;

public class TextInfo {
    private Integer id;

    private String comment;

    private String date;

    private String islabel;

    private String isrelated;

    private String title;

    private String topic;

    private String url;

    private String labelname;

    private String summary;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic == null ? null : topic.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getLabelname() {
        return labelname;
    }

    public void setLabelname(String labelname) {
        this.labelname = labelname == null ? null : labelname.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}