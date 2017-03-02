package com.family.tools.code.create.action.support;

import java.util.ArrayList;
import java.util.List;

/**
 * 公用静态变量类
 * @author dell
 *
 */
public class Constants {
	/**
	 * 全局status
	 * @author dell
	 *
	 */
	public static class GolbalStatus{
		// 有效
        public static final String STATUS_VALID = "1";

        // 无效
        public static final String STATUS_INVALID = "2";
	}
	public static class DateMsg{
		//时间格式化
		public static final String DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
		//简单时间只有年日月格式化
		public static final String SIMPLE_DATE_FORMAT="yyyy-MM-dd";
		//时间格式正则验证yyyy-MM-dd
		public static final String DATE_REGEX="\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}";
		//简单时间格式正则验证yyyy-MM-dd HH:mm:ss
		public static final String SIMPLE_DATE_REGEX="\\d{4}-\\d{1,2}-\\d{1,2}";
	}
	/**
	 * ajax返回的参数
	 * @author dell
	 *
	 */
	public static class AjaxReturn {
		// AJAX返回的成功标志值
        public static final String SUCCESS = "success";
        // AJAX返回的状态码KEY值
        public static final String STATUS_CODE = "status";
        // AJAX返回的状态的信息
        public static final String STATUS_INFO = "msg";
        // AJAX返回的列表数据
        public static final String RETURN_ROWS = "rows";
        // AJAX返回的列表数据总数
        public static final String RETURN_TOTAL_COUNT = "totalCount";
        //返回的form
        public static final String RETURN_DATA="data";
    }
	/**
	 * ajax返回状态
	 * @author dell
	 *
	 */
	public static class AjaxStatus{
		// 处理成功，表示没有捕捉到任何异常
        public static final String STATUS_SUCCESS = "1";

        // 处理失败,标识捕捉到异常信息
        public static final String STATUS_FAILURE = "2";
	}
	//保存在session中的userinfo
	public static final String USER_INFO="userInfo";
	//保存在session中的userinfoJson 前台js使用
	public static final String USER_INFO_JSON="userInfoJson";
	//分割符
	public static final String DELIMITER="#";
	public static class SysParamConstants{
		//全局status编码配置
		public static final String ALL_STATUS_TYPE="all";
		public static final String ALL_STATUS_VALUE="status";
		//配置表
		public static class MANAGE_TABLE{
			//表中配置的type 基本type
			public static final String MANAGE_TYPE="manage";
			//suit_scope范围value
			public static final String MANAGE_SUIT_SCOPE_VALUE="suit_scope";
		}
		//代码模板表
		public static class CODE_TEMPLET{
			public static final String CODE_TEMPLET="code_templet";
			public static final String TEMPLET_TYPE="templet_type";
			public static final String TEMPLET_CREATE_WAY="templet_create_way";
			public static final String DATA_FROM_TYPE="data_from_type";
			public static final String CODE_TYPE="code_type";
		}
		//收支管理
		public static class HOME_DEPOSIT_PAY{
			//存入
			public static final String TAB_HOME_DEPOSIT="tab_home_deposit";
			//事由
			public static final String DEPOSIT_CAUSE="deposit_cause";
			//支出
			public static final String TAB_HOME_PAY="tab_home_pay";
			//支出用途
			public static final String PAY_REASON="pay_reason";
			//支出详细
			public static final String PAY_DETAIL="pay_detail";
		}
	}
	/**
	 * 作用范围
	 * 
	 * @date Jun 19, 2013 4:16:58 PM
	 * @author wuzl
	 * @comment
	 */
	public static class SUIT_SCOPE{
		//所有用户
		public static final String ALL_SCOPE="1";
		//个人独有
		public static final String PERSONAL_SCOPE="2";
	}
	/**
	 * 菜单类型
	 * 
	 * @date Jun 20, 2013 3:20:25 PM
	 * @author wuzl
	 * @comment
	 */
	public static class MenuType{
		//目录
		public static final String FOLDER="1";
		//节点
		public static final String LEAF_NODE="2";
	}
	public static class TreeAndNodeMsg{
		public static final String NODE_ID="id";
		public static final String NODE_TEXT="text";
		public static final String NODE_LEAF="leaf";
		public static final String NODE_LOADED="loaded";
		public static final String NODE_EXPANDED="expanded";
		public static final String CHILDREN_NODES="children";
		public static final String NODE_URL="url";
	}
	public static class CodeTemplet{
		//通用后缀
		public static final String COMMON_SUFFIX="}";
		//变量的前缀
		public static final String PARAM_PREFIX="!#{";
		//引入的前缀
		public static final String INCLUDE_PREFIX="!#include{";
		//规则的分隔符
		public static final String RULE_BREAK="\r\n";
		//方法的分隔符
		public static final String METHOD_BREAK="?";
		//参数默认值的分隔符
		public static final String DEFAULT_VALUE_BREAK=":";
		//循环的结束符号
		public static final String FOR_END="!${endFor}";
		//数据来源
		public static class DataFromType{
			//页面输入
			public static final String PAGE_INPUT="1";
			//数据库
			public static final String DB="2";
			//文件上传
			public static final String FILE_UP="3";
		}
		//模板代码中生成格式FILE
		public static String CREATE_TYPE_FILE="FILE";
		//模板代码中生成格式VIEW
		public static String CREATE_TYPE_VIEW="VIEW";
		//函数
		public static class Method{
			//下划线转成驼峰
			public static final String CHANGE_UNDER_LINE_TO_HUMP="changeUnderLineToHump";
			//下划线转成驼峰并首字母大写
			public static final String CHANGE_UNDER_LINE_TO_HUMPAND_FONT_UP="changeUnderLineToHumpAndFontUp";
		}
		//系统变量
		public static class SystemParam{
			//自增长
			public static final String AUTO_INCREASE="AUTO_INCREASE";
			//tab符号
			public static final String TAB="TAB";
			//tab符号
			public static final String TAB_TEMPLET="!{TAB}";
			//换行符号
			public static final String ENTER="ENTER";	
			//换行符号
			public static final String ENTER_TEMPLET="!{ENTER}";
			//时间
			public static final String TIME="TIME";
		}
	}
	//文件输入符号
	public static class FileSign{
		//换行 
		public static final String ENTER="\r\n";
		//tab
		public static final String TAB="\t";
	}
	/**
	 * 默认编码
	 */
	public static final String DEFAULT_ENCODED="UTF-8";
	// 分组
	public static class GROUP{
		public static String DORMITORY_GROUP_NAME="寝室";
		public static String DORMITORY_GROUP_CODE="dormitory";
	}
}
