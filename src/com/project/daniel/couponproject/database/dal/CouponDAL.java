package com.project.daniel.couponproject.database.dal;

import com.project.daniel.couponproject.database.connection.ConnectionPool;
import com.project.daniel.couponproject.enums.Category;
import com.project.daniel.couponproject.enums.CrudOperation;
import com.project.daniel.couponproject.enums.EntityType;
import com.project.daniel.couponproject.error.exceptions.EntityCrudException;

import com.project.daniel.couponproject.model.Coupon;
import com.project.daniel.couponproject.util.ObjectExtractionUtil;
import lombok.Data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Data

//-------------------------------------------Coupon-DAL-----------------------------------------------------------------
public class CouponDAL implements CrudDAL<Long, Coupon> {

    public static CouponDAL instance = new CouponDAL();
    private final ConnectionPool connectionPool;

    private CouponDAL() {
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while getting with the connection");
        }
    }

//-------------------------------------------Create-coupon--------------------------------------------------------------

    public Long create(final Coupon coupon) throws EntityCrudException {
        Connection connection = null;
        try {
            //Creating the SQL query
            final String sqlStatement =
                    "INSERT INTO coupons (title, company_id, start_date, end_date, amount, category, description," +
                            " price, image) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            connection = connectionPool.getConnection();
            //Combining between the syntax and our connection
            final PreparedStatement preparedStatement = connection.prepareStatement(
                    sqlStatement, Statement.RETURN_GENERATED_KEYS);
            //Replacing the question marks in the statement above with the relevant data
            preparedStatement.setString(1, coupon.getTitle());
            preparedStatement.setLong(2, coupon.getCompanyId());
            preparedStatement.setString(3, coupon.getStartDate().toString());
            preparedStatement.setString(4, coupon.getEndDate().toString());
            preparedStatement.setInt(5, coupon.getAmount());
            preparedStatement.setString(6, coupon.getCategory().toString());
            preparedStatement.setString(7, coupon.getDescription());
            preparedStatement.setDouble(8, coupon.getPrice());
            preparedStatement.setString(9, coupon.getImage());
            //Executing the update
            preparedStatement.executeUpdate();
            final ResultSet generatedKeysResult = preparedStatement.getGeneratedKeys();

            if (!generatedKeysResult.next()) {
                throw new RuntimeException("Failed to retrieve auto generated key");
            }
            // return generated key
            return generatedKeysResult.getLong(1);
        } catch (final SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.CREATE);
        } finally {
            //Return connection when finish the operation
            connectionPool.returnConnection(connection);
        }
    }
//--------------------------------------------read-coupon---------------------------------------------------------------

    @Override
    public Coupon read(final Long aLong) throws EntityCrudException {
        Connection connection = null;
        try {
            //Creating the SQL query
            final String sqlStatement = "SELECT * FROM coupons WHERE id = ?";
            //get a connection from the connection pool
            connection = connectionPool.getConnection();
            //Combining between the syntax and our connection
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, aLong);
            //Executing the query and saving the DB response in the resultSet.
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new RuntimeException("Failed to retrieve auto generated key");
            }
            //Transfer from a relation to an object
            return ObjectExtractionUtil.couponResult(resultSet);
        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ);
        } finally {
            //Return connection when finish the operation
            connectionPool.returnConnection(connection);
        }
    }

//--------------------------------------------update-coupon-------------------------------------------------------------

    @Override
    public void update(final Coupon coupon) throws EntityCrudException {
        Connection connection = null;
        try {
            //Creating the SQL query
            final String sqlStatement = "UPDATE coupons SET category = ?, title = ?, description = ?," +
                    " start_date = ?, end_date = ?, amount = ?, price = ?, image = ? WHERE id = ?";

            //get a connection from the connection pool
            connection = connectionPool.getConnection();

            //Combining between the syntax and our connection
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement,
                    Statement.RETURN_GENERATED_KEYS);

            //Replacing the question marks in the statement above with the relevant data
            preparedStatement.setString(1, coupon.getCategory().toString());
            preparedStatement.setString(2, coupon.getTitle());
            preparedStatement.setString(3, coupon.getDescription());
            preparedStatement.setString(4, coupon.getStartDate().toString());
            preparedStatement.setString(5, coupon.getEndDate().toString());
            preparedStatement.setInt(6, coupon.getAmount());
            preparedStatement.setDouble(7, coupon.getPrice());
            preparedStatement.setString(8, coupon.getImage());
            preparedStatement.setLong(9, coupon.getId());

            //Executing the update
            preparedStatement.executeUpdate();
        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.UPDATE);
        } finally {
            //Return connection when finish the operation
            connectionPool.returnConnection(connection);
        }
    }

    //--------------------------------------------delete-coupon-------------------------------------------------------------
    @Override
    public void delete(final Long aLong) throws EntityCrudException {
        Connection connection = null;
        try {
            //Creating the SQL query
            final String sqlStatement = "DELETE FROM coupons WHERE id = ?";
            //get a connection from the connection pool
            connection = connectionPool.getConnection();
            //Combining between the syntax and our connection
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement,
                    Statement.RETURN_GENERATED_KEYS);
            //Replacing the question marks in the statement above with the relevant data
            preparedStatement.setLong(1, aLong);
            //Executing the update
            preparedStatement.executeUpdate();

        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.DELETE);
        } finally {
            //Return connection when finish the operation
            connectionPool.returnConnection(connection);
        }
    }

//--------------------------------------------read-all-coupon-----------------------------------------------------------

    @Override
    public List<Coupon> readAll() throws EntityCrudException {
        Connection connection = null;
        try {
            List<Coupon> couponList = new ArrayList<>();
            //Creating the SQL query
            final String sqlStatement = "SELECT * FROM coupons";
            //get a connection from the connection pool
            connection = connectionPool.getConnection();
            //Combining between the syntax and our connection
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement,
                    Statement.RETURN_GENERATED_KEYS);
            //Executing the query and saving the DB response in the resultSet.
            ResultSet resultSet = preparedStatement.executeQuery();

            //Transfer from a relation to an object
            while (resultSet.next()) {
                couponList.add(ObjectExtractionUtil.couponResult(resultSet));
            }
            //return list with all the coupons
            return couponList;
        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ);
        } finally {
            //Return connection when finish the operation
            connectionPool.returnConnection(connection);
        }
    }

//---------------------------------------------read-coupon-by-customer-id-----------------------------------------------

    public List<Coupon> readCouponsByCompanyId(final long companyId) throws EntityCrudException {
        Connection connection = null;
        try {
            //Creating the SQL query
            final String sqlStatement = "SELECT * FROM coupons WHERE company_id = ?";
            //get a connection from the connection pool
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            //Replacing the question marks in the statement above with the relevant data
            preparedStatement.setLong(1, companyId);
            //Executing the query and saving the DB response in the resultSet.
            final ResultSet result = preparedStatement.executeQuery();

            final List<Coupon> coupons = new ArrayList<>();
            while (result.next()) {
                coupons.add(ObjectExtractionUtil.couponResult(result));
            }
            return coupons;
        } catch (final Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.CREATE);
        } finally {
            //Return connection when finish the operation
            connectionPool.returnConnection(connection);
        }
    }

//--------------------------------------------read-coupon-by-category---------------------------------------------------

    public List<Coupon> readCouponsByCategory(final Category category, final Long companyId) throws EntityCrudException {
        Connection connection = null;
        try {
            //Creating the SQL query
            final String sqlStatement = "SELECT * FROM coupons WHERE category = ? AND company_id = ?";
            //get a connection from the connection pool
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            //Replacing the question marks in the statement above with the relevant data
            preparedStatement.setString(1, category.toString());
            preparedStatement.setLong(2, companyId);
            //Executing the query and saving the DB response in the resultSet.
            final ResultSet result = preparedStatement.executeQuery();

            final List<Coupon> coupons = new ArrayList<>();
            while (result.next()) {
                coupons.add(ObjectExtractionUtil.couponResult(result));
            }
            return coupons;
        } catch (final Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.CREATE);
        } finally {
            //Return connection when finish the operation
            connectionPool.returnConnection(connection);
        }
    }

    //-----------------------------------------add-coupon-purchase---------------------------------------------------------

    public void addCouponPurchase(final Long customerID, final Long couponID) throws EntityCrudException {
        Connection connection = null;
        try {
            //Creating the SQL query
            String sqlStatement = "INSERT INTO customer_vs_coupon (customer_id, coupon_id) VALUES(?,?)";
            //get a connection from the connection pool
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            //Replacing the question marks in the statement above with the relevant data
            preparedStatement.setLong(1, customerID);
            preparedStatement.setLong(2, couponID);
            //Executing the update
            preparedStatement.executeUpdate();


        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.CREATE);
        } finally {
            //Return connection when finish the operation
            connectionPool.returnConnection(connection);
        }
    }

    public void deleteCouponPurchase(final Long customerID) throws EntityCrudException {
        Connection connection = null;
        try {
            //Creating the SQL query
            String sqlStatement = "DELETE FROM customer_to_coupon WHERE customer_id = ?";
            //get a connection from the connection pool
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            //Replacing the question marks in the statement above with the relevant data
            preparedStatement.setLong(1, customerID);
            //Executing the update
            preparedStatement.executeUpdate();

        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.DELETE);
        } finally {
            //Return connection when finish the operation
            connectionPool.returnConnection(connection);
        }
    }


    public void deleteCouponPurchaseById(final Long couponID) throws EntityCrudException {
        Connection connection = null;
        try {
            //Creating the SQL query
            String sqlStatement = "DELETE FROM customer_vs_coupon WHERE coupon_id = ?";
            //get a connection from the connection pool
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            //Replacing the question marks in the statement above with the relevant data
            preparedStatement.setLong(1, couponID);
            //Executing the update
            preparedStatement.executeUpdate();

        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.DELETE);
        } finally {
            //Return connection when finish the operation
            connectionPool.returnConnection(connection);
        }
    }

    public void deleteCustomerPurchaseById(final Long customerID) throws EntityCrudException {
        Connection connection = null;
        try {
            //Creating the SQL query
            String sqlStatement = "DELETE FROM customer_vs_coupon WHERE customer_id = ?";
            //get a connection from the connection pool
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            //Replacing the question marks in the statement above with the relevant data
            preparedStatement.setLong(1, customerID);
            //Executing the update
            preparedStatement.executeUpdate();

        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.DELETE);
        } finally {
            //Return connection when finish the operation
            connectionPool.returnConnection(connection);
        }
    }


    public List<Long> readCouponsByCustomerId(final long customerId) throws EntityCrudException {
        Connection connection = null;
        try {
            //Creating the SQL query
            final String sqlStatement = "SELECT coupon_id FROM customer_vs_coupon WHERE customer_id = ?";
            //get a connection from the connection pool
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            //Replacing the question marks in the statement above with the relevant data
            preparedStatement.setLong(1, customerId);
            //Executing the query and saving the DB response in the resultSet.
            final ResultSet result = preparedStatement.executeQuery();

            final List<Long> idList = new ArrayList<>();
            while (result.next()) {
                idList.add(result.getLong(1));
            }

            return idList;

        } catch (final InterruptedException | SQLException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ);
        } finally {
            //Return connection when finish the operation
            connectionPool.returnConnection(connection);
        }
    }

    public Coupon readByTitle(String title) throws EntityCrudException {
        Connection connection = null;
        try {
            //Creating the SQL query
            final String sqlStatement = "SELECT * FROM coupons WHERE title = ?";
            //get a connection from the connection pool
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement,
                    Statement.RETURN_GENERATED_KEYS);
            //Replacing the question marks in the statement above with the relevant data
            preparedStatement.setString(1, title);
            //Executing the query and saving the DB response in the resultSet.
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new RuntimeException("Failed to retrieve auto generated key");
            }
            return ObjectExtractionUtil.couponResult(resultSet);
        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ);
        } finally {
            //Return connection when finish the operation
            connectionPool.returnConnection(connection);
        }
    }

    public void updateAmount(Coupon coupon) throws EntityCrudException {
        Connection connection = null;
        try {
            //Creating the SQL query
            final String sqlStatement = "UPDATE coupons SET amount = ? WHERE id = ?";
            //get a connection from the connection pool
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement,
                    Statement.RETURN_GENERATED_KEYS);
            //Replacing the question marks in the statement above with the relevant data
            preparedStatement.setInt(1, (coupon.getAmount() - 1));
            preparedStatement.setLong(2, coupon.getId());
            //Executing the update
            preparedStatement.executeUpdate();
        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.UPDATE);
        } finally {
            //Return connection when finish the operation
            connectionPool.returnConnection(connection);
        }
    }

    public List<Long> readCouponsByCouponId(final long couponId) throws EntityCrudException {
        Connection connection = null;
        try {
            //Creating the SQL query
            final String sqlStatement = "SELECT coupon_id FROM customer_vs_coupon WHERE coupon_id = ?";
            //get a connection from the connection pool
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            //Replacing the question marks in the statement above with the relevant data
            preparedStatement.setLong(1, couponId);
            //Executing the query and saving the DB response in the resultSet.
            final ResultSet result = preparedStatement.executeQuery();

            final List<Long> idList = new ArrayList<>();
            while (result.next()) {
                idList.add(result.getLong(1));
            }

            return idList;

        } catch (final InterruptedException | SQLException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ);
        } finally {
            //Return connection when finish the operation
            connectionPool.returnConnection(connection);
        }
    }

}
