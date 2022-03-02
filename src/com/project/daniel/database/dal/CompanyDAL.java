package com.project.daniel.database.dal;

import com.project.daniel.database.connection.ConnectionPool;
import com.project.daniel.enums.CrudOperation;
import com.project.daniel.enums.EntityType;
import com.project.daniel.error.exceptions.EntityCrudException;
import com.project.daniel.logging.Logger;
import com.project.daniel.model.Company;
import com.project.daniel.util.ObjectExtractionUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data

public class CompanyDAL extends UserDAL<Long, Company> {
    public static CompanyDAL instance = new CompanyDAL();
    private final ConnectionPool connectionPool;
    private static final Logger logger = Logger.getLogger(CompanyDAL.class);

    private CompanyDAL() {
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while getting with the connection");
        }
    }


    @Override
    public Long create(Company company) throws EntityCrudException {
        Connection connection = null;
        try {
            String sql = "INSERT INTO companies (name, email, password) VALUES(?, ?, ?)";
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, company.getName());
            preparedStatement.setString(2, company.getEmail());
            preparedStatement.setString(3, String.valueOf(company.getPassword().hashCode()));
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (!resultSet.next()) {
                throw new RuntimeException("Failed to retrieve auto-incremented id");
            }
            return resultSet.getLong(1);

        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.CREATE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Company read(Long aLong) throws EntityCrudException {
        Connection connection = null;
        try {
            String sqlStatement = "SELECT * FROM companies WHERE id = ?";
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, aLong);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }
            return ObjectExtractionUtil.companyResult(resultSet);
        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.READ);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void update(Company company) throws EntityCrudException {
        Connection connection = null;
        try {
            String sqlStatement = "UPDATE companies SET email = ?, password = ? WHERE id = ?";
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, company.getEmail());
            preparedStatement.setString(2, String.valueOf(company.getPassword().hashCode()));
            preparedStatement.setLong(3, company.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.UPDATE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void delete(Long aLong) throws EntityCrudException {
        Connection connection = null;
        try {
            String sqlStatement = "DELETE FROM companies WHERE id = ?";
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, aLong);
            preparedStatement.executeUpdate();
        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.DELETE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Company> readAll() throws EntityCrudException {
        Connection connection = null;
        try {
            List<Company> companyList = new ArrayList<>();
            String sqlStatement = "SELECT * FROM companies";
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                companyList.add(ObjectExtractionUtil.companyResult(resultSet));
            }
            return companyList;
        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.READ);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }


    @Override
    public Company readByEmail(final String email) throws EntityCrudException {
        Connection connection = null;
        try {
            final String sqlStatement = "SELECT * FROM companies WHERE email = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, email);
            final ResultSet result = preparedStatement.executeQuery();

            if (!result.next()) {
                return null;
            }

            return ObjectExtractionUtil.companyResult(result);
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.READ);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public Company readById(final Long id) throws EntityCrudException {
        Connection connection = null;
        try {
            final String sqlStatement = "SELECT * FROM companies WHERE id = ?";
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, id);
            final ResultSet result = preparedStatement.executeQuery();

            if (!result.next()) {
                return null;
            }

            return ObjectExtractionUtil.companyResult(result);
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.READ);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public Company readByName(final String companyName) throws EntityCrudException {
        Connection connection = null;
        try {
            String sqlStatement = "SELECT * FROM companies WHERE name = ?";
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, companyName);
            ResultSet result = preparedStatement.executeQuery();

            if (!result.next()) {
                return null;
            }
            return ObjectExtractionUtil.companyResult(result);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            throw new EntityCrudException(EntityType.COMPANY,CrudOperation.READ);
        }finally {
            connectionPool.returnConnection(connection);
        }
    }
}
