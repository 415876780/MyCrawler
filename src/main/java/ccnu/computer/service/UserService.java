package ccnu.computer.service;

import java.util.List;

import com.github.pagehelper.PageInfo;

import ccnu.computer.model.User;
import ccnu.computer.model.UserInfo;

public interface UserService {
	
	User selectUserById(Integer userId);  
	
	PageInfo<User> queryByPage(String userName,Integer pageNo,Integer pageSize);
	public UserInfo selectUserById(int userId);
	int insert(UserInfo record);
	
	
    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
    
    List<UserInfo> selectAll();
    
    UserInfo selectUserByname(String name);
}
