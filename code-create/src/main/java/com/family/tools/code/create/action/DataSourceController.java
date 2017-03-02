package com.family.tools.code.create.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.family.tools.code.create.action.support.MsgBean;

@Controller
@RequestMapping("/datasource")
public class DataSourceController {
	@RequestMapping("/list")
	public @ResponseBody
	Object searchDataSource(String dataSourceName) {
		MsgBean msg=new MsgBean();
		
		return msg.returnMsg();
	}
}
