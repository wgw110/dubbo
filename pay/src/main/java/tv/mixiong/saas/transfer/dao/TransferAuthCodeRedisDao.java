package tv.mixiong.saas.transfer.dao;

import mobi.mixiong.cache.BaseValueCache;
import org.springframework.stereotype.Repository;

import static org.apache.http.client.methods.RequestBuilder.delete;

@Repository
public class TransferAuthCodeRedisDao extends BaseValueCache<String,String> {


	public final static String PRE = "authcode:";
	public final static String LAST_PRE = "authcode:last";

	// 验证码有效期
	public final static Long EXPIRE = 6 * 60L;
	// 再次发送验证码间隔
	private static final Long NEXT_EXPIRE = 60L;

	public void insertAuthCode(String passport, String authcode) {
		set(PRE + passport, authcode, EXPIRE);
		set(LAST_PRE + passport, "" + System.currentTimeMillis()+ NEXT_EXPIRE);
	}

	public String getAuthCode(String passport) {
		return get(PRE + passport);
	}

	public Boolean delAuthCode(String passport) {
		delete(PRE + passport);
		return true;
	}

	public boolean checkResendAble(String passport) {
		String lastTimeStr = get(LAST_PRE + passport);
		if (lastTimeStr != null) {
			long lastTime = Long.valueOf(lastTimeStr);
			if (lastTime + 60L * 1000L>= System.currentTimeMillis()) {
				return false;
			}
		}
		return true;
	}

	@Override
	protected String getPrefix() {
		return "mx:transfer:";
	}
}
