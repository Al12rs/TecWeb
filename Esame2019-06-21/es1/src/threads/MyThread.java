package threads;

import java.util.ArrayList;

public class MyThread extends Thread {

    ArrayList<String> blackList;
    String msg;
    boolean valid;

    
	@Override
    public void run() {
        valid = true;
        if (blackList == null) {
            return;
        } else {
            for (int i = 0; i < blackList.size(); i++) {
                if (msg.contains(blackList.get(i))) {
                    valid = false;
                    break;
                }
            }
        }
        return;
	}



	public MyThread(ArrayList<String> blackList, String msg) {
        this.msg = msg;
        this.blackList = blackList;
        
	}

    public boolean isValid() {
        return valid;
    }

}