package bcz.user;

import java.util.Iterator;
import java.util.List;

public class user_Test {
	public static void main(String[] args) {
		user_Bean bean = new user_Bean();
		List<user_Info> data = bean.getAll();
		Iterator<user_Info> iterator = data.iterator();
		while (iterator.hasNext()) {
			user_Info info = iterator.next();
			System.out.println(info.toString());
		}
	}
}
