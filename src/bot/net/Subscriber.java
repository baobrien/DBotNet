package bot.net;

import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Subscriber.
 */
public class Subscriber implements Runnable {
	
	/** The listeners. */
	Vector<SubscriptionListener> listeners;
	
	/** The netif. */
	NetIF netif;
	
	/**
	 * Instantiates a new subscriber.
	 *
	 * @param ni the ni
	 */
	public Subscriber(NetIF ni)
	{
		netif=ni;
	}
	
	/**
	 * Subscribe.
	 *
	 * @param subscribenode the subscribenode
	 * @param subid the subid
	 * @param exdata the exdata
	 */
	public void Subscribe(int subscribenode,int subid,String exdata)
	{
		Packet p = new Packet(0,subscribenode,Publisher.TYPE_SUBCONT,Publisher.CMD_NEWSUB,"");
		p.mdata=new String[2];
		p.mdata[0]=Integer.toString(subid);
		p.mdata[1]=exdata;
		try{netif.Send(p);}catch(Exception e){e.printStackTrace();}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
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
	
	/**
	 * Announce listeners.
	 *
	 * @param d the d
	 * @param sub the sub
	 */
	private void announceListeners(String d[],int sub)
	{
		for(SubscriptionListener l:listeners)
		{
			if(l.subscription==sub)
				l.SubscriptionUpdated(sub, d);
		}
	}
}
