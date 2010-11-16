package bot.net;

// TODO: Auto-generated Javadoc
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

	/*public static int EParse(String s) throws Exception {
		return new Integer(s).intValue();
	}*/

	/** The raw. */
	public String raw;
	
	/** The rdata. */
	public String rdata;
	
	/** The multmode. */
	public boolean multmode = false;
	
	/** The mdata. */
	public String mdata[];
	
	/** The data. */
	public String data;
	
	/** The nodesending. */
	public int nodesending;

	/** The nodereceving. */
	public int nodereceving;

	/** The type. */
	public int type;
	
	/** The cmd. */
	public int cmd;
	
	/**
	 * Instantiates a new packet.
	 *
	 * @param ns the ns
	 * @param nr the nr
	 * @param type the type
	 * @param cmd the cmd
	 * @param data the data
	 */
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
	
	/**
	 * Instantiates a new packet.
	 *
	 * @param raw the raw
	 * @throws Exception the exception
	 */
	public Packet(String raw) throws Exception {
		this.raw = raw;
	}
	
	/**
	 * Cno.
	 *
	 * @param t the t
	 * @return the int
	 * @throws Exception the exception
	 */
	private int cno(int t) throws Exception {
		if (t < 0)
			throw new Exception("bot.net.Packet: error 1: malformed packet");
		return t;
	}

	/**
	 * Decode.
	 *
	 * @throws Exception the exception
	 */
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
	
	/**
	 * Str rmv nul.
	 *
	 * @param s the s
	 * @return the string[]
	 */
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
	
	/**
	 * Enc m data.
	 *
	 * @param mtData the mt data
	 * @return the string
	 */
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
	
	/**
	 * Eibt.
	 *
	 * @param s the s
	 * @param b the b
	 * @param e the e
	 * @return the int
	 * @throws Exception the exception
	 */
	private int eibt(String s, String b, String e) throws Exception {
		return new Integer(s.substring(cno(s.indexOf(b)) + 1, cno(s.indexOf(e)))).intValue();
	}
	
	/**
	 * Gets the raw.
	 *
	 * @return the raw
	 */
	public String getRaw() {
		return raw;
	}
	
	/**
	 * Sets the raw.
	 *
	 * @param raw the new raw
	 */
	public void setRaw(String raw) {
		this.raw = raw;
	}
	
	/**
	 * Gets the rdata.
	 *
	 * @return the rdata
	 */
	public String getRdata() {
		return rdata;
	}
	
	/**
	 * Sets the rdata.
	 *
	 * @param rdata the new rdata
	 */
	public void setRdata(String rdata) {
		this.rdata = rdata;
	}
	
	/**
	 * Gets the mdata.
	 *
	 * @return the mdata
	 */
	public String[] getMdata() {
		return mdata;
	}
	
	/**
	 * Sets the mdata.
	 *
	 * @param mdata the new mdata
	 */
	public void setMdata(String[] mdata) {
		this.mdata = mdata;
	}
	
	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public String getData() {
		return data;
	}
	
	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(String data) {
		this.data = data;
	}
	
	/**
	 * Gets the nodesending.
	 *
	 * @return the nodesending
	 */
	public int getNodesending() {
		return nodesending;
	}
	
	/**
	 * Sets the nodesending.
	 *
	 * @param nodesending the new nodesending
	 */
	public void setNodesending(int nodesending) {
		this.nodesending = nodesending;
	}
	
	/**
	 * Gets the nodereceving.
	 *
	 * @return the nodereceving
	 */
	public int getNodereceving() {
		return nodereceving;
	}
	
	/**
	 * Sets the nodereceving.
	 *
	 * @param nodereceving the new nodereceving
	 */
	public void setNodereceving(int nodereceving) {
		this.nodereceving = nodereceving;
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(int type) {
		this.type = type;
	}
	
	/**
	 * Gets the cmd.
	 *
	 * @return the cmd
	 */
	public int getCmd() {
		return cmd;
	}
	
	/**
	 * Sets the cmd.
	 *
	 * @param cmd the new cmd
	 */
	public void setCmd(int cmd) {
		this.cmd = cmd;
	}
	
	/**
	 * Encode.
	 */
	public void Encode() {
		rdata=encMData(mdata);
		raw = "(" + nodesending + ">" + nodereceving + ":"
		+ type + "*" + cmd + ";" + rdata + ")";
	}

}
