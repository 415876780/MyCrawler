package ccnu.computer.dao;

import ccnu.computer.model.PosterInfo;

public interface PosterInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PosterInfo record);

    int insertSelective(PosterInfo record);

    PosterInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PosterInfo record);

    int updateByPrimaryKey(PosterInfo record);
}