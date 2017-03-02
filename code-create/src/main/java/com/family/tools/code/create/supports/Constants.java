package com.family.tools.code.create.supports;

public class Constants {
	public static class Dao{
		public static final String BEAN_PACKAGE_PATH="BEAN_PACKAGE_PATH";//bean 的相对目录
		public static final String BEAN_PROJECT_PATH="BEAN_PROJECT_PATH";//bean 的工程目录
		public static final String DAO_PACKAGE_PATH="DAO_PACKAGE_PATH";//dao 的接口的相对目录
		public static final String DAO_IMPL_PACKAGE_PATH="DAO_IMPL_PACKAGE_PATH";//dao的实现类的相对目录
		public static final String DAO_PROJECT_PATH="DAO_PROJECT_PATH";//dao的工程目录
		public static final String DAO_SPRING_RESOURCES_PATH="DAO_SPRING_RESOURCES_PATH";//dao 的spring相对目录
		public static final String DAO_SPRING_FILE_NAME="DAO_SPRING_FILE_NAME";//dao spring 的配置文件名称
		public static final String MYBATIS_PACKAGE_PATH="MYBATIS_PACKAGE_PATH";//MYBATIS的包路径
		public static final String MYBATIS_PROJECT_PATH="MYBATIS_PROJECT_PATH";//MYBATIS的工程绝对路径
		public static final String MYBATIS_CONF_PACKAGE_PATH="MYBATIS_CONF_PACKAGE_PATH";//MYBATIS配置的包路径
		public static final String MYBATIS_CONF_FILE_NAME="MYBATIS_CONF_FILE_NAME";//MYBATIS配置的文件名称
	}
	public static class Service{
		public static final String SERVICE_INTERFACES_PROJECT_PATH="SERVICE_INTERFACES_PROJECT_PATH";//SERVICE接口的工程目录
		public static final String SERVICE_IMPL_PROJECT_PATH="SERVICE_IMPL_PROJECT_PATH";//SERVICE接口的工程目录
		public static final String SERVICE_PACKAGE_PATH="SERVICE_PACKAGE_PATH";//service 的接口的相对目录
		public static final String SERVICE_IMPL_PACKAGE_PATH="SERVICE_IMPL_PACKAGE_PATH";//service 实现类的接口的相对目录
		public static final String SERVICE_SPRING_RESOURCES_PATH="SERVICE_SPRING_RESOURCES_PATH";//SERVICE 的spring相对目录
		public static final String SERVICE_SPRING_FILE_NAME="SERVICE_SPRING_FILE_NAME";//service spring的配置文件名称
	}
	public static class Config{
		public static final String IS_MAVEN="IS_MAVEN";//是否maven工程 默认为true
		public static final String CLOSE_DB="CLOSE_DB";//是否关闭数据库连接 默认为false
		public static final String CHECK_FILE="CHECK_FILE";//是否检查文件是否存在
		public static final String EXECUTE_CHAIN="EXECUTE_CHAIN";//是否执行链 默认为true
	}
	public static class Dubbo{
		public static final String DUBBO_PROVIDER_FILE_NAME="DUBBO_PROVIDER_FILE_NAME";//DUBBO配置文件名称
	}
}
