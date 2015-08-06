package iwode.service.dao.wechat.hibernateImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import iwode.service.dao.wechat.BookingDAO;
import iwode.service.model.Booking;

public class BookingDAOHibernateImpl implements BookingDAO {

	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public void save(Booking booking) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(booking);
	}
	
	@Override
	@Transactional
	public void update(Booking booking) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(booking);
	}

	@Override
	@Transactional
	public Booking getById(int bookId) {
		// TODO Auto-generated method stub
		return (Booking) sessionFactory.getCurrentSession().get(Booking.class, bookId);
	}

	@Override
	@Transactional
	public List<Booking> getList(int ownerId,int condition, int start, int offset) {
		// TODO Auto-generated method stub
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Booking.class);
		criteria.add(Restrictions.eq("ownerId", ownerId));
		criteria.setFirstResult(start);
		criteria.setMaxResults(offset);
		
		switch (condition) {
			case 0:{
				//全部
				break;
			}
			case 1:{
				//预约中
				criteria.add(Restrictions.eq("status", 0));
				break;
			}
			case 2:{
				//已预约
				criteria.add(Restrictions.and(Restrictions.eq("status", 2),Restrictions.eq("status", 3)));
				break;
			}
			case 3:{
				//未评价
				criteria.add(Restrictions.eq("status", 4));
				criteria.add(Restrictions.eq("isComment", 0));
				break;
			}
			default:
				break;
		}
		
		List<Booking> re = criteria.list();
		
		return re;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
