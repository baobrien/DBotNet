/*----------------------------------------------------------------------------------------------*/
/*Copyright 2010 Brady O'Brien. All rights reserved.											*/
/*																								*/
/* Redistribution and use in source and binary forms, with or without modification, are			*/
/*permitted provided that the following conditions are met:										*/
/*																								*/
/*   1. Redistributions of source code must retain the above copyright notice, this list of		*/
/*      conditions and the following disclaimer.												*/
/*																								*/
/*   2. Redistributions in binary form must reproduce the above copyright notice, this list		*/
/*      of conditions and the following disclaimer in the documentation and/or other materials	*/
/*      provided with the distribution.															*/
/*																								*/
/*THIS SOFTWARE IS PROVIDED BY BRADY O'BRIEN ``AS IS'' AND ANY EXPRESS OR IMPLIED				*/
/*WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND		*/
/*FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BRADY O'BRIEN OR			*/
/*CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR			*/
/*CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR		*/
/*SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON		*/
/*ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING			*/
/*NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF			*/
/*ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.													*/
/*																								*/
/*The views and conclusions contained in the software and documentation are those of the		*/
/*authors and should not be interpreted as representing official policies, either expressed		*/
/*or implied, of Brady O'Brien.																	*/
/*----------------------------------------------------------------------------------------------*/

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
