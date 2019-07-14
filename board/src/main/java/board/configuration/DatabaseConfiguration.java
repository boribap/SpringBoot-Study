package board.configuration;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/*
 * application.properties 를 사용할 수 있도록 설정 파일의 위치 지정 
 * 
 */
@Configuration
@PropertySource("classpath:/application.properties")
public class DatabaseConfiguration {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	/*
	 * application.properties에 설정했던 데이터베이스 관련 정보를 사용하도록 지정 
	 * 
	 * prefix 사용 -> spring.datasource.hikari로 시작하는 설정을 이용해 히카리CP의 설정 파일 만듬 
	 * 
	 */
	@Bean
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	/*
	 * MyBatis와 관련된 설정 가져오기 
	 * 
	 */
	@Bean
	@ConfigurationProperties(prefix="mybatis.configuration")
	public org.apache.ibatis.session.Configuration mybatisConfig(){
		return new org.apache.ibatis.session.Configuration();
	}
	
	
	/*
	 * 위에서 만든 히카리CP 설정파일을 이용해 데이터베이스와 연결하는 데이터소스 생성 
	 * 
	 */
	@Bean
	public DataSource dataSource() throws Exception{
		DataSource dataSource = new HikariDataSource(hikariConfig());
		System.out.println(dataSource.toString());
		return dataSource;
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		// mapper 파일 : SQL을 담고있는 XML파일을 의미 
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/**/sql-*.xml"));
		// mybatis 설정 관련
		sqlSessionFactoryBean.setConfiguration(mybatisConfig());
		
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
