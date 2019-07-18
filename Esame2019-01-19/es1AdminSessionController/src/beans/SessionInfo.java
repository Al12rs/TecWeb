package beans;

import java.io.Serializable;

import javax.servlet.http.HttpSession;

public class SessionInfo implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private String sessionId;
    private long creationTime;
    private Long endTime;
    private LoginInfo loginInfo;
    private HttpSession session;

    public SessionInfo(HttpSession session) {
        this.session = session;
        this.sessionId = session.getId();
        this.creationTime = session.getCreationTime();
        this.endTime = null;
        this.loginInfo = (LoginInfo) session.getAttribute("loginInfo");
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }
    
    
}