package ccnu.computer.service;

import org.springframework.beans.factory.annotation.Autowired;

import ccnu.computer.dao.PosterInfoMapper;
import ccnu.computer.model.PosterInfo;

public class PosterServiceImpl implements PosterService {
	@Autowired
	PosterInfoMapper post;
	
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return post.deleteByPrimaryKey(id);
	}

	public int insert(PosterInfo record) {
		// TODO Auto-generated method stub
		return post.insert(record);
	}

	public int insertSelective(PosterInfo record) {
		// TODO Auto-generated method stub
		return post.insertSelective(record);
	}

	public PosterInfo selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return post.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(PosterInfo record) {
		// TODO Auto-generated method stub
		return post.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(PosterInfo record) {
		// TODO Auto-generated method stub
		return post.updateByPrimaryKey(record);
	}

}
