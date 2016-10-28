package ccnu.computer.common;

public class Constant {
	/**
	 * 用户对象放到Session中的键名称
	 */

	public static final String Session_LoginInfo = "LoginInfo";

	public static final String Session_RoleInfo = "RoleInfo";

	public static final String Session_RightInfo = "RightInfo";

	public static final String DoubleSlash = "//";

	/**超级管理员用户编号 */
	public static final int SuperAdminId = 1;
	
	/**超级管理员角色编号 */
	public static final int SuperRoleId = 1;

	/**
	 * 权限节点根目录编号
	 */
	public static final String RightRootId = "0";

	/**
	 * 每页的记录数
	 */
	public static final int PAGE_SIZE = 20;

	/**
	 * 每页的记录数
	 */
	public static final int Zero = 0;

	/**
	 * 用户启用状态
	 */
	public static final int User_Is_Valid = 1;

	/**
	 * 用户停用状态
	 */
	public static final int User_No_Valid = 2;

	/**
	 * 启用状态
	 */
	public static final int Is_Valid = 1;
}
