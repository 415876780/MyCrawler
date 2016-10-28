package ccnu.computer.dao;

import ccnu.computer.model.TextInfo;

public interface TextInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TextInfo record);

    int insertSelective(TextInfo record);

    TextInfo selectByPrimaryKey(Integer id);
    
    

    int updateByPrimaryKeySelective(TextInfo record);

    int updateByPrimaryKeyWithBLOBs(TextInfo record);

    int updateByPrimaryKey(TextInfo record);
}