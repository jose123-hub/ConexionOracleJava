import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");


            Connection conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE",
                    "SYSTEM",
                    "infermihai"
            );

            System.out.println("✅ Conexión exitosa a Oracle Database 11g\n");

            String sql = """
                SELECT c.nombre AS cliente,
                       r.nombre_rutina AS rutina,
                       cr.fecha_inicio,
                       cr.fecha_final
                FROM SYSTEM.CLIENTE c
                JOIN SYSTEM.CLIENTE_RUTINA cr ON c.cod_cliente = cr.cod_cliente
                JOIN SYSTEM.RUTINA r ON r.cod_rutina = cr.cod_rutina
            """;

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("--- Clientes con sus rutinas ---");

            boolean hayDatos = false;
            while (rs.next()) {
                hayDatos = true;
                System.out.println(
                        "Cliente: " + rs.getString("cliente") +
                                " | Rutina: " + rs.getString("rutina") +
                                " | Inicio: " + rs.getDate("fecha_inicio") +
                                " | Fin: " + rs.getDate("fecha_final")
                );
            }

            if (!hayDatos) {
                System.out.println("⚠️ No se encontraron registros.");
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