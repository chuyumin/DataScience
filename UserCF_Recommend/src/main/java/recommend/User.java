package recommend;

import java.util.HashMap;
import java.util.Map;

public class User {
    private long id;
    private String user_id;

    private Map<String , Integer> cases;

    public User() {
        this.cases = new HashMap<>();
    }

    public User(long id, String user_id, Map<String, Integer> cases) {
        this.id = id;
        this.user_id = user_id;
        this.cases = cases;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Map<String, Integer> getCases() {
        return cases;
    }

    public void setCases(Map<String, Integer> cases) {
        this.cases = cases;
    }

    @Override
    public String toString() {
        return "用户ID:" + user_id + "\\n" + "做题数目:" + cases.keySet().size();
    }
}
