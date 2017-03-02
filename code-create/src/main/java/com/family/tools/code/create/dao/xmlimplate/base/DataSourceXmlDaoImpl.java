package com.family.tools.code.create.dao.xmlimplate.base;


import com.family.tools.code.create.dao.bean.DataSourceBean;
import com.family.tools.code.create.dao.interfaces.IDataSourceDao;

public class DataSourceXmlDaoImpl extends GenericXmlDaoImpl<DataSourceBean> implements
		IDataSourceDao {

	public DataSourceXmlDaoImpl() {
		super("data_source", "DataSource", DataSourceBean.class, "id");
	}

	@Override
	protected void doSetId(long id, DataSourceBean bean) {
		bean.setId(id);
	}

	@Override
	protected Long doGetId(DataSourceBean bean) {
		return  bean.getId();
	}
}
