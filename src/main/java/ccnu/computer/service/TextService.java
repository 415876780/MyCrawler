package ccnu.computer.service;

import ccnu.computer.model.TextInfo;

public interface TextService {
	int deleteByPrimaryKey(Integer id);

    int insert(TextInfo record);

    int insertSelective(TextInfo record);

    TextInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TextInfo record);

    int updateByPrimaryKeyWithBLOBs(TextInfo record);

    int updateByPrimaryKey(TextInfo record);
}
