package bot.net;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving subscription events.
 * The class that is interested in processing a subscription
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addSubscriptionListener<code> method. When
 * the subscription event occurs, that object's appropriate
 * method is invoked.
 *
 * @see SubscriptionEvent
 */
public interface SubscriptionListener {
	
	/** The subscription. */
	public int subscription=0;
	
	/**
	 * Invoked when Subscription update occurs.
	 *
	 * @param Subscription the subscription
	 * @param data the data
	 */
	public void SubscriptionUpdated(int Subscription,String data[]);
}
