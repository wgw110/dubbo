package tv.mixiong.dao;


import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface CustomizedMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
