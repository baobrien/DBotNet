package bot.net;
import java.io.*;
import java.net.*;
import java.util.*;
// TODO: Auto-generated Javadoc

/**
 * The Class Publisher.
 */
public class Publisher implements Runnable {
		
		/** The Network Interface. */
		NetIF nif;
		
		/** The subscribers. */
		Vector<Integer> subscribers;
		
		/** The extra included data. */
		String exdata[];
		
		/** The servid. */
		int servid;
	
	/**
	 * Instantiates a new publisher.
	 *
	 * @param nf the nf
	 * @param sc the sc
	 */
	public Publisher(NetIF nf,int sc)
	{
		nif=nf;
		servid=sc;
		subscribers = new Vector<Integer>(32);
		exdata=new String[32];
	}
	
	/**
	 * Publish.
	 *
	 * @param data the data
	 * @throws Exception the exception
	 */
	public void Publish(String data[]) throws Exception
	{
		String rdata[]=new String[data.length+1];
		rdata[data.length]=Integer.toString(servid);
		for(int i=0;i<data.length;i++)
		{
			rdata[i]=data[i];
		}
		for(Integer i:subscribers)
		{
			Packet p = new Packet(0,i.intValue(),TYPE_SUBDATA,CMD_SUBDATA,"");
			p.mdata=rdata;
			p.Encode();
			nif.Send(p);
		}
	}
	
	/**
	 * Publish matching.
	 *
	 * @param data the data
	 * @param match the match
	 * @throws Exception the exception
	 */
	public void PublishMatching(String data[],String match) throws Exception
	{
		String rdata[]=new String[data.length+1];
		rdata[data.length]=Integer.toString(servid);
		for(int i=0;i<data.length;i++)
		{
			rdata[i]=data[i];
		}
		for(Integer i:subscribers)
		{
			if(exdata[i.intValue()].matches(match))
			{
				Packet p = new Packet(0,i.intValue(),TYPE_SUBDATA,CMD_SUBDATA,"");
				p.mdata=rdata;
				p.Encode();
				nif.Send(p);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		while(true)
		{
			Packet p = nif.TypeDeSpool(TYPE_SUBCONT);
			try {p.Decode();} catch (Exception e) {e.printStackTrace();} //Decode packet (probably don't need to)
			if(p.cmd==CMD_NEWSUB&&(new Integer(p.mdata[0]).intValue())==servid){
				boolean already = false;
				for(Integer i:subscribers)
					if (i.intValue()==p.nodesending)
						already=true;
				if(!already)
					subscribers.add(p.nodesending);
				if(p.mdata.length>1)
					exdata[p.nodesending] = p.mdata[1];
				try {nif.Send(new Packet(0,p.nodesending,TYPE_SUBCONT,CMD_SUBACK,""));} catch (Exception e) {e.printStackTrace();}
			}
			if(p.cmd==CMD_ENDSUB&&(new Integer(p.mdata[0]).intValue())==servid){
				subscribers.remove(subscribers.indexOf(p.nodesending));
				try {nif.Send(new Packet(0,p.nodesending,TYPE_SUBCONT,CMD_SUBACK,""));} catch (Exception e) {e.printStackTrace();}
			}
		}
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception
	{
		BufferedReader kb=new BufferedReader(new InputStreamReader(System.in));
		NetIF ni=new NetIF(new Socket("localhost",5000),8);
		ni.StartSpooler();
		Publisher publ = new Publisher(ni,8);
		Thread t = new Thread(publ);
		t.start();
		while(true){
			String p = kb.readLine();
			String x[]=new String[1];
			x[0]=p;
			publ.Publish(x);
		}
	}
	
	/** The Constant TYPE_SUBCONT. */
	public static final int TYPE_SUBCONT=5;
	
	/** The Constant TYPE_SUBDATA. */
	public static final int TYPE_SUBDATA=6;
	
	/** The Constant CMD_NEWSUB. */
	public static final int CMD_NEWSUB=0;
	
	/** The Constant CMD_ENDSUB. */
	public static final int CMD_ENDSUB=1;
	
	/** The Constant CMD_SUBACK. */
	public static final int CMD_SUBACK=2;
	
	/** The Constant CMD_SUBDATA. */
	public static final int CMD_SUBDATA=0;
}
