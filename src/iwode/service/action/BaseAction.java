package iwode.service.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Logger logger;
	private Map<String, Object> map;

	protected BaseAction() {
		logger = Logger.getLogger(getClass());
	}

	protected Map<String, Object> getMap() {
		if (map == null) {
			map = new HashMap<String, Object>();
			for (Entry<String, Object> ety : ActionContext.getContext()
					.getParameters().entrySet()) {
				String key = ety.getKey();
				String value;
				if (ety.getValue() != null
						&& ety.getValue() instanceof String[]) {
					value = ((String[]) ety.getValue())[0];
				} else {
					value = null;
				}
				map.put(key, value);
			}
		}
		return map;
	}

	protected String getParam(String key) {
		return ServletActionContext.getRequest().getParameter(key);
	}

	protected String getRequestString() {
		return getMap().toString();
	}

	protected void error(Exception e) {
		logger.error("request : " + getRequestString(), e);
	}

	protected void setResult(String result) {
		ServletActionContext.getRequest().setAttribute("result", result);
	}

}
