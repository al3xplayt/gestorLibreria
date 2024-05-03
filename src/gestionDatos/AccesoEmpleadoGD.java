package gestionDatos;

import java.io.ObjectInputFilter.Config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modelo.ConfigBD;
import modelo.Empleado;

public class AccesoEmpleadoGD {
	public static boolean consultarEmpleadosPorCodLibreria(int codigoLibreria)
			throws SQLException, ClassNotFoundException {
		Connection conexion = null;
		try {
			conexion = ConfigBD.conectarseABD();

			String codigoSQL = "SELECT * FROM Empleado WHERE codigo_libreria = ?";
			PreparedStatement sentenciaPreparada = conexion.prepareStatement(codigoSQL);

			sentenciaPreparada.setInt(1, codigoLibreria);
			ResultSet resultados = sentenciaPreparada.executeQuery();
			if (resultados.next())
				return true;
			else
				return false;
		} finally {
			ConfigBD.desconectar(conexion);
		}
	}
	public static Empleado consultarEmpleadoPorDni(String dni) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		Empleado emple = null;
		try {
			conexion = ConfigBD.conectarseABD();
			String codigoSQL = "SELECT Dni FROM Empleado where Dni = ?";
			PreparedStatement sentencia = conexion.prepareStatement(codigoSQL);
			sentencia.setString(1, dni);
			ResultSet resultados = sentencia.executeQuery();
			if(resultados.next()) {
				int codigoEmpleado = resultados.getInt("Codigo");
				String nombre = resultados.getString("Nombre");
//				String Dni = resultados.getString("Dni");
				int codLibreria  = resultados.getInt("codigo_libreria");
				emple = new Empleado(codigoEmpleado,codLibreria, nombre, dni);
			}
			return emple;
		} finally {
			ConfigBD.desconectar(conexion);
		}
	}
	public static void insertarEmpleado (Empleado empleado) throws ClassNotFoundException, SQLException {
			Connection conexion = null;
			System.out.println("MensajeV2");
			try {
				conexion = ConfigBD.conectarseABD();
				String codigoSQL = "INSERT INTO Empleado (Nombre, Dni, codigo_libreria) VALUES (?,?,?)";
				PreparedStatement sentencia = conexion.prepareStatement(codigoSQL);
				String nombre = empleado.getNombre();
				String dni = empleado.getDni();
				int codLibrerira = empleado.getCodigoLibreria();
				sentencia.setString(1, nombre);
				sentencia.setString(2, dni);
				sentencia.setInt(3, codLibrerira);
				System.out.println("MensajeV1");
				sentencia.executeUpdate();
				System.out.println("MensajeV0");
			} finally {
				ConfigBD.desconectar(conexion);
				System.out.println("mensajeV3");
			}
	}
	public static boolean eliminarEmpleadoPorCodigo (int codigo) throws ClassNotFoundException, SQLException {
		int numFilas = 0;
		Connection conexion = null;
		try {
			conexion = ConfigBD.conectarseABD();
			String sentenciaSQL = "DELETE FROM Empleado WHERE Codigo = ? ";
			PreparedStatement sentenciaPreparada = conexion.prepareStatement(sentenciaSQL);
			sentenciaPreparada.setInt(1, codigo);
			numFilas = sentenciaPreparada.executeUpdate();
		} finally {
			ConfigBD.desconectar(conexion);
		}
		
		return numFilas != 0;
	}
}
