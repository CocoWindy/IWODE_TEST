package iwode.service.manager.wechat.defaultImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;

import com.xiao.util.json.xjson.JArrayObj;
import com.xiao.util.json.xjson.JMapObj;

import freemarker.ext.beans.BeansWrapper;
import iwode.service.dao.wechat.BookingDAO;
import iwode.service.dao.wechat.CouponDAO;
import iwode.service.dao.wechat.MemberUserDAO;
import iwode.service.dao.wechat.TailorDAO;
import iwode.service.dao.wechat.WechatLinkDAO;
import iwode.service.manager.wechat.WechatManager;
import iwode.service.model.Booking;
import iwode.service.model.Coupon;
import iwode.service.model.MemberUser;
import iwode.service.model.Tailor;
import iwode.service.model.WechatLink;
import util.MatchTailor;

public class WechatManagerDefaultImpl implements WechatManager {

	private WechatLinkDAO wechatLinkDAO;
	private MemberUserDAO memberUserDAO;
	private BookingDAO bookingDAO;
	private CouponDAO couponDAO;
	private TailorDAO tailorDAO;
	
	@Override
	public boolean banding(String openId, String phone) {
		// TODO Auto-generated method stub
		try {
			WechatLink wechatLink = null;
			wechatLink = wechatLinkDAO.getByOpenId(openId);
			
			MemberUser memberUser = memberUserDAO.getByPhone(phone);
			if(memberUser == null){
				memberUser = createNewUser(phone);
			}
		
			if(wechatLink == null){
				wechatLink = new WechatLink();
				wechatLink.setOpenId(openId);
				wechatLink.setStatus(0);
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			wechatLink.setMemberuserId(memberUser.getId());
			wechatLink.setModifyTime(sdf.format(new Date()));
			
			wechatLinkDAO.saveOrUpdate(wechatLink);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean sendMessage(String phone, String msg) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isBanded(String openId) {
		// TODO Auto-generated method stub
		if(wechatLinkDAO.getByOpenId(openId) != null){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public MemberUser createNewUser(String phone) {
		
		MemberUser memberUser = new MemberUser();
		memberUser.setPhone(phone);
		memberUser.setName(phone);
		memberUserDAO.save(memberUser);
		return memberUser;
	};

	@Override
	public Booking booking(String openId,String phone, String name, String date, String address, String height, String weight) {
		// TODO Auto-generated method stub
		
		try {
			WechatLink wechatLink = wechatLinkDAO.getByOpenId(openId);
			if(wechatLink == null){
				return null;
			}
			MemberUser memberUser = memberUserDAO.getById(wechatLink.getMemberuserId());
			if(memberUser == null){
				return null;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Booking booking = new Booking();
			booking.setOrderNum(wechatLink.getMemberuserId()+"+"+sdf.format(new Date()));
			booking.setOwnerId(wechatLink.getMemberuserId());
			booking.setUserName(memberUser.getName());
			booking.setPhone(memberUser.getPhone());
			booking.setStartTime(date);
			booking.setAddress(address);
			booking.setHeight(height);
			booking.setWeight(weight);
			booking.setStatus(0);
			booking.setTailorId(MatchTailor.match());
			booking.setIsComment(0);
			
			bookingDAO.save(booking);
			return booking;
			
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	@Override
	public boolean cancel(int bookId) {
		// TODO Auto-generated method stub
		try {
			Booking booking = bookingDAO.getById(bookId);
			booking.setStatus(1);
			bookingDAO.update(booking);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	@Override
	public JArrayObj getBookList(String openId,int condition, int start, int offset) {
		// TODO Auto-generated method stub
		JArrayObj reJson = new JArrayObj();
		
		WechatLink wechatLink = wechatLinkDAO.getByOpenId(openId);
		
		List<Booking> reList = bookingDAO.getList(wechatLink.getMemberuserId(),condition, start, offset);
		
		for(Booking booking : reList){
			Tailor tailor = tailorDAO.getById(booking.getTailorId());
			
			JMapObj box = new JMapObj();
			box.put("bookId", booking.getId());
			box.put("name", tailor.getName());
			box.put("phone", tailor.getPhone());
			box.put("address", booking.getAddress());
			box.put("date", booking.getStartTime());
			box.put("status",booking.getStatus());
			
			reJson.add(box);
		}
		
		return reJson;
	}

	@Override
	public Booking getBookById(int bookId) {
		// TODO Auto-generated method stub
		return bookingDAO.getById(bookId);
	}

	@Override
	public JArrayObj getCouponList(String openId,int condition, int start, int offset) {
		// TODO Auto-generated method stub
		JArrayObj reJson = new JArrayObj();
		
		WechatLink wechatLink = wechatLinkDAO.getByOpenId(openId);
		
		List<Coupon> reList = couponDAO.getList(wechatLink.getMemberuserId(),condition, start, offset);
		
		for(Coupon coupon : reList){
			
			JMapObj box = new JMapObj();
			box.put("code", coupon.getCode());
			box.put("name", coupon.getName());
			box.put("deadline", coupon.getEndTime());
			
			reJson.add(box);
		}
		
		return reJson;
	}

	@Override
	public boolean comment(int bookId, String context, int score, String phone) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Tailor getTailorById(int tailorId) {
		// TODO Auto-generated method stub
		return tailorDAO.getById(tailorId);
	}
	
	public WechatLinkDAO getWechatLinkDAO() {
		return wechatLinkDAO;
	}

	public void setWechatLinkDAO(WechatLinkDAO wechatLinkDAO) {
		this.wechatLinkDAO = wechatLinkDAO;
	}

	public MemberUserDAO getMemberUserDAO() {
		return memberUserDAO;
	}

	public void setMemberUserDAO(MemberUserDAO memberUserDAO) {
		this.memberUserDAO = memberUserDAO;
	}

	public BookingDAO getBookingDAO() {
		return bookingDAO;
	}

	public void setBookingDAO(BookingDAO bookingDAO) {
		this.bookingDAO = bookingDAO;
	}

	public CouponDAO getCouponDAO() {
		return couponDAO;
	}

	public void setCouponDAO(CouponDAO couponDAO) {
		this.couponDAO = couponDAO;
	}

	public TailorDAO getTailorDAO() {
		return tailorDAO;
	}

	public void setTailorDAO(TailorDAO tailorDAO) {
		this.tailorDAO = tailorDAO;
	}
}
