package com.example.bootsecurity.mapper;

import com.example.bootsecurity.model.User;
import com.example.bootsecurity.model.UserExample;
import com.github.pagehelper.PageHelper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    long countByExample(UserExample example);

    default int deleteByExample(User record, UserExample example) {
        return updateByExampleSelective(record, example);
    }

    int deleteByExample(UserExample example);

    default int deleteByPrimaryKey(User record) {
        return updateByPrimaryKey(record);
    }

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    default User selectOneByExample(UserExample example) {
        PageHelper.startPage(1, 1);
        List<User> list = selectByExample(example);
        if (list == null || list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int insertByList(List<User> records);
}