public enum CustomerType {
	COMMON,EXPRESS,VIP;
	public String toString(){
		String name = null;
		switch(this){
		case COMMON:
			name = "common";
			break;
		case EXPRESS:
			name = "fast";
			break;
		case VIP:
			name = name();
			break;
		}
		return name;
	}
}
