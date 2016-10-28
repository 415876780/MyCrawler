package ccnu.computer.service;

import ccnu.computer.model.PosterInfo;

public interface PosterService {
	int deleteByPrimaryKey(Integer id);

    int insert(PosterInfo record);

    int insertSelective(PosterInfo record);

    PosterInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PosterInfo record);

    int updateByPrimaryKey(PosterInfo record);
}
