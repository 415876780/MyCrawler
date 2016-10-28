package ccnu.computer.service;

import org.springframework.beans.factory.annotation.Autowired;

import ccnu.computer.dao.TextInfoMapper;
import ccnu.computer.model.TextInfo;

public class TesxtServiceImpl implements TextService {
	@Autowired
	TextInfoMapper text;
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return text.deleteByPrimaryKey(id);
	}

	public int insert(TextInfo record) {
		// TODO Auto-generated method stub
		return text.insert(record);
	}

	public int insertSelective(TextInfo record) {
		// TODO Auto-generated method stub
		return text.insertSelective(record);
	}

	public TextInfo selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return text.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(TextInfo record) {
		// TODO Auto-generated method stub
		return text.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKeyWithBLOBs(TextInfo record) {
		// TODO Auto-generated method stub
		return text.updateByPrimaryKeyWithBLOBs(record);
	}

	public int updateByPrimaryKey(TextInfo record) {
		// TODO Auto-generated method stub
		return text.updateByPrimaryKey(record);
	}

}
