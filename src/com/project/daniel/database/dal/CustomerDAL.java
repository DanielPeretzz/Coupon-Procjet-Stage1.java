package com.project.daniel.database.dal;

import com.project.daniel.database.connection.ConnectionPool;
import com.project.daniel.database.connection.JDBCUtil;
import com.project.daniel.enums.CrudOperation;
import com.project.daniel.enums.EntityType;
import com.project.daniel.error.exceptions.EntityCrudException;
import com.project.daniel.logging.Logger;
import com.project.daniel.model.Customer;
import com.project.daniel.util.ObjectExtractionUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data

public class CustomerDAL extends UserDAL<Long, Customer> {
    public static CustomerDAL instance = new CustomerDAL();
    private final ConnectionPool connectionPool;
    private static final Logger logger = Logger.getLogger(CustomerDAL.class);

    private CustomerDAL() {
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while getting with the connection");
        }
    }



    @Override
    public Long create(Customer customer) throws EntityCrudException {
        Connection connection = null;
        try {
            final String sql = "INSERT INTO customers (first_name, last_name, email, password) VALUES(?, ?, ?, ?)";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, String.valueOf(customer.getPassword().hashCode()));
            preparedStatement.executeUpdate();
            final ResultSet generatedKeysResult = preparedStatement.getGeneratedKeys();

            if (!generatedKeysResult.next()){
                throw new RuntimeException("No results");
            }
            return generatedKeysResult.getLong(1);
        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.CUSTOMER ,CrudOperation.CREATE);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Customer read(Long aLong) throws EntityCrudException {
        Connection connection = null;
        try {
            final String sqlStatement = "SELECT * FROM customers WHERE id = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1,aLong);
            ResultSet result = preparedStatement.executeQuery();

            if (!result.next()){
                return null;
            }

            return ObjectExtractionUtil.customerResult(result);
        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.CUSTOMER ,CrudOperation.READ);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void update(Customer customer) throws EntityCrudException {
        Connection connection = null;
        try {
            final String sqlStatement = "UPDATE customers SET first_name = ?, last_name = ?, email = ?, password = ? WHERE id = ?";
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, String.valueOf(customer.getPassword().hashCode()));
            preparedStatement.setLong(4,customer.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.CUSTOMER ,CrudOperation.UPDATE);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void delete(Long aLong) throws EntityCrudException {
        Connection connection = null;
        try {
            final String sqlStatement = "DELETE FROM customers WHERE id = ?";
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1,aLong);
            preparedStatement.executeUpdate();
        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.CUSTOMER ,CrudOperation.DELETE);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Customer> readAll() throws EntityCrudException {
        Connection connection = null;
        try {
            List<Customer> customerList = new ArrayList<>();
            String sqlStatement = "SELECT * FROM customers";
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                customerList.add(ObjectExtractionUtil.customerResult(resultSet));
            }
            return customerList;
        }catch (SQLException | InterruptedException e){
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.READ);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Customer readByEmail(String email) throws EntityCrudException {
        Connection connection = null;
        try {
            final String sqlStatement = "SELECT * FROM customers WHERE email = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, email);
            ResultSet result = preparedStatement.executeQuery();

            if (!result.next()) {
                return null;
            }

            return ObjectExtractionUtil.customerResult(result);
        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.CUSTOMER , CrudOperation.READ);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Customer readById(Long id) throws EntityCrudException {
        Connection connection = null;
        try {
            final String sqlStatement = "SELECT * FROM customers WHERE id = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, id);
            ResultSet result = preparedStatement.executeQuery();

            if (!result.next()) {
                return null;
            }

            return ObjectExtractionUtil.customerResult(result);
        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.CUSTOMER , CrudOperation.READ);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Customer readByName(String name) throws EntityCrudException {
        Connection connection = null;
        try {
            final String sqlStatement = "SELECT * FROM customers WHERE name = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            ResultSet result = preparedStatement.executeQuery();

            if (!result.next()) {
                return null;
            }

            return ObjectExtractionUtil.customerResult(result);
        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.CUSTOMER , CrudOperation.READ);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
    }
}
