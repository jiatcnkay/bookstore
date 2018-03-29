package idv.david.orders.model;

import java.sql.Connection;
import java.util.List;

public interface OrderItemDAO {
	void add(OrderItem orderItem);
	void update(OrderItem orderItem);
	void delete(int orderid);
	List<OrderItem> findByOrderId(int orderid);
	void addWithOrderMaster(OrderItem orderItem, Connection con);
}
