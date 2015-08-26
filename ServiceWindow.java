import java.util.Random;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * 没有把VIP窗口和快速窗口做成子类，是因为实际业务中的普通窗口可以随时被设置为VIP窗口和快速窗口。
 * */
public class ServiceWindow {
	private static Logger logger = Logger.getLogger("interview.bankqueue");
	private CustomerType type = CustomerType.COMMON;
	private int number = 1;

	public CustomerType getType() {
		return type;
	}

	public void setType(CustomerType type) {
		this.type = type;
	}
	
	public void setNumber(int number){
		this.number = number;
	}
	
	public void start(){
		Executors.newSingleThreadExecutor().execute(
				new Runnable(){
					public void run(){
						//下面这种写法的运行效率低，最好是把while放在case下面
						while(true){
							switch(type){
								case COMMON:
									commonService();
									break;
								case EXPRESS:
									expressService();
									break;
								case VIP:
									vipService();
									break;
							}
						}
					}
				}
		);
	}
	
	private void commonService(){
		String windowName = "No." + number + "number" + type + "window";		
		System.out.println(windowName + "work for common");
		Integer serviceNumber = NumberMachine.getInstance().getCommonManager().fetchNumber();		
		if(serviceNumber != null ){
			System.out.println(windowName + "working" + serviceNumber + "number common member");		
			int maxRandom = Constants.MAX_SERVICE_TIME - Constants.MIN_SERVICE_TIME;
			int serviceTime = new Random().nextInt(maxRandom)+1 + Constants.MIN_SERVICE_TIME;
	
			try {
				Thread.sleep(serviceTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
			System.out.println(windowName + "done" + serviceNumber + "number common member,time" + serviceTime/1000 + "秒");		
		}else{
			System.out.println(windowName + "no job,waiting 1 min");		
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}				
		}
	}
	
	private void expressService(){
		Integer serviceNumber = NumberMachine.getInstance().getExpressManager().fetchNumber();
		String windowName = "No." + number + "number" + type + "window";	
		System.out.println(windowName + "work for fast");		
		if(serviceNumber !=null){
			System.out.println(windowName + "working" + serviceNumber + "number fast member");			
			int serviceTime = Constants.MIN_SERVICE_TIME;
			try {
				Thread.sleep(serviceTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
			System.out.println(windowName + "done" + serviceNumber + "number fast member,time" + serviceTime/1000 + "秒");		
		}else{
			System.out.println(windowName + "no fast member");				
			commonService();
		}
	}
	
	private void vipService(){

		Integer serviceNumber = NumberMachine.getInstance().getVipManager().fetchNumber();
		String windowName = "NO." + number + "number" + type + "window";	
		System.out.println(windowName + "work for VIP");			
		if(serviceNumber !=null){
			System.out.println(windowName + "working" + serviceNumber + "number VIP member");			
			int maxRandom = Constants.MAX_SERVICE_TIME - Constants.MIN_SERVICE_TIME;
			int serviceTime = new Random().nextInt(maxRandom)+1 + Constants.MIN_SERVICE_TIME;
			try {
				Thread.sleep(serviceTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
			System.out.println(windowName + "done" + serviceNumber + "number VIP member,time" + serviceTime/1000 + "秒");		
		}else{
			System.out.println(windowName + "no VIP member");				
			commonService();
		}	
	}
}
