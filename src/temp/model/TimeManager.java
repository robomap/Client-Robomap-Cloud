package temp.model;

public class TimeManager {
	public String getTimeMinutesSeconds(long time){
		String duracion = "";	
		long minuts = 0;
		long segons = 0;
		String stringMinuts = "";
		String stringSegons = "";
		minuts = (time/60);
		segons = (time%60);
		
		if(minuts < 10){
			stringMinuts = ("0"+minuts);
		}else{
			stringMinuts = (""+minuts);
		}
		if(segons < 10){
			stringSegons = ("0"+segons);
		}else{
			stringSegons = (""+segons);
		}
		duracion = (stringMinuts+":"+stringSegons);
		return duracion;
	}
}
