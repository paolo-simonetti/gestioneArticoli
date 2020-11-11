package it.gestionearticoli.dao.utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeSet;

import it.gestionearticoli.dao.AbstractMySQLDAO;
import it.gestionearticoli.model.Utente;

public class UtenteDAOImpl extends AbstractMySQLDAO implements UtenteDAO {

	@Override
	public Long insert(Utente input) throws Exception {
		if (isNotActive() || input == null) {
			return -1L;
		}
		Long result = 0L;
		try (PreparedStatement ps = connection.prepareStatement("INSERT INTO utente (nome,cognome,codice_fiscale,username,password,ruolo) "
				+ "VALUES (?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, input.getNome());
			ps.setString(2, input.getCognome());
			ps.setString(3, input.getCodiceFiscale());
			ps.setString(4, input.getUsername());
			ps.setString(5, input.getPassword());
			ps.setString(6, input.getRuolo().toString());
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys(); 
			if (resultSet.next()) {
				input.setIdUtente(resultSet.getLong(1));
				result=input.getIdUtente();				
			} else {
				System.err.println("Generated keys non trovate per insert utente");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int delete(Utente input) throws Exception {
		if (isNotActive()||input==null) {
			return -1;
		} else {
			int result=0;
			String query="DELETE FROM utente WHERE id_utente=?";
			try (PreparedStatement preparedStatement=connection.prepareStatement(query)) {
				preparedStatement.setLong(1,input.getIdUtente());
				result= preparedStatement.executeUpdate();
			} catch(SQLException e) {
				e.printStackTrace();
			}
			return result;
		}
	}

	@Override
	public TreeSet<Utente> list() throws Exception {
		if (isNotActive()) {
			return null;
		}

		TreeSet<Utente> result = new TreeSet<Utente>();
		Utente utenteTemp = null;
		Utente.Ruolo.conversioneRuolo.put("admin",Utente.Ruolo.ADMIN); /*conversioneRuolo è un TreeSet, quindi l'aggiunta di queste entry
																		ha effetto solo la prima volta che viene nvocato il metodo*/
		Utente.Ruolo.conversioneRuolo.put("op",Utente.Ruolo.OP);
		Utente.Ruolo.conversioneRuolo.put("guest",Utente.Ruolo.GUEST);
		try (Statement ps = connection.createStatement()) {
			ResultSet rs = ps.executeQuery("select * from utente");
			while (rs.next()) {
				utenteTemp = new Utente();
				utenteTemp.setIdUtente(rs.getLong("id_utente"));
				utenteTemp.setNome(rs.getString("nome"));
				utenteTemp.setCognome(rs.getString("cognome"));
				utenteTemp.setCodiceFiscale(rs.getString("codice_fiscale"));
				utenteTemp.setUsername(rs.getString("username"));
				utenteTemp.setPassword(rs.getString("password"));
				utenteTemp.setRuolo(Utente.Ruolo.conversioneRuolo.get(rs.getString("ruolo")));
				result.add(utenteTemp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;

	}

	@Override
	public Utente get(Long idUtente) throws Exception {
		if (isNotActive()||idUtente==null) {
			return null;
		} else {
			Utente result=new Utente();
			String query="SELECT * FROM utente WHERE id_utente=?";
			try (PreparedStatement preparedStatement=connection.prepareStatement(query)) {
				preparedStatement.setLong(1,idUtente);
				ResultSet resultSet=preparedStatement.executeQuery();
				if (resultSet.next()) {
					result.setIdUtente(resultSet.getLong("id_utente"));
					result.setNome(resultSet.getString("nome"));
					result.setCognome(resultSet.getString("cognome"));
					result.setCodiceFiscale(resultSet.getString("codice_fiscale"));
					result.setUsername(resultSet.getString("username"));
					result.setPassword(resultSet.getString("password"));
					result.setRuolo(Utente.Ruolo.conversioneRuolo.get(resultSet.getString("ruolo")));
				} else {
					result=null;
				}
			} catch(SQLException e) {
				e.printStackTrace();
			} catch(Exception e) {
				e.printStackTrace();
			}
			return result;			
		}

	}


	@Override
	public void setConnection(Connection connection) {
		this.connection=connection;
	}

	@Override
	public int update(Utente input) throws Exception {
		if (isNotActive()||input==null) {
			return -1;
		} else {
			int result=0;
			String query="UPDATE utente SET nome=?,cognome=?,codice_fiscale=?,username=?,password=?,ruolo=? WHERE id_utente=?";
			try (PreparedStatement preparedStatement=connection.prepareStatement(query)) {
				preparedStatement.setString(1,input.getNome());
				preparedStatement.setString(2,input.getCognome());
				preparedStatement.setString(3,input.getCodiceFiscale());
				preparedStatement.setString(4,input.getUsername());
				preparedStatement.setString(5,input.getPassword());
				preparedStatement.setString(6,input.getRuolo().toString());
				preparedStatement.setLong(7,input.getIdUtente());
				result= preparedStatement.executeUpdate();
			} catch(SQLException e) {
				e.printStackTrace();
			}
			return result;
		}
	}

	@Override
	public TreeSet<Utente> findByExample(Utente input) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
