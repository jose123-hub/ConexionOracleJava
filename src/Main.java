import oracle.jdbc.internal.OracleTypes;
import java.sql.*;

public class Main {
    public static void main(String[] args) {

        Connection conn = null;
        CallableStatement cs = null;
        ResultSet rsVista = null;
        ResultSet rsAlumno = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE",
                    "SYSTEM",
                    "infermihai123"
            );

            System.out.println("RESULTADOS DE LA VISTA: VW_NOTAS_ALUMNOS");

            String sqlVista = "SELECT * FROM VW_NOTAS_ALUMNOS";

            Statement stmt = conn.createStatement();
            rsVista = stmt.executeQuery(sqlVista);

            while (rsVista.next()) {
                System.out.println(
                        "Alumno: " + rsVista.getString("alumno") +
                                " | Curso: " + rsVista.getString("curso") +
                                " | Nota: " + rsVista.getDouble("nota")
                );
            }
            rsVista.close();
            stmt.close();

            System.out.println("\n------------------------------------------\n");

            System.out.println("RESULTADO DEL PROCEDIMIENTO: SP_LISTAR_ALUMNOS");

            cs = conn.prepareCall("{ call sp_listar_alumnos(?) }");
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            rsAlumno = (ResultSet) cs.getObject(1);

            while (rsAlumno.next()) {
                System.out.println(
                        "ID: " + rsAlumno.getInt("id_alumno") +
                                " | Nombre: " + rsAlumno.getString("nombres") +
                                " | Apellido: " + rsAlumno.getString("apellidos") +
                                " | Grado: " + rsAlumno.getString("grado")
                );
            }
            rsAlumno.close();
            cs.close();
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if(conn != null) conn.close(); } catch (Exception ex) {}
        }

    }
}