package bot.net;

public interface SubscriptionListener {
	public int subscription=0;
	public void SubscriptionUpdated(int Subscription,String data[]);
}
