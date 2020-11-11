package it.gestionearticoli.dao.categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeSet;

import it.gestionearticoli.dao.AbstractMySQLDAO;
import it.gestionearticoli.model.Articolo;
import it.gestionearticoli.model.Categoria;

public class CategoriaDAOImpl extends AbstractMySQLDAO implements CategoriaDAO {

	@Override
	public TreeSet<Categoria> list() throws Exception {
		if(isNotActive()) {
			return null;
		}
		String query="SELECT * FROM categoria";
		TreeSet<Categoria> result=new TreeSet<>();
		try (Statement statement=connection.createStatement()) {
			ResultSet resultSet=statement.executeQuery(query);
			Categoria categoriaTemp;
			while (resultSet.next()) {
				categoriaTemp=new Categoria();
				categoriaTemp.setIdCategoria(resultSet.getLong("id_categoria"));
				categoriaTemp.setNomeCategoria(resultSet.getString("nome_categoria"));
				result.add(categoriaTemp);
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;		
	}

	@Override
	public Categoria get(Long idCategoria) throws Exception {
		if (isNotActive()||idCategoria==null) {
			return null;
		} else {
			Categoria result=new Categoria();
			String query="SELECT * FROM categoria WHERE id_categoria=?";
			try (PreparedStatement preparedStatement=connection.prepareStatement(query)) {
				preparedStatement.setLong(1,idCategoria);
				ResultSet resultSet=preparedStatement.executeQuery();
				if (resultSet.next()) {
					result.setIdCategoria(resultSet.getLong("id_categoria"));
					result.setNomeCategoria(resultSet.getString("nome_categoria"));
				} else {
					result=null;
				}
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			} catch(Exception e) {
				e.printStackTrace();
				throw e;
			}
			return result;			
		}
	}

	@Override
	public int update(Categoria input) throws Exception {
		if (isNotActive()||input==null) {
			return -1;
		} else {
			int result=0;
			String query="UPDATE categoria SET nome_categoria=? WHERE id_categoria=?";
			try (PreparedStatement preparedStatement=connection.prepareStatement(query)) {
				preparedStatement.setString(1,input.getNomeCategoria());
				preparedStatement.setLong(2,input.getIdCategoria());
				result= preparedStatement.executeUpdate();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
			return result;
		}
	}

	@Override
	public Long insert(Categoria input) throws Exception {
		if (isNotActive() || input == null) {
			return -1L;
		}
		Long result = 0L;
		try (PreparedStatement ps = connection.prepareStatement("INSERT INTO categoria (nome_categoria) "
				+ "VALUES (?)",Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, input.getNomeCategoria());
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();
			if (resultSet.next()) {
				result=resultSet.getLong(1);
				input.setIdCategoria(result);
			} else {
				System.err.println("Generated Keys non trovate per insertCategoria");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int delete(Categoria input) throws Exception {
		if (isNotActive()||input==null) {
			return -1;
		} else {
			int result=0;
			String query="DELETE FROM categoria WHERE id_categoria=?";
			try (PreparedStatement preparedStatement=connection.prepareStatement(query)) {
				preparedStatement.setLong(1,input.getIdCategoria());
				result= preparedStatement.executeUpdate();
			} catch(SQLException e) {
				e.printStackTrace();
			}
			return result;
		}
	}
	
	public boolean synchroElencoArticoli(Categoria input) throws Exception {
		if (isNotActive()||input==null) {
			return false;
		} else {
			boolean esito=false;
			Categoria categoriaTemp=null;
			try { 
				categoriaTemp=this.get(input.getIdCategoria());
				if (categoriaTemp!=null) {
					esito=true;
					String query="SELECT * FROM articolo WHERE CATEGORIA_FK=?";
					try(PreparedStatement preparedStatement=connection.prepareStatement(query)) {					
						preparedStatement.setLong(1,input.getIdCategoria());
						ResultSet resultSet=preparedStatement.executeQuery();
						Articolo articolo=null;
						while (resultSet.next()) {
							articolo=new Articolo();
							articolo.setId(resultSet.getLong("ID"));
							articolo.setCodice(resultSet.getString("CODICE"));
							articolo.setDescrizione(resultSet.getString("DESCRIZIONE"));
							articolo.setPrezzo(resultSet.getInt("PREZZO"));
							articolo.setCategoriaDiAfferenza(this.get(resultSet.getLong("CATEGORIA_FK")));
							input.incrementaElencoArticoli(articolo);
						}
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}					
				}
			} catch (Exception e) {
				System.out.println("La categoria in input non è presente nel database!");
				throw new NullPointerException();
			}
			return esito;
		}
	}

	@Override
	public TreeSet<Categoria> searchCategoria(TreeSet<String> input) throws Exception {
		if (isNotActive()) { //ho già controllato lato servlet se l'input è null; 
			return null;
		}
		TreeSet<Categoria> result=null;
		String secondoPezzoQuery="";
		for (String s:input) {
			 secondoPezzoQuery+="'%"+s+"%' AND nome_categoria LIKE";
		}
		secondoPezzoQuery=secondoPezzoQuery.substring(0,secondoPezzoQuery.length()-23); //devo togliere l'"and nome_categoria LIKE" alla fine
		String query="SELECT * FROM categoria WHERE nome_categoria LIKE "+secondoPezzoQuery;
		System.out.println(query);
		try(Statement statement=connection.createStatement()) {
			result=new TreeSet<>();
			ResultSet resultSet=statement.executeQuery(query);
			Categoria categoriaTemp=null;
			while (resultSet.next()) {
				categoriaTemp=new Categoria();
				categoriaTemp.setIdCategoria(resultSet.getLong("id_categoria"));
				categoriaTemp.setNomeCategoria(resultSet.getString("nome_categoria"));
				result.add(categoriaTemp);
			}
		}
		return result;
	}

	@Override
	public void setConnection(Connection connection) {
		this.connection=connection;
	}

	@Override
	public Connection getConnection() {
		return connection;
	}
	
}
