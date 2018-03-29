package idv.david.category.model;

import java.util.List;

public interface CategoryDAO {
	void add(Category category);
	void update(Category category);
	void delete(int cid);
	Category findById(int cid);
	List<Category> getAll();
}
