package com.example.controller;

import com.example.entity.repo.RestBean;
import com.example.entity.repo.RestBeanBuilder;
import com.example.entity.repo.ResultCode;
import com.example.tool.ResultSetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Slf4j
@ApiIgnore
@RestController
@RequestMapping("/api/dao")
public class SqlApiController {
    @Value("${spring.datasource.url}")
    String url;


    /**
     * 执行查询
     * @param sql sql语句
     * @return 后端执行结果
     */
    @GetMapping("/executeQuery")
    public RestBean<Object> executeQuery(@RequestParam(value = "sql") String sql) {
        int count = 0;
        try (
                Connection connection = DriverManager.getConnection(url);
                Statement statement = connection.createStatement();
        ) {
            ResultSet rs = statement.executeQuery(sql);
            statement.setFetchSize(Integer.MIN_VALUE);
            while (rs.next()) {
                count++;
            }
            log.info("executeQuery count:" + count);
            return RestBeanBuilder.builder().code(ResultCode.SUCCESS).messageType(RestBeanBuilder.USER_DEFINED)
                    .message("executeQuery count:" + count).data(ResultSetUtil.resultSetToJson(rs)).build().ToRestBean();

        } catch (Exception e) {
            e.printStackTrace();
            return RestBeanBuilder.builder().code(ResultCode.FAILURE).messageType(RestBeanBuilder.USER_DEFINED)
                    .message("执行sql语句时出现错误,请联系管理员").build().ToRestBean();
        }

    }

    /**
     * 执行更新
     * @param sql sql语句
     * @return 后端执行结果
     */
    @PostMapping("/executeUpdate")
    public RestBean<Object> executeUpdate(@RequestParam(value = "sql") String sql) {
        try (
                Connection connection = DriverManager.getConnection(url);
                Statement statement = connection.createStatement();
        ) {
            int updateCount = statement.executeUpdate(sql);
            log.info("executeUpdate count:" + updateCount);
            return RestBeanBuilder.builder().code(ResultCode.SUCCESS).messageType(RestBeanBuilder.USER_DEFINED)
                    .message("executeUpdate:" + updateCount).build().ToRestBean();

        } catch (Exception e) {
            e.printStackTrace();
            return RestBeanBuilder.builder().code(ResultCode.FAILURE).messageType(RestBeanBuilder.USER_DEFINED)
                    .message("执行sql语句时出现错误,请联系管理员").build().ToRestBean();
        }

    }

    /**
     * 执行全部模糊操作
     * @param sql sql语句
     * @return 后端执行结果
     */
    @RequestMapping("/execute")
    public RestBean<Object> execute(@RequestParam(value = "sql") String sql) {
        int count = 0;
        try (
                Connection connection = DriverManager.getConnection(url);
                Statement statement = connection.createStatement();
        ) {
            ResultSet rs ;
            statement.setFetchSize(Integer.MIN_VALUE);
            if (statement.execute(sql)) {  //执行的是query
                rs = statement.getResultSet();

                while (rs.next()) {
                    count++;
                }
                return RestBeanBuilder.builder().code(ResultCode.SUCCESS).messageType(RestBeanBuilder.USER_DEFINED)
                        .message("executeQuery:" + count).data(ResultSetUtil.resultSetToJson(rs)).build().ToRestBean();

            } else {  //执行的是update
                int updateCount=statement.getUpdateCount();
                return RestBeanBuilder.builder().code(ResultCode.SUCCESS).messageType(RestBeanBuilder.USER_DEFINED)
                        .message("executeUpdate:" + updateCount).build().ToRestBean();

            }
        } catch (Exception e) {
            e.printStackTrace();
            return RestBeanBuilder.builder().code(ResultCode.FAILURE).messageType(RestBeanBuilder.USER_DEFINED)
                    .message("执行sql语句时出现错误,请联系管理员").build().ToRestBean();
        }

    }
}
