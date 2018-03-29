package idv.david.orders.model;

import java.util.List;
import java.util.Set;

public interface OrderMasterDAO {
	OrderMaster findById(int orderid);
	List<OrderMaster> getAll(String userId, String start, String end);
	int addWithOrderItem(OrderMaster orderMaster, List<OrderItem> orderItemList);
}
