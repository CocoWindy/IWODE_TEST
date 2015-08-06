package iwode.service.manager.wechat;

import com.xiao.util.json.xjson.JArrayObj;
import com.xiao.util.json.xjson.JMapObj;

import iwode.service.model.Booking;
import iwode.service.model.MemberUser;
import iwode.service.model.Tailor;
import iwode.service.model.WechatLink;

public interface WechatManager {

	public boolean isBanded(String openId);
	
	public MemberUser createNewUser(String phone);
	
	public boolean banding(String openId,String phone);
	
	public Booking booking(String openId,String phone,String name,String date,String address,String height,String weight);
	
	public boolean cancel(int bookId);
	
	public JArrayObj getBookList(String openId,int condition,int start,int offset);
	
	public Booking getBookById(int bookId);
	
	public JArrayObj getCouponList(String openId,int condition,int start,int offset);
	
	public boolean comment(int bookId,String context,int score,String phone);
	
	public boolean sendMessage(String phone,String msg);
	
	public Tailor getTailorById(int tailorId);
	
}
