package gob.sis.simos.service.impl;

import gob.sis.simos.db.DBHelper;
import gob.sis.simos.entity.Account;
import gob.sis.simos.service.LoginService;
import gob.sis.simos.soap.SimosSoapServices;
import java.io.IOException;
import java.sql.SQLException;
import org.xmlpull.v1.XmlPullParserException;
import android.content.Context;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

public class LoginServiceImpl implements LoginService {

	private Context _context;
	private DBHelper dbhelper;

	@Override
	public void setContext(Context context) {
		this._context = context;
	}

	@Override
	public String loginFromServer(String username, String password)
			throws IOException, XmlPullParserException {
		SimosSoapServices services = new SimosSoapServices(this._context);
		return services.Login(username, password);
	}

	@Override
	public Account getStoredAccount(String userLogin, String password) {
		return null;
	}

	@Override
	public Account getStoredAccount() {
		Dao<Account, String> dao;
		try {
			dao = getHelper().getAccountDao();
			QueryBuilder<Account, String> qb = dao.queryBuilder();
			Account account = qb.limit((long)1).queryForFirst();
			return account;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int storeAccount(Account account) {
		Dao<Account, String> dao;
		try {
			dao = getHelper().getAccountDao();
			dao.create(account);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}

	public DBHelper getHelper() {
		if (this.dbhelper == null) {
			this.dbhelper = OpenHelperManager.getHelper(this._context,
					DBHelper.class);
		}
		return this.dbhelper;
	}

}
