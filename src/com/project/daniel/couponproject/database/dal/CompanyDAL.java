package com.project.daniel.couponproject.database.dal;

import com.project.daniel.couponproject.database.connection.ConnectionPool;
import com.project.daniel.couponproject.enums.CrudOperation;
import com.project.daniel.couponproject.enums.EntityType;
import com.project.daniel.couponproject.error.exceptions.EntityCrudException;

import com.project.daniel.couponproject.model.Company;
import com.project.daniel.couponproject.util.ObjectExtractionUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data

//-------------------------------------------Company-DAL----------------------------------------------------------------

public class CompanyDAL extends UserDAL<Long, Company> {
    public static CompanyDAL instance = new CompanyDAL();
    private final ConnectionPool connectionPool;

    private CompanyDAL() {
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while getting with the connection");
        }
    }


    //--------------------------------------------create-company------------------------------------------------------------
    @Override
    public Long create(final Company company) throws EntityCrudException {
        Connection connection = null;
        try {
            //create SQL query
            String sql = "INSERT INTO companies (name, email, password) VALUES(?, ?, ?)";
            //get a connection from the connection pool
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, company.getName());
            preparedStatement.setString(2, company.getEmail());
            preparedStatement.setString(3, String.valueOf(company.getPassword().hashCode()));
            //Executing the update
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (!resultSet.next()) {
                throw new RuntimeException("Failed to retrieve auto-incremented id");
            }
            return resultSet.getLong(1);

        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.CREATE);
        } finally {
            //Return connection when finish the operation
            connectionPool.returnConnection(connection);
        }
    }


    //-------------------------------------------read-company--------------------------------------------------------------
    @Override
    public Company read(final Long aLong) throws EntityCrudException {
        Connection connection = null;
        try {
            //create SQL query
            String sqlStatement = "SELECT * FROM companies WHERE id = ?";
            //get a connection from the connection pool
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, aLong);
            //Executing the query and saving the DB response in the resultSet.
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }
            //Transfer from a relation to an object
            return ObjectExtractionUtil.companyResult(resultSet);
        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.READ);
        } finally {
            //Return connection when finish the operation
            connectionPool.returnConnection(connection);
        }
    }

    //-------------------------------------------update-company-------------------------------------------------------------
    @Override
    public void update(final Company company) throws EntityCrudException {
        Connection connection = null;
        try {
            //Create SQL query
            String sqlStatement = "UPDATE companies SET email = ?, password = ? WHERE id = ?";
            //Get a connection from the connection pool
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, company.getEmail());
            preparedStatement.setString(2, String.valueOf(company.getPassword().hashCode()));
            preparedStatement.setLong(3, company.getId());
            //Executing the update
            preparedStatement.executeUpdate();

        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.UPDATE);
        } finally {
            //Return connection when finish the operation
            connectionPool.returnConnection(connection);
        }
    }

    //-------------------------------------------delete-company-------------------------------------------------------------
    @Override
    public void delete(final Long aLong) throws EntityCrudException {
        Connection connection = null;
        try {
            //Create SQL query
            String sqlStatement = "DELETE FROM companies WHERE id = ?";
            //Get a connection from the connection pool
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, aLong);
            //Executing the update
            preparedStatement.executeUpdate();
        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.DELETE);
        } finally {
            //Return connection when finish the operation
            connectionPool.returnConnection(connection);
        }
    }

    //-------------------------------------------read-all-company-----------------------------------------------------------
    @Override
    public List<Company> readAll() throws EntityCrudException {
        Connection connection = null;
        try {
            //Create List for return type
            List<Company> companyList = new ArrayList<>();
            //Create SQL query
            String sqlStatement = "SELECT * FROM companies";
            //Get a connection from the connection pool
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            //Executing the query and saving the DB response in the resultSet.
            ResultSet resultSet = preparedStatement.executeQuery();

            //Transfer from a relation to an object
            while (resultSet.next()) {
                companyList.add(ObjectExtractionUtil.companyResult(resultSet));
            }
            //return list with all company object
            return companyList;
        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.READ);
        } finally {
            //Return connection when finish the operation
            connectionPool.returnConnection(connection);
        }
    }

    //------------------------------------------read-company-by-email-------------------------------------------------------
    @Override
    public Company readByEmail(final String email) throws EntityCrudException {
        Connection connection = null;
        try {
            //Create SQL query
            final String sqlStatement = "SELECT * FROM companies WHERE email = ?";
            //Get a connection from the connection pool
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, email);

            //Executing the query and saving the DB response in the resultSet.
            final ResultSet result = preparedStatement.executeQuery();

            //check if you don't have next resulted & return null
            if (!result.next()) {
                return null;
            }
            //Transfer from a relation to an object
            return ObjectExtractionUtil.companyResult(result);
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.READ);
        } finally {
            //Return connection when finish the operation
            connectionPool.returnConnection(connection);
        }
    }

//------------------------------------------read-company-by-id----------------------------------------------------------

    public Company readById(final Long id) throws EntityCrudException {
        Connection connection = null;
        try {
            //Create SQL query
            final String sqlStatement = "SELECT * FROM companies WHERE id = ?";
            //Get a connection from the connection pool
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, id);
            //Executing the query and saving the DB response in the resultSet.
            final ResultSet result = preparedStatement.executeQuery();

            if (!result.next()) {
                return null;
            }
            //Transfer from a relation to an object
            return ObjectExtractionUtil.companyResult(result);
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.READ);
        } finally {
            //Return connection when finish the operation
            connectionPool.returnConnection(connection);
        }
    }

//------------------------------------------read-by-name----------------------------------------------------------------

    public Company readByName(final String companyName) throws EntityCrudException {
        Connection connection = null;
        try {
            //Create SQL query
            String sqlStatement = "SELECT * FROM companies WHERE name = ?";
            //Get a connection from the connection pool
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, companyName);
            ResultSet result = preparedStatement.executeQuery();

            if (!result.next()) {
                return null;
            }
            //Transfer from a relation to an object
            return ObjectExtractionUtil.companyResult(result);
        } catch (SQLException | InterruptedException e) {
            System.err.println(e.getMessage());
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.READ);
        } finally {
            //Return connection when finish the operation
            connectionPool.returnConnection(connection);
        }
    }
}
//----------------------------------------------------------------------------------------------------------------------