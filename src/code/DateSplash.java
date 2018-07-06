package code;

public class DateSplash {
	
	public static DateSplash[] all = new DateSplash[255];
	
	int id;
	String txt, date = "";
	
	public DateSplash(int id, String txt, String d){
		this.id = id;
		this.txt = txt;
		this.date = d;
		all[id] = this;
	}
	
	public static DateSplash halloween = null;
	public static DateSplash christmas = null;
	public static DateSplash birthday = null;
	public static DateSplash easter = null;
	public static DateSplash birthday1 = null;
	public static DateSplash birthday2 = null;
	public static DateSplash test = null;
	
	public static void initSplashes(){
		halloween = new DateSplash(0, "Happy Halloween!", "31/10");
		christmas = new DateSplash(1, "Merry Christmas!", "31/10");
		birthday = new DateSplash(2, "Happy Brithday Adam!", "10/12");
		easter = new DateSplash(3, "Happy Easter!", "31/10");
		birthday1 = new DateSplash(4, "Happy Birthday Lili!", "09/03");
		birthday2 = new DateSplash(5, "Happy Birthday Lily!", "31/10");
	}
}
