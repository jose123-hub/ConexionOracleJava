import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Cargar el driver de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Conexión a la base de datos
            Connection conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE",
                    "SYSTEM",          // Usuario
                    "infermihai"       // Contraseña
            );

            System.out.println("✅ Conexión exitosa a Oracle Database 11g\n");

            // Mostrar usuario conectado
            Statement stUser = conn.createStatement();
            ResultSet rsUser = stUser.executeQuery("SELECT USER FROM dual");
            if (rsUser.next()) {
                System.out.println("👤 Usuario conectado desde Java: " + rsUser.getString(1) + "\n");
            }
            rsUser.close();
            stUser.close();

            // Consulta SQL
            String sql = "SELECT * FROM SYSTEM.CLIENTE";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("--- Clientes con sus rutinas ---");

            boolean hayDatos = false;
            while (rs.next()) {
                hayDatos = true;
                System.out.println(
                        "CodCliente: " + rs.getInt("cod_cliente") +
                                " | Nombre: " + rs.getString("nombre") +
                                " | Edad: " + rs.getInt("edad") +
                                " | Peso: " + rs.getDouble("peso") +
                                " | Sexo: " + rs.getString("sexo") +
                                " | Altura: " + rs.getDouble("altura")
                );
            }

            if (!hayDatos) {
                System.out.println("⚠️ No se encontraron registros en SYSTEM.CLIENTE.");
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}