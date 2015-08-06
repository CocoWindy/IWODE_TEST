package iwode.service.action.wechat;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.xiao.Socket.WebClient.KeyValuePair;
import com.xiao.util.json.xjson.JArrayObj;
import com.xiao.util.json.xjson.JMapObj;
import com.xiao.util.json.xjson.JSONDecodeException;
import com.xiao.util.json.xjson.JSONObjFactory;

import iwode.service.action.BaseAction;
import iwode.service.manager.wechat.WechatManager;
import iwode.service.model.Booking;
import iwode.service.model.MemberUser;
import iwode.service.model.Tailor;
import iwode.service.model.WechatLink;
import util.WechatWebServiceUtil;

public class WechatAction extends BaseAction {

	/**
	 * 微信公众号appId
	 */
	public static final String APP_ID = "wxe0d77cf02b43616b";
	
	/**
	 * 微信公众号appSecret
	 */
	public static final String APP_SECRET = "cd4fe25341381af40fc15a1d2744b2a0";
 	
	/**
	 * 预约成功跳转的URL
	 */
	public static final String BOOK_SUCCESS_URL = "";
	
	private WechatManager wechatManager;
	
	public String test(){
		System.out.println("print test");
		setResult("test ok");	
		return "result";
	}
	
	
	/**
	 * 获取用户openId，并检测该openId是否已绑定
	 * 
	 * @param String code 
	 * 					微信分配给微信用户的一个具有时效性的用于获取openId的字符串
	 * 
	 * @return 
	 */
	public  String checkOpenId(){
		
		String code = getParam("code");
		
		/**
		 * 返回结果json
		 */
		JMapObj reJson = new JMapObj();

		String path = "/sns/oauth2/access_token";

		KeyValuePair[] pairs = new KeyValuePair[4];
		pairs[0] = new KeyValuePair("appid",APP_ID);
		pairs[1] = new KeyValuePair("secret",APP_SECRET);
		pairs[2] = new KeyValuePair("code",code);
		pairs[3] = new KeyValuePair("grant_type", "authorization_code");
		
		String returnJSON = WechatWebServiceUtil.doGet(path, pairs);
		
		/**
		 * 解析返回的json
		 */
		JMapObj mapObj = null;
		try {
			mapObj = (JMapObj)JSONObjFactory.decode(returnJSON);
		} catch (JSONDecodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(mapObj != null){
			if(mapObj.get("errcode") == null){
				reJson.put("code", 200);
				reJson.put("msg", "查询成功");
				
				String openId = (String)mapObj.get("openid");
				reJson.put("openId", openId);

				
				if(wechatManager.isBanded(openId)){
					reJson.put("isBanded", 1);
				}else{
					reJson.put("isBanded", 0);
				}
			}else{
				reJson.put("code", 406);
				reJson.put("msg", "code无效");
			}
		}else{
			reJson.put("code", 500);
			reJson.put("msg", "服务器错误");
		}
		
		setResult(reJson.toJSONString());
		
		
		
		return "result";
	}
	
	

	/**
	 * 预约
	 * 
	 * @param String openId 
	 * 					用户微信号的openId
	 * 
	 * @param String date 
	 * 					预约日期 格式yyyy-MM-dd HH:mm
	 * 
	 * @param String address 
	 * 					预约地址
	 * 
	 * @param String height 
	 * 					身高
	 * 
	 * @param String weight 
	 * 					体重
	 * 
	 * @param String phone 
	 * 					预约手机号
	 * 
	 * @param String name 
	 * 					预约人姓名
	 * 
	 * @return 
	 */
	public String booking(){
		
		JMapObj reJson = new JMapObj();
		try {
			
			String openId = getParam("openId");
			String date = getParam("date");
			String address = getParam("address");
			String height = getParam("height");
			String weight = getParam("weight");
			String phone = getParam("phone");
			String name = getParam("name");
			
			
			if(wechatManager.isBanded(openId)){
				
				/**
				 * 对参数进行校验
				 */
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date bookDate = null;
				try{
					bookDate = sdf.parse(date);
				}catch(Exception e){
					reJson.put("code", 402);
					reJson.put("msg", "预约时间格式错误");
					setResult(reJson.toJSONString());
					return "result";
				}
				
				if(address.length() <= 0 || address.length() > 255){
					reJson.put("code", 403);
					reJson.put("msg", "预约地址长度错误");
					setResult(reJson.toJSONString());
					return "result";
				}
				if(height.length() <= 0 || height.length() > 255){
					reJson.put("code", 404);
					reJson.put("msg", "身高长度错误");
					setResult(reJson.toJSONString());
					return "result";
				}
				if(weight.length() <= 0 || weight.length() > 255){
					reJson.put("code", 405);
					reJson.put("msg", "体重长度错误");
					setResult(reJson.toJSONString());
					return "result";
				}
				if(phone.length() <= 0 || phone.length() > 255){
					reJson.put("code", 406);
					reJson.put("msg", "预约手机号长度错误");
					setResult(reJson.toJSONString());
					return "result";
				}
				if(name.length() <= 0 || name.length() > 255){
					reJson.put("code", 407);
					reJson.put("msg", "预约人姓名长度错误");
					setResult(reJson.toJSONString());
					return "result";
				}
				
				/**
				 * 新增预约
				 */
				Booking reBooking = wechatManager.booking(openId,phone, name, date, address, height, weight);
				if(reBooking != null){
					
					Tailor tailor = wechatManager.getTailorById(reBooking.getTailorId());
					
					KeyValuePair[] pairs = new KeyValuePair[5];
					pairs[0] = new KeyValuePair("bookId",Integer.toString(reBooking.getId()));
					pairs[1] = new KeyValuePair("name",tailor.getName());
					pairs[2] = new KeyValuePair("phone",tailor.getPhone());
					pairs[3] = new KeyValuePair("date",date);
					pairs[4] = new KeyValuePair("address",address);
					
					String reUrl = BOOK_SUCCESS_URL + WechatWebServiceUtil.turnPairsToString(pairs);
					
					reJson.put("code", 200);
					reJson.put("msg", "预约成功");
					reJson.put("url", reUrl);
				}else{
					reJson.put("code", 600);
					reJson.put("msg", "数据库错误");
				}
				
			}else{
				reJson.put("code", 401);
				reJson.put("msg", "该微信号未绑定");
			}
		} catch (Exception e) {
			reJson.put("code", 500);
			reJson.put("msg", "服务器错误");
		}
		
		setResult(reJson.toJSONString());
		
		return "result";
	}
	
	
	/**
	 * 绑定
	 * 
	 * @param String openId 
	 * 					用户微信号的openId
	 * 
	 * @param String phone 
	 * 					要绑定的手机号
	 * 
	 * @param String captcha 
	 * 					短信验证码
	 * 
	 * @return 
	 */
	public String banding(){
		
		JMapObj reJson = new JMapObj();
		
		try {
			
			String openId = getParam("openId");
			String phone = getParam("phone");
			String captcha = getParam("captcha");
			
			if(phone.length() != 11){
				reJson.put("code", 401);
				reJson.put("msg", "手机号长度错误");
				setResult(reJson.toJSONString());
				return "result";
			}
			
			if(wechatManager.banding(openId, phone)){
				reJson.put("code", 200);
				reJson.put("msg", "绑定成功");
			}else{
				reJson.put("code", 600);
				reJson.put("msg", "数据库错误");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			reJson.put("code", 500);
			reJson.put("msg", "服务器错误");
		}
		
		setResult(reJson.toJSONString());
		return "result";
	}
	
	
	/**
	 * 获取预约单
	 * 
	 * @param String openId 
	 * 					用户微信号的openId
	 * 
	 * @param int condition 
	 * 					条件 0表示全部，1表示预约中，2表示已预约，3表示已完成
	 * 
	 * @param int start 
	 * 					起始位置，从第几条记录开始拿
	 * 
	 * @param int offset 
	 * 					偏移量，一次拿几条记录
	 * 
	 * @return
	 */
	public String getBookingList(){
		JMapObj reJson = new JMapObj();
		
		try {
		
			String openId = getParam("openId");
			int condition = Integer.parseInt(getParam("condition"));
			int start = Integer.parseInt(getParam("start"));
			int offset = Integer.parseInt(getParam("offset"));
		
		    if(wechatManager.isBanded(openId)){
		    	
		    	if(condition == 0 || condition == 1 || condition == 2 || condition == 3){
		    		
		    		JArrayObj listArray = null;
		    		listArray = wechatManager.getBookList(openId,condition, start, offset);
		    		
		    		if(listArray != null){
		    			reJson.put("code", 200);
		    			reJson.put("msg", "获取成功");
		    			reJson.put("data", listArray);
		    		}else{
		    			reJson.put("code", 600);
						reJson.put("msg", "数据库错误");
		    		}
		    		
		    	}else{
		    		reJson.put("code", 402);
					reJson.put("msg", "分类字段错误");
		    	}
		    	
		    }else{
		    	reJson.put("code", 401);
				reJson.put("msg", "该微信号未绑定");
		    }
		    
		} catch (Exception e) {
			// TODO: handle exception
			reJson.put("code", 500);
			reJson.put("msg", "服务器错误");
		}
		
		setResult(reJson.toJSONString());
		return "result";
	}
	
	/**
	 * 获取预约单详情
	 * 
	 * @param bookId
	 * 				预约单id
	 * 
	 * @return
	 */
	public String getBookDetails(){
		JMapObj reJson = new JMapObj();
		
		try {
			int bookId = Integer.parseInt(getParam("bookId"));
			
			Booking booking = null;
			booking = wechatManager.getBookById(bookId);
			
			if(booking != null){
				JMapObj detailMap = new JMapObj();
				
				Tailor tailor = wechatManager.getTailorById(booking.getTailorId());
				
				if(tailor != null){
					detailMap.put("avatar", tailor.getAvatar());
					detailMap.put("name", tailor.getName());
					detailMap.put("phone", tailor.getPhone());
					detailMap.put("orderNum", booking.getOrderNum());
					detailMap.put("date", booking.getStartTime());
					detailMap.put("address", booking.getAddress());
				}
				
				reJson.put("code", 200);
				reJson.put("msg", "查询成功");
				reJson.put("data", detailMap);
				
			}else{
				reJson.put("code", 400);
				reJson.put("msg", "预约单id无效");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			reJson.put("code", 500);
			reJson.put("msg", "服务器错误");
		}
		
		setResult(reJson.toJSONString());
		return "result";
	}
	
	/**
	 * 获取优惠券
	 * 
	 * @param String openId 
	 * 					用户微信号的openId
	 * 
	 * @param int condition 
	 * 					条件 0表示全部，1表示未使用，2表示已使用，3表示已过期
	 * 
	 * @param int start 
	 * 					起始位置，从第几条记录开始拿
	 * 
	 * @param int offset 
	 * 					偏移量，一次拿几条记录
	 * 
	 * @return
	 */
	public String getCouponList(){
		JMapObj reJson = new JMapObj();
		
		try {
			
			String openId = getParam("openId");
			int condition = Integer.parseInt(getParam("condition"));
			int start = Integer.parseInt(getParam("start"));
			int offset = Integer.parseInt(getParam("offset"));
		
		    if(wechatManager.isBanded(openId)){
		    	
		    	if(condition == 0 || condition == 1 || condition == 2 || condition == 3){
		    		
		    		JArrayObj listArray = null;
		    		listArray = wechatManager.getCouponList(openId, condition, start, offset);
		    		
		    		if(listArray != null){
		    			reJson.put("code", 200);
		    			reJson.put("msg", "获取成功");
		    			reJson.put("data", listArray);
		    		}else{
		    			reJson.put("code", 600);
						reJson.put("msg", "数据库错误");
		    		}
		    		
		    	}else{
		    		reJson.put("code", 402);
					reJson.put("msg", "分类字段错误");
		    	}
		    	
		    }else{
		    	reJson.put("code", 401);
				reJson.put("msg", "该微信号未绑定");
		    }
		    
		} catch (Exception e) {
			// TODO: handle exception
			reJson.put("code", 500);
			reJson.put("msg", "服务器错误");
		}
		
		setResult(reJson.toJSONString());
		return "result";
	}
	
	/**
	 * 取消预约
	 * 
	 * @param String openId
	 * 					用户微信号的openId
	 * 
	 * @param String bookId
	 * 					预约单号
	 * 
	 * @return
	 */
	public String cancelBooking(){
		JMapObj reJson = new JMapObj();
		
		try {
			
			String openId = getParam("openId");
			String bookId = getParam("bookId");
			
			if(wechatManager.isBanded(openId)){
				Booking booking = null;
				booking = wechatManager.getBookById(Integer.parseInt(bookId));
				
				if(booking != null){
					
					if(booking.getStatus() == 0 || booking.getStatus() == 2){
						
						if(wechatManager.cancel(Integer.parseInt(bookId))){
							reJson.put("code", 403);
							reJson.put("msg", "取消成功");
						}else{
							reJson.put("code", 600);
							reJson.put("msg", "数据库错误");
						}
						
					}else {
						reJson.put("code", 403);
						reJson.put("msg", "预约单取消失败（仅正在预约和预约成功状态可取消预约单）");
					}
					
				}else{
					reJson.put("code", 402);
					reJson.put("msg", "预约单id无效");
				}
				
			}else{
				reJson.put("code", 401);
				reJson.put("msg", "微信号未绑定");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			reJson.put("code", 500);
			reJson.put("msg", "服务器错误");
		}
		
		setResult(reJson.toJSONString());
		return "result";
	}


	public WechatManager getWechatManager() {
		return wechatManager;
	}


	public void setWechatManager(WechatManager wechatManager) {
		this.wechatManager = wechatManager;
	}
}
