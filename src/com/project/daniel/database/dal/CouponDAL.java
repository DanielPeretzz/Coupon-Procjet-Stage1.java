package com.project.daniel.database.dal;

import com.project.daniel.database.connection.ConnectionPool;
import com.project.daniel.database.connection.JDBCUtil;
import com.project.daniel.enums.Category;
import com.project.daniel.enums.CrudOperation;
import com.project.daniel.enums.EntityType;
import com.project.daniel.error.exceptions.EntityCrudException;
import com.project.daniel.logging.Logger;
import com.project.daniel.model.Coupon;
import com.project.daniel.model.Customer;
import com.project.daniel.util.ObjectExtractionUtil;
import lombok.Data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Data

public class CouponDAL implements CrudDAL<Long, Coupon> {

    public static CouponDAL instance = new CouponDAL();
    private final ConnectionPool connectionPool;
    private static final Logger logger = Logger.getLogger(CouponDAL.class);

    private CouponDAL() {
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while getting with the connection");
        }
    }


    public Long create(final Coupon coupon) throws EntityCrudException {
        Connection connection = null;
        try {
            final String sqlStatement =
                    "INSERT INTO coupons (title, company_id, start_date, end_date, amount, category, description," +
                            " price, image) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(
                    sqlStatement, Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, coupon.getTitle());
            preparedStatement.setLong(2, coupon.getCompanyId());
            preparedStatement.setString(3, coupon.getStartDate().toString());
            preparedStatement.setString(4, coupon.getEndDate().toString());
            preparedStatement.setInt(5, coupon.getAmount());
            preparedStatement.setString(6, coupon.getCategory().toString());
            preparedStatement.setString(7, coupon.getDescription());
            preparedStatement.setDouble(8, coupon.getPrice());
            preparedStatement.setString(9, coupon.getImage());
            preparedStatement.executeUpdate();
            final ResultSet generatedKeysResult = preparedStatement.getGeneratedKeys();

            if (!generatedKeysResult.next()) {
                throw new RuntimeException("Failed to retrieve auto generated key");
            }

            return generatedKeysResult.getLong(1);
        } catch (final SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.CREATE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Coupon read(Long aLong) throws EntityCrudException {
        Connection connection = null;
        try {
            final String sqlStatement = "SELECT * FROM coupons WHERE id = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, aLong);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new RuntimeException("Failed to retrieve auto generated key");
            }
            return ObjectExtractionUtil.couponResult(resultSet);
        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void update(Coupon coupon) throws EntityCrudException {
        Connection connection = null;
        try {
            final String sqlStatement = "UPDATE coupons SET category = ?, title = ?, description = ?," +
                    " start_date = ?, end_date = ?, amount = ?, price = ?, image = ? WHERE id = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, coupon.getCategory().toString());
            preparedStatement.setString(2, coupon.getTitle());
            preparedStatement.setString(3, coupon.getDescription());
            preparedStatement.setString(4, coupon.getStartDate().toString());
            preparedStatement.setString(5, coupon.getEndDate().toString());
            preparedStatement.setInt(6, coupon.getAmount());
            preparedStatement.setDouble(7, coupon.getPrice());
            preparedStatement.setString(8, coupon.getImage());
            preparedStatement.executeUpdate();
        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.UPDATE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void delete(Long aLong) throws EntityCrudException {
        Connection connection = null;
        try {
            final String sqlStatement = "DELETE FROM coupons WHERE id = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, aLong);
            preparedStatement.executeUpdate();

        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.DELETE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Coupon> readAll() throws EntityCrudException {
        Connection connection = null;
        try {
            List<Coupon> couponList = new ArrayList<>();
            final String sqlStatement = "SELECT * FROM coupons";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement,
                    Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                couponList.add(ObjectExtractionUtil.couponResult(resultSet));
            }
            return couponList;
        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public List<Coupon> readCouponsByCompanyId(final long companyId) throws EntityCrudException {
        Connection connection = null;
        try {
            final String sqlStatement = "SELECT * FROM coupons WHERE company_id = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setLong(1, companyId);
            final ResultSet result = preparedStatement.executeQuery();

            final List<Coupon> coupons = new ArrayList<>();
            while (result.next()) {
                coupons.add(ObjectExtractionUtil.couponResult(result));
            }
            return coupons;
        } catch (final Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.CREATE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public List<Coupon> readCouponsByCategory(final Category category, final Long companyId) throws EntityCrudException {
        Connection connection = null;
        try {
            final String sqlStatement = "SELECT * FROM coupons WHERE category = ? AND company_id = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, category.toString());
            preparedStatement.setLong(2, companyId);
            final ResultSet result = preparedStatement.executeQuery();

            final List<Coupon> coupons = new ArrayList<>();
            while (result.next()) {
                coupons.add(ObjectExtractionUtil.couponResult(result));
            }
            return coupons;
        } catch (final Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.CREATE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public void addCouponPurchase(final Long customerID, final Long couponID) throws EntityCrudException {
        Connection connection = null;
        try {
            String sqlStatement = "INSERT INTO customer_to_coupon (customer_id, coupon_id) VALUES(?,?)";
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setLong(1, customerID);
            preparedStatement.setLong(2, couponID);
            preparedStatement.executeUpdate();


        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.CREATE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public void deleteCouponPurchase(final Long customerID) throws EntityCrudException {
        Connection connection = null;
        try {
            String sqlStatement = "DELETE FROM customer_to_coupon WHERE customer_id = ?";
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setLong(1, customerID);
            preparedStatement.executeUpdate();

        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.DELETE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }


    public void deleteCouponPurchaseById(final Long couponID) throws EntityCrudException {
        Connection connection = null;
        try {
            String sqlStatement = "DELETE FROM customer_to_coupon WHERE coupon_id = ?";
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setLong(1, couponID);
            preparedStatement.executeUpdate();

        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.DELETE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }


  public List<Long> readCouponsByCustomerId(final long customerId) throws EntityCrudException {
        Connection connection = null;
        try {
            final String sqlStatement = "SELECT coupon_id FROM customer_to_coupon WHERE customer_id = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setLong(1, customerId);
            final ResultSet result = preparedStatement.executeQuery();

            final List<Long> idList = new ArrayList<>();
            while (result.next()) {
                idList.add(result.getLong(1));
            }
            return idList;
        } catch (final Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.CREATE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }



}
