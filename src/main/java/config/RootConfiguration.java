package config;

import com.zaxxer.hikari.HikariDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@ComponentScans({
        @ComponentScan("dao.impl"),
        @ComponentScan("dao"),
        @ComponentScan("entity")
})
@MapperScan("dao.mapper")
@Configuration
public class RootConfiguration {
    @Bean
    public DataSource dataSource(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://geoffyli.mysql.rds.aliyuncs.com:3306/user");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername("k_administrator");
        dataSource.setPassword("Kk123456");
        return dataSource;
    }

    @Bean
    public SqlSessionFactoryBean SqlSessionFactoryBean(@Autowired DataSource dataSource) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean;
    }
}
