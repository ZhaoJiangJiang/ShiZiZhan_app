package bcz.user;

public class user_Info {
	String account;
	String password;
	String username;
	int isAutoLogin;
	int insistDay;
	int planNum;
	String selectKind;
	String collection;
	String studied;
	String cutted;

	public user_Info() {

	}

	public user_Info(String account, String password, String username, int isAutoLogin, int insistDay, int planNum,
			String selectKind, String collection) {
		super();
		this.account = account;
		this.password = password;
		this.username = username;
		this.isAutoLogin = isAutoLogin;
		this.insistDay = insistDay;
		this.planNum = planNum;
		this.selectKind = selectKind;
		this.collection = collection;
	}

	public user_Info(String account, String password, String username) {
		this.account = account;
		this.password = password;
		this.username = username;
		this.isAutoLogin = 0;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getIsAutoLogin() {
		return isAutoLogin;
	}

	public void setIsAutoLogin(int isRememberPassword) {
		this.isAutoLogin = isRememberPassword;
	}

	public int getInsistDay() {
		return insistDay;
	}

	public void setInsistDay(int insistDay) {
		this.insistDay = insistDay;
	}

	public int getPlanNum() {
		return planNum;
	}

	public void setPlanNum(int planNum) {
		this.planNum = planNum;
	}

	public String getSelectKind() {
		return selectKind;
	}

	public void setSelectKind(String selectKind) {
		this.selectKind = selectKind;
	}
	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}
	public String getStudied() {
		return studied;
	}

	public void setStudied(String studied) {
		this.studied = studied;
	}

	public String getCutted() {
		return cutted;
	}

	public void setCutted(String cutted) {
		this.cutted = cutted;
	}

	@Override
	public String toString() {
		return "user_Info [account=" + account + ", password=" + password + ", username=" + username + "]";
	}
}
