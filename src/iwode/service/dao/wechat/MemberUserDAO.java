package iwode.service.dao.wechat;

import iwode.service.model.MemberUser;

public interface MemberUserDAO {

	public int save(MemberUser memberUser);
	
	public MemberUser getById(int id);
	
	public MemberUser getByPhone(String phone);
}
