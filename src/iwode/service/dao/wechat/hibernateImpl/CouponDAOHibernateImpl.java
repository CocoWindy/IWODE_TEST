package iwode.service.dao.wechat.hibernateImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import iwode.service.dao.wechat.CouponDAO;
import iwode.service.model.Booking;
import iwode.service.model.Coupon;

public class CouponDAOHibernateImpl implements CouponDAO {

	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public Coupon getById(int couponId) {
		// TODO Auto-generated method stub
		return (Coupon)sessionFactory.getCurrentSession().get(Coupon.class, couponId);
	}

	@Override
	@Transactional
	public List<Coupon> getList(int ownerId,int condition, int start, int offset) {
		// TODO Auto-generated method stub
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Coupon.class);
		criteria.add(Restrictions.eq("ownerId", ownerId));
		criteria.setFirstResult(start);
		criteria.setMaxResults(offset);
		
		switch (condition) {
			case 0:{
				//全部
				break;
			}
			case 1:{
				//未使用
				criteria.add(Restrictions.eq("status", 0));
				break;
			}
			case 2:{
				//已使用
				criteria.add(Restrictions.eq("status", 1));
				break;
			}
			case 3:{
				//已过期
				criteria.add(Restrictions.eq("status", 2));
				break;
			}
			default:
				break;
		}
		
		List<Coupon> re = criteria.list();
		
		return re;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
