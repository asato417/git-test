package com.example.springwebtask.repository;

import com.example.springwebtask.entity.ProductRecord;
import com.example.springwebtask.entity.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository implements IProductRepository{
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Override
    public List<ProductRecord> findAll() {
        return jdbcTemplate.query("SELECT * FROM products ORDER BY product_id",
                new DataClassRowMapper<>(ProductRecord.class));
    }

    @Override
    public List<ProductRecord> findByName(String name) {
        var param = new MapSqlParameterSource();
        name = "%" + name + "%";
        param.addValue("name", name);
        final String FIND_PRODUCT_SQL = "SELECT * FROM products WHERE name LIKE :name";
        var resultList = jdbcTemplate.query(FIND_PRODUCT_SQL, param,
                new DataClassRowMapper<>(ProductRecord.class));
        return resultList.isEmpty() ? null : resultList;
    }

    @Override
    public ProductRecord findByProductId(String productId) {
        var param = new MapSqlParameterSource();
        param.addValue("productId", productId);
        final String FIND_PRODUCT_SQL = "SELECT * FROM products WHERE product_id = :productId";
        var resultList = jdbcTemplate.query(FIND_PRODUCT_SQL, param,
                new DataClassRowMapper<>(ProductRecord.class));
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public ProductRecord findBytId(int id) {
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        final String FIND_PRODUCT_SQL = "SELECT * FROM products WHERE id = :id";
        var resultList = jdbcTemplate.query(FIND_PRODUCT_SQL, param,
                new DataClassRowMapper<>(ProductRecord.class));

        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public int insert(ProductRecord product) {
        var param = new MapSqlParameterSource();
        param.addValue("pId", product.productId());
        param.addValue("cId", product.categoryId());
        param.addValue("name", product.name());
        param.addValue("price", product.price());
        param.addValue("description", product.description());
        final String INSERT =
                "INSERT INTO products (product_id, category_id, name, price, description) VALUES(:pId, :cId, :name, :price, :description)";
        return jdbcTemplate.update(INSERT, param);
    }

    @Override
    public int delete(String productId) {
        var param = new MapSqlParameterSource();
        param.addValue("productId", productId);
        final String DELETE = "DELETE FROM products WHERE product_id = :productId";
        return jdbcTemplate.update(DELETE, param);
    }

    @Override
    public int update(ProductRecord product) {
        var param = new MapSqlParameterSource();
        param.addValue("productId", product.productId());
        param.addValue("name", product.name());
        param.addValue("price", product.price());
        param.addValue("categoryId", product.categoryId());
        param.addValue("description", product.description());
        param.addValue("file", product.imagePath());
        param.addValue("id", product.id());
        final String UPDATE =
                "UPDATE products SET product_id = :productId, name=:name, price=:price, category_id=:categoryId, description=:description, image_path=:file WHERE id = :id";
        return jdbcTemplate.update(UPDATE, param);

    }

//    @Override
//    public int insert(String productId, int categoryId, String name, int price, String description) {
//        var param = new MapSqlParameterSource();
//        param.addValue("pId", productId);
//        param.addValue("cId", categoryId);
//        param.addValue("name", name);
//        param.addValue("price", price);
//        param.addValue("description", description);
//        final String INSERT = "INSERT INTO products (product_id, category_id, name, price, description" +
//                "VALUE(:pID, :cID, :name, :price, :description)";
//        return jdbcTemplate.update(INSERT, param);
//    }
}
