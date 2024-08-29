package user;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import lombok.AllArgsConstructor;
import materials.DBUtil;
import order.OrderDAO;
import order.OrderListDAO;

@AllArgsConstructor
public class UserService {
	private UserDAO userDAO;
	private DeleteUserDAO deleteUserDAO;
	private OrderDAO orderDAO;
	private OrderListDAO orderListDAO;
	
	public boolean deleteUser(User user) {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection("jojosoft");
			conn.setAutoCommit(false);
			List<Integer> orderIdList = orderDAO.getOrderIdList(user);
			int resultDeleteOrderList = orderListDAO.deleteOrderListWithOrderId(conn, orderIdList);
			int resultDeleteOrder = orderDAO.deleteOrder(conn, user);
			int resultDeleteDBInsert = deleteUserDAO.insertDeleteUser(conn);
			int resultUserDBDelete = userDAO.deleteUser(conn, user);
			if(resultDeleteDBInsert == 1 && resultUserDBDelete == 1 
					&& resultDeleteOrder == 1 && resultDeleteOrderList == 1) {
				conn.commit();
				return true;
			} else {
				conn.rollback();
				return false;
			}
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
		
		return false;
	}
}
