package beans;
import java.util.ArrayList;

public class MsgList {

    private ArrayList<String> msgList = new ArrayList<String>();
    
    public void addMessage(String msg){
        this.msgList.add(msg);
    }

    public ArrayList<String> getAllMessagesByParity(int start){
        ArrayList<String> result = new ArrayList<String>();
        for(int i=(start%2); i < this.msgList.size(); i+=2){
            result.add(this.msgList.get(i));
        }
         return result;
    }

	public ArrayList<String> getMsgList() {
		return msgList;
	}

	public void setMsgList(ArrayList<String> msgList) {
		this.msgList = msgList;
	}

    

}
 