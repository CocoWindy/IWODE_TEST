package iwode.service.dao.wechat;

import iwode.service.model.WechatLink;

public interface WechatLinkDAO {
	
	public void saveOrUpdate(WechatLink wechatLink);
	
	public WechatLink getById(int id);
	
	public WechatLink getByOpenId(String openId);
	
	public WechatLink getByByMemberuserId(String memberuserId);
	
}
