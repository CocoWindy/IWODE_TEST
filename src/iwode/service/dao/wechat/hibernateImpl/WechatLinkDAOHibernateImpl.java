package iwode.service.dao.wechat.hibernateImpl;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import iwode.service.dao.wechat.WechatLinkDAO;
import iwode.service.model.WechatLink;

public class WechatLinkDAOHibernateImpl implements WechatLinkDAO {

	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public void saveOrUpdate(WechatLink wechatLink) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().saveOrUpdate(wechatLink);
	}

	@Override
	@Transactional
	public WechatLink getById(int id) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().get(WechatLink.class,id);
		return null;
	}

	@Override
	@Transactional
	public WechatLink getByOpenId(String openId) {
		// TODO Auto-generated method stub
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(WechatLink.class);
		criteria.add(Restrictions.eq("openId", openId));
		return (WechatLink)criteria.uniqueResult();
	}

	@Override
	@Transactional
	public WechatLink getByByMemberuserId(String memberuserId) {
		// TODO Auto-generated method stub
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(WechatLink.class);
		criteria.add(Restrictions.eq("memberuserId", memberuserId));
		return (WechatLink)criteria.uniqueResult();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
}
