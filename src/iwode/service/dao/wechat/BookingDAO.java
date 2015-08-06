package iwode.service.dao.wechat;

import java.util.List;

import iwode.service.model.Booking;

public interface BookingDAO {
	
	public void save(Booking booking);
	
	public void update(Booking booking);
	
	public Booking getById(int bookId);
	
	public List<Booking> getList(int ownerId,int condition,int start,int offset);
	
}
