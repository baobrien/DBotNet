package bot.net;

import java.util.*;

public class Subscriber implements Runnable {
	Vector<SubscriptionListener> listeners;
	NetIF netif;
	public Subscriber(NetIF ni)
	{
		netif=ni;
	}
	public void Subscribe(int subscribenode,int subid,String exdata)
	{
		Packet p = new Packet(0,subscribenode,Publisher.TYPE_SUBCONT,Publisher.CMD_NEWSUB,"");
		p.mdata=new String[2];
		p.mdata[0]=Integer.toString(subid);
		p.mdata[1]=exdata;
		try{netif.Send(p);}catch(Exception e){e.printStackTrace();}
	}
	public void run() {
		try{
			while(true)
			{
				Packet p = netif.TypeDeSpool(Publisher.TYPE_SUBDATA);
				try{p.Decode();}catch(Exception e){e.printStackTrace();}
				announceListeners(p.mdata,new Integer(p.mdata[p.mdata.length-1]).intValue());
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	private void announceListeners(String d[],int sub)
	{
		for(SubscriptionListener l:listeners)
		{
			if(l.subscription==sub)
				l.SubscriptionUpdated(sub, d);
		}
	}
}
