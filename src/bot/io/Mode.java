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

package bot.io;

// TODO: Auto-generated Javadoc
/**
 * The Class Mode.
 */
public final class Mode {
	
	/** The Constant TYPE_IOSERVICE_COMMAND. */
	public static final int TYPE_IOSERVICE_COMMAND = 2;
	
	/** The Constant TYPE_IOSERVICE_REPLY. */
	public static final int TYPE_IOSERVICE_REPLY = 3;
	
	/** The Constant COMMAND_IOSERVICE_LOCK. */
	public static final int COMMAND_IOSERVICE_LOCK = 0;
	
	/** The Constant COMMAND_IOSERVICE_UNLOCK. */
	public static final int COMMAND_IOSERVICE_UNLOCK = 1;
	
	/** The Constant COMMAND_IOSERVICE_SELECT. */
	public static final int COMMAND_IOSERVICE_SELECT = 2;
	
	/** The Constant COMMAND_IOSERVICE_MODE. */
	public static final int COMMAND_IOSERVICE_MODE = 3;
	
	/** The Constant COMMAND_IOSERVICE_OUT. */
	public static final int COMMAND_IOSERVICE_OUT = 4;
	
	/** The Constant COMMAND_IOSERVICE_IN. */
	public static final int COMMAND_IOSERVICE_IN = 5;
	
	/** The Constant REPLY_IOSERVICE_SACK. */
	public static final int REPLY_IOSERVICE_SACK = 0;
	
	/** The Constant REPLY_IOSERVICE_ERR_LOCK. */
	public static final int REPLY_IOSERVICE_ERR_LOCK = 1;
	
	/** The Constant REPLY_IOSERVICE_ERR_MODE. */
	public static final int REPLY_IOSERVICE_ERR_MODE = 2;
	
	/** The Constant REPLY_IOSERVICE_ERR_RESEND. */
	public static final int REPLY_IOSERVICE_ERR_RESEND = 3;
	
	/** The Constant REPLY_IOSERVICE_ERR_SELECTION. */
	public static final int REPLY_IOSERVICE_ERR_SELECTION = 4;
	
	/** The Constant REPLY_IOSERVICE_CLEAR. */
	public static final int REPLY_IOSERVICE_CLEAR = 5;
	
	/** The Constant REPLY_IOSERVICE_DATA. */
	public static final int REPLY_IOSERVICE_DATA = 6;
	
	/** The Constant IN_ANALOG. */
	public final static int IN_ANALOG = 1;
	
	/** The Constant IN_DIGITAL. */
	public final static int IN_DIGITAL = 2;
	
	/** The Constant OUT_SERVO. */
	public final static int OUT_SERVO = -1;
	
	/** The Constant OUT_DIGITAL. */
	public final static int OUT_DIGITAL = -2;
}
