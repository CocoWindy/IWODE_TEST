package iwode.service.dao.wechat.hibernateImpl;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;

import iwode.service.dao.wechat.TailorDAO;
import iwode.service.model.Tailor;

public class TailorDAOHibernateImpl implements TailorDAO {

	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public Tailor getById(int tailorId) {
		// TODO Auto-generated method stub
		return (Tailor)sessionFactory.getCurrentSession().get(Tailor.class, tailorId);
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
