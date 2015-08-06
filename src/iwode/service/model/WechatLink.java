package iwode.service.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="iwode_wechat_link")
public class WechatLink {

	private int id;
	private String openId;
	private String unionId;
	private int memberuserId;
	private String modifyTime;
	private int status;
	
	public WechatLink(){
		
	}
	
	public WechatLink(int id,String openId,String unionId,int memberuserId,String modifyTime,int status){
		this.id = id;
		this.openId = openId;
		this.unionId = unionId;
		this.memberuserId = memberuserId;
		this.modifyTime = modifyTime;
		this.status = status;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id",updatable=false)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="openId")
	public String getOpenId() {
		return openId;
	}
	
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	@Column(name="unionId")
	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	@Column(name="memberuserId")
	public int getMemberuserId() {
		return memberuserId;
	}
	
	public void setMemberuserId(int memberuserId) {
		this.memberuserId = memberuserId;
	}

	@Column(name="modifyTime")
	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name="status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
