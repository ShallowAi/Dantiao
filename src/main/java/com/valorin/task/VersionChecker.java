package com.valorin.task;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import com.valorin.Dantiao;
import com.valorin.event.CheckVersion;
import com.valorin.network.Update;

public class VersionChecker extends BukkitRunnable {
	boolean send = false;
	CommandSender receiver;
	
    public void run() {
        Update update = Dantiao.getInstance().getUpdate();
	    String context = null;
	    HttpURLConnection conn = null;
	    try {
		    URL url = new URL("https://valorin.coding.net/p/VersionChecker/d/VersionChecker/git/raw/master/Dantiao-L");
		    conn = (HttpURLConnection)url.openConnection();
		    conn.setConnectTimeout(5000);
		    InputStream inStream  = conn.getInputStream();
		    final StringBuilder builder = new StringBuilder(255);
		    int byteRead;
		    while ((byteRead = inStream.read()) != -1) {
		      builder.append((char) byteRead);
		    }
		    context = new String(builder.toString().getBytes("ISO-8859-1"), "UTF-8");
		    update.setState(1);
		    String version = context.split("\\[change]")[0];
		    String[] messageStringArray = (context.split("\\[change]")[1]).split("\\[next]");
		    List<String> messageList = new ArrayList<String>();
		    for (String message : messageStringArray) {
		        messageList.add(message.replace("&", "§"));
		    }
        	update.setVersion(version);
        	update.setContext(messageList);
        	
        	if (send) {
        		CheckVersion.sendUpdateInfo(update, receiver);
        	}
	    } catch (SocketTimeoutException exception) {
		    update.setState(2);
		    exception.printStackTrace();
	    } catch (Exception exception) {
		    update.setState(3);
		    exception.printStackTrace();
	    }
	    if (conn != null) {
	    	conn.disconnect();
	    }
	}
    
    public void setSend(CommandSender receiver) {
    	this.send = true;
    	this.receiver = receiver;
    }
}
