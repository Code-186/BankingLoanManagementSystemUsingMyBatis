package com.crimsonlogic.bankingandloanmanagementsystem.config;

import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.crimsonlogic.bankingandloanmanagementsystem.mapper.EmiMapper;

public class MyBatisUtil {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

            // Programmatically registers EmiMapper in the MapperRegistry
            if (!sqlSessionFactory.getConfiguration().hasMapper(EmiMapper.class)) {
                sqlSessionFactory.getConfiguration().addMapper(EmiMapper.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}