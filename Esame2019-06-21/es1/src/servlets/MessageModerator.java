package servlets;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.MsgList;
import threads.MyThread;

public class MessageModerator extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ArrayList<String> blacklist;
    private MsgList msgList;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.msgList = new MsgList();
        String blackListPath = config.getInitParameter("blacklist");
        blacklist = new ArrayList<String>();
        try{
            BufferedReader buf = new BufferedReader(new FileReader(blackListPath));
            String currentLine;
            while((currentLine = buf.readLine()) != null){
                this.blacklist.add(currentLine);
            }
           buf.close();
        }
        catch (IOException e) {
            this.blacklist = null;
        }

    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        int sessionId = Integer.parseInt(session.getId());
        MyThread messageChecker = new MyThread(this.blacklist, req.getParameter("msgInput"));
        messageChecker.start();
        ArrayList<String> messagesToReturn = this.msgList.getAllMessagesByParity(sessionId);
        try {
            messageChecker.join();
            if(messageChecker.isValid()){
                this.msgList.addMessage(req.getParameter("msgInput"));
                messagesToReturn.add(req.getParameter("msgInput"));
            }
		} catch (InterruptedException e) {
			e.printStackTrace();
        }
        MsgList msgToReturn = new MsgList();
        msgToReturn.setMsgList(messagesToReturn);
        session.setAttribute("messagesToReturn", msgToReturn);
        resp.sendRedirect("pages/home.jsp");
    }
}