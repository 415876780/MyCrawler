package ccnu.computer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ccnu.computer.dao.UserDao;
import ccnu.computer.dao.UserInfoMapper;
import ccnu.computer.model.User;
import ccnu.computer.model.UserInfo;

@Service  
public class UserServiceImpl implements UserService {

	@Autowired  
    private UserDao userDao;  
  
	@Autowired  
	private UserInfoMapper userMapper;
	
   /* public User selectUserById(Integer userId) {  
        return userDao.selectUserById(userId);  
          
    }*/
    
    public UserInfo selectUserById(int userId) {  
        return userMapper.selectByPrimaryKey(userId);
          
    }
    

	public PageInfo<User> queryByPage(String userName, Integer pageNo,
			Integer pageSize) {
		pageNo = pageNo == null?1:pageNo;
		pageSize = pageSize == null?10:pageSize;
		PageHelper.startPage(pageNo, pageSize);
		List<User> list = userDao.selectUserByUserName(userName);
		//��PageInfo�Խ����а�װ
		PageInfo<User> page = new PageInfo<User>(list);
		//����PageInfoȫ������
		System.out.println(page.getPageNum());
		System.out.println(page.getPageSize());
		System.out.println(page.getStartRow());
		System.out.println(page.getEndRow());
		System.out.println(page.getTotal());
		System.out.println(page.getPages());
		System.out.println(page.getFirstPage());
		System.out.println(page.getLastPage());
		System.out.println(page.isHasPreviousPage());
		System.out.println(page.isHasNextPage());
		return page;
	}

	public User selectUserById(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public int insert(UserInfo record) {
		// TODO Auto-generated method stub
		return userMapper.insert(record);
	}

	public int insertSelective(UserInfo record) {
		// TODO Auto-generated method stub
		return userMapper.insertSelective(record);
	}

	public UserInfo selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(UserInfo record) {
		// TODO Auto-generated method stub
		return userMapper.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(UserInfo record) {
		// TODO Auto-generated method stub
		return userMapper.updateByPrimaryKey(record);
	}


	public List<UserInfo> selectAll() {
		// TODO Auto-generated method stub
		return userMapper.selectAll();
	}


	public UserInfo selectUserByname(String name) {
		// TODO Auto-generated method stub
		return userMapper.selectByName(name);
	}  
}
