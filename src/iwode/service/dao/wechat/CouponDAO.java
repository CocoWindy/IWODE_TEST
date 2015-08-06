package iwode.service.dao.wechat;

import java.util.List;

import iwode.service.model.Coupon;

public interface CouponDAO {

	public Coupon getById(int couponId);
	
	public List<Coupon> getList(int ownerId,int condition,int start,int offset);
	
}
