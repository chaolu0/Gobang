package bean;

public class HttpResBean {

    private Integer state;
    private User user;


    public HttpResBean(Integer state, User user) {
        this.state = state;
        this.user = user;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
