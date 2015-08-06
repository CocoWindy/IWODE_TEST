package iwode.service.dao.wechat.hibernateImpl;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import iwode.service.dao.wechat.MemberUserDAO;
import iwode.service.model.MemberUser;

public class MemberUserDAOHibernateImpl implements MemberUserDAO {

	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public int save(MemberUser memberUser) {
		// TODO Auto-generated method stub
		return (Integer) sessionFactory.getCurrentSession().save(memberUser);
	}
	
	@Override
	@Transactional
	public MemberUser getById(int id) {
		// TODO Auto-generated method stub
		return (MemberUser) sessionFactory.getCurrentSession().get(MemberUser.class, id);
	}
	
	@Override
	@Transactional
	public MemberUser getByPhone(String phone) {
		// TODO Auto-generated method stub
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MemberUser.class);
		criteria.add(Restrictions.eq("phone", phone));
		MemberUser re = (MemberUser) criteria.uniqueResult();
		return re;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
