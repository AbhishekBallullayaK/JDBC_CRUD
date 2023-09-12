import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcCRUD {

    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/acabes_employees";
    private static final String USER = "root";
    private static final String PASSWORD = "abhi18@mysql";

    public static void main(String[] args) {
        try {
            // Create a connection to the database
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Create a new record (CREATE operation)
            createRecord(connection, "Arjun Das", 30000);

            // Read records (READ operation)
            System.out.println("Reading records:");
            readRecords(connection);

            // Update a record (UPDATE operation)
            updateRecord(connection, 1, "Akil ram", 35000);

            // Read records again to verify the update
            System.out.println("\nReading records after update:");
            readRecords(connection);

            // Delete a record (DELETE operation)
            deleteRecord(connection, 1);

            // Read records after delete
            System.out.println("\nReading records after delete:");
            readRecords(connection);

            // Close the connection
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Create a new record
    private static void createRecord(Connection connection, String emp_name, int emp_salary) throws SQLException {
        String sql = "INSERT INTO employees (emp_name, emp_salary) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, emp_name);
            preparedStatement.setInt(2, emp_salary);
            preparedStatement.executeUpdate();
            System.out.println("Record created successfully");
        }
    }

    // Read records
    private static void readRecords(Connection connection) throws SQLException {
        String sql = "SELECT * FROM employees";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("emp_id");
                String name = resultSet.getString("emp_name");
                int salary = resultSet.getInt("emp_salary");
                System.out.println("Emp ID: " + id + ", Emp Name: " + name + ", Emp Salary: " + salary);
            }
        }
    }

    // Update a record
    private static void updateRecord(Connection connection, int emp_id, String emp_name, int emp_salary) throws SQLException {
        String sql = "UPDATE employees SET emp_name=?, emp_salary=? WHERE emp_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, emp_name);
            preparedStatement.setInt(2, emp_salary);
            preparedStatement.setInt(3, emp_id);
            preparedStatement.executeUpdate();
            System.out.println("Record updated successfully");
        }
    }

    // Delete a record
    private static void deleteRecord(Connection connection, int emp_id) throws SQLException {
        String sql = "DELETE FROM employees WHERE emp_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, emp_id);
            preparedStatement.executeUpdate();
            System.out.println("Record deleted successfully");
        }
    }
}
