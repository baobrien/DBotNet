package bot.net;

/**
 * Packet is used to encapsulate data being passed between bot process. It also
 * contains features to encode and decode chunks of data to be sent. Raw Packet
 * is string encoded like so: (nodesending>nodereceiving
 * :type*command;_data0_data1_etc...) .
 * 
 * @author Brady O'Brien
 * 
 */
public class Packet {

	public static int EParse(String s) throws Exception {
		return new Integer(s).intValue();
	}

	public String raw;
	public String rdata;
	public boolean multmode = false;
	public String mdata[];
	public String data;
	public int nodesending;

	public int nodereceving;

	public int type;
	public int cmd;
	public Packet(int ns, int nr, int type, int cmd, String data) {
		nodesending = ns;
		nodereceving = nr;
		this.type = type;
		this.cmd = cmd;
		this.mdata=new String[1];
		this.mdata[0] = data;
		this.data=data;
		this.Encode();
	}
	public Packet(String raw) throws Exception {
		this.raw = raw;
	}
	private int cno(int t) throws Exception {
		if (t < 0)
			throw new Exception("bot.net.Packet: error 1: malformed packet");
		return t;
	}

	public void Decode() throws Exception {
		if (!raw.endsWith(")") || !raw.startsWith("("))
			System.err.println("bot.net.Packet: malformed packet");
		try {
			nodesending = eibt(raw, "(", ">");
			nodereceving = eibt(raw, ">", ":");
			type = eibt(raw, ":", "*");
			cmd = eibt(raw, "*", ";");
			rdata = raw.substring(cno(raw.indexOf(";")) + 1,
					cno(raw.length() - 1));
			mdata=rdata.split("_");
			mdata=StrRmvNul(mdata);
			if(mdata.length!=0)
			{
				data=mdata[0];
			}else{
				data="";
				mdata=new String[1];
				mdata[0]="";
			}
		} catch (Exception e) {
			System.err.println("bot.net.Packet: packet decoding error");
			throw e;
		}

	}
	private String[] StrRmvNul(String[] s)
	{
		int j=0;
		for(String x:s)
			if(x.equals(""))
				j++;
		String[] stx = new String[s.length-j];
		for(int i=0;i<stx.length;i++)
			stx[i]=s[i+j];
		return stx;
			
	}
	private String encMData(String mtData[])
	{
		String r = new String();
		r="_";
		for(String s:mtData)
		{
			r=r+s+"_";
		}
		return r;
	}
	private int eibt(String s, String b, String e) throws Exception {
		return EParse(s.substring(cno(s.indexOf(b)) + 1, cno(s.indexOf(e))));
	}
	
	public void Encode() {
		rdata=encMData(mdata);
		raw = "(" + nodesending + ">" + nodereceving + ":"
		+ type + "*" + cmd + ";" + rdata + ")";
	}

}
